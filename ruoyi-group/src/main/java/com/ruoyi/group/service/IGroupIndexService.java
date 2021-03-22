package com.ruoyi.group.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.group.domain.Course;
import com.ruoyi.group.domain.GroupIndex;
import com.ruoyi.group.domain.GroupIndexCourse;

/**
 * 课群管理首页Service接口
 *
 * @author 周宇龙
 * @date 2021-02-19
 */
public interface IGroupIndexService
{
    /**
     * 查询课群管理首页
     *
     * @param groupId 课群管理首页ID
     * @return 课群管理首页
     */
    public GroupIndex selectGroupIndexById(Long groupId);

    /**
     * 查询课群管理首页列表
     *
     * @param groupIndex 课群管理首页
     * @return 课群管理首页集合
     */
    public List<GroupIndex> selectGroupIndexList(GroupIndex groupIndex);

    /**
     * 新增课群管理首页
     *
     * @param groupIndex 课群管理首页
     * @return 结果
     */
    public int insertGroupIndex(GroupIndex groupIndex);

    /**
     * 修改课群管理首页
     *
     * @param groupIndex 课群管理首页
     * @return 结果
     */
    public int updateGroupIndex(GroupIndex groupIndex);

    /**
     * 批量删除课群管理首页
     *
     * @param groupIds 需要删除的课群管理首页ID
     * @return 结果
     */
    public int deleteGroupIndexByIds(Long[] groupIds);

    /**
     * 删除课群管理首页信息
     *
     * @param groupId 课群管理首页ID
     * @return 结果
     */
    public int deleteGroupIndexById(Long groupId);

    /**
     *  查询角色id=103的所有userId
     *
     * @return 结果
     */
    public List<Long> selectUserIdByRoleId();

    /**
     *   查询userId对应的user信息
     *  @param userId 用户Id
     *  @return 结果
     */
    public SysUser selectUserById(Long userId);

    /**
     *   查询专业
     *  @return 结果
     */
    public List<SysDept> selectDept();

    /**
     *  查询课程
     * @return
     */
    public List<Course> selectCourse();

    /**
     * 通过课程名称查询课程ID
     * @param courseName
     * @return
     */
    public Long selectCourseIdByName(String courseName);

    /**
     * 通过groupID删除Group_index_course表中的数据
     * @param groupId
     * @return
     */
    public int deleteCourseById(Long groupId);

    /**
     * 修改GroupIndexCourse表
     * @param indexCourse
     * @return
     */
    public int updateGroupIndexCourse(GroupIndexCourse indexCourse);

    /**
     * 通过groupId，获取到group_index_course表中对应的courseId
     * @param groupId
     * @return
     */
    public List<Long> selectCourseIdByGroupId(Long groupId);

    /**
     * 通过courseId,查询到course表中对应的课程名
     * @param courseId
     * @return
     */
    public Course selectCourseById(Long courseId);

    /**
     * 查询所有role_id=104的user_id
     * @param role_id
     * @return
     */
    public List<Long> selectUserIdByRole(Long role_id);
}
