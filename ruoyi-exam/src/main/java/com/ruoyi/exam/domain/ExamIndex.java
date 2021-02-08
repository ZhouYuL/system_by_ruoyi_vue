package com.ruoyi.exam.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考试信息管理首页对象 exam_index
 *
 * @author 周宇龙
 * @date 2021-02-06
 */
public class ExamIndex extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 考试信息表编号 */
    private Long indexId;

    /** 学期 */
    @Excel(name = "学期")
    private String examSemester;

    /** 任课老师 */
    @Excel(name = "任课老师")
    private String examTeacher;

    /** 考试课程名称 */
    @Excel(name = "考试课程名称")
    private String examName;

    /** 考试时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考试时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date examTime;

    private List<Exam> exams;

    public void setIndexId(Long indexId)
    {
        this.indexId = indexId;
    }

    public Long getIndexId()
    {
        return indexId;
    }
    public void setExamSemester(String examSemester)
    {
        this.examSemester = examSemester;
    }

    public String getExamSemester()
    {
        return examSemester;
    }
    public void setExamTeacher(String examTeacher)
    {
        this.examTeacher = examTeacher;
    }

    public String getExamTeacher()
    {
        return examTeacher;
    }
    public void setExamName(String examName)
    {
        this.examName = examName;
    }

    public String getExamName()
    {
        return examName;
    }
    public void setExamTime(Date examTime)
    {
        this.examTime = examTime;
    }

    public Date getExamTime()
    {
        return examTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("indexId", getIndexId())
                .append("examSemester", getExamSemester())
                .append("examTeacher", getExamTeacher())
                .append("examName", getExamName())
                .append("examTime", getExamTime())
                .toString();
    }
}
