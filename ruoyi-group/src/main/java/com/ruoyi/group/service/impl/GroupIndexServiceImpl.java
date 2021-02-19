package com.ruoyi.group.service.impl;

import java.util.List;
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
}
