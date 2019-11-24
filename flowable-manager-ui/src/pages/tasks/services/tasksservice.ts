import request from '@/utils/request';

export async function getApplyingTasks(params: any): Promise<any> {
  return request(`/server/rest/task/get-applying-tasks`, {
    method: 'GET',
    params: params,
  });
}
export async function getApplyedTasks(params: any): Promise<any> {
  return request(`/server/rest/task/get-applyed-tasks`, {
    method: 'GET',
    params: params,
  });
}
export async function getMyProcessInstances(params: any): Promise<any> {
  return request(`/server/rest/task/my-processInstances`, {
    method: 'GET',
    params: params,
  });
}
export async function getFormInfoForTask(params: any): Promise<any> {
  return request(`/server/rest/task/find-formInfo`, {
    method: 'POST',
    params: params,
  });
}
