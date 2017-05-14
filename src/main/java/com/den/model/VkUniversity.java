package com.den.model;

import javax.persistence.*;

@Entity
@Table(name = "vk_university")
public class VkUniversity extends Model {

    @Column(name = "university_id")
    private int idUniversity;

    @Column(name = "university_name")
    private String nameUniversity;

    @Column(name = "university_city_id")
    private long idUniversityCity;

    public VkUniversity() {
    }

    public long getIdUniversityCity() {
        return idUniversityCity;
    }

    public void setIdUniversityCity(long idUniversityCity) {
        this.idUniversityCity = idUniversityCity;
    }

    public int getIdUniversity() {
        return idUniversity;
    }

    public void setIdUniversity(int idVkUniversity) {
        this.idUniversity = idVkUniversity;
    }

    public String getNameUniversity() {
        return nameUniversity;
    }

    public void setNameUniversity(String nameVkUniversity) {
        this.nameUniversity = nameVkUniversity;
    }

    @Override
    public String toString() {
        return "VkUniversity{" +
                "idUniversity=" + idUniversity +
                ", nameUniversity='" + nameUniversity + '\'' +
                ", idUniversityCity=" + idUniversityCity +
                '}';
    }
}
