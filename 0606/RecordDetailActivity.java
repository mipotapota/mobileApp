package com.example.realapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecordDetailActivity extends AppCompatActivity {

    // 수정: btnEditSave 제거, btnSaveChanges를 편집/저장 버튼으로 사용
    private ImageButton btnBack;
    private Button btnSaveChanges, btnDelete;
    private ImageButton btnMood1, btnMood2, btnMood3, btnMood4, btnMood5;
    private EditText etMoodDescription, etTodayEvents, etFeelings;
    private TextView tvDate;
    private SharedPreferences sharedPreferences;

    private String currentTimestamp;
    private int selectedMood = -1;
    private ImageButton[] moodButtons;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("MoodRecords", MODE_PRIVATE);

        // Intent에서 timestamp 받기
        currentTimestamp = getIntent().getStringExtra("timestamp");
        if (currentTimestamp == null) {
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 뷰 초기화
        initializeViews();

        // 기록 데이터 로드
        loadRecordData();

        // 버튼 리스너 설정
        setupButtonListeners();
    }

    private void initializeViews() {
        // 수정: btnEditSave 제거
        btnBack = findViewById(R.id.btn_back);           // ImageButton
        btnDelete = findViewById(R.id.btn_delete);       // Button
        btnSaveChanges = findViewById(R.id.btn_save_changes); // Button

        btnMood1 = findViewById(R.id.btn_mood_1);
        btnMood2 = findViewById(R.id.btn_mood_2);
        btnMood3 = findViewById(R.id.btn_mood_3);
        btnMood4 = findViewById(R.id.btn_mood_4);
        btnMood5 = findViewById(R.id.btn_mood_5);

        moodButtons = new ImageButton[]{btnMood1, btnMood2, btnMood3, btnMood4, btnMood5};

        etMoodDescription = findViewById(R.id.et_mood_description);
        etTodayEvents = findViewById(R.id.et_today_events);
        etFeelings = findViewById(R.id.et_feelings);
        tvDate = findViewById(R.id.tv_date);
    }

    private void loadRecordData() {
        String date = sharedPreferences.getString(currentTimestamp + "_date", "");
        int mood = sharedPreferences.getInt(currentTimestamp + "_mood", 1);
        String description = sharedPreferences.getString(currentTimestamp + "_description", "");
        String events = sharedPreferences.getString(currentTimestamp + "_events", "");
        String feelings = sharedPreferences.getString(currentTimestamp + "_feelings", "");

        // 데이터 표시
        tvDate.setText(date);
        selectedMood = mood;
        displayMood(mood);
        etMoodDescription.setText(description);
        etTodayEvents.setText(events);
        etFeelings.setText(feelings);
    }

    private void displayMood(int mood) {
        // 모든 버튼을 기본 상태로 리셋
        for (ImageButton btn : moodButtons) {
            btn.setSelected(false);
            btn.setAlpha(0.5f); // 기본 상태: 더 어둡게
            btn.clearColorFilter(); // 컬러 필터 완전 제거
        }

        // 선택된 기분 버튼 활성화
        if (mood >= 1 && mood <= 5) {
            ImageButton selectedButton = moodButtons[mood - 1];
            selectedButton.setSelected(true);
            selectedButton.setAlpha(1.0f); // 완전 불투명으로 선택된 것 표시
            // 컬러 필터 없이 알파값 차이만으로 선택 상태 표시
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

        // 삭제 버튼
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmDialog();
            }
        });

        // 저장 버튼 (편집/저장 기능 통합) - 수정: 처음에는 "편집" 모드로 시작
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditMode) {
                    // 저장 모드
                    saveChanges();
                } else {
                    // 편집 모드로 전환
                    toggleEditMode();
                }
            }
        });

        // 기분 버튼들
        for (int i = 0; i < moodButtons.length; i++) {
            final int moodValue = i + 1;
            moodButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEditMode) {
                        selectMood(moodValue);
                    }
                }
            });
        }
    }

    private void toggleEditMode() {
        isEditMode = !isEditMode;

        // EditText 활성화/비활성화
        etMoodDescription.setEnabled(isEditMode);
        etTodayEvents.setEnabled(isEditMode);
        etFeelings.setEnabled(isEditMode);

        // 기분 버튼 활성화/비활성화
        for (ImageButton btn : moodButtons) {
            btn.setEnabled(isEditMode);
        }

        // UI 업데이트 - 수정: 하단 버튼들만 제어
        if (isEditMode) {
            btnSaveChanges.setText("저장");
            btnSaveChanges.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        } else {
            btnSaveChanges.setText("편집");
            btnSaveChanges.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }
    }

    private void selectMood(int mood) {
        selectedMood = mood;

        // 모든 버튼을 기본 상태로 리셋
        for (ImageButton btn : moodButtons) {
            btn.setSelected(false);
            btn.setAlpha(0.7f); // 기본 상태: 약간 어둡게
            btn.clearColorFilter(); // 컬러 필터 제거
        }

        // 선택된 버튼만 밝게 빛나게
        ImageButton selectedButton = moodButtons[mood - 1];
        selectedButton.setSelected(true);
        selectedButton.setAlpha(1.0f); // 완전 불투명

        // 투명한 흰색 오버레이로 글로우 효과 (이미지는 여전히 보임)
        selectedButton.setColorFilter(Color.argb(80, 255, 255, 255), PorterDuff.Mode.SRC_ATOP);
    }

    // 글로우 효과 적용 메소드
    private void applyGlowEffect(ImageButton button) {
        // 방법 1: ColorFilter를 사용한 밝은 효과
        button.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        button.setAlpha(1.0f);

        // 방법 2: 배경에 흰색 원형 추가 (더 강한 효과)
        button.setBackground(getResources().getDrawable(R.drawable.mood_button_glow));
    }

    // 글로우 효과 제거 메소드
    private void removeGlowEffect(ImageButton button) {
        button.clearColorFilter();
        button.setAlpha(0.8f); // 기본 상태는 약간 투명하게
        button.setBackground(getResources().getDrawable(R.drawable.mood_button_selector));
    }

    private void saveChanges() {
        String moodDescription = etMoodDescription.getText().toString().trim();
        String todayEvents = etTodayEvents.getText().toString().trim();
        String feelings = etFeelings.getText().toString().trim();

        // 입력 검증
        if (selectedMood == -1 || moodDescription.isEmpty() || todayEvents.isEmpty() || feelings.isEmpty()) {
            Toast.makeText(this, "모든 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // SharedPreferences에 저장
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(currentTimestamp + "_mood", selectedMood);
        editor.putString(currentTimestamp + "_description", moodDescription);
        editor.putString(currentTimestamp + "_events", todayEvents);
        editor.putString(currentTimestamp + "_feelings", feelings);
        editor.apply();

        Toast.makeText(this, "변경사항이 저장되었습니다.", Toast.LENGTH_SHORT).show();

        // 편집 모드 종료
        toggleEditMode();
    }

    private void showDeleteConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("기록 삭제");
        builder.setMessage("이 기록을 삭제하시겠습니까?\n삭제된 기록은 복구할 수 없습니다.");

        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteRecord();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void deleteRecord() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 해당 timestamp의 모든 데이터 삭제
        editor.remove(currentTimestamp + "_date");
        editor.remove(currentTimestamp + "_mood");
        editor.remove(currentTimestamp + "_description");
        editor.remove(currentTimestamp + "_events");
        editor.remove(currentTimestamp + "_feelings");

        // timestamps 목록에서도 제거
        String existingTimestamps = sharedPreferences.getString("timestamps", "");

        // 수정: 빈 문자열 체크 추가
        if (!existingTimestamps.isEmpty()) {
            String[] timestampArray = existingTimestamps.split(",");
            StringBuilder newTimestamps = new StringBuilder();

            for (String timestamp : timestampArray) {
                if (!timestamp.trim().equals(currentTimestamp)) {
                    if (newTimestamps.length() > 0) {
                        newTimestamps.append(",");
                    }
                    newTimestamps.append(timestamp);
                }
            }

            editor.putString("timestamps", newTimestamps.toString());
        }

        editor.apply();

        Toast.makeText(this, "기록이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
        finish(); // 화면 종료
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}