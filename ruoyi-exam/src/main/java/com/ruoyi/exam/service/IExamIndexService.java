package com.ruoyi.exam.service;

import java.util.List;
import com.ruoyi.exam.domain.ExamIndex;

/**
 * 考试信息管理首页Service接口
 *
 * @author 周宇龙
 * @date 2021-02-06
 */
public interface IExamIndexService
{
    /**
     * 查询考试信息管理首页
     *
     * @param indexId 考试信息管理首页ID
     * @return 考试信息管理首页
     */
    public ExamIndex selectExamIndexById(Long indexId);

    /**
     * 查询考试信息管理首页列表
     *
     * @param examIndex 考试信息管理首页
     * @return 考试信息管理首页集合
     */
    public List<ExamIndex> selectExamIndexList(ExamIndex examIndex);

    /**
     * 新增考试信息管理首页
     *
     * @param examIndex 考试信息管理首页
     * @return 结果
     */
    public int insertExamIndex(ExamIndex examIndex);

    /**
     * 修改考试信息管理首页
     *
     * @param examIndex 考试信息管理首页
     * @return 结果
     */
    public int updateExamIndex(ExamIndex examIndex);

    /**
     * 批量删除考试信息管理首页
     *
     * @param indexIds 需要删除的考试信息管理首页ID
     * @return 结果
     */
    public int deleteExamIndexByIds(Long[] indexIds);

    /**
     * 删除考试信息管理首页信息
     *
     * @param indexId 考试信息管理首页ID
     * @return 结果
     */
    public int deleteExamIndexById(Long indexId);

    /**
     * 通过任课老师的name，获取到对应的考试信息首页信息
     *
     * @param examTeacher 任课老师的专属name
     * @return
     */
    public List<ExamIndex> selectExamIndexByName(String examTeacher);
}
