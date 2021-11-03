package com.example.asac_test.DAO;


import androidx.room.*;

import com.example.asac_test.Entity.TaskEntity;
import com.example.asac_test.Task;

import java.util.List;

@Dao
public interface TaskDAO {
@Query("SELECT * FROM taskentity")
List<TaskEntity> getAll();

    @Query("SELECT * FROM taskentity WHERE tid IN (:taskIds)")
    List<TaskEntity> loadAllByIds(int[] taskIds);

    @Insert
    public Long insert(TaskEntity taskModel);

//    @Delete
//    void delete(User user);
}
