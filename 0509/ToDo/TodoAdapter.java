package com.example.todoapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.model.Todo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<Todo> todoList;
    private Context context;
    private OnTodoClickListener listener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public interface OnTodoClickListener {
        void onTodoClick(int position);
        void onTodoCheckboxClick(int position, boolean isChecked);
    }

    public TodoAdapter(Context context, List<Todo> todoList, OnTodoClickListener listener) {
        this.context = context;
        this.todoList = todoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todoList.get(position);

        holder.titleTextView.setText(todo.getTitle());

        // 마감일 설정
        Date dueDate = todo.getDueDate();
        if (dueDate != null) {
            holder.dueDateTextView.setText(dateFormat.format(dueDate));

            // 마감일이 지났는지 확인
            if (dueDate.before(new Date()) && !todo.isCompleted()) {
                holder.dueDateTextView.setTextColor(Color.RED);
            } else {
                holder.dueDateTextView.setTextColor(Color.BLACK);
            }
        } else {
            holder.dueDateTextView.setText("마감일 없음");
        }

        // 중요도에 따른 색상 설정
        switch (todo.getPriority()) {
            case 1: // 낮음
                holder.priorityView.setBackgroundColor(Color.GREEN);
                break;
            case 2: // 보통
                holder.priorityView.setBackgroundColor(Color.YELLOW);
                break;
            case 3: // 높음
                holder.priorityView.setBackgroundColor(Color.RED);
                break;
        }

        // 완료 여부 설정
        holder.completedCheckBox.setChecked(todo.isCompleted());

        if (todo.isCompleted()) {
            holder.titleTextView.setTextColor(Color.GRAY);
        } else {
            holder.titleTextView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void updateData(List<Todo> newTodoList) {
        this.todoList = newTodoList;
        notifyDataSetChanged();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, dueDateTextView;
        CheckBox completedCheckBox;
        View priorityView;
        CardView cardView;

        TodoViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.todo_title);
            dueDateTextView = view.findViewById(R.id.todo_due_date);
            completedCheckBox = view.findViewById(R.id.todo_completed);
            priorityView = view.findViewById(R.id.priority_indicator);
            cardView = view.findViewById(R.id.todo_card);

            cardView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onTodoClick(position);
                }
            });

            completedCheckBox.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onTodoCheckboxClick(position, completedCheckBox.isChecked());
                }
            });
        }
    }
}