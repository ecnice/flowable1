import request from '@/utils/request';

export async function pageModel(params: any): Promise<any> {
  return request('/server/rest/definition/page-model', {
    method: 'POST',
    params: params,
  });
}
