import request from '@/utils/request';

/**
 * 查询分页采购单列表
 * @param params
 */
export async function listPurchase(params: any): Promise<any> {
  return request('/server/rest/demo/purchase/list', {
    method: 'GET',
    params: params,
  });
}

/**
 * 添加采购单
 * @param params
 */
export async function addPurchase(params: any): Promise<any> {
  return request('/server/rest/demo/purchase/add', {
    method: 'POST',
    params: params,
  });
}

/**
 * 修改采购单
 * @param params
 */
export async function updatePurchase(params: any): Promise<any> {
  return request('/server/rest/demo/purchase/update', {
    method: 'POST',
    params: params,
  });
}

/**
 * 删除采购单
 * @param ids
 */
export async function delPurchase(params: any): Promise<any> {
  return request('/server/rest/demo/purchase/dels', {
    method: 'GET',
    params: {
      ids: params,
    },
  });
}
