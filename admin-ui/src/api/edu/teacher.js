import request from '@/utils/request'

const api_name = '/eduservice/admin'

export default {
  // 获取讲师列表：带条件的分页查询
  getPageList(page, limit, teacherQuery){
    return request({
      // url: '/edu/admin/'+page+'/'+limit,
      url: `${api_name}/pageTeacherCondition/${page}/${limit}`,
      method: 'post',
      // data 表示把对象转换为 JSon 数据格式传递到接口中
      data: teacherQuery
    })
  },
  // 添加讲师
  addTeacher(teacher) {
    return request({
      url: `${api_name}/addTeacher`,
      method: 'post',
      data: teacher
    })
  },
  // 根据ID删除讲师
  deleteTeacherById(id) {
    return request({
      url: `${api_name}/teacher/${id}`,
      method: 'delete'
    })
  },
  // 根据ID查询讲师
  getTeacherInfoById(id) {
    return request({
      url: `${api_name}/getTeacher/${id}`,
      method: 'get'
    })
  },
  // 修改讲师
  updateTeacherInfo(teacher){
    return request({
      url: `${api_name}/updateTeacher/${teacher.id}`,
      method: 'put',
      data: teacher
    })
  }
}
