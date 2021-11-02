package com.example.asac_test;

public class Task {
    String body;
    String status;

    public Task(String body, String status) {
        this.body = body;
        this.status = status;
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
