import request from '@/utils/request';

export async function pageModel(params: any): Promise<any> {
  return request('/server/rest/definition/page-model', {
    method: 'POST',
    params: params,
  });
}

export async function deleteDeployment(params: any): Promise<any> {
  return request('/server/rest/definition/deleteDeployment', {
    method: 'POST',
    params: params,
  });
}

export async function saDefinitionById(params: any): Promise<any> {
  return request('/server/rest/definition/saDefinitionById', {
    method: 'POST',
    params: params,
  });
}
