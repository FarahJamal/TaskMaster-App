package com.example.asac_test.DataBase;


import android.content.Context;

import androidx.room.*;

import com.example.asac_test.DAO.TaskDAO;
import com.example.asac_test.Entity.TaskEntity;

@Database(entities = {TaskEntity.class}, version = 1,exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDAO taskDAO();
    private static AppDatabase appDatabase;

    public AppDatabase() {
    }
    public static AppDatabase getInstance(Context context){
        if(appDatabase==null){
            appDatabase=Room.databaseBuilder(context,AppDatabase.class,"tasks").allowMainThreadQueries().build();
        }
    return appDatabase;
    }
}
