package com.example.asac_test.Entity;

import androidx.room.*;

@Entity
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
        public int tid;

        @ColumnInfo(name = "title")
        public String title;

        @ColumnInfo(name = "body")
        public String body;

    @ColumnInfo(name = "status")
    public String status;
    @ColumnInfo(name = "team")
    public String team;
    public TaskEntity(String title, String body, String status,String team) {
        this.title = title;
        this.body = body;
        this.status = status;
this.team=team;
    }
}

