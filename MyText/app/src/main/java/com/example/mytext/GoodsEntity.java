package com.example.mytext;


import java.io.Serializable;

public class GoodsEntity implements Serializable {
    private String title;
    private String content;
    private String username;;

    public GoodsEntity() {
    }

    public GoodsEntity(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




}
