package com.example.diceroller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView diceImage;
    private Button rollButton;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diceImage = findViewById(R.id.imageView);
        rollButton = findViewById(R.id.button);
        random = new Random();

        rollButton.setOnClickListener(v -> rollDice());
    }

    private void rollDice() {
        int randomNumber = random.nextInt(6) + 1;  // 1부터 6까지의 랜덤 숫자 생성

        int drawableResource;
        switch (randomNumber) {
            case 1:
                drawableResource = R.drawable.dice_1;
                break;
            case 2:
                drawableResource = R.drawable.dice_2;
                break;
            case 3:
                drawableResource = R.drawable.dice_3;
                break;
            case 4:
                drawableResource = R.drawable.dice_4;
                break;
            case 5:
                drawableResource = R.drawable.dice_5;
                break;
            default:
                drawableResource = R.drawable.dice_6;
                break;
        }

        diceImage.setImageResource(drawableResource);  // 주사위 이미지 업데이트
    }
}