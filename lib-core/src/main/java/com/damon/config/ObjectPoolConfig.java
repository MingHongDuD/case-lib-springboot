package com.damon.config;

/**
 * 配置对象池相关配置类
 *
 * @author damon du/minghongdud
 */
public class ObjectPoolConfig {

//    /**
//     * 定义 slowServiceProxy 方法，创建代理 Bean，参数为对象池配置
//     *
//     * @param slowServicePool 对象池的 TargetSource，定义了池化对象的配置
//     * @return 返回一个 ProxyFactoryBean，代理池化的对象
//     */
//    @Bean
//    public ProxyFactoryBean slowServiceProxy(CommonsPool2TargetSource slowServicePool) {
//        // 用来生成代理对象
//        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
//        // 设置代理的目标为对象池，代理将从池中获取对象
//        proxyFactoryBean.setTargetSource(slowServicePool);
//        return proxyFactoryBean;
//    }
//
//    /**
//     * 创建对象池配置
//     */
//    @Bean
//    public CommonsPool2TargetSource slowServicePool() {
//        CommonsPool2TargetSource pool = new CommonsPool2TargetSource();
//        // 设置对象池最大大小为4，最多有4个对象实例
//        pool.setMaxSize(4);
//        // 设置目标 Bean 的名称为 "slowService"，池将管理名为 "slowService" 的原型 Bean
//        pool.setTargetBeanName("slowService");
//        return pool;
//    }
}
