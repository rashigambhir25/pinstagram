package com.example.pinstagram.Relations;

public class FollowModel {
    private String userName;

    public FollowModel(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
