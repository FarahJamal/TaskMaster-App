package com.example.asac_test;

public class Task {
    String title;
    String body;
    String status;

    public Task(String title,String body, String status) {
        this.title=title;
        this.body = body;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
