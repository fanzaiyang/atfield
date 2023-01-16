import axios from 'axios'
// @ts-ignore
import axiosExtra from 'axios-extra'
const baseUrl = 'http://localhost:8080'

const http = axios.create({
    baseURL: baseUrl
})

const httpExtra = axiosExtra.create({
    maxConcurrent: 5, //并发为1
    queueOptions: {
        retry: 3, //请求失败时,最多会重试3次
        retryIsJump: false //是否立即重试, 否则将在请求队列尾部插入重试请求
    }
})

http.interceptors.response.use(response => {
    return response.data
})


/**
 * 初始化一个分片上传任务
 * @param data data
 * @returns {Promise<AxiosResponse<any>>}
 */
const initTask = (data:any) => {
    return http.post('/upload/before', data)
}


/**
 * 合并分片
 * @param data
 * @returns {Promise<AxiosResponse<any>>}
 */
const merge = (identifier:string) => {
    return http.get(`/upload/merge?identifier=${identifier}`)
}

export {
    initTask,
    merge,
    httpExtra
}
