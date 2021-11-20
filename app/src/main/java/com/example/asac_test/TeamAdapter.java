package com.example.asac_test;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.asac_test.Entity.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamHolder> {
    List<Team> allTasksData2 = new ArrayList<>();
    Context c;

    public  TeamAdapter (List<Team> allTasksData2) {
        this.allTasksData2 = allTasksData2;
    }
    public class TeamHolder extends RecyclerView.ViewHolder {

        public Team task;
        View itemView;
        public TeamHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("my Adapter", "Element " + allTasksData2.get(getAdapterPosition()).getName()+ " clicked");
                    Intent goToDetailsPage = new Intent(view.getContext(), Teams.class);
                    goToDetailsPage.putExtra("body",allTasksData2.get(getAdapterPosition()).getName());


                    view.getContext().startActivity(goToDetailsPage);
                }
            });

        }
    }






    @NonNull
    @Override
    public TeamAdapter.TeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_team, parent , false);


        return  new TeamHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamHolder holder, int position) {
holder.task=allTasksData2.get(position);
        TextView name = holder.itemView.findViewById(R.id.teamName);
        name.setText(holder.task.getName());
    }

//    @Override
//    public void onBindViewHolder(@NonNull TaskAdapter.TaskHolder holder, int position) {
////        holder.task = allTasksData2.get(position);
////        TextView body = holder.itemView.findViewById(R.id.body);
////        TextView status = holder.itemView.findViewById(R.id.status);
////
////        TextView team = holder.itemView.findViewById(R.id.team);
////        ImageView file = holder.itemView.findViewById(R.id.img);
////        TextView file_file = holder.itemView.findViewById(R.id.file);
////
////
////        body.setText(holder.task.body);
////        status.setText(holder.task.status);
////        team.setText(holder.task.team);
////        file.setImageBitmap(BitmapFactory.decodeFile(holder.task.file));
////        file_file.setText(holder.task.file);
//
//    }

    @Override
    public int getItemCount() {
        return allTasksData2.size();    }


    public void setTaskOGList(List<Team> allTasksData2) {
        this.allTasksData2 = allTasksData2;
    }
}

