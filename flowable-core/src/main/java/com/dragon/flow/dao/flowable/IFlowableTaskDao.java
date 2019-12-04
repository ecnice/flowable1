package com.dragon.flow.dao.flowable;

import com.dragon.flow.vo.flowable.TaskQueryVo;
import com.dragon.flow.vo.flowable.ret.TaskVo;
import com.dragon.flow.vo.flowable.ret.UserVo;
import com.dragon.tools.pager.PagerModel;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bruce.liu
 * @title: : IFlowableTaskDao
 * @projectName : flowable
 * @description: flowabletask的查询
 * @date : 2019/11/2316:34
 */
@Mapper
@Repository
public interface IFlowableTaskDao {
    /**
     * 查询待办任务
     * @param params 参数
     * @return
     */
    public Page<TaskVo> getApplyingTasks(TaskQueryVo params) ;

    /**
     * 查询已办任务列表
     * @param params 参数
     * @return
     */
    public Page<TaskVo> getApplyedTasks(TaskQueryVo params) ;

}
