package com.den.model.old;



public class User {
    private int id;
    private String first_name;
    private String last_name;
    private String company;

//    public User(int id, String firstName, String last_name, String company) {
//        this.id = id;
//        this.first_name = firstName;
//        this.last_name = last_name;
//        this.company = company;
//    }

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}


