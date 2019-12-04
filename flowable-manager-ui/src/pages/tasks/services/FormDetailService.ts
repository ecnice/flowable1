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

export async function image(params: any): Promise<any> {
  return request(`/server/rest/formdetail/image`, {
    method: 'GET',
    params: params,
  });
}
