import axios from 'axios'
import config from '../config/dev'
import store from '../store/index'

// create an axios instance
const service = axios.create({
    baseURL: config.BASE_API_URL + '/api', // url = base url + request url
    withCredentials: true, // send cookies when cross-domain requests
    timeout: 1800000 // request timeout
})

// request interceptor
service.interceptors.request.use(
    config => {
        if (store.getters.token) {
            config.headers['token'] = store.getters.token
        }
        config.responseType = 'blob'
        return config
    },
    error => {
        // do something with request error
        console.log(error) // for debug
        return Promise.reject(error)
    }
)

// response interceptor
service.interceptors.response.use(
    /**
     * If you want to get http information such as headers or status
     * Please return  response => response
     */

    /**
     * Determine the request status by custom code
     * Here is just an example
     * You can also judge the status by HTTP Status Code
     */
    response => {
        return response
    },
    error => {
        console.log(error) // for debug
        NProgress.done()
        if (error.response.status === 401) {
            console.log('token过期')
        } else if (error.response.status === 403) {
            console.log('没有权限')
        } else {
            console.log('其他错误')
        }
        return Promise.reject(error)
    }
)

export default service