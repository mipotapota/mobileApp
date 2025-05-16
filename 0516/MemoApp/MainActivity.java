package com.example.memoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MemoAdapter.OnMemoClickListener {

    private RecyclerView recyclerView;
    private MemoAdapter adapter;
    private List<Memo> memoList;
    private MemoDatabase memoDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 툴바 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("스타일리시 메모장");

        // 메모 데이터베이스 초기화
        memoDatabase = new MemoDatabase(this);

        // 리사이클러뷰 설정
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 메모 리스트 초기화
        memoList = new ArrayList<>();

        // 어댑터 설정
        adapter = new MemoAdapter(memoList, this);
        recyclerView.setAdapter(adapter);

        // 플로팅 액션 버튼 설정
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditMemoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMemos();
    }

    private void loadMemos() {
        memoList.clear();
        memoList.addAll(memoDatabase.getAllMemos());
        adapter.notifyDataSetChanged();

        if (memoList.isEmpty()) {
            findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.emptyView).setVisibility(View.GONE);
        }
    }

    @Override
    public void onMemoClick(int position) {
        Memo memo = memoList.get(position);
        Intent intent = new Intent(this, EditMemoActivity.class);
        intent.putExtra("memo_id", memo.getId());
        startActivity(intent);
    }

    @Override
    public void onMemoCopyClick(int position) {
        Memo memo = memoList.get(position);
        Memo newMemo = new Memo();
        newMemo.setTitle(memo.getTitle() + " (복사본)");
        newMemo.setContent(memo.getContent());
        newMemo.setTimestamp(System.currentTimeMillis());

        long newId = memoDatabase.insertMemo(newMemo);
        if (newId != -1) {
            Toast.makeText(this, "메모가 복사되었습니다.", Toast.LENGTH_SHORT).show();
            loadMemos();
        } else {
            Toast.makeText(this, "메모 복사 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}