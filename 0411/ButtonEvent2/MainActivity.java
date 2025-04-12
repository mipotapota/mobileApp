package com.example.buttonevent2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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

        Button colorButton1 = findViewById(R.id.B1);
        Button colorButton2 = findViewById(R.id.B2);
        Button colorButton3 = findViewById(R.id.B3);
        Button colorButton4 = findViewById(R.id.B4);

        colorButton1.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
                imageView2(Color.RED);
            }
        });

        colorButton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                imageView2(Color.BLUE);
            }
        });

        colorButton3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                imageView2(Color.GREEN);
            }
        });
        colorButton4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                imageView2(Color.YELLOW);
            }
        });
    }

    private void imageView2(int color){
        imageView2.setBackgroundColor(color);
    }
}