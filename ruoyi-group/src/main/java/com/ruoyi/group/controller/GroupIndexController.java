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
import com.ruoyi.group.domain.GroupIndex;
import com.ruoyi.group.service.IGroupIndexService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 课群管理首页Controller
 * 
 * @author 周宇龙
 * @date 2021-02-19
 */
@RestController
@RequestMapping("/group/group_index")
public class GroupIndexController extends BaseController
{
    @Autowired
    private IGroupIndexService groupIndexService;

    /**
     * 查询课群管理首页列表
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:list')")
    @GetMapping("/list")
    public TableDataInfo list(GroupIndex groupIndex)
    {
        startPage();
        List<GroupIndex> list = groupIndexService.selectGroupIndexList(groupIndex);
        return getDataTable(list);
    }

    /**
     * 导出课群管理首页列表
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:export')")
    @Log(title = "课群管理首页", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GroupIndex groupIndex)
    {
        List<GroupIndex> list = groupIndexService.selectGroupIndexList(groupIndex);
        ExcelUtil<GroupIndex> util = new ExcelUtil<GroupIndex>(GroupIndex.class);
        return util.exportExcel(list, "group_index");
    }

    /**
     * 获取课群管理首页详细信息
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:query')")
    @GetMapping(value = "/{groupId}")
    public AjaxResult getInfo(@PathVariable("groupId") Long groupId)
    {
        return AjaxResult.success(groupIndexService.selectGroupIndexById(groupId));
    }

    /**
     * 新增课群管理首页
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:add')")
    @Log(title = "课群管理首页", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GroupIndex groupIndex)
    {
        return toAjax(groupIndexService.insertGroupIndex(groupIndex));
    }

    /**
     * 修改课群管理首页
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:edit')")
    @Log(title = "课群管理首页", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GroupIndex groupIndex)
    {
        return toAjax(groupIndexService.updateGroupIndex(groupIndex));
    }

    /**
     * 删除课群管理首页
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:remove')")
    @Log(title = "课群管理首页", businessType = BusinessType.DELETE)
	@DeleteMapping("/{groupIds}")
    public AjaxResult remove(@PathVariable Long[] groupIds)
    {
        return toAjax(groupIndexService.deleteGroupIndexByIds(groupIds));
    }
}
