package com.den.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "VkUniversity")
public class VkUniversity extends Model {

    @Column
    private int idVkUniversity;
    @Column
    private String nameVkUniversity;
    @Column
    private long idUniversityCity;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "university")
//    private Set<UniversityInformationDao> universityInformationSet;


    public VkUniversity() {
    }

    public long getIdUniversityCity() {
        return idUniversityCity;
    }

    public void setIdUniversityCity(long idUniversityCity) {
        this.idUniversityCity = idUniversityCity;
    }

    public int getIdVkUniversity() {
        return idVkUniversity;
    }

    public void setIdVkUniversity(int idVkUniversity) {
        this.idVkUniversity = idVkUniversity;
    }

    public String getNameVkUniversity() {
        return nameVkUniversity;
    }

    public void setNameVkUniversity(String nameVkUniversity) {
        this.nameVkUniversity = nameVkUniversity;
    }

    @Override
    public String toString() {
        return "VkUniversity{" +
                "idVkUniversity=" + idVkUniversity +
                ", nameVkUniversity='" + nameVkUniversity + '\'' +
                ", idUniversityCity=" + idUniversityCity +
                '}';
    }
}
