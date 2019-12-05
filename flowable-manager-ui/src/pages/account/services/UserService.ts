import request from '@/utils/request';

export async function pageModel(params: any): Promise<any> {
  return request('/server/rest/user/getPagerModel', {
    method: 'GET',
    params: params,
  });
}

export async function saveUser(params: any): Promise<any> {
  return request('/server/rest/user/save', {
    method: 'POST',
    params: params,
  });
}

export async function deleteUser(params: any): Promise<any> {
  return request('/server/rest/user/delete', {
    method: 'GET',
    params: params,
  });
}

export async function addUserGroup(params: any): Promise<any> {
  return request('/server/rest/user/addUserGroup', {
    method: 'POST',
    params: params,
  });
}
