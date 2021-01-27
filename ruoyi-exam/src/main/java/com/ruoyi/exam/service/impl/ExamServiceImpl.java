package com.ruoyi.exam.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.CustomException;
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

    /**
     * 导入考试信息数据
     *
     * @param examList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importUser(List<Exam> examList, Boolean isUpdateSupport) {
        if (StringUtils.isNull(examList) || examList.size() == 0)
        {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
//        String password = configService.selectConfigByKey("sys.user.initPassword");
//        for (Exam user : examList)
//        {
//            try
//            {
//                // 验证是否存在这个用户
//                Exam u = ExamMapper.selectUserByUserName(user.getUserName());
//                if (StringUtils.isNull(u))
//                {
//                    user.setPassword(SecurityUtils.encryptPassword(password));
//                    user.setCreateBy(operName);
//                    this.insertUser(user);
//                    successNum++;
//                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
//                }
//                else if (isUpdateSupport)
//                {
//                    user.setUpdateBy(operName);
//                    this.updateUser(user);
//                    successNum++;
//                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
//                }
//                else
//                {
//                    failureNum++;
//                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
//                }
//            }
//            catch (Exception e)
//            {
//                failureNum++;
//                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
//                failureMsg.append(msg + e.getMessage());
//                log.error(msg, e);
//            }
//        }
//        int i;
        for (Exam exam : examList) {
            this.insertExam(exam);
            successNum++;
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
