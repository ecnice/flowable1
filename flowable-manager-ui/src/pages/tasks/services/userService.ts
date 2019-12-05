import request from '@/utils/request';

export async function getUserList(params: any): Promise<any> {
  return request(`/server/rest/user/getUserList`, {
    method: 'GET',
    params: params,
  });
}
