package com.example.memoapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditMemoActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editContent;
    private MemoDatabase memoDatabase;
    private long memoId = -1;
    private Memo currentMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);

        // 툴바 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("메모 편집");

        // UI 초기화
        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);

        // 데이터베이스 초기화
        memoDatabase = new MemoDatabase(this);

        // 전달받은 메모 ID 확인
        memoId = getIntent().getLongExtra("memo_id", -1);

        if (memoId != -1) {
            // 기존 메모 편집 모드
            currentMemo = memoDatabase.getMemoById(memoId);
            if (currentMemo != null) {
                editTitle.setText(currentMemo.getTitle());
                editContent.setText(currentMemo.getContent());
            }
        } else {
            // 새 메모 생성 모드
            getSupportActionBar().setTitle("새 메모");
            currentMemo = new Memo();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            saveMemoAndFinish();
            return true;
        } else if (id == R.id.action_save) {
            saveMemoAndFinish();
            return true;
        } else if (id == R.id.action_delete) {
            showDeleteConfirmationDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveMemoAndFinish();
    }

    private void saveMemoAndFinish() {
        String title = editTitle.getText().toString().trim();
        String content = editContent.getText().toString().trim();

        if (title.isEmpty() && content.isEmpty()) {
            // 내용이 없으면 그냥 종료
            finish();
            return;
        }

        if (title.isEmpty()) {
            // 제목이 없으면 내용의 첫 줄을 제목으로 사용
            String[] lines = content.split("\n", 2);
            title = lines[0];
            if (title.length() > 30) {
                title = title.substring(0, 30) + "...";
            }
        }

        currentMemo.setTitle(title);
        currentMemo.setContent(content);
        currentMemo.setTimestamp(System.currentTimeMillis());

        boolean success;
        if (memoId == -1) {
            // 새 메모 저장
            success = memoDatabase.insertMemo(currentMemo) != -1;
        } else {
            // 기존 메모 업데이트
            success = memoDatabase.updateMemo(currentMemo);
        }

        if (success) {
            Toast.makeText(this, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "메모 저장 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private void showDeleteConfirmationDialog() {
        if (memoId == -1) {
            // 새 메모는 그냥 종료
            finish();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("메모 삭제")
                .setMessage("이 메모를 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    memoDatabase.deleteMemo(memoId);
                    Toast.makeText(EditMemoActivity.this, "메모가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("취소", null)
                .show();
    }
}