package com.wfj.pay.utils;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁实现
 */
public class DistributedLock implements Lock, Watcher {
    private ZooKeeper zk;
    private String root = "/pay_order";//根
    private String tempParentNode;
    private String lockName;//竞争资源的标志
    private String waitNode;//等待前一个锁
    private String tempNodeName;//当前锁
    private CountDownLatch latch;//计数器
    private int sessionTimeout = 30000;
    private List<Exception> exception = new ArrayList<Exception>();
    private static final Logger logger = LoggerFactory.getLogger(DistributedLock.class);

    /**
     * 创建分布式锁,使用前请确认config配置的zookeeper服务可用
     *
     * @param config   127.0.0.1:2181
     * @param lockName 竞争资源标志,lockName中不能包含单词lock
     */
    public DistributedLock(String config, String lockName) {
        this.lockName = lockName;
        // 创建一个与服务器的连接
        try {
            zk = new ZooKeeper(config, sessionTimeout, this);
            Stat stat = zk.exists(root, false);
            if (stat == null) {
                //创建根节点
                zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            Stat stat1 = zk.exists(root + "/" + lockName, false);
            if (stat1 == null) {
                //创建临时根节点
                try {
                    zk.create(root + "/" + lockName, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                } catch (Exception e) {
                    Stat stat2 = zk.exists(root + "/" + lockName, false);
                    if (stat2 == null) {
                        throw new RuntimeException(e);
                    }
                }

            }
            tempParentNode = root + "/" + lockName;
        } catch (IOException e) {
            exception.add(e);
        } catch (KeeperException e) {
            exception.add(e);
        } catch (InterruptedException e) {
            exception.add(e);
        }
    }

    /**
     * zookeeper节点的监视器
     */
    @Override
    public void process(WatchedEvent event) {
        logger.info("开始监听");

        if (event.getPath() != null) {
            if (event.getState() == KeeperState.SyncConnected) {
                if (EventType.NodeDeleted == event.getType()) {
                    if (event.getPath().equalsIgnoreCase(tempParentNode + "/" + waitNode)) {
                        if (this.latch != null) {
                            this.latch.countDown();
                        }
                    }
                }
            }
        }

    }

    public void lock() {
        if (exception.size() > 0) {
            throw new LockException(exception.get(0));
        }
        try {
            if (this.tryLock()) {
                logger.info("Thread " + Thread.currentThread().getId() + " " + tempNodeName + " get lock true");
                return;
            } else {
                waitForLock(waitNode, sessionTimeout);//等待锁
            }
        } catch (KeeperException e) {
            throw new LockException(e);
        } catch (InterruptedException e) {
            throw new LockException(e);
        }
    }

    public boolean tryLock() {
        try {
            String splitStr = "_lock_";
            if (lockName.contains(splitStr))
                throw new LockException("lockName can not contains \\u000B");

            //创建临时子节点
            tempNodeName = zk.create(tempParentNode + "/" + lockName + splitStr, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            logger.info(tempNodeName + " is created ");
            //取出所有子节点
            List<String> subNodes = zk.getChildren(tempParentNode, false);
            //取出所有lockName的锁
            Collections.sort(subNodes);
            if (tempNodeName.equals(tempParentNode + "/" + subNodes.get(0))) {
                //如果是最小的节点,则表示取得锁
                return true;
            }
            //如果不是最小的节点，找到比自己小1的节点
            String subZnode = tempNodeName.substring(tempNodeName.lastIndexOf("/") + 1);
            waitNode = subNodes.get(Collections.binarySearch(subNodes, subZnode) - 1);
        } catch (KeeperException e) {
            throw new LockException(e);
        } catch (InterruptedException e) {
            throw new LockException(e);
        }
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) {
        try {
            if (this.tryLock()) {
                return true;
            }
            return waitForLock(waitNode, time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean waitForLock(String lower, long waitTime) throws InterruptedException, KeeperException {
        Stat stat = zk.exists(tempParentNode + "/" + lower, true);
        //判断比自己小一个数的节点是否存在,如果不存在则无需等待锁,同时注册监听
        if (stat != null) {
            logger.info("Thread " + Thread.currentThread().getId() + " waiting for " + tempParentNode + "/" + lower);
            this.latch = new CountDownLatch(1);
            this.latch.await(waitTime, TimeUnit.MILLISECONDS);
            this.latch = null;
        }
        return true;
    }

    public void unlock() {
        try {
            logger.info("unlock " + tempNodeName);
            zk.delete(tempNodeName, -1);
            tempNodeName = null;
            List<String> subNodes = zk.getChildren(tempParentNode, false);
            if (subNodes.isEmpty()) {
                zk.delete(tempParentNode, -1);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void lockInterruptibly() throws InterruptedException {
        this.lock();
    }

    public Condition newCondition() {
        return null;
    }

    public class LockException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public LockException(String e) {
            super(e);
        }

        public LockException(Exception e) {
            super(e);
        }
    }
}
