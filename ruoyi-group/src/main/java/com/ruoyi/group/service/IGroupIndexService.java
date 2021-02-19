package com.ruoyi.group.service;

import java.util.List;
import com.ruoyi.group.domain.GroupIndex;

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
}
