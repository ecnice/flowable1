import request from '@/utils/request';

export async function commentsByProcessInstanceId(params: any): Promise<any> {
  return request(`/server/rest/formdetail/commentsByProcessInstanceId`, {
    method: 'GET',
    params: params,
  });
}

export async function complete(params: any): Promise<any> {
  return request(`/server/rest/formdetail/complete`, {
    method: 'POST',
    params: params,
  });
}

export async function stopProcess(params: any): Promise<any> {
  return request(`/server/rest/formdetail/stopProcess`, {
    method: 'POST',
    params: params,
  });
}

export async function revokeProcess(params: any): Promise<any> {
  return request(`/server/rest/formdetail/revokeProcess`, {
    method: 'POST',
    params: params,
  });
}

export async function turnTask(params: any): Promise<any> {
  return request(`/server/rest/formdetail/turnTask`, {
    method: 'POST',
    params: params,
  });
}

export async function delegateTask(params: any): Promise<any> {
  return request(`/server/rest/formdetail/delegateTask`, {
    method: 'POST',
    params: params,
  });
}

export async function userList(params: any): Promise<any> {
  return request(`/server/rest/user/getPagerModel`, {
    method: 'POST',
    params: params,
  });
}

export async function image(params: any): Promise<any> {
  return request(`/server/rest/formdetail/image`, {
    method: 'GET',
    params: params,
  });
}
