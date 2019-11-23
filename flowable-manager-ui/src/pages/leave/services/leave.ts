import request from '@/utils/request';

export async function queryLeave(params: any): Promise<any> {
  return request('/server/rest/leave/list', {
    method: 'GET',
    params: params
  });
}

export async function insertLeave(params: any): Promise<any> {
  // _spring_security_remember_me
  return request('/server/rest/leave/add', {
    method: 'POST',
    params: params,
  });
}

export async function updateLeave(params: any): Promise<any> {
  // _spring_security_remember_me
  return request('/server/rest/leave/update', {
    method: 'POST',
    params: params,
  });
}
