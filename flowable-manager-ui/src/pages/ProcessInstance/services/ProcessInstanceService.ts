import request from '@/utils/request';

export async function pageModel(params: any): Promise<any> {
  return request('/server/rest/processInstance/page-model', {
    method: 'POST',
    params: params,
  });
}

export async function deleteProcessInstanceById(params: any): Promise<any> {
  const url = '/server/rest/processInstance/deleteProcessInstanceById/' + params.processInstanceId;
  return request(url, {
    method: 'GET',
  });
}
