import request from '@/utils/request';

export async function queryLeave(): Promise<any> {
  return request('/server/rest/leave/list');
}

export async function insertLeave(params): Promise<any> {
  // _spring_security_remember_me
  return request('/server/rest/leave/add', {
    method: 'POST',
    params: params,
  });
}

export async function updateLeave(params): Promise<any> {
  // _spring_security_remember_me
  return request('/server/rest/leave/update', {
    method: 'POST',
    params: params,
  });
}
