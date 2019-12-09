package com.dragon.flow.service.leave;

import java.util.List;

import com.dragon.flow.model.leave.Purchase;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
/**
 * @author : admin
 * @date : 2019-12-09 10:00:54
 * description : 采购Service接口
 */
public interface IPurchaseService {

	/**
	 * 通过id得到采购Purchase
	 * @param id
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public Purchase getPurchaseById(String id) throws Exception;

	/**
	 * 通过ids批量得到采购Purchase
	 * @param ids 如："'1','2','3','4'..."
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public List<Purchase> getPurchaseByIds(String ids) throws Exception;

	/**
	 * 通过ids批量得到采购Purchase
	 * @param ids
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public List<Purchase> getPurchaseByIdsList(List<String> ids) throws Exception;

	/**
	 * 得到所有采购Purchase
	 * @param purchase
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public List<Purchase> getAll(Purchase purchase) throws Exception;

	/**
	 * 分页查询采购Purchase
	 * @param purchase
	 * @param query
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public PagerModel<Purchase> getPagerModelByQuery(Purchase purchase, Query query) throws Exception;

	/**
	 * 查询记录数
	 * @param purchase
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public int getByPageCount(Purchase purchase)throws Exception ;

	/**
	 * 添加采购Purchase
	 * @param purchase
	 * @throws Exception
	 * @Description:
	 */
	public void insertPurchase(Purchase purchase) throws Exception;

	/**
	 * 批量添加采购Purchase
	 * @param purchases
	 * @throws Exception
	 * @Description:
	 */
	public void insertPurchaseBatch(List<Purchase> purchases) throws Exception;

	/**
	 * 通过id删除采购Purchase
	 * @param id
	 * @throws Exception
	 * @Description:
	 */
	public void delPurchaseById(String id) throws Exception;

	/**
	 * 通过id批量删除采购Purchase
	 * @param ids 如："'1','2','3','4'..."
	 * @throws Exception
	 * @Description:
	 */
	public void delPurchaseByIds(String ids) throws Exception;

	/**
	 * 通过id批量删除采购Purchase
	 * @param ids
	 * @throws Exception
	 * @Description:
	 */
	public void delPurchaseByIdsList(List<String> ids) throws Exception;

	/**
	 * 通过id修改采购Purchase
	 * @param purchase
	 * @throws Exception
	 * @Description:
	 */
	public int updatePurchase(Purchase purchase) throws Exception;

	/**
	 * 通过ids批量修改采购Purchase
	 * @param ids 如："'1','2','3','4'..."
	 * @param purchase
	 * @throws Exception
	 * @Description:
	 */
	public int updatePurchaseByIds(String ids, Purchase purchase) throws Exception;

	/**
	 * 通过ids批量修改采购Purchase
	 * @param ids
	 * @param purchase
	 * @throws Exception
	 * @Description:
	 */
	public int updatePurchaseByIdsList(List<String> ids, Purchase purchase) throws Exception;

	/**
	 * 通过id批量修改采购Purchase
	 * @param purchases
	 * @throws Exception
	 * @Description:
	 */
	public int updatePurchaseList(List<Purchase> purchases) throws Exception;

	//------------api------------
}
