package com.ruoyi.exam.service;

import java.util.List;
import com.ruoyi.exam.domain.Exam;

/**
 * 考试信息管理Service接口
 *
 * @author 周宇龙
 * @date 2021-01-26
 */
public interface IExamService
{
    /**
     * 查询考试信息管理
     *
     * @param examId 考试信息管理ID
     * @return 考试信息管理
     */
    public Exam selectExamById(Long examId);

    /**
     * 根据考生Id查询考试信息
     *
     * @param userId 考生ID
     * @return
     */
    public Exam selectExamByUserId(Long userId);

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
     * 批量删除考试信息管理
     *
     * @param examIds 需要删除的考试信息管理ID
     * @return 结果
     */
    public int deleteExamByIds(Long[] examIds);

    /**
     * 删除考试信息管理信息
     *
     * @param examId 考试信息管理ID
     * @return 结果
     */
    public int deleteExamById(Long examId);

    /**
     * 导入考试信息数据
     *
     * @param examList 用户数据列表
     * @param indexId 考试表对应的id
     * @return 结果
     */
    public String importExam(List<Exam> examList, boolean updateSupport, Long indexId);
}
