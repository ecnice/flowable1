import request from '@/utils/request';

export async function queryModules(): Promise<any> {
  return request('/server/rest/model/page-model');
}

export async function deployModules(params): Promise<any> {
  // _spring_security_remember_me
  return request('/server/rest/model/deploy', {
    method: 'POST',
    params: params,
  });
}

export async function addModel(params): Promise<any> {
  return request('/server/rest/model/addModel', {
    method: 'POST',
    params: params,
  });
}

export async function queryCurrent(): Promise<any> {
  return request('/server/app/rest/account');
}

export async function queryNotices(): Promise<any> {
  return request('/api/notices');
}
