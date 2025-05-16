package com.example.mytodoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends BaseAdapter {
    Context context;
    List<Task> taskList;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    public void updateTasks(List<Task> newTasks) {
        taskList.clear();
        taskList.addAll(newTasks);
        notifyDataSetChanged();  // 중요!
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return taskList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.item_task, parent, false);
        }

        TextView title = row.findViewById(R.id.taskTitle);
        TextView date = row.findViewById(R.id.taskDate);

        Task task = taskList.get(position);
        title.setText(task.title);
        date.setText(task.date);

        return row;
    }
}
