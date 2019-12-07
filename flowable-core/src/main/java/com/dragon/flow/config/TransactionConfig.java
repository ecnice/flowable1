package com.dragon.flow.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * 事务配置
 *
 * @author wangzhiyong
 * @date 2018/6/25 11:07
 */
@Aspect
@Configuration
public class TransactionConfig {

    @Resource
    private DataSourceTransactionManager transactionManager;

    @Bean(name = "txAdvice")
    public TransactionInterceptor txAdvice() {
        Properties properties = new Properties();
        properties.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("insert*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("create*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("edit*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("del*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("drop*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("remove*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("import*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("active*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("stop*", "PROPAGATION_REQUIRED,-Exception");
        /*************************   flowable   **************************/
        properties.setProperty("start*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("stop*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("revoke*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("complete*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("turn*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("claim*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("unClaim*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("back*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("deploy*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("set*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("before*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("after*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("activate*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("suspend*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("sync*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("review*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("copy*", "PROPAGATION_REQUIRED,-Exception");

        /*************************   flowable   **************************/
        properties.setProperty("query*", "PROPAGATION_REQUIRED,-Exception,readOnly");
        properties.setProperty("find*", "PROPAGATION_REQUIRED,-Exception,readOnly");
        properties.setProperty("select*", "PROPAGATION_REQUIRED,-Exception,readOnly");
        properties.setProperty("get*", "PROPAGATION_REQUIRED,-Exception,readOnly");
        properties.setProperty("*", "PROPAGATION_REQUIRED,-Exception,readOnly");
        return new TransactionInterceptor(transactionManager, properties);
    }

    @Bean
    public BeanNameAutoProxyCreator txProxy() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setInterceptorNames("txAdvice");
        creator.setBeanNames("*Service", "*ServiceImpl","*generator");
        creator.setProxyTargetClass(true);
        return creator;
    }

}
