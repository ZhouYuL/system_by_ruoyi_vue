package com.ruoyi.group.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.SysUserRole;
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
//        List userlist = groupIndexService.selectUserIdByRoleId();
//        System.out.println(userlist);
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
     * 查询角色id=103的所有userId
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:query')")
    @GetMapping(value = "/id/{roleId}")
    public AjaxResult getUserIdByRoleId(@PathVariable("roleId") Long roleId)
    {
        // 1.获取到roleId=103的userId
        List<Long> list = groupIndexService.selectUserIdByRoleId();
//        System.out.println(list);
        //2. 通过获取到的userId获取sys_user表中的nick_name
        String j = null;
        Long k;
        List<String> userlist = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            //  使用toString可能会出现空指针错误
            //  j = list.get(i).toString();
            j = String.valueOf(list.get(i));
            k = Long.parseLong(j);
            SysUser u = groupIndexService.selectUserById(k);
//            System.out.println(j + ":" + u.getNickName());
            userlist.add(u.getNickName());
        }
        System.out.println(userlist);
        return AjaxResult.success(userlist);
    }

    /**
     *  查询专业 getDept
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:query')")
    @GetMapping(value = "/dept/{roleId}")
    public AjaxResult getDept(@PathVariable("roleId") Long roleId)
    {
        // 1.获取SysDept对象
        List<SysDept> list = groupIndexService.selectDept();
//        System.out.println(list);
        // 2.获取每个SYSDept对象的deptName属性，并分装进一个集合中
        List<String> deptlist = new ArrayList<>();
        String j = null;
        Long k;
        for(int i = 0;i < list.size();i++){
            j = String.valueOf(list.get(i).getDeptName());
            deptlist.add(j);
        }
        System.out.println(deptlist);
        return AjaxResult.success(deptlist);
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
