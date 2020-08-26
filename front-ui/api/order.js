import request from '@/utils/request'

export default {
  // 根据课程id生成订单
  createOrder(courseId) {
    return request({
      url: `/eduorder/order/createOrder/${courseId}`,
      method: 'post'
    })
  }, 
  // 根据订单号查询订单信息
  getOrderInfo(orderNo) {
    return request({
      url: `/eduorder/order/getOrderInfo/${orderNo}`,
      method: 'get'
    })
  },
  // 根据订单号生成二维码
  createNative(orderNo) {
    return request({
      url: `/eduorder/paylog/createNative/${orderNo}`,
      method: 'get'
    })
  },
  // 查询订单支付状态
  queryOrderPayStatus(orderNo) {
    return request({
      url: `/eduorder/paylog/queryPayStatus/${orderNo}`,
      method: 'get'
    })
  }
}