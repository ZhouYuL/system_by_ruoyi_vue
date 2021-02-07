package com.ruoyi.exam.controller;

import java.util.List;
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
import com.ruoyi.exam.domain.Exam;
import com.ruoyi.exam.service.IExamService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 考试信息管理Controller
 *
 * @author 周宇龙
 * @date 2021-01-26
 */
@RestController
@RequestMapping("/exam/exam_manage")
public class ExamController extends BaseController
{
    @Autowired
    private IExamService examService;

    public Long indexId;

    /**
     * 查询考试信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('exam:exam_manage:list')")
    @GetMapping("/list")
    public TableDataInfo list(Exam exam)
    {
        indexId = exam.getIndexId();
//        System.out.println(indexId);
        startPage();
        List<Exam> list = examService.selectExamList(exam);
        return getDataTable(list);
    }

    /**
     * 导出考试信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('exam:exam_manage:export')")
    @Log(title = "考试信息管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Exam exam)
    {
        List<Exam> list = examService.selectExamList(exam);
        ExcelUtil<Exam> util = new ExcelUtil<Exam>(Exam.class);
        return util.exportExcel(list, "exam_manage");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('exam:exam_manage:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Exam> util = new ExcelUtil<Exam>(Exam.class);
        List<Exam> examList = util.importExcel(file.getInputStream());
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        String operName = loginUser.getUsername();
//        String message = examService.importUser(examList, updateSupport, operName);
//        System.out.println(indexId);
//        Long indexId = ;
        String message = examService.importExam(examList, updateSupport, indexId);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<Exam> util = new ExcelUtil<Exam>(Exam.class);
        return util.importTemplateExcel("考试信息数据");
    }

    /**
     * 获取考试信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('exam:exam_manage:query')")
    @GetMapping(value = "/{examId}")
    public AjaxResult getInfo(@PathVariable("examId") Long examId)
    {
        return AjaxResult.success(examService.selectExamById(examId));
    }

    /**
     * 新增考试信息管理
     */
    @PreAuthorize("@ss.hasPermi('exam:exam_manage:add')")
    @Log(title = "考试信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Exam exam)
    {
        return toAjax(examService.insertExam(exam));
    }

    /**
     * 修改考试信息管理
     */
    @PreAuthorize("@ss.hasPermi('exam:exam_manage:edit')")
    @Log(title = "考试信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Exam exam)
    {
        return toAjax(examService.updateExam(exam));
    }

    /**
     * 删除考试信息管理
     */
    @PreAuthorize("@ss.hasPermi('exam:exam_manage:remove')")
    @Log(title = "考试信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{examIds}")
    public AjaxResult remove(@PathVariable Long[] examIds)
    {
        return toAjax(examService.deleteExamByIds(examIds));
    }
}
