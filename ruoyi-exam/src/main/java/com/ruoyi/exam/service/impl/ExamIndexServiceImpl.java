package com.ruoyi.exam.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.exam.mapper.ExamIndexMapper;
import com.ruoyi.exam.domain.ExamIndex;
import com.ruoyi.exam.service.IExamIndexService;

import static com.ruoyi.framework.datasource.DynamicDataSourceContextHolder.log;

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

    @Override
    public String importExam(List<ExamIndex> examList, Boolean isUpdateSupport) {

        if (StringUtils.isNull(examList) || examList.size() == 0)
        {
            throw new CustomException("导入考试信息数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (ExamIndex exam : examList)
        {
            try
            {
                if(StringUtils.isBlank(exam.getExamName()) || StringUtils.isBlank(exam.getExamSemester()) || StringUtils.isBlank(exam.getExamTeacher())) {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、学期，课程名称，任课老师不能为空";
                    failureMsg.append(msg);
                }else {
                    List<ExamIndex> examIndices = examIndexMapper.selectExamIndexList(exam);
                    if (StringUtils.isNull(examIndices) || examIndices.size() == 0)
                    {

                        this.insertExamIndex(exam);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、课程名 " + exam.getExamName() + " 导入成功");
                    }
                    else if (isUpdateSupport)
                    {
                        this.updateExamIndex(exam);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、课程名 " + exam.getExamName() + " 更新成功");
                    }
                    else
                    {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、课程名 " + exam.getExamName() + " 已存在");
                    }
                }

            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、课程名 " + exam.getExamName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
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
