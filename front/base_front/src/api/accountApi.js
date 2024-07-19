import request from "../util/request";
//编写axios的配置对象

/**
 * 登录接口
 * @param {*} data 
 * @returns 
 */
export function getMenu() {
  return request({
    url: "/account/getMenu",
    method: "post",
  });
}
