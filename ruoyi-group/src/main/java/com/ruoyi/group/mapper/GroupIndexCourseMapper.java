package com.ruoyi.group.mapper;

import java.util.List;
import com.ruoyi.group.domain.GroupIndexCourse;

/**
 * indexCourse关联表Mapper接口
 *
 * @author 周宇龙
 * @date 2021-02-23
 */
public interface GroupIndexCourseMapper
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
     * 删除indexCourse关联表
     *
     * @param courseId indexCourse关联表ID
     * @return 结果
     */
    public int deleteGroupIndexCourseById(Long courseId);

    /**
     * 批量删除indexCourse关联表
     *
     * @param courseIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupIndexCourseByIds(Long[] courseIds);

    /**
     * 通过groupId，获取到group_index_course表中对应的courseId
     * @param courseId
     * @return
     */
    public List<Long> selectCourseIdByGroupId(Long courseId);
}
