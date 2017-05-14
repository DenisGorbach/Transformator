package com.den.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vk_career")
public class VkCareer extends Model{

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "company_name")
    private String companyName;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer idVkJob) {
        this.groupId = idVkJob;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String nameVkJob) {
        this.companyName = nameVkJob;
    }

    @Override
    public String toString() {
        return "VkCareer{" +
                "group_id=" + groupId +
                ", company_name='" + companyName + '\'' +
                '}';
    }
}
