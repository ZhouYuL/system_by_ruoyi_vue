package com.ruoyi.group.service;

import java.util.List;
import com.ruoyi.group.domain.GroupIndexCourse;

/**
 * indexCourse关联表Service接口
 *
 * @author 周宇龙
 * @date 2021-02-23
 */
public interface IGroupIndexCourseService
{
    /**
     * 查询indexCourse关联表
     *
     * @param groupId indexCourse关联表ID
     * @return indexCourse关联表
     */
    public GroupIndexCourse selectGroupIndexCourseById(Long groupId);

    /**
     * 查询indexCourse关联表列表
     *
     * @param groupIndexCourse indexCourse关联表
     * @return indexCourse关联表集合
     */
    public List<GroupIndexCourse> selectGroupIndexCourseList(GroupIndexCourse groupIndexCourse);

    /**
     * 新增indexCourse关联表
     *
     * @param groupIndexCourse indexCourse关联表
     * @return 结果
     */
    public int insertGroupIndexCourse(GroupIndexCourse groupIndexCourse);

    /**
     * 修改indexCourse关联表
     *
     * @param groupIndexCourse indexCourse关联表
     * @return 结果
     */
//    public int updateGroupIndexCourse(GroupIndexCourse groupIndexCourse);

    /**
     * 批量删除indexCourse关联表
     *
     * @param groupIds 需要删除的indexCourse关联表ID
     * @return 结果
     */
    public int deleteGroupIndexCourseByIds(Long[] groupIds);

    /**
     * 删除indexCourse关联表信息
     *
     * @param groupId indexCourse关联表ID
     * @return 结果
     */
    public int deleteGroupIndexCourseById(Long groupId);
}
