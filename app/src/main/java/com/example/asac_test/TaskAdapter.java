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
    List<TaskMaster> allTasksData2 = new ArrayList<>();
Context c;
    public  TaskAdapter (Context c,ArrayList<TaskEntity> allTasksData) {
        this.allTasksData = allTasksData;
        this.c=c;
    }
    public  TaskAdapter (List<TaskMaster> allTasksData2, Context context) {
        this.allTasksData2 = allTasksData2;
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
                    goToDetailsPage.putExtra("team",allTasksData.get(getAdapterPosition()).team);

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

        TextView team = holder.itemView.findViewById(R.id.team);

        body.setText(holder.task.body);
        status.setText(holder.task.status);
 team.setText(holder.task.team);

    }

    @Override
    public int getItemCount() {
        return allTasksData.size();    }

    public List<TaskEntity> getTaskList() {
        return allTasksData;
    }

    public void setTaskOGList(List<TaskEntity> allTasksData) {
        this.allTasksData = allTasksData;
    }
}
