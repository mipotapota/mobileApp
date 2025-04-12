package com.example.ratingbartest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private TextView value;
    private Button button;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
            ratingBar = findViewById(R.id.ratingBar);
            value = (TextView) findViewById(R.id.textView1);
            button = (Button) findViewById(R.id.button1);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    float rating = ratingBar.getRating();
                    value.setText(String.valueOf("SCORE=" + rating));
                }
            });

    }
}