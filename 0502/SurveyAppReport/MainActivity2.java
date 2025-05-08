package com.example.hotelsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private String[] questions = {
            "1. 호텔 체크인 과정은 얼마나 만족스러웠습니까?",
            "2. 객실의 청결도는 어떻게 평가하십니까?",
            "3. 직원의 서비스 친절도는 어떠했습니까?",
            "4. 호텔 시설(수영장, 헬스장 등)에 대한 만족도는 어떠합니까?",
            "5. 호텔 식당 음식 품질에 대해 어떻게 생각하십니까?",
            "6. 호텔 주변 환경 및 위치의 편리성은 어떠했습니까?",
            "7. 가격 대비 가치는 어떻게 평가하십니까?",
            "8. 와이파이 및 기술 설비에 대한 만족도는 어떠합니까?",
            "9. 소음 관리 및 수면 품질에 대해 어떻게 평가하십니까?",
            "10. 다시 이 호텔을 방문하거나 다른 사람에게 추천할 의향이 있으십니까?",
            "11. 호텔 예약 과정은 얼마나 용이했습니까?",
            "12. 특별 요청에 대한 호텔 대응은 어떠했습니까?"
    };

    private int currentQuestionIndex = 0;
    private int[] answers;

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button prevButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);

        answers = new int[questions.length];
        for (int i = 0; i < answers.length; i++) {
            answers[i] = -1; // -1 means unanswered
        }

        displayQuestion(currentQuestionIndex);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAnswer();
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    displayQuestion(currentQuestionIndex);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionsRadioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(MainActivity2.this, "항목을 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveAnswer();

                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                    displayQuestion(currentQuestionIndex);
                } else {
                    // Survey completed, show results
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("SURVEY_RESULTS", answers);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void displayQuestion(int questionIndex) {
        questionTextView.setText(questions[questionIndex]);
        optionsRadioGroup.clearCheck();

        // Set the previously selected answer if any
        if (answers[questionIndex] != -1) {
            RadioButton radioButton = (RadioButton) optionsRadioGroup.getChildAt(answers[questionIndex]);
            radioButton.setChecked(true);
        }

        // Update button text for the last question
        if (questionIndex == questions.length - 1) {
            nextButton.setText("완료");
        } else {
            nextButton.setText("다음");
        }

        // Update prev button visibility
        prevButton.setVisibility(questionIndex > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    private void saveAnswer() {
        int selectedId = optionsRadioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            int radioIndex = optionsRadioGroup.indexOfChild(selectedRadioButton);
            answers[currentQuestionIndex] = radioIndex;
        }
    }
}