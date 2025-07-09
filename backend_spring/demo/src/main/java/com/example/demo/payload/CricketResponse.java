package com.example.demo.payload;

public class CricketResponse {
    private String content;

    public CricketResponse(String content) {
        this.content = content;
    }

    public CricketResponse() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CricketResponse(content=" + content + ")";
    }
}
