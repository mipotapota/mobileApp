package com.example.help;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);
    }

    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.web:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.joytelhotels.com/shinsekai/en/"));
                break;
            case R.id.instargram:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/joytelhotel_shinsekai?utm_source=ig_web_button_share_sheet&igsh=ZDNlZDc0MzIxNw=="));
                break;
            case R.id.map:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.app.goo.gl/ve9VSw1EuJ9LxdDj6"));
                break;
            case R.id.call:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+81)0666362555"));
                break;
            case R.id.survey: // 설문조사 버튼 클릭 시
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLScLd3s9KEj7-1O_R2EHdAMXWDHbUueuoAVgRdPTPV2bJKMPuA/viewform"));
                break;
            case R.id.info: // info 버튼 클릭 시
                intent = new Intent(this, MainActivity5.class); // MainActivity5로 이동
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}