package com.ruoyi.group.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.group.mapper.GroupIndexMapper;
import com.ruoyi.group.domain.GroupIndex;
import com.ruoyi.group.service.IGroupIndexService;

/**
 * 课群管理首页Service业务层处理
 *
 * @author 周宇龙
 * @date 2021-02-19
 */
@Service
public class GroupIndexServiceImpl implements IGroupIndexService
{
    @Autowired
    private GroupIndexMapper groupIndexMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询课群管理首页
     *
     * @param groupId 课群管理首页ID
     * @return 课群管理首页
     */
    @Override
    public GroupIndex selectGroupIndexById(Long groupId)
    {
        return groupIndexMapper.selectGroupIndexById(groupId);
    }

    /**
     * 查询课群管理首页列表
     *
     * @param groupIndex 课群管理首页
     * @return 课群管理首页
     */
    @Override
    public List<GroupIndex> selectGroupIndexList(GroupIndex groupIndex)
    {
        return groupIndexMapper.selectGroupIndexList(groupIndex);
    }

    /**
     * 新增课群管理首页
     *
     * @param groupIndex 课群管理首页
     * @return 结果
     */
    @Override
    public int insertGroupIndex(GroupIndex groupIndex)
    {
        return groupIndexMapper.insertGroupIndex(groupIndex);
    }

    /**
     * 修改课群管理首页
     *
     * @param groupIndex 课群管理首页
     * @return 结果
     */
    @Override
    public int updateGroupIndex(GroupIndex groupIndex)
    {
        return groupIndexMapper.updateGroupIndex(groupIndex);
    }

    /**
     * 批量删除课群管理首页
     *
     * @param groupIds 需要删除的课群管理首页ID
     * @return 结果
     */
    @Override
    public int deleteGroupIndexByIds(Long[] groupIds)
    {
        return groupIndexMapper.deleteGroupIndexByIds(groupIds);
    }

    /**
     * 删除课群管理首页信息
     *
     * @param groupId 课群管理首页ID
     * @return 结果
     */
    @Override
    public int deleteGroupIndexById(Long groupId)
    {
        return groupIndexMapper.deleteGroupIndexById(groupId);
    }

    @Override
    public List<Long> selectUserIdByRoleId() {
        return userRoleMapper.selectUserIdByRoleId();
    }

    /**
     *   查询userId对应的user信息
     *  @param userId 用户Id
     *  @return 结果
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     *   查询专业
     *  @return 结果
     */
    @Override
    public List<SysDept> selectDept(){
        return deptMapper.selectDept();
    }

}
