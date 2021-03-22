import request from '@/utils/request'

// 查询课群管理首页列表
export function listGroup_index(query) {
  return request({
    url: '/group/group_index/list',
    method: 'get',
    params: query
  })
}

// 查询课群管理首页详细
export function getGroup_index(groupId) {
  return request({
    url: '/group/group_index/' + groupId,
    method: 'get'
  })
}

// 查询角色id=103的所有userId
export function getUserIdByRoleId(roleId) {
  return request({
    url: '/group/group_index/id/' + roleId,
    method: 'get'
  })
}

// 查询相关专业
export function getDept(roleId) {
  return request({
    url: '/group/group_index/dept/' + roleId,
    method: 'get'
  })
}

// 新增课群管理首页
export function addGroup_index(data) {
  return request({
    url: '/group/group_index',
    method: 'post',
    data: data
  })
}

// 修改课群管理首页
export function updateGroup_index(data) {
  return request({
    url: '/group/group_index',
    method: 'put',
    data: data
  })
}

// 删除课群管理首页
export function delGroup_index(groupId) {
  return request({
    url: '/group/group_index/' + groupId,
    method: 'delete'
  })
}

// 导出课群管理首页
export function exportGroup_index(query) {
  return request({
    url: '/group/group_index/export',
    method: 'get',
    params: query
  })
}

// 查询课程
export function getCourse(groupId) {
  return request({
    url: '/group/group_index/course/' + groupId,
    method: 'get'
  })
}

// 更新group_index_course数据库
export function updateIndexCourse(data) {
  return request({
    url: '/group/group_index/IndexCourse/',
    method: 'put',
    data: data
  })
}

// 查询上次提交的课程
export function getLastCourse(groupId) {
  return request({
    url: '/group/group_index/Lastcourse/' + groupId,
    method: 'get'
  })
}

// 查询所有老师
export function listTeacher(query) {
  return request({
    url: '/group/group_index/teacher',
    method: 'get',
    params: query
  })
}