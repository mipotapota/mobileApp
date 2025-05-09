package com.example.todoapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.db.TodoDatabaseHelper;
import com.example.todoapp.model.Todo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodoDetailActivity extends AppCompatActivity {

    private EditText editTitle, editDescription;
    private TextView textDueDate;
    private RadioGroup radioGroupPriority;
    private RadioButton radioPriorityLow, radioPriorityMedium, radioPriorityHigh;
    private CheckBox checkBoxCompleted;
    private Button btnSetDate, btnSave;

    private TodoDatabaseHelper databaseHelper;
    private Todo currentTodo;
    private boolean isEditMode = false;
    private Calendar calendar;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        // 초기화
        initViews();
        databaseHelper = new TodoDatabaseHelper(this);
        calendar = Calendar.getInstance();

        // Todo가 전달되었는지 확인
        if (getIntent().hasExtra("todo")) {
            isEditMode = true;
            currentTodo = (Todo) getIntent().getSerializableExtra("todo");
            populateFields();
            setTitle("할 일 수정");
        } else {
            isEditMode = false;
            currentTodo = new Todo();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            currentTodo.setDueDate(calendar.getTime());
            updateDateDisplay();
            setTitle("새 할 일");
        }

        setupListeners();
    }

    private void initViews() {
        editTitle = findViewById(R.id.edit_title);
        editDescription = findViewById(R.id.edit_description);
        textDueDate = findViewById(R.id.text_due_date);
        radioGroupPriority = findViewById(R.id.radio_group_priority);
        radioPriorityLow = findViewById(R.id.radio_priority_low);
        radioPriorityMedium = findViewById(R.id.radio_priority_medium);
        radioPriorityHigh = findViewById(R.id.radio_priority_high);
        checkBoxCompleted = findViewById(R.id.checkbox_completed);
        btnSetDate = findViewById(R.id.btn_set_date);
        btnSave = findViewById(R.id.btn_save);
    }

    private void populateFields() {
        editTitle.setText(currentTodo.getTitle());
        editDescription.setText(currentTodo.getDescription());
        calendar.setTime(currentTodo.getDueDate());
        updateDateDisplay();

        // 중요도 설정
        switch (currentTodo.getPriority()) {
            case 1:
                radioPriorityLow.setChecked(true);
                break;
            case 2:
                radioPriorityMedium.setChecked(true);
                break;
            case 3:
                radioPriorityHigh.setChecked(true);
                break;
        }

        checkBoxCompleted.setChecked(currentTodo.isCompleted());
    }

    private void setupListeners() {
        btnSetDate.setOnClickListener(v -> showDatePickerDialog());

        btnSave.setOnClickListener(v -> saveTodo());
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    updateDateDisplay();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDateDisplay() {
        textDueDate.setText(dateFormat.format(calendar.getTime()));
    }

    private void saveTodo() {
        String title = editTitle.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        Date dueDate = calendar.getTime();

        // 제목 검증
        if (title.isEmpty()) {
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        // 중요도 확인
        int priority;
        int selectedPriorityId = radioGroupPriority.getCheckedRadioButtonId();
        if (selectedPriorityId == R.id.radio_priority_low) {
            priority = 1;
        } else if (selectedPriorityId == R.id.radio_priority_medium) {
            priority = 2;
        } else {
            priority = 3;
        }

        // 완료 여부
        boolean isCompleted = checkBoxCompleted.isChecked();

        // Todo 객체 업데이트
        currentTodo.setTitle(title);
        currentTodo.setDescription(description);
        currentTodo.setDueDate(dueDate);
        currentTodo.setPriority(priority);
        currentTodo.setCompleted(isCompleted);

        // 저장
        if (isEditMode) {
            databaseHelper.updateTodo(currentTodo);
            Toast.makeText(this, "할 일이 업데이트되었습니다", Toast.LENGTH_SHORT).show();
        } else {
            long id = databaseHelper.addTodo(currentTodo);
            currentTodo.setId(id);
            Toast.makeText(this, "새 할 일이 추가되었습니다", Toast.LENGTH_SHORT).show();
        }

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEditMode) {
            getMenuInflater().inflate(R.menu.menu_todo_detail, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            showDeleteConfirmationDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("할 일 삭제")
                .setMessage("이 할 일을 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    databaseHelper.deleteTodo(currentTodo.getId());
                    Toast.makeText(TodoDetailActivity.this, "할 일이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                })
                .setNegativeButton("취소", null)
                .show();
    }
}