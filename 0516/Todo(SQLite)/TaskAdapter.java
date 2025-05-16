package com.example.stylishtodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {

    Context context;
    ArrayList<Task> taskList;
    DBHelper dbHelper;

    public TaskAdapter(Context context, ArrayList<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
        dbHelper = new DBHelper(context);
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int i) {
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return taskList.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        Task task = taskList.get(i);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_task, parent, false);
        }

        TextView taskText = convertView.findViewById(R.id.task_text);
        TextView taskDate = convertView.findViewById(R.id.task_date);
        ImageButton deleteBtn = convertView.findViewById(R.id.delete_btn);

        taskText.setText(task.getTask());
        taskDate.setText("마감일: " + task.getDate());

        deleteBtn.setOnClickListener(v -> {
            dbHelper.deleteTask(task.getId());
            taskList.remove(i);
            notifyDataSetChanged();
        });

        return convertView;
    }
}
