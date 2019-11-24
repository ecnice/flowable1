import request from '@/utils/request';

export async function addComment(params: any): Promise<any> {
  return request(`/server/rest/comment/addComment`, {
    method: 'POST',
    params: params,
  });
}

export async function listByProcessInstanceId(params: any): Promise<any> {
  return request(`/server/rest/comment/listByProcessInstanceId`, {
    method: 'GET',
    params: params,
  });
}
