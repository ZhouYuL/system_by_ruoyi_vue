package com.ruoyi.group.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课群管理首页对象 group_index
 * 
 * @author 周宇龙
 * @date 2021-02-19
 */
public class GroupIndex extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 课群序号 */
    private Long groupId;

    /** 课群名称 */
    @Excel(name = "课群名称")
    private String groupType;

    /** 课群负责人 */
    @Excel(name = "课群负责人")
    private String groupLeader;

    /** 学期 */
    @Excel(name = "学期")
    private String groupSemester;

    /** 专业 */
    @Excel(name = "专业")
    private String groupProfession;

    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setGroupType(String groupType) 
    {
        this.groupType = groupType;
    }

    public String getGroupType() 
    {
        return groupType;
    }
    public void setGroupLeader(String groupLeader) 
    {
        this.groupLeader = groupLeader;
    }

    public String getGroupLeader() 
    {
        return groupLeader;
    }
    public void setGroupSemester(String groupSemester) 
    {
        this.groupSemester = groupSemester;
    }

    public String getGroupSemester() 
    {
        return groupSemester;
    }
    public void setGroupProfession(String groupProfession) 
    {
        this.groupProfession = groupProfession;
    }

    public String getGroupProfession() 
    {
        return groupProfession;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("groupId", getGroupId())
            .append("groupType", getGroupType())
            .append("groupLeader", getGroupLeader())
            .append("groupSemester", getGroupSemester())
            .append("groupProfession", getGroupProfession())
            .toString();
    }
}
