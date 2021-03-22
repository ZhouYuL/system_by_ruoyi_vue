package com.ruoyi.group.controller;

import java.lang.reflect.Array;
import java.util.*;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.group.domain.*;
import com.ruoyi.group.service.ITeacherService;
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

    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询课群管理首页列表
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:list')")
    @GetMapping("/list")
    public TableDataInfo list(GroupIndex groupIndex)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        groupIndex.setDeptId(user.getDeptId());
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
//        System.out.println(userlist);
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
//        System.out.println(deptlist);
        return AjaxResult.success(deptlist);
    }

    /**
     *  查询课程 getCourse
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:query')")
    @GetMapping(value = "/course/{groupId}")
    public AjaxResult getCourse(@PathVariable("groupId") Long groupId)
    {
        // deptlist两部分对象，一个是左边未添加的，一个是右边已添加的
        // 1.获取Course对象
        List<Course> list = groupIndexService.selectCourse();
        // 2.获取每个SYSDept对象的deptName属性，并分装进一个集合中
        List<String> deptlist = new ArrayList<>();
        String j = null;
        for(int i = 0;i < list.size();i++){
            j = String.valueOf(list.get(i).getCourseName());
            deptlist.add(j);
        }
        // 此时，deptlist包含所有的课程，未进行分类。

        /**
         * 3.获取右边已添加的课程名。
         *   通过groupId，获取到group_index_course表中对应的courseId。
         *   循环courseId，查询到course表中对应的课程名
         *   将获取到的结果放在一个集合中。
         */
        //通过groupId，获取到group_index_course表中对应的courseId。
        List<Long> courseId = groupIndexService.selectCourseIdByGroupId(groupId);
        System.out.println(courseId); //[1,4]
        // 循环courseId，查询到course表中对应的课程名.
        // 获取到已选课程courseName集合
        List<String> courseName = new ArrayList<>();
        Course course = new Course();
        for(int i = 0;i<courseId.size();i++){
            course = groupIndexService.selectCourseById(courseId.get(i));
            courseName.add(course.getCourseName());
         }
        System.out.println(courseName);//[编译原理, 单片机]
        // 获取到未选择课程集合newlist
        List<String> newlist = new ArrayList<>();
        HashSet hs1 = new HashSet(deptlist);
        HashSet hs2 = new HashSet(courseName);
        hs1.removeAll(hs2);
        newlist.addAll(hs1);
        System.out.println(newlist);


        return AjaxResult.success(deptlist);
    }

    /**
     *  查询所有老师 listTeacher
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:query')")
    @GetMapping(value = "/teacher")
    public TableDataInfo listTeacher(Teacher teacher)
    {

        /*// 查询所有老师
        // 1. 获取到sys_user_role表中的所有role_id=104的所有user_id
        Long role_id = Long.valueOf(104);
        List<Long> teacherlist = groupIndexService.selectUserIdByRole(role_id);
        System.out.println(teacherlist);

        String j = null;
        Long k;
        List<SysUser> list = new ArrayList<>();

        for(int i = 0;i < teacherlist.size();i++){
            j = String.valueOf(teacherlist.get(i));
            k = Long.parseLong(j);
        // 2. 通过获取到的user_id。获取到sys_user表中所有的相关的nickName
            startPage();
            SysUser u = groupIndexService.selectUserById(k);
            list.add(u);
        }*/
        startPage();
        List<Teacher> teacherList = teacherService.selectTeacherList(teacher);
        return getDataTable(teacherList);
    }

    /**
     *  查询上次已选择课程 getLastCourse
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:query')")
    @GetMapping(value = "/Lastcourse/{groupId}")
    public AjaxResult getLastCourse(@PathVariable("groupId") Long groupId)
    {

        // 1.获取Course对象
        List<Course> list = groupIndexService.selectCourse();

        List<Long> courseId = groupIndexService.selectCourseIdByGroupId(groupId);
        System.out.println(courseId); //[1,4]


        return AjaxResult.success(courseId);
    }

    /**
     * 新增课群管理首页
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:add')")
    @Log(title = "课群管理首页", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GroupIndex groupIndex)
    {

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        groupIndex.setCreater(user.getUserId());
        groupIndex.setDeptId(user.getDeptId());
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
     * 更新group_index_course数据库
     * @param form
     * @return
     */
    @PreAuthorize("@ss.hasPermi('group:group_index:edit')")
    @Log(title = "课群详情信息", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/IndexCourse")
    public AjaxResult updateIndexCourse(@RequestBody List<GroupIndexCourse> form)
    {
        System.out.println(form);
        Long groupId = null;
        String courseName = null;
        Long courseId = null;
        GroupIndexCourse indexCourse = new GroupIndexCourse();
        for(int l = 0;l<form.size();l++){
            groupId = form.get(l).getGroupId();
            groupIndexService.deleteCourseById(groupId);
        }
        for(int i = 0;i<form.size();i++){

            courseName = form.get(i).getCourseName();
            courseId = groupIndexService.selectCourseIdByName(courseName);

            indexCourse.setGroupId(groupId);
            indexCourse.setCourseId(courseId);

            groupIndexService.updateGroupIndexCourse(indexCourse);
        }
        return null;
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
