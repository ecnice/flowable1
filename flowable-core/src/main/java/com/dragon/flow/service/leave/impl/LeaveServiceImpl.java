package com.dragon.flow.service.leave.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.dragon.flow.dao.leave.ILeaveDao;
import com.dragon.flow.model.leave.Leave;
import com.dragon.flow.service.leave.ILeaveService;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.utils.StringTools;
import com.github.pagehelper.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;

/**
 * @author : admin
 * @date : 2019-11-20 19:06:48
 * description : 请假单Service实现
 */
@Service
public class LeaveServiceImpl implements ILeaveService {

	@Autowired
	private ILeaveDao LeaveDao;

	@Override
	public Leave getLeaveById(String id) throws Exception {
		return StringUtils.isNotBlank(id) ? this.LeaveDao.getLeaveById(id.trim()) : null;
	}

	@Override
	public List<Leave> getLeaveByIds(String ids) throws Exception {
		ids = StringTools.converString(ids);
		return StringUtils.isNotBlank(ids) ? this.LeaveDao.getLeaveByIds(ids) : null;
	}

	@Override
	public List<Leave> getLeaveByIdsList(List<String> ids) throws Exception {
		return CollectionUtils.isNotEmpty(ids) ? this.LeaveDao.getLeaveByIdsList(ids) : null;
	}

	@Override
	public List<Leave> getAll(Leave Leave) throws Exception {
		return null != Leave ? this.LeaveDao.getAll(Leave) : null;
	}

	@Override
	public PagerModel<Leave> getPagerModelByQuery(Leave Leave, Query query)
			throws Exception {
		PageHelper.startPage(query.getPageNum(),query.getPageSize());
		Page<Leave> page = this.LeaveDao.getPagerModelByQuery(Leave);
		return new PagerModel<>(page);
	}

	@Override
	public int getByPageCount(Leave Leave)throws Exception {
		return (null != Leave) ? this.LeaveDao.getByPageCount(Leave) : 0;
	}

	@Override
	public Leave insertLeave(Leave Leave) throws Exception {
		if (StringUtils.isBlank(Leave.getId())){
			Leave.setId(UUID.randomUUID().toString().replace("-",""));
		}
		Leave.setCreateTime(new Date());
		Leave.setUpdateTime(new Date());
		this.LeaveDao.insertLeave(Leave);
		return Leave;
	}

	@Override
	public void insertLeaveBatch(List<Leave> Leaves) throws Exception {
		for (Leave Leave : Leaves) {
			if (StringUtils.isBlank(Leave.getId())){
				Leave.setId(UUID.randomUUID().toString().replace("-",""));
			}
			Leave.setCreateTime(new Date());
			Leave.setUpdateTime(new Date());
		}
		this.LeaveDao.insertLeaveBatch(Leaves);
	}

	@Override
	public void delLeaveById(String id) throws Exception {
		if (StringUtils.isNotBlank(id)) {
			this.LeaveDao.delLeaveById(id.trim());
		}
	}

	@Override
	public void delLeaveByIds(String ids) throws Exception {
		ids = StringTools.converString(ids);
		if(StringUtils.isNotBlank(ids)){
			this.LeaveDao.delLeaveByIds(ids);
		}
	}

	@Override
	public void delLeaveByIdsList(List<String> ids) throws Exception {
		if(CollectionUtils.isNotEmpty(ids)){
			this.LeaveDao.delLeaveByIdsList(ids);
		}
	}

	@Override
	public int updateLeave(Leave Leave) throws Exception {
		Leave.setUpdateTime(new Date());
		return this.LeaveDao.updateLeave(Leave);
	}

	@Override
	public int updateLeaveByIds(String ids,Leave Leave) throws Exception {
		Leave.setUpdateTime(new Date());
		return this.LeaveDao.updateLeaveByIds(StringTools.converString(ids), Leave);
	}

	@Override
	public int updateLeaveByIdsList(List<String> ids,Leave Leave) throws Exception {
		Leave.setUpdateTime(new Date());
		return this.LeaveDao.updateLeaveByIdsList(ids, Leave);
	}

	@Override
	public int updateLeaveList(List<Leave> Leaves) throws Exception {
		return this.LeaveDao.updateLeaveList(Leaves);
	}

	//------------api------------

}

