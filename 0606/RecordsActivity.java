package com.example.realapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecordsActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private ListView listViewRecords;
    private TextView tvEmptyMessage;
    private SharedPreferences sharedPreferences;
    private List<String> timestampList;
    private List<String> recordTitleList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("MoodRecords", MODE_PRIVATE);

        // 뷰 초기화
        initializeViews();

        // 기록 목록 로드
        loadRecords();

        // 버튼 리스너 설정
        setupButtonListeners();
    }

    @SuppressLint("WrongViewCast")
    private void initializeViews() {
        btnBack = findViewById(R.id.btn_back);
        listViewRecords = findViewById(R.id.list_view_records);
        tvEmptyMessage = findViewById(R.id.tv_empty_message);
    }

    private void loadRecords() {
        timestampList = new ArrayList<>();
        recordTitleList = new ArrayList<>();

        String timestamps = sharedPreferences.getString("timestamps", "");

        if (!timestamps.isEmpty()) {
            String[] timestampArray = timestamps.split(",");

            for (String timestamp : timestampArray) {
                String date = sharedPreferences.getString(timestamp + "_date", "");
                int mood = sharedPreferences.getInt(timestamp + "_mood", 1);
                String description = sharedPreferences.getString(timestamp + "_description", "");

                if (!date.isEmpty()) {
                    timestampList.add(timestamp);
                    String moodEmoji = getMoodEmoji(mood);
                    String title = date + " " + moodEmoji + " " +
                            (description.length() > 20 ? description.substring(0, 20) + "..." : description);
                    recordTitleList.add(title);
                }
            }
        }

        // 리스트뷰 설정
        if (recordTitleList.isEmpty()) {
            tvEmptyMessage.setVisibility(View.VISIBLE);
            listViewRecords.setVisibility(View.GONE);
        } else {
            tvEmptyMessage.setVisibility(View.GONE);
            listViewRecords.setVisibility(View.VISIBLE);

            // **변경된 부분**: 기본 레이아웃 대신 커스텀 레이아웃 사용 (텍스트 색상을 흰색으로 만들기 위해)
            // 기존: adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recordTitleList);
            adapter = new ArrayAdapter<>(this, R.layout.list_item_record, recordTitleList);
            listViewRecords.setAdapter(adapter);
        }
    }

    private String getMoodEmoji(int mood) {
        switch (mood) {
            case 1: return "😢";
            case 2: return "😔";
            case 3: return "😐";
            case 4: return "😊";
            case 5: return "😄";
            default: return "😐";
        }
    }

    private void setupButtonListeners() {
        // 뒤로가기 버튼
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 리스트 아이템 클릭 리스너
        listViewRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTimestamp = timestampList.get(position);

                // 상세보기 화면으로 이동
                Intent intent = new Intent(RecordsActivity.this, RecordDetailActivity.class);
                intent.putExtra("timestamp", selectedTimestamp);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 화면이 다시 활성화될 때 기록 목록 새로고침
        loadRecords();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}