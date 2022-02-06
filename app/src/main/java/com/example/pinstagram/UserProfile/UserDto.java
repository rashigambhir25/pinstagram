package com.example.pinstagram.UserProfile;

import java.io.Serializable;

public class UserDto implements Serializable {
    private String id;
    private String name;
    private Boolean type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }
}
