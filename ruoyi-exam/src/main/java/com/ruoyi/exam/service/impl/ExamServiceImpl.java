package com.ruoyi.exam.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.exam.mapper.ExamMapper;
import com.ruoyi.exam.domain.Exam;
import com.ruoyi.exam.service.IExamService;

/**
 * 考试信息管理Service业务层处理
 * 
 * @author 周宇龙
 * @date 2021-01-26
 */
@Service
public class ExamServiceImpl implements IExamService 
{
    @Autowired
    private ExamMapper examMapper;

    /**
     * 查询考试信息管理
     * 
     * @param examId 考试信息管理ID
     * @return 考试信息管理
     */
    @Override
    public Exam selectExamById(Long examId)
    {
        return examMapper.selectExamById(examId);
    }

    /**
     * 查询考试信息管理列表
     * 
     * @param exam 考试信息管理
     * @return 考试信息管理
     */
    @Override
    public List<Exam> selectExamList(Exam exam)
    {
        return examMapper.selectExamList(exam);
    }

    /**
     * 新增考试信息管理
     * 
     * @param exam 考试信息管理
     * @return 结果
     */
    @Override
    public int insertExam(Exam exam)
    {
        return examMapper.insertExam(exam);
    }

    /**
     * 修改考试信息管理
     * 
     * @param exam 考试信息管理
     * @return 结果
     */
    @Override
    public int updateExam(Exam exam)
    {
        return examMapper.updateExam(exam);
    }

    /**
     * 批量删除考试信息管理
     * 
     * @param examIds 需要删除的考试信息管理ID
     * @return 结果
     */
    @Override
    public int deleteExamByIds(Long[] examIds)
    {
        return examMapper.deleteExamByIds(examIds);
    }

    /**
     * 删除考试信息管理信息
     * 
     * @param examId 考试信息管理ID
     * @return 结果
     */
    @Override
    public int deleteExamById(Long examId)
    {
        return examMapper.deleteExamById(examId);
    }
}
