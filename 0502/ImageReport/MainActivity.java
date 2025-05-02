package kr.co.company.viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    int[] images = {R.drawable.s1, R.drawable.s2, R.drawable.s};
    // 이미지 설명 텍스트 배열 추가
    String[] imageTexts = {"DIT 학생생활관 상벌점 관리 프로그램", "경제 정보 제공/공유 앱(Economy Information Sharing Community (EISC))", "학습자 상호 간의 원활한 소통과 자료 공유를 가능하게 하는 통합 커뮤니케이션 플랫폼"};

    MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        myPagerAdapter = new MyPagerAdapter(MainActivity.this, images, imageTexts);
        viewPager.setAdapter(myPagerAdapter);
    }
}