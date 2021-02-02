import request from '@/utils/request'

// 查询考试信息管理首页列表
export function listExam_index(query) {
  return request({
    url: '/exam/exam_index/list',
    method: 'get',
    params: query
  })
}

// 查询考试信息管理首页详细
export function getExam_index(indexId) {
  return request({
    url: '/exam/exam_index/' + indexId,
    method: 'get'
  })
}

// // 根据考试信息列表ID查询考试信息
// export function getExamById(indexId) {
//   return request({
//     url: '/exam/exam_manage/' + indexId,
//     method: 'get'
//   })
// }

// 新增考试信息管理首页
export function addExam_index(data) {
  return request({
    url: '/exam/exam_index',
    method: 'post',
    data: data
  })
}

// 修改考试信息管理首页
export function updateExam_index(data) {
  return request({
    url: '/exam/exam_index',
    method: 'put',
    data: data
  })
}

// 删除考试信息管理首页
export function delExam_index(indexId) {
  return request({
    url: '/exam/exam_index/' + indexId,
    method: 'delete'
  })
}

// 导出考试信息管理首页
export function exportExam_index(query) {
  return request({
    url: '/exam/exam_index/export',
    method: 'get',
    params: query
  })
}