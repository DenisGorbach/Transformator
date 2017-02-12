package com.den.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "vkUser")
public class VkUser extends Model {

    @Column
    private int uid;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private Integer city;

    @Column
    private Integer country;

    @Column
    private String b_date;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "vkUser")
    private Set<UniversityInformation> universityInformations;

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

    public String getB_date() {
        return b_date;
    }

    public void setB_date(String b_date) {
        this.b_date = b_date;
    }

    public Set<UniversityInformation> getUniversityInformations() {
        return universityInformations;
    }

    public void setUniversityInformations(Set<UniversityInformation> universityInformations) {
        this.universityInformations = universityInformations;
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
                ", universityInformations=" + universityInformations +
                '}';
    }
}
