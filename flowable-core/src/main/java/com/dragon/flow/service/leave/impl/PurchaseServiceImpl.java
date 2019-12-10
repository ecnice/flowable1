package com.dragon.flow.service.leave.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.dragon.flow.dao.leave.IPurchaseDao;
import com.dragon.flow.model.leave.Purchase;
import com.dragon.flow.service.flowable.IFlowableProcessInstanceService;
import com.dragon.flow.service.leave.IPurchaseService;
import com.dragon.tools.utils.StringTools;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;





/**
 * @author : admin
 * @date : 2019-12-09 10:00:54
 * description : 采购Service实现
 */
@Service
public class PurchaseServiceImpl implements IPurchaseService {

	@Autowired
	private IPurchaseDao purchaseDao;
	@Autowired
	private IFlowableProcessInstanceService flowableProcessInstanceService;

	@Override
	public Purchase getPurchaseById(String id) throws Exception {
		return StringUtils.isNotBlank(id) ? this.purchaseDao.getPurchaseById(id.trim()) : null;
	}

	@Override
	public List<Purchase> getPurchaseByIds(String ids) throws Exception {
		ids = StringTools.converString(ids);
		return StringUtils.isNotBlank(ids) ? this.purchaseDao.getPurchaseByIds(ids) : null;
	}

	@Override
	public List<Purchase> getPurchaseByIdsList(List<String> ids) throws Exception {
		return CollectionUtils.isNotEmpty(ids) ? this.purchaseDao.getPurchaseByIdsList(ids) : null;
	}

	@Override
	public List<Purchase> getAll(Purchase purchase) throws Exception {
		return null != purchase ? this.purchaseDao.getAll(purchase) : null;
	}

	@Override
	public PagerModel<Purchase> getPagerModelByQuery(Purchase purchase, Query query)
			throws Exception {
		PageHelper.startPage(query.getPageIndex(),query.getPageSize());
		Page<Purchase> page = this.purchaseDao.getPagerModelByQuery(purchase);
		return new PagerModel<Purchase>(page);
	}

	@Override
	public int getByPageCount(Purchase purchase)throws Exception {
		return (null != purchase) ? this.purchaseDao.getByPageCount(purchase) : 0;
	}

	@Override
	public void insertPurchase(Purchase purchase) throws Exception {
		if (StringUtils.isBlank(purchase.getId())){
			purchase.setId(UUID.randomUUID().toString().replace("-",""));
		}
		purchase.setCreateTime(new Date());
		purchase.setUpdateTime(new Date());
		this.purchaseDao.insertPurchase(purchase);
	}

	@Override
	public void insertPurchaseBatch(List<Purchase> purchases) throws Exception {
		for (Purchase purchase : purchases) {
			if (StringUtils.isBlank(purchase.getId())){
				purchase.setId(UUID.randomUUID().toString().replace("-",""));
			}
			purchase.setCreateTime(new Date());
			purchase.setUpdateTime(new Date());
		}
		this.purchaseDao.insertPurchaseBatch(purchases);
	}

	@Override
	public void delPurchaseById(String id) throws Exception {
		if (StringUtils.isNotBlank(id)) {
			Purchase purchase = purchaseDao.getPurchaseById(id);
			this.purchaseDao.delPurchaseById(id.trim());
//			flowableProcessInstanceService.deleteProcessInstanceById(purchase.getProcessInstanceId());
		}
	}

	@Override
	public void delPurchaseByIds(String ids) throws Exception {
		ids = StringTools.converString(ids);
		if(StringUtils.isNotBlank(ids)){
			this.purchaseDao.delPurchaseByIds(ids);
		}
	}

	@Override
	public void delPurchaseByIdsList(List<String> ids) throws Exception {
		if(CollectionUtils.isNotEmpty(ids)){
			this.purchaseDao.delPurchaseByIdsList(ids);
		}
	}

	@Override
	public int updatePurchase(Purchase purchase) throws Exception {
		purchase.setUpdateTime(new Date());
		return this.purchaseDao.updatePurchase(purchase);
	}

	@Override
	public int updatePurchaseByIds(String ids,Purchase purchase) throws Exception {
		purchase.setUpdateTime(new Date());
		return this.purchaseDao.updatePurchaseByIds(StringTools.converString(ids), purchase);
	}

	@Override
	public int updatePurchaseByIdsList(List<String> ids,Purchase purchase) throws Exception {
		purchase.setUpdateTime(new Date());
		return this.purchaseDao.updatePurchaseByIdsList(ids, purchase);
	}

	@Override
	public int updatePurchaseList(List<Purchase> purchases) throws Exception {
		return this.purchaseDao.updatePurchaseList(purchases);
	}

	//------------api------------

}

