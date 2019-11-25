import request from '@/utils/request';

export async function commentsByProcessInstanceId(params: any): Promise<any> {
  return request(`/server/rest/formdetail/commentsByProcessInstanceId`, {
    method: 'GET',
    params: params,
  });
}
