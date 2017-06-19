王府井全渠道支付平台重构项目
========
Maven：3.x.x
JDK：Oracle JDK 1.8


模块包括：
1. pay-common：公共组件、工具类等
2. pay-offline-gateway：线下支付网关服务
3. pay-schedule：支付调度服务
4. pay-service-interface：支付服务DUBBO接口
5. pay-service:支付主服务，包含所有的支付逻辑
6. pay-syn:完结订单同步ES服务