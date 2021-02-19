package com.ruoyi.group.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.group.mapper.CourseMapper;
import com.ruoyi.group.domain.Course;
import com.ruoyi.group.service.ICourseService;

/**
 * 课程管理Service业务层处理
 * 
 * @author 周宇龙
 * @date 2021-02-19
 */
@Service
public class CourseServiceImpl implements ICourseService 
{
    @Autowired
    private CourseMapper courseMapper;

    /**
     * 查询课程管理
     * 
     * @param courseId 课程管理ID
     * @return 课程管理
     */
    @Override
    public Course selectCourseById(Long courseId)
    {
        return courseMapper.selectCourseById(courseId);
    }

    /**
     * 查询课程管理列表
     * 
     * @param course 课程管理
     * @return 课程管理
     */
    @Override
    public List<Course> selectCourseList(Course course)
    {
        return courseMapper.selectCourseList(course);
    }

    /**
     * 新增课程管理
     * 
     * @param course 课程管理
     * @return 结果
     */
    @Override
    public int insertCourse(Course course)
    {
        return courseMapper.insertCourse(course);
    }

    /**
     * 修改课程管理
     * 
     * @param course 课程管理
     * @return 结果
     */
    @Override
    public int updateCourse(Course course)
    {
        return courseMapper.updateCourse(course);
    }

    /**
     * 批量删除课程管理
     * 
     * @param courseIds 需要删除的课程管理ID
     * @return 结果
     */
    @Override
    public int deleteCourseByIds(Long[] courseIds)
    {
        return courseMapper.deleteCourseByIds(courseIds);
    }

    /**
     * 删除课程管理信息
     * 
     * @param courseId 课程管理ID
     * @return 结果
     */
    @Override
    public int deleteCourseById(Long courseId)
    {
        return courseMapper.deleteCourseById(courseId);
    }
}
