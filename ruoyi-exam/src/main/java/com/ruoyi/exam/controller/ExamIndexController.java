package com.ruoyi.exam.controller;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.service.TokenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.exam.domain.ExamIndex;
import com.ruoyi.exam.service.IExamIndexService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考试信息管理首页Controller
 *
 * @author 周宇龙
 * @date 2021-02-06
 */
@RestController
@RequestMapping("/exam/exam_index")
public class ExamIndexController extends BaseController
{
    @Autowired
    private IExamIndexService examIndexService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询考试信息管理首页列表
     */
    @PreAuthorize("@ss.hasPermi('exam:exam_index:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExamIndex examIndex)
    {
        // 获取当前登录用户信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
//        System.out.println(user.toString());
        Long userId = user.getUserId();
        if(userId == 1 || userId == 3){
            // 当用户为管理员时，所有信息全部显示
            startPage();
            List<ExamIndex> list = examIndexService.selectExamIndexList(examIndex);
            return getDataTable(list);
        }else{
            // 当用户为任课老师时，获取到该老师的user_name
            String examTeacher = user.getUserName();
            startPage();
            List<ExamIndex> list = examIndexService.selectExamIndexByName(examTeacher);
            return getDataTable(list);

        }

//        startPage();
//        List<ExamIndex> list = examIndexService.selectExamIndexList(examIndex);
//        return getDataTable(list);
    }

    /**
     * 导出考试信息管理首页列表
     */
    @PreAuthorize("@ss.hasPermi('exam:exam_index:export')")
    @Log(title = "考试信息管理首页", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ExamIndex examIndex)
    {
        List<ExamIndex> list = examIndexService.selectExamIndexList(examIndex);
        ExcelUtil<ExamIndex> util = new ExcelUtil<ExamIndex>(ExamIndex.class);
        return util.exportExcel(list, "exam_index");
    }

    /**
     * 获取考试信息管理首页详细信息
     */
    @PreAuthorize("@ss.hasPermi('exam:exam_index:query')")
    @GetMapping(value = "/{indexId}")
    public AjaxResult getInfo(@PathVariable("indexId") Long indexId)
    {
        return AjaxResult.success(examIndexService.selectExamIndexById(indexId));
    }

    /**
     * 新增考试信息管理首页
     */
    @PreAuthorize("@ss.hasPermi('exam:exam_index:add')")
    @Log(title = "考试信息管理首页", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExamIndex examIndex)
    {
        return toAjax(examIndexService.insertExamIndex(examIndex));
    }

    /**
     * 修改考试信息管理首页
     */
    @PreAuthorize("@ss.hasPermi('exam:exam_index:edit')")
    @Log(title = "考试信息管理首页", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExamIndex examIndex)
    {
        return toAjax(examIndexService.updateExamIndex(examIndex));
    }

    /**
     * 删除考试信息管理首页
     */
    @PreAuthorize("@ss.hasPermi('exam:exam_index:remove')")
    @Log(title = "考试信息管理首页", businessType = BusinessType.DELETE)
    @DeleteMapping("/{indexIds}")
    public AjaxResult remove(@PathVariable Long[] indexIds)
    {
        return toAjax(examIndexService.deleteExamIndexByIds(indexIds));
    }
}
