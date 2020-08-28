import request from '@/utils/request'

const api_name = '/edustatistics/statistics'

export default {
  // 生成统计数据
  createStatisticsData(day) {
    return request({
      url: `${api_name}/registerCount/${day}`,
      method: 'post'
    })
  },

  // 获取图表统计数据
  getShowData(searchObj) {
    return request({
      url: `${api_name}/getShowData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
      method: 'get'
    })
  }
}