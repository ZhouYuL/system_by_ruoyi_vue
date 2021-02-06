package com.ruoyi.exam.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考试信息管理对象 exam_exam
 *
 * @author 周宇龙
 * @date 2021-01-26
 */
public class Exam extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long examId;

    /** indexId */
    private Long IndexId;

    /** 学期 */
    @Excel(name = "学期")
    private String examSemester;

    /** 课程名 */
    @Excel(name = "课程名")
    private String examProject;

    /** 学生ID */
    @Excel(name = "学生ID")
    private Long userId;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String userName;

    /** 性别 0=男生,1=女生,2=未知 */
    @Excel(name = "性别",readConverterExp="0=男生,1=女生,2=未知", prompt="请填对应的数字0.男生1.女生2.未知")
    private String sex;

    /** 是否缺考 0=缺考,1=不缺考,2=未知 */
    @Excel(name = "是否缺考",readConverterExp="0=缺考,1=不缺考,2=未知", prompt="请填对应的数字0.缺考1.不缺考2.未知")
    private String isMiss;

    /** 成绩 */
    @Excel(name = "成绩")
    private Long record;

    /** 考试时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考试时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date examTime;

    /** 是否及格 0=不及格,1=及格,2=未知 */
    @Excel(name = "是否及格",readConverterExp="0=不及格,1=及格,2=未知", prompt="请填对应的数字0.不及格1.及格2.未知")
    private String isFail;

    public void setExamId(Long examId)
    {
        this.examId = examId;
    }

    public Long getExamId()
    {
        return examId;
    }

    public Long getIndexId() {
        return IndexId;
    }

    public void setIndexId(Long indexId) {
        IndexId = indexId;
    }

    public void setExamSemester(String examSemester)
    {
        this.examSemester = examSemester;
    }

    public String getExamSemester()
    {
        return examSemester;
    }
    public void setExamProject(String examProject)
    {
        this.examProject = examProject;
    }

    public String getExamProject()
    {
        return examProject;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }
    public void setIsMiss(String isMiss)
    {
        this.isMiss = isMiss;
    }

    public String getIsMiss()
    {
        return isMiss;
    }
    public void setRecord(Long record)
    {
        this.record = record;
    }

    public Long getRecord()
    {
        return record;
    }
    public void setExamTime(Date examTime)
    {
        this.examTime = examTime;
    }

    public Date getExamTime()
    {
        return examTime;
    }
    public void setIsFail(String isFail)
    {
        this.isFail = isFail;
    }

    public String getIsFail()
    {
        return isFail;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("examId", getExamId())
            .append("indexId", getIndexId())
            .append("examSemester", getExamSemester())
            .append("examProject", getExamProject())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("sex", getSex())
            .append("isMiss", getIsMiss())
            .append("record", getRecord())
            .append("examTime", getExamTime())
            .append("isFail", getIsFail())
            .toString();
    }
}
