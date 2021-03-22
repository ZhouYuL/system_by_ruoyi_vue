package com.ruoyi.group.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author: ZhouYuLong
 * @date: Created in 17:05 2021/2/23
 * @Description
 */
public class GroupIndexCourse extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 课群Id **/
    private Long groupId;

    /** 课程Id **/
    private Long courseId;

    /** 课程名称 **/
    private String courseName;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("groupId", getGroupId())
                .append("courseName", getCourseName())
                .append("courseId", getCourseId())
                .toString();
    }
}
