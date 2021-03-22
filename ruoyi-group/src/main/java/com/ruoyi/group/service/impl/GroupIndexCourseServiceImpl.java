package com.ruoyi.group.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.group.mapper.GroupIndexCourseMapper;
import com.ruoyi.group.domain.GroupIndexCourse;
import com.ruoyi.group.service.IGroupIndexCourseService;

/**
 * indexCourse关联表Service业务层处理
 *
 * @author 周宇龙
 * @date 2021-02-23
 */
@Service
public class GroupIndexCourseServiceImpl implements IGroupIndexCourseService
{
    @Autowired
    private GroupIndexCourseMapper groupIndexCourseMapper;

    /**
     * 查询indexCourse关联表
     *
     * @param groupId indexCourse关联表ID
     * @return indexCourse关联表
     */
    @Override
    public GroupIndexCourse selectGroupIndexCourseById(Long groupId)
    {
        return groupIndexCourseMapper.selectGroupIndexCourseById(groupId);
    }

    /**
     * 查询indexCourse关联表列表
     *
     * @param groupIndexCourse indexCourse关联表
     * @return indexCourse关联表
     */
    @Override
    public List<GroupIndexCourse> selectGroupIndexCourseList(GroupIndexCourse groupIndexCourse)
    {
        return groupIndexCourseMapper.selectGroupIndexCourseList(groupIndexCourse);
    }

    /**
     * 新增indexCourse关联表
     *
     * @param groupIndexCourse indexCourse关联表
     * @return 结果
     */
    @Override
    public int insertGroupIndexCourse(GroupIndexCourse groupIndexCourse)
    {
        return groupIndexCourseMapper.insertGroupIndexCourse(groupIndexCourse);
    }

    /**
     * 修改indexCourse关联表
     *
     * @param groupIndexCourse indexCourse关联表
     * @return 结果
     */
//    @Override
//    public int updateGroupIndexCourse(GroupIndexCourse groupIndexCourse)
//    {
//        return groupIndexCourseMapper.updateGroupIndexCourse(groupIndexCourse);
//    }

    /**
     * 批量删除indexCourse关联表
     *
     * @param groupIds 需要删除的indexCourse关联表ID
     * @return 结果
     */
    @Override
    public int deleteGroupIndexCourseByIds(Long[] groupIds)
    {
        return groupIndexCourseMapper.deleteGroupIndexCourseByIds(groupIds);
    }

    /**
     * 删除indexCourse关联表信息
     *
     * @param groupId indexCourse关联表ID
     * @return 结果
     */
    @Override
    public int deleteGroupIndexCourseById(Long groupId)
    {
        return groupIndexCourseMapper.deleteGroupIndexCourseById(groupId);
    }
}
