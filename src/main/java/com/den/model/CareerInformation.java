package com.den.model;

import javax.jws.WebParam;
import javax.persistence.*;

@Entity
@Table(name = "career_information")
public class CareerInformation extends Model{

    @ManyToOne
    @JoinColumn(name = "career_id")
    private VkCareer career;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private VkUser user;

    @Column
    private Integer country;

    @Column
    private Integer city;

    @Column(name = "data_from")
    private Integer from;

    @Column(name = "data_until")
    private Integer until;

    @Column
    private String position;

    public VkCareer getCareer() {
        return career;
    }

    public void setCareer(VkCareer career) {
        this.career = career;
    }

    public VkUser getUser() {
        return user;
    }

    public void setUser(VkUser user) {
        this.user = user;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getUntil() {
        return until;
    }

    public void setUntil(Integer until) {
        this.until = until;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "CareerInformation{" +
                "career=" + career +
                ", user=" + user +
                ", country=" + country +
                ", city=" + city +
                ", from=" + from +
                ", until=" + until +
                ", position='" + position + '\'' +
                '}';
    }
}
