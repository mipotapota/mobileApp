package com.example.contextmenuclaude;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 레이아웃과 텍스트뷰 초기화
        mainLayout = findViewById(R.id.main_layout);
        textView = findViewById(R.id.text_view);

        // 텍스트뷰에 컨텍스트 메뉴 등록
        registerForContextMenu(textView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // 컨텍스트 메뉴 아이템 추가
        menu.setHeaderTitle("배경색 선택");
        menu.add(0, 1, 0, "빨간색");
        menu.add(0, 2, 0, "초록색");
        menu.add(0, 3, 0, "파란색");
        menu.add(0, 4, 0, "노란색");
        menu.add(0, 5, 0, "흰색");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 선택된 메뉴 아이템에 따라 배경색 변경
        switch (item.getItemId()) {
            case 1: // 빨간색
                mainLayout.setBackgroundColor(Color.RED);
                return true;
            case 2: // 초록색
                mainLayout.setBackgroundColor(Color.GREEN);
                return true;
            case 3: // 파란색
                mainLayout.setBackgroundColor(Color.BLUE);
                return true;
            case 4: // 노란색
                mainLayout.setBackgroundColor(Color.YELLOW);
                return true;
            case 5: // 흰색
                mainLayout.setBackgroundColor(Color.WHITE);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}