package com.ruoyi.exam.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.exam.mapper.ExamIndexMapper;
import com.ruoyi.exam.domain.ExamIndex;
import com.ruoyi.exam.service.IExamIndexService;

/**
 * 考试信息管理首页Service业务层处理
 *
 * @author 周宇龙
 * @date 2021-02-06
 */
@Service
public class ExamIndexServiceImpl implements IExamIndexService
{
    @Autowired
    private ExamIndexMapper examIndexMapper;

    /**
     * 查询考试信息管理首页
     *
     * @param indexId 考试信息管理首页ID
     * @return 考试信息管理首页
     */
    @Override
    public ExamIndex selectExamIndexById(Long indexId)
    {
        return examIndexMapper.selectExamIndexById(indexId);
    }

    /**
     * 查询考试信息管理首页列表
     *
     * @param examIndex 考试信息管理首页
     * @return 考试信息管理首页
     */
    @Override
    public List<ExamIndex> selectExamIndexList(ExamIndex examIndex)
    {
        return examIndexMapper.selectExamIndexList(examIndex);
    }

    /**
     * 新增考试信息管理首页
     *
     * @param examIndex 考试信息管理首页
     * @return 结果
     */
    @Override
    public int insertExamIndex(ExamIndex examIndex)
    {
        return examIndexMapper.insertExamIndex(examIndex);
    }

    /**
     * 修改考试信息管理首页
     *
     * @param examIndex 考试信息管理首页
     * @return 结果
     */
    @Override
    public int updateExamIndex(ExamIndex examIndex)
    {
        return examIndexMapper.updateExamIndex(examIndex);
    }

    /**
     * 批量删除考试信息管理首页
     *
     * @param indexIds 需要删除的考试信息管理首页ID
     * @return 结果
     */
    @Override
    public int deleteExamIndexByIds(Long[] indexIds)
    {
        return examIndexMapper.deleteExamIndexByIds(indexIds);
    }

    /**
     * 删除考试信息管理首页信息
     *
     * @param indexId 考试信息管理首页ID
     * @return 结果
     */
    @Override
    public int deleteExamIndexById(Long indexId)
    {
        return examIndexMapper.deleteExamIndexById(indexId);
    }

    @Override
    public List<ExamIndex> selectExamIndexByName(String examTeacher) {
        return examIndexMapper.selectExamIndexByName(examTeacher);
    }
}
