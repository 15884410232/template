import request from "../util/request";
//编写axios的配置对象

/**
 * 登录接口
 * @param {*} data 
 * @returns 
 */
export function getMenu(data) {
  return request({
    url: "/account/getMenu",
    method: "post",
    data
  });
}

export function getPermission() {
  return request({
    url: "/account/getPermission",
    method: "post",
  });
}

export function addPermission(data) {
  return request({
    url: "/account/permission/add",
    method: "post",
    data:data
  });
}
