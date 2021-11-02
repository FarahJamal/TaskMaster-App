package com.example.asac_test;

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

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    List<Task> allTasksData = new ArrayList<>();
    public  TaskAdapter (ArrayList<Task> allTasksData) {
        this.allTasksData = allTasksData;
    }

    public class TaskHolder extends RecyclerView.ViewHolder {

        public Task task;
        View itemView;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("my Adapter", "Element " + allTasksData.get(getAdapterPosition()).getBody() + " clicked");
                    Intent goToDetailsPage = new Intent(view.getContext(), TaskDetailPage.class);
                    goToDetailsPage.putExtra("body",allTasksData.get(getAdapterPosition()).getBody());
                    goToDetailsPage.putExtra("status",allTasksData.get(getAdapterPosition()).getStatus());
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
