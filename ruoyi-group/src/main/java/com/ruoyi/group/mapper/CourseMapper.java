package com.ruoyi.group.mapper;

import java.util.List;
import com.ruoyi.group.domain.Course;

/**
 * 课程管理Mapper接口
 * 
 * @author 周宇龙
 * @date 2021-02-19
 */
public interface CourseMapper 
{
    /**
     * 查询课程管理
     * 
     * @param courseId 课程管理ID
     * @return 课程管理
     */
    public Course selectCourseById(Long courseId);

    /**
     * 查询课程管理列表
     * 
     * @param course 课程管理
     * @return 课程管理集合
     */
    public List<Course> selectCourseList(Course course);

    /**
     * 新增课程管理
     * 
     * @param course 课程管理
     * @return 结果
     */
    public int insertCourse(Course course);

    /**
     * 修改课程管理
     * 
     * @param course 课程管理
     * @return 结果
     */
    public int updateCourse(Course course);

    /**
     * 删除课程管理
     * 
     * @param courseId 课程管理ID
     * @return 结果
     */
    public int deleteCourseById(Long courseId);

    /**
     * 批量删除课程管理
     * 
     * @param courseIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCourseByIds(Long[] courseIds);
}
