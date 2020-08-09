import request from '@/utils/request'

const api_name = '/eduservice/subject'

export default {
  //获取课程分类列表
  getSubjectList() {
    return request({
      url: '/eduservice/subject/getAllSubject',
      method: 'get'
    })
  }
}
