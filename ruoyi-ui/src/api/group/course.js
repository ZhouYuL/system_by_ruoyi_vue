import request from '@/utils/request'

// 查询课程管理列表
export function listCourse(query) {
  return request({
    url: '/group/course/list',
    method: 'get',
    params: query
  })
}

// 添加课程时的查询课程列表
export function listCourse1(query) {
  return request({
    url: '/group/course/list/course',
    method: 'get',
    params: query
  })
}

export function listAllCourse(query) {
  return request({
    url: '/group/course/listAll',
    method: 'get',
    params: query
  })
}

// 查询课程管理详细
export function getCourse(courseId) {
  return request({
    url: '/group/course/' + courseId,
    method: 'get'
  })
}

// 新增课程管理
export function addCourse(data) {
  return request({
    url: '/group/course',
    method: 'post',
    data: data
  })
}

// 修改课程管理
export function updateCourse(data) {
  return request({
    url: '/group/course',
    method: 'put',
    data: data
  })
}

// 删除课程管理
export function delCourse(courseId) {
  return request({
    url: '/group/course/' + courseId,
    method: 'delete'
  })
}

// 导出课程管理
export function exportCourse(query) {
  return request({
    url: '/group/course/export',
    method: 'get',
    params: query
  })
}