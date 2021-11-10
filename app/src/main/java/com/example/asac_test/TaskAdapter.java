package com.example.asac_test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;

import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.example.asac_test.Entity.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    List<TaskEntity> allTasksData = new ArrayList<>();
    List<TaskMaster> allTask = new ArrayList<>();
Context context;
    public  TaskAdapter (ArrayList<TaskEntity> allTasksData) {
        this.allTasksData = allTasksData;
    }
    public TaskAdapter(List<TaskMaster> allTask, Context context) {
        this.allTask = allTask;
        this.context=context;
    }


    public class TaskHolder extends RecyclerView.ViewHolder {

        public TaskEntity task;
        View itemView;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("my Adapter", "Element " + allTasksData.get(getAdapterPosition()).body+ " clicked");
                    Intent goToDetailsPage = new Intent(view.getContext(), TaskDetailPage.class);
                    goToDetailsPage.putExtra("body",allTasksData.get(getAdapterPosition()).body);
                    goToDetailsPage.putExtra("status",allTasksData.get(getAdapterPosition()).status);
                    goToDetailsPage.putExtra("title",allTasksData.get(getAdapterPosition()).title);
                    view.getContext().startActivity(goToDetailsPage);
                }
                });

        }
    }






    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_all_tasks, parent , false);


        return  new TaskHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        holder.task = allTasksData.get(position);
        TextView body = holder.itemView.findViewById(R.id.body);
        TextView status = holder.itemView.findViewById(R.id.status);

        body.setText(holder.task.body);
        status.setText(holder.task.status);
    }

    @Override
    public int getItemCount() {
        return allTasksData.size();    }


}
