import axios from "axios"; 
import store from '../store/index'
import config from '../config/dev'
import router from '../router'
import otherUtil from '@/util/otherUtil'
//编写axios的配置对象
const request = axios.create({
    //基础路径
    baseURL: config.BASE_API_URL,
    //请求超时时间
    timeout: 5000,
    //带上凭证  这是一个设置，让JavaScript在向其他网站发送请求时，可以带上你的身份信息（比如登录凭证）。这样即使请求不同的网站，也能识别你是谁，方便你在多个关联网站之间无缝切换，不需要重复登录。
    //withCredentials: true

})
/*
    1.axios的拦截器
        1.1.请求拦截器：在发请求之前，请求拦截器可以检测到，可以在请求发出去之前做一些事情
        1.2.响应拦截器：在请求返回之后，响应拦截器可以检测到，可以在请求返回之后做一些事情
*/
request.interceptors.request.use(config => {
    //config：配置对象，对象里面有一个属性很重要，headers请求头
    if(store.getters.token){
        config.headers['Authorization']=store.getters.token
    }
    const a=otherUtil.signature(config)
    console.log("a2222222")
    console.log(a)
    //还可以设置语言和签名
    return config
})
request.interceptors.response.use(res => {
    //可以在这儿判断code,进行通用的错误提示
    //成功的回调函数
    console.log("4010")
    return res.data
}, err => {
    if(err.response.status===401){
        
        //跳转HelloWorld
        router.push({
            path:'/HelloWorld'
        })


    }else if(err.response.status===403){
        //跳转403界面
    }
    //失败的回调函数
    return Promise.reject(new Error('faile'))
})

export default request
