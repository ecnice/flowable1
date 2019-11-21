package com.dragon.flow.dao.leave;

import java.util.List;

import com.dragon.flow.model.leave.Leave;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
 * @author : admin
 * @date : 2019-11-20 19:06:48
 * description : 请假单Dao接口
 */
@Mapper
@Repository
public interface ILeaveDao {

	/**
	 * 通过id得到请假单Leave
	 * @param id
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public Leave getLeaveById(String id) throws Exception;

	/**
	 * 通过ids批量得到请假单Leave
	 * @param ids 如："'1','2','3','4'..."
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public List<Leave> getLeaveByIds(@Param("ids") String ids) throws Exception;

	/**
	 * 通过ids批量得到请假单Leave
	 * @param ids
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public List<Leave> getLeaveByIdsList(List<String> ids) throws Exception;

	/**
	 * 得到所有请假单Leave
	 * @param Leave
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public List<Leave> getAll(Leave Leave) throws Exception;

	/**
	 * 分页查询请假单Leave
	 * @param Leave
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public Page<Leave> getPagerModelByQuery(Leave Leave) throws Exception;

	/**
	 * 查询记录数
	 * @param Leave
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public int getByPageCount(Leave Leave)throws Exception ;

	/**
	 * 添加请假单Leave
	 * @param Leave
	 * @throws Exception
	 * @Description:
	 */
	public void insertLeave(Leave Leave) throws Exception;

	/**
	 * 批量添加请假单Leave
	 * @param Leaves
	 * @throws Exception
	 * @Description:
	 */
	public void insertLeaveBatch(List<Leave> Leaves) throws Exception;

	/**
	 * 通过id删除请假单Leave
	 * @param id
	 * @throws Exception
	 * @Description:
	 */
	public void delLeaveById(String id) throws Exception;

	/**
	 * 通过id批量删除请假单Leave
	 * @param ids 如："'1','2','3','4'..."
	 * @throws Exception
	 * @Description:
	 */
	public void delLeaveByIds(@Param("ids") String ids) throws Exception;

	/**
	 * 通过id批量删除请假单Leave
	 * @param ids
	 * @throws Exception
	 * @Description:
	 */
	public void delLeaveByIdsList(List<String> ids) throws Exception;

	/**
	 * 通过id修改请假单Leave
	 * @param Leave
	 * @throws Exception
	 * @Description:
	 */
	public int updateLeave(Leave Leave) throws Exception;

	/**
	 * 通过ids批量修改请假单Leave
	 * @param ids 如："'1','2','3','4'..."
	 * @param Leave
	 * @throws Exception
	 * @Description:
	 */
	public int updateLeaveByIds(@Param("ids") String ids, @Param("Leave") Leave Leave) throws Exception;

	/**
	 * 通过ids批量修改请假单Leave
	 * @param ids
	 * @param Leave
	 * @throws Exception
	 * @Description:
	 */
	public int updateLeaveByIdsList(@Param("ids") List<String> ids, @Param("Leave") Leave Leave) throws Exception;

	/**
	 * 通过id批量修改请假单Leave
	 * @param Leaves
	 * @throws Exception
	 * @Description:
	 */
	public int updateLeaveList(List<Leave> Leaves) throws Exception;

	//------------api------------
}
