import request from '@/utils/request'

const api_name = '/educenter/member'

export default {
    // 用户登陆
    userLogin(userInfo) {
        return request({
            url: `${api_name}/login`,
            method: 'post',
            data: userInfo
        })
    }, 

    // 根据token获取用户信息
    getUserInfo() {
        return request({
            url: `${api_name}/getMemberInfo`,
            method: 'get'
        })
    }
}