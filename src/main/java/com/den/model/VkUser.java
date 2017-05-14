package com.den.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "vk_user")
public class VkUser extends Model {

    @Column
    private int uid;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column
    private Integer city;

    @Column
    private Integer country;

    @Column
    private String b_date;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "vkUser")
    private Set<UniversityInformation> universityInfoSet;

    public VkUser() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getBDate() {
        return b_date;
    }

    public void setBDate(String b_date) {
        this.b_date = b_date;
    }

    public Set<UniversityInformation> getUniversityInfoSet() {
        return universityInfoSet;
    }

    public void setUniversityInfoSet(Set<UniversityInformation> universityInformations) {
        this.universityInfoSet = universityInformations;
    }

    @Override
    public String toString() {
        return "VkUser{" +
                "uid=" + uid +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", city=" + city +
                ", country=" + country +
                ", b_date='" + b_date + '\'' +
                ", universityInformations=" + universityInfoSet +
                '}';
    }
}
