package com.ruoyi.exam.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
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
     * 根据ExamID查询考试信息
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
     * 根据考生Id查询考试信息
     *
     * @param userId 考生ID
     * @return
     */
    @Override
    public Exam selectExamByUserId(Long userId) {
        return examMapper.selectExamByUserId(userId);
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


    /**
     * 导入考试信息数据
     *
     * @param examList 用户数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param indexId 考试表对应的id
     * @return 结果
     */
    @Override
    public String importExam(List<Exam> examList, boolean updateSupport,  Long indexId) {
        if (StringUtils.isNull(examList) || examList.size() == 0)
        {
            throw new CustomException("导入考试信息数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (Exam exam : examList)
        {
            try
            {
                // 根绝userId验证这张表是否存在这个用户
                Exam e = examMapper.selectExamByUserId(exam.getUserId());
//                System.out.println(e);
                // 如果表中不存在此信息，则插入新数据
                if (StringUtils.isNull(e))
                {
                    System.out.println(indexId);
                    exam.setIndexId(indexId);
                    this.insertExam(exam);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、考试信息 " + exam.getUserName() + " 导入成功");
                }
                // 如果存在并且允许更新，则更新信息。本系统不支持更新数据，
                // 如需修改为可更新模式，修改index.vue页面中的data中upload中updateSupport为1
                else if (updateSupport)
                {
                    exam.setIndexId(indexId);
                    this.updateExam(exam);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、考试信息 " + exam.getUserName() + " 更新成功");
                }
                // 如果存在并且不允许更新，则提示已存在该数据
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、考试信息 " + exam.getUserName() + " 已存在");
                }
            }catch (Exception e)
            {
//                failureNum++;
//                String msg = "<br/>" + failureNum + "、考试信息 " + exam.getUserName() + " 导入失败：";
//                failureMsg.append(msg + e.getMessage());
//                failureMsg.append(e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
