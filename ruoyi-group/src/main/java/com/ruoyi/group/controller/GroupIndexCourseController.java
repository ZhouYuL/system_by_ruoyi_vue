package com.ruoyi.group.controller;

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
import com.ruoyi.group.domain.GroupIndexCourse;
import com.ruoyi.group.service.IGroupIndexCourseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * indexCourse关联表Controller
 *
 * @author 周宇龙
 * @date 2021-02-23
 */
@RestController
@RequestMapping("/group/groupCourse")
public class GroupIndexCourseController extends BaseController
{
    @Autowired
    private IGroupIndexCourseService groupIndexCourseService;

    /**
     * 查询indexCourse关联表列表
     */
    @PreAuthorize("@ss.hasPermi('group:groupCourse:list')")
    @GetMapping("/list")
    public TableDataInfo list(GroupIndexCourse groupIndexCourse)
    {
        startPage();
        List<GroupIndexCourse> list = groupIndexCourseService.selectGroupIndexCourseList(groupIndexCourse);
        return getDataTable(list);
    }

    /**
     * 导出indexCourse关联表列表
     */
//    @PreAuthorize("@ss.hasPermi('group:course:export')")
//    @Log(title = "indexCourse关联表", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(GroupIndexCourse groupIndexCourse)
//    {
//        List<GroupIndexCourse> list = groupIndexCourseService.selectGroupIndexCourseList(groupIndexCourse);
//        ExcelUtil<GroupIndexCourse> util = new ExcelUtil<GroupIndexCourse>(GroupIndexCourse.class);
//        return util.exportExcel(list, "course");
//    }

    /**
     * 获取indexCourse关联表详细信息
     */
//    @PreAuthorize("@ss.hasPermi('group:course:query')")
//    @GetMapping(value = "/{groupId}")
//    public AjaxResult getInfo(@PathVariable("groupId") Long groupId)
//    {
//        return AjaxResult.success(groupIndexCourseService.selectGroupIndexCourseById(groupId));
//    }

    /**
     * 新增indexCourse关联表
     */
    @PreAuthorize("@ss.hasPermi('group:groupCourse:add')")
    @Log(title = "indexCourse关联表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GroupIndexCourse groupIndexCourse)
    {
        List<GroupIndexCourse> groupIndexCourses = groupIndexCourseService.selectGroupIndexCourseList(groupIndexCourse);
        if(groupIndexCourses != null && groupIndexCourses.size() > 0) {
            return AjaxResult.error("新增课群课程失败，该课程已存在课群当中");
        }
        return toAjax(groupIndexCourseService.insertGroupIndexCourse(groupIndexCourse));
    }

    /**
     * 修改indexCourse关联表
     */
//    @PreAuthorize("@ss.hasPermi('group:course:edit')")
//    @Log(title = "indexCourse关联表", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody GroupIndexCourse groupIndexCourse)
//    {
//        return toAjax(groupIndexCourseService.updateGroupIndexCourse(groupIndexCourse));
//    }

    /**
     * 删除indexCourse关联表
     */
    @PreAuthorize("@ss.hasPermi('group:groupCourse:remove')")
    @Log(title = "indexCourse关联表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{courseIds}")
    public AjaxResult remove(@PathVariable Long[] courseIds)
    {
        return toAjax(groupIndexCourseService.deleteGroupIndexCourseByIds(courseIds));
    }
}
