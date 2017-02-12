package com.den.model;

import javax.persistence.*;

@Entity
@Table(name = "universityInformation")
public class UniversityInformation extends Model {

    @ManyToOne
    @JoinColumn(name = "id_university")
    private VkUniversity university;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private VkUser vkUser;

    @Column
    private Integer faculty;

    @Column
    private String faculty_name;

    @Column
    private Integer chair;

    @Column
    private String chair_name;

    @Column
    private Integer graduation;

    public UniversityInformation() {
    }

    public VkUniversity getUniversity() {
        return university;
    }

    public void setUniversity(VkUniversity university) {
        this.university = university;
    }

    public VkUser getVkUser() {
        return vkUser;
    }

    public void setVkUser(VkUser vkUser) {
        this.vkUser = vkUser;
    }

    public Integer getFaculty() {
        return faculty;
    }

    public void setFaculty(Integer faculty) {
        this.faculty = faculty;
    }

    public Integer getChair() {
        return chair;
    }

    public void setChair(Integer chair) {
        this.chair = chair;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public String getChair_name() {
        return chair_name;
    }

    public void setChair_name(String chair_name) {
        this.chair_name = chair_name;
    }

    public Integer getGraduation() {
        return graduation;
    }

    public void setGraduation(Integer graduation) {
        this.graduation = graduation;
    }

    @Override
    public String toString() {
        return "UniversityInformation{" +
                "university=" + university +
                ", faculty=" + faculty +
                ", faculty_name='" + faculty_name + '\'' +
                ", chair=" + chair +
                ", chair_name='" + chair_name + '\'' +
                ", graduation=" + graduation +
                '}';
    }
}
