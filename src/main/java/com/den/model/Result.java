package com.den.model;

import java.util.List;

public class Result {

    private int group_id;
    private String social_network;
    private List<User> response;

//    public Result(int groupId, String social_network, List<String> users) {
//        this.group_id = groupId;
//        this.social_network = social_network;
//        this.response = users;
//    }

    public Result(){

    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getSocial_network() {
        return social_network;
    }

    public void setSocial_network(String social_network) {
        this.social_network = social_network;
    }

    public List<User> getResponse() {
        return response;
    }

    public void setResponse(List<User> response) {
        this.response = response;
    }
}
