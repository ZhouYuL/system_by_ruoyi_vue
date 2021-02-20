package com.ruoyi.group.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.group.domain.GroupIndex;
import com.ruoyi.system.domain.SysUserRole;

/**
 * 课群管理首页Service接口
 *
 * @author 周宇龙
 * @date 2021-02-19
 */
public interface IGroupIndexService
{
    /**
     * 查询课群管理首页
     *
     * @param groupId 课群管理首页ID
     * @return 课群管理首页
     */
    public GroupIndex selectGroupIndexById(Long groupId);

    /**
     * 查询课群管理首页列表
     *
     * @param groupIndex 课群管理首页
     * @return 课群管理首页集合
     */
    public List<GroupIndex> selectGroupIndexList(GroupIndex groupIndex);

    /**
     * 新增课群管理首页
     *
     * @param groupIndex 课群管理首页
     * @return 结果
     */
    public int insertGroupIndex(GroupIndex groupIndex);

    /**
     * 修改课群管理首页
     *
     * @param groupIndex 课群管理首页
     * @return 结果
     */
    public int updateGroupIndex(GroupIndex groupIndex);

    /**
     * 批量删除课群管理首页
     *
     * @param groupIds 需要删除的课群管理首页ID
     * @return 结果
     */
    public int deleteGroupIndexByIds(Long[] groupIds);

    /**
     * 删除课群管理首页信息
     *
     * @param groupId 课群管理首页ID
     * @return 结果
     */
    public int deleteGroupIndexById(Long groupId);

    /**
     *  查询角色id=103的所有userId
     *
     * @return 结果
     */
    public List<Long> selectUserIdByRoleId();

    /**
     *   查询userId对应的user信息
     *  @param userId 用户Id
     *  @return 结果
     */
    public SysUser selectUserById(Long userId);

    /**
     *   查询专业
     *  @return 结果
     */
    public List<SysDept> selectDept();
}
