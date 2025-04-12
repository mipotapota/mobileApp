package com.example.buttonevent3;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        imageView2 = findViewById(R.id.imageView2);

        Button B1 = findViewById(R.id.B1);
        Button B2 = findViewById(R.id.B2);
        Button B3 = findViewById(R.id.B3);
        Button B4 = findViewById(R.id.B4);

        B1.setOnClickListener(view -> imageView2(Color.RED));
        B2.setOnClickListener(view -> imageView2(Color.BLUE));
        B3.setOnClickListener(view -> imageView2(Color.GREEN));
        B4.setOnClickListener(view -> imageView2(Color.YELLOW));
    }

    private void imageView2(int color) {
        imageView2.setBackgroundColor(color);
    }
}