package com.den.model;

import javax.persistence.*;

@Entity
@Table(name = "university_information")
public class UniversityInformation extends Model {

    @ManyToOne
    @JoinColumn(name = "university_id")
    private VkUniversity university;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private VkUser vkUser;

    @Column
    private Integer faculty;

    @Column(name = "faculty_name")
    private String facultyName;

    @Column
    private Integer chair;

    @Column(name = "chair_name")
    private String chairName;

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

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String faculty_name) {
        this.facultyName = faculty_name;
    }

    public String getChairName() {
        return chairName;
    }

    public void setChairName(String chairName) {
        this.chairName = chairName;
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
                ", faculty_name='" + facultyName + '\'' +
                ", chair=" + chair +
                ", chair_name='" + chairName + '\'' +
                ", graduation=" + graduation +
                '}';
    }
}
