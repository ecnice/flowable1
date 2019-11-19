import request from '@/utils/request';

export interface LoginParamsType {
  userName: string;
  password: string;
  mobile: string;
  captcha: string;
}

export async function fakeAccountLogin(params: LoginParamsType) {
  // _spring_security_remember_me
  return request('/server/app/authentication', {
    requestType: 'form',
    method: 'POST',
    data: params,
  });
}

export async function doLogout() {
  return request('/server/app/logout', {
    method: 'POST',
  });
}

export async function getFakeCaptcha(mobile: string) {
  return request(`/api/login/captcha?mobile=${mobile}`);
}
