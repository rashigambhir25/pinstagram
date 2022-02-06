package com.example.pinstagram.Feed;

import java.io.Serializable;
import java.util.Date;

public class Story implements Serializable {
//    private boolean seen;
////    private String imageUrl;
//
//    public Story(boolean seen) {
//        this.seen = seen;
//
//    }
//
//    public boolean isSeen() {
//        return seen;
//    }
//
//    public void setSeen(boolean seen) {
//        this.seen = seen;
//    }

    private Long id;
    private String userId;
    private Long expiryTime;
    private Boolean type; //true Image false Video
    private String url;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }




}
