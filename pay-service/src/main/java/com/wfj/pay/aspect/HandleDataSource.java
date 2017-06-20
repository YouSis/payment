package com.wfj.pay.aspect;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ghost
 */
public class HandleDataSource {

    public static final String MASTER_DS = "master";
    public static final String SLAVE_DS = "slave";
    // 数据源名称线程池
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();

    public static void putDataSource(String datasource) {
        holder.set(datasource);
    }

    public static String getDataSource() {
        return holder.get();
    }

    public static boolean isEnableDataSource(String datasource){
        return MASTER_DS.equals(datasource) || SLAVE_DS.equals(datasource);
    }
}
