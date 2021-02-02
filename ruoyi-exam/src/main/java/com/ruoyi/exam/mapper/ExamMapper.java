package com.ruoyi.exam.mapper;

import java.util.List;
import com.ruoyi.exam.domain.Exam;

/**
 * 考试信息管理Mapper接口
 * 
 * @author 周宇龙
 * @date 2021-01-26
 */
public interface ExamMapper 
{
    /**
     * 查询考试信息管理
     * 
     * @param examId 考试信息管理ID
     * @return 考试信息管理
     */
    public Exam selectExamById(Long examId);

    /**
     * 查询考试信息管理列表
     * 
     * @param exam 考试信息管理
     * @return 考试信息管理集合
     */
    public List<Exam> selectExamList(Exam exam);

    /**
     * 新增考试信息管理
     * 
     * @param exam 考试信息管理
     * @return 结果
     */
    public int insertExam(Exam exam);

    /**
     * 修改考试信息管理
     * 
     * @param exam 考试信息管理
     * @return 结果
     */
    public int updateExam(Exam exam);

    /**
     * 删除考试信息管理
     * 
     * @param examId 考试信息管理ID
     * @return 结果
     */
    public int deleteExamById(Long examId);

    /**
     * 批量删除考试信息管理
     * 
     * @param examIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamByIds(Long[] examIds);
}