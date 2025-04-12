package com.example.electriccircuit;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggleButton1;
    private ToggleButton toggleButton2;
    private ImageView lightBulb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        toggleButton1 = findViewById(R.id.toggleButton1);
        toggleButton2 = findViewById(R.id.toggleButton2);
        lightBulb = findViewById(R.id.lightBulb);

        //토글 버튼 상태 변경 리스너 설정
        toggleButton1.setOnCheckedChangeListener(toggleListener);
        toggleButton2.setOnCheckedChangeListener(toggleListener);
    }
    private CompoundButton.OnCheckedChangeListener  toggleListener = new CompoundButton.OnCheckedChangeListener(){
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
            //두 토글 버튼의 상태를 확인하여 전구 상태 업데이트
            updateLightBulbState();
        }
    };

    private void updateLightBulbState(){
        boolean isToggle10n = toggleButton1.isChecked();
        boolean isToggle20n = toggleButton2.isChecked();

        if(isToggle10n && isToggle20n){
            // 두 토글 버튼이 모두 On 상태일 때 전구를 켭니다.
            lightBulb.setImageResource(R.drawable.on_2);
        }else{
            lightBulb.setImageResource(R.drawable.off_2);
        }
    }
}