package com.dragon.flow.flowable.custommapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author : bruce.liu
 * @title: : CustomCommentMapper
 * @projectName : flowable
 * @description: 自定义sql查询
 * @date : 2019/12/239:47
 */
public interface CustomCommentMapper {

    @Select({ "select t.* from act_hi_comment t where t.PROC_INST_ID_ = #{procInstId}" })
    List<Map<String, Object>> selectCommentsByProcInstId(String procInstId);
}
