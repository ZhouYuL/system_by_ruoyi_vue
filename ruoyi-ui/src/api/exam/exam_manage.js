import request from '@/utils/request'

// 查询考试信息管理列表
export function listExam_manage(query) {
  return request({
    url: '/exam/exam_manage/list',
    method: 'get',
    params: query
  })
}

// 查询考试信息管理详细
export function getExam_manage(examId) {
  return request({
    url: '/exam/exam_manage/' + examId,
    method: 'get'
  })
}

// 新增考试信息管理
export function addExam_manage(data) {
  return request({
    url: '/exam/exam_manage',
    method: 'post',
    data: data
  })
}

// 修改考试信息管理
export function updateExam_manage(data) {
  return request({
    url: '/exam/exam_manage',
    method: 'put',
    data: data
  })
}

// 删除考试信息管理
export function delExam_manage(examId) {
  return request({
    url: '/exam/exam_manage/' + examId,
    method: 'delete'
  })
}

// 导出考试信息管理
export function exportExam_manage(query) {
  return request({
    url: '/exam/exam_manage/export',
    method: 'get',
    params: query
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/exam/exam_manage/importTemplate',
    method: 'get'
  })
}