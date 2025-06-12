package com.example.realapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Menu2Activity extends AppCompatActivity {

    private static final String TAG = "Menu2Activity";

    private ImageButton btnBack;
    private Button btnViewRecords, btnSave;
    private ImageButton btnMood1, btnMood2, btnMood3, btnMood4, btnMood5;
    private EditText etMoodDescription, etTodayEvents, etFeelings;
    private TextView tvDate;
    private SharedPreferences sharedPreferences;

    private int selectedMood = -1;
    private ImageButton[] moodButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate 시작");

        try {
            setContentView(R.layout.activity_menu2);
            Log.d(TAG, "레이아웃 설정 완료");

            // SharedPreferences 초기화
            sharedPreferences = getSharedPreferences("MoodRecords", MODE_PRIVATE);
            Log.d(TAG, "SharedPreferences 초기화 완료");

            // 뷰 초기화
            initializeViews();
            Log.d(TAG, "뷰 초기화 완료");

            // 현재 날짜 설정
            setCurrentDate();
            Log.d(TAG, "날짜 설정 완료");

            // 버튼 리스너 설정
            setupButtonListeners();
            Log.d(TAG, "버튼 리스너 설정 완료");

        } catch (Exception e) {
            Log.e(TAG, "onCreate 오류: " + e.getMessage(), e);
            Toast.makeText(this, "초기화 중 오류가 발생했습니다: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initializeViews() {
        Log.d(TAG, "initializeViews 시작");

        try {
            // 필수 뷰들을 안전하게 초기화
            btnBack = findViewById(R.id.btn_back);
            btnSave = findViewById(R.id.btn_save);
            tvDate = findViewById(R.id.tv_date);
            etMoodDescription = findViewById(R.id.et_mood_description);
            etTodayEvents = findViewById(R.id.et_today_events);
            etFeelings = findViewById(R.id.et_feelings);

            Log.d(TAG, "필수 뷰 초기화 완료");

            // 기록 보기 버튼 (선택적)
            btnViewRecords = findViewById(R.id.btn_view_records);
            if (btnViewRecords != null) {
                Log.d(TAG, "기록 보기 버튼 찾음");
            } else {
                Log.w(TAG, "기록 보기 버튼을 찾을 수 없음");
            }

            // 기분 버튼들 초기화
            btnMood1 = findViewById(R.id.btn_mood_1);
            btnMood2 = findViewById(R.id.btn_mood_2);
            btnMood3 = findViewById(R.id.btn_mood_3);
            btnMood4 = findViewById(R.id.btn_mood_4);
            btnMood5 = findViewById(R.id.btn_mood_5);

            Log.d(TAG, "기분 버튼 초기화 완료");

            // null 체크
            if (btnMood1 == null || btnMood2 == null || btnMood3 == null ||
                    btnMood4 == null || btnMood5 == null) {
                throw new RuntimeException("기분 버튼을 찾을 수 없습니다. 레이아웃을 확인하세요.");
            }

            moodButtons = new ImageButton[]{btnMood1, btnMood2, btnMood3, btnMood4, btnMood5};
            Log.d(TAG, "기분 버튼 배열 생성 완료");

            // 필수 뷰들 null 체크
            if (btnBack == null || btnSave == null || tvDate == null ||
                    etMoodDescription == null || etTodayEvents == null || etFeelings == null) {
                throw new RuntimeException("필수 뷰를 찾을 수 없습니다. 레이아웃을 확인하세요.");
            }

        } catch (Exception e) {
            Log.e(TAG, "뷰 초기화 실패: " + e.getMessage(), e);
            throw new RuntimeException("뷰 초기화 실패: " + e.getMessage());
        }
    }

    private void setCurrentDate() {
        Log.d(TAG, "setCurrentDate 시작");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.KOREAN);
            String currentDate = sdf.format(new Date());
            if (tvDate != null) {
                tvDate.setText(currentDate);
                Log.d(TAG, "날짜 설정: " + currentDate);
            }
        } catch (Exception e) {
            Log.e(TAG, "날짜 설정 오류: " + e.getMessage(), e);
            if (tvDate != null) {
                tvDate.setText("날짜 로딩 실패");
            }
        }
    }

    private void setupButtonListeners() {
        Log.d(TAG, "setupButtonListeners 시작");

        try {
            // 뒤로가기 버튼
            if (btnBack != null) {
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "뒤로가기 버튼 클릭");
                        finish();
                    }
                });
                Log.d(TAG, "뒤로가기 버튼 리스너 설정 완료");
            }

            // 기록 보기 버튼
            if (btnViewRecords != null) {
                btnViewRecords.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "기록 보기 버튼 클릭");
                        try {
                            Intent intent = new Intent(Menu2Activity.this, RecordsActivity.class);
                            startActivity(intent);
                            Log.d(TAG, "RecordsActivity 시작");
                        } catch (Exception e) {
                            Log.e(TAG, "RecordsActivity 시작 오류: " + e.getMessage(), e);
                            Toast.makeText(Menu2Activity.this, "기록 페이지를 열 수 없습니다: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                Log.d(TAG, "기록 보기 버튼 리스너 설정 완료");
            } else {
                Log.w(TAG, "기록 보기 버튼이 null이므로 리스너 설정 생략");
            }

            // 기분 버튼들
            if (moodButtons != null) {
                for (int i = 0; i < moodButtons.length; i++) {
                    if (moodButtons[i] != null) {
                        final int moodValue = i + 1;
                        moodButtons[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "기분 버튼 클릭: " + moodValue);
                                selectMood(moodValue);
                            }
                        });
                    }
                }
                Log.d(TAG, "기분 버튼 리스너 설정 완료");
            }

            // 저장 버튼
            if (btnSave != null) {
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "저장 버튼 클릭");
                        saveMoodRecord();
                    }
                });
                Log.d(TAG, "저장 버튼 리스너 설정 완료");
            } else {
                Log.e(TAG, "저장 버튼이 null입니다!");
            }

        } catch (Exception e) {
            Log.e(TAG, "버튼 설정 중 오류: " + e.getMessage(), e);
            Toast.makeText(this, "버튼 설정 중 오류: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void selectMood(int mood) {
        Log.d(TAG, "selectMood 시작: " + mood);

        try {
            selectedMood = mood;

            if (moodButtons == null) {
                Log.e(TAG, "moodButtons가 null입니다");
                return;
            }

            // 모든 버튼을 기본 상태로 리셋
            for (ImageButton btn : moodButtons) {
                if (btn != null) {
                    btn.setSelected(false);
                    btn.setAlpha(0.7f);
                    btn.clearColorFilter();
                }
            }

            // 선택된 버튼만 밝게 빛나게
            if (mood >= 1 && mood <= moodButtons.length && moodButtons[mood - 1] != null) {
                ImageButton selectedButton = moodButtons[mood - 1];
                selectedButton.setSelected(true);
                selectedButton.setAlpha(1.0f);
                selectedButton.setColorFilter(Color.argb(80, 255, 255, 255), PorterDuff.Mode.SRC_ATOP);
                Log.d(TAG, "기분 " + mood + " 선택됨");
            }
        } catch (Exception e) {
            Log.e(TAG, "기분 선택 중 오류: " + e.getMessage(), e);
            Toast.makeText(this, "기분 선택 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveMoodRecord() {
        Log.d(TAG, "saveMoodRecord 시작");

        try {
            String moodDescription = etMoodDescription != null ? etMoodDescription.getText().toString().trim() : "";
            String todayEvents = etTodayEvents != null ? etTodayEvents.getText().toString().trim() : "";
            String feelings = etFeelings != null ? etFeelings.getText().toString().trim() : "";

            Log.d(TAG, "입력 데이터 - 기분: " + selectedMood + ", 설명: " + moodDescription.length() + "자, 사건: " + todayEvents.length() + "자, 감정: " + feelings.length() + "자");

            // 입력 검증
            if (selectedMood == -1) {
                Toast.makeText(this, "기분을 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (moodDescription.isEmpty()) {
                Toast.makeText(this, "기분 설명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (todayEvents.isEmpty()) {
                Toast.makeText(this, "오늘 있었던 일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (feelings.isEmpty()) {
                Toast.makeText(this, "감정과 느낌을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 현재 시간을 키로 사용
            String timestamp = String.valueOf(System.currentTimeMillis());
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

            Log.d(TAG, "저장할 데이터 - timestamp: " + timestamp + ", date: " + date);

            // SharedPreferences에 저장
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(timestamp + "_date", date);
            editor.putInt(timestamp + "_mood", selectedMood);
            editor.putString(timestamp + "_description", moodDescription);
            editor.putString(timestamp + "_events", todayEvents);
            editor.putString(timestamp + "_feelings", feelings);

            // 타임스탬프 목록에 추가
            String existingTimestamps = sharedPreferences.getString("timestamps", "");
            String newTimestamps = timestamp + (existingTimestamps.isEmpty() ? "" : "," + existingTimestamps);
            editor.putString("timestamps", newTimestamps);

            boolean success = editor.commit(); // apply() 대신 commit() 사용으로 즉시 저장 확인

            if (success) {
                Log.d(TAG, "데이터 저장 성공");
                Toast.makeText(this, "기록이 저장되었습니다.", Toast.LENGTH_SHORT).show();

                // 기록 목록 화면으로 이동
                try {
                    Intent intent = new Intent(Menu2Activity.this, RecordsActivity.class);
                    startActivity(intent);
                    Log.d(TAG, "RecordsActivity로 이동");
                } catch (Exception e) {
                    Log.e(TAG, "RecordsActivity 이동 오류: " + e.getMessage(), e);
                    // RecordsActivity가 없으면 현재 화면 유지하거나 메인으로 돌아가기
                    finish();
                }
            } else {
                Log.e(TAG, "데이터 저장 실패");
                Toast.makeText(this, "저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e(TAG, "저장 중 오류: " + e.getMessage(), e);
            Toast.makeText(this, "저장 중 오류가 발생했습니다: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed 호출");
        super.onBackPressed();
        finish();
    }
}