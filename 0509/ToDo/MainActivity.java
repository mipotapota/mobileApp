package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.adapter.TodoAdapter;
import com.example.todoapp.db.TodoDatabaseHelper;
import com.example.todoapp.model.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTodoClickListener {

    private static final int REQUEST_CODE_ADD_TODO = 1;
    private static final int REQUEST_CODE_EDIT_TODO = 2;

    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private TodoDatabaseHelper databaseHelper;
    private List<Todo> todoList;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기화
        databaseHelper = new TodoDatabaseHelper(this);
        todoList = new ArrayList<>();

        // RecyclerView 설정
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 어댑터 설정
        todoAdapter = new TodoAdapter(this, todoList, this);
        recyclerView.setAdapter(todoAdapter);

        // 빈 목록일 때 표시할 뷰
        emptyView = findViewById(R.id.empty_view);

        // 플로팅 액션 버튼 설정
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TodoDetailActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_TODO);
        });

        // 데이터 로드
        loadTodoList();
    }

    private void loadTodoList() {
        todoList.clear();
        todoList.addAll(databaseHelper.getAllTodos());
        todoAdapter.notifyDataSetChanged();

        // 빈 목록일 때 메시지 표시
        if (todoList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTodoClick(int position) {
        Todo todo = todoList.get(position);
        Intent intent = new Intent(MainActivity.this, TodoDetailActivity.class);
        intent.putExtra("todo", todo);
        startActivityForResult(intent, REQUEST_CODE_EDIT_TODO);
    }

    @Override
    public void onTodoCheckboxClick(int position, boolean isChecked) {
        Todo todo = todoList.get(position);
        todo.setCompleted(isChecked);
        databaseHelper.updateTodo(todo);
        todoAdapter.notifyItemChanged(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            loadTodoList();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort_date) {
            // 날짜별 정렬 로직
            return true;
        } else if (id == R.id.action_sort_priority) {
            // 중요도별 정렬 로직
            return true;
        } else if (id == R.id.action_show_completed) {
            // 완료된 항목 표시/숨기기
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}