package com.dragon.flow.flowable.listener;

import com.dragon.tools.common.JsonUtils;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 调用业务系统接口的监听器
 * @Author: Bruce.liu
 * @Since:9:58 2018/9/27
 * 爱拼才会赢 2018 ~ 2030 版权所有
 */
public abstract class BusinessCallListener extends BaseListener {
    private static final long serialVersionUID = -5140234938739863473L;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    public void callBack(String pocessInstanceId, String restUrl, String params) {
        String paramsJson = null;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pocessInstanceId).singleResult();
            paramMap.put("businessKey", processInstance.getBusinessKey());
            this.setParams(params, paramMap);
            paramsJson = JsonUtils.toJson(paramMap);
            //执行dubbo方法
            logger.info("开始调用业务系统接口" + restUrl + ",业务参数:" + paramsJson);
            restTemplate.postForObject(restUrl, paramsJson, String.class);
        } catch (Exception e) {
            logger.error("调用业务系统的方法失败", e);
//            FlowBuesinessException fbe = new FlowBuesinessException(clazzName,
//                    method, version, paramsJson, e.getMessage());
//            this.createWfBuesinessException(fbe);
        }
    }

    //添加容错信息
//    private void createWfBuesinessException(FlowBuesinessException fbe) {
//        try {
//            flowBuesinessExceptionService.insertFlowBuesinessException(fbe);
//        } catch (Exception e) {
//            logger.error("创建调用异常出错", e);
//        }
//    }


}
