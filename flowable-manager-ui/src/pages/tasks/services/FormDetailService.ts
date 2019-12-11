import request from '@/utils/request';

export async function commentsByProcessInstanceId(params: any): Promise<any> {
  return request(`/server/rest/formdetail/commentsByProcessInstanceId`, {
    method: 'GET',
    params: params,
  });
}

export async function completeTask(params: any): Promise<any> {
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

export async function claimTask(params: any): Promise<any> {
  return request(`/server/rest/formdetail/claimTask`, {
    method: 'POST',
    params: params,
  });
}

export async function unClaimTask(params: any): Promise<any> {
  return request(`/server/rest/formdetail/unClaimTask`, {
    method: 'POST',
    params: params,
  });
}

export async function beforeAddSignTask(params: any): Promise<any> {
  return request(`/server/rest/formdetail/beforeAddSignTask`, {
    method: 'POST',
    params: params,
  });
}

export async function afterAddSignTask(params: any): Promise<any> {
  return request(`/server/rest/formdetail/afterAddSignTask`, {
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

export async function getBackStepList(params: any): Promise<any> {
  return request(
    `/server/rest/formdetail/getBackNodesByProcessInstanceId/${params.processInstanceId}/${params.taskId}`,
    {
      method: 'GET',
    },
  );
}

// 驳回
export async function doBackStep(params: any): Promise<any> {
  return request(`/server/rest/formdetail/doBackStep`, {
    method: 'POST',
    params: params,
  });
}
