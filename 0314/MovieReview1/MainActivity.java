package com.example.termpaper01;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar1, float rating, boolean fromUser) {
                textView2.setText("별점 : " + rating);
            }
        });
    }
    // 버튼 클릭 시 호출되는 메서드
    public void onClicked(View view) {
        Toast.makeText(this, "참여해주셔서 감사합니다", Toast.LENGTH_SHORT).show();
        finish(); //메세지 나타난 후 종료
    }
}