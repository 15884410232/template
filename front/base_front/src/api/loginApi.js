import request from "../util/request";
//编写axios的配置对象

/**
 * 登录接口
 * @param {*} data 
 * @returns 
 */
export function doLogin(data) {
  return request({
    url: "/account/doLogin",
    method: "post",
    data
  });
}

export function captcha() {
  return request({
    url: "/pass/captcha",
    method: "post",
  });
}

export function getUserList(data) {
  return request({
    url: "/test/getUserList",
    method: "post",
    data
  });
}