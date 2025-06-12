package com.example.realapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Menu1Activity extends AppCompatActivity {

    private TextView tvAdviceText, tvAdviceAuthor;
    private TextView tvMusicTitle, tvMusicArtist, tvMusicGenre;
    private Button btnMusic;
    private ImageButton btnBack;
    private CardView cardMusic;
    private SharedPreferences sharedPreferences;

    // 더 많은 명언 배열 (50개)
    private String[] adviceTexts = {
            "성공은 준비와 기회가 만나는 지점에서 일어난다.",
            "어제는 역사이고, 내일은 미스터리다. 하지만 오늘은 선물이다.",
            "당신이 할 수 있다고 믿든 할 수 없다고 믿든, 당신이 옳다.",
            "위대한 일을 하려면 하는 일을 사랑해야 한다.",
            "실패는 성공으로 가는 계단이다.",
            "꿈을 이루는 방법은 깨어나는 것이다.",
            "변화는 생명이고, 완벽함은 죽음이다.",
            "행복은 습관이다. 그것을 기르라.",
            "오늘 할 수 있는 일을 내일로 미루지 마라.",
            "당신의 한계는 당신의 마음속에만 존재한다.",
            "작은 진전이라도 여전히 진전이다.",
            "어려움 속에서 기회를 찾아라.",
            "당신이 생각하는 것보다 더 강하다.",
            "매일 조금씩 나아지면 큰 변화가 일어난다.",
            "포기하지 마라. 기적은 포기를 모르는 자에게 일어난다.",
            "행동은 모든 성공의 기초가 된다.",
            "완벽한 때를 기다리지 마라. 지금이 완벽한 때다.",
            "당신의 유일한 한계는 당신 자신이다.",
            "오늘의 고통은 내일의 힘이다.",
            "성공하려면 실패를 두려워하지 마라.",
            "꿈은 목표가 있는 소원이다.",
            "인생은 10%의 일어나는 일과 90%의 반응하는 방식이다.",
            "최고의 복수는 대성공이다.",
            "당신이 할 수 있다고 생각하면 이미 반은 성공한 것이다.",
            "기회는 준비된 마음에게만 온다.",
            "성공의 비밀은 시작하는 것이다.",
            "어둠을 저주하지 말고 촛불을 켜라.",
            "위대함은 넘어지지 않는 것이 아니라 넘어질 때마다 일어나는 것이다.",
            "당신의 시간은 한정되어 있다. 다른 사람의 삶을 살며 시간을 낭비하지 마라.",
            "혁신은 리더와 추종자를 구분한다.",
            "미래는 현재 우리가 하는 일에 달려 있다.",
            "불가능한 것은 가능한 것의 의견일 뿐이다.",
            "용기는 두려움의 극복이다.",
            "성공은 최종 목적지가 아니라 여행이다.",
            "자신을 믿어라. 당신은 생각보다 더 많은 것을 알고 있다.",
            "좋은 일은 기다리는 자에게 오지만, 더 좋은 일은 나서는 자에게 온다.",
            "인생에서 가장 중요한 것은 살아있는 동안 계속 배우는 것이다.",
            "당신의 태도가 당신의 고도를 결정한다.",
            "문제는 기회로 위장한 채 온다.",
            "성공한 사람이 되려고 노력하지 말고, 가치 있는 사람이 되려고 노력하라.",
            "삶이 당신에게 레몬을 준다면 레모네이드를 만들어라.",
            "과거는 변경할 수 없지만 미래는 당신의 손에 있다.",
            "꿈을 꾸는 것을 멈추지 마라. 꿈이 없으면 희망도 없다.",
            "성취의 크기는 목표의 크기에 비례한다.",
            "당신의 잠재력을 과소평가하지 마라.",
            "실수를 두려워하지 마라. 실수는 성장의 기회다.",
            "오늘 심은 씨앗이 내일의 열매가 된다.",
            "당신이 원하는 변화가 되어라.",
            "성공은 행복의 열쇠가 아니다. 행복이 성공의 열쇠다.",
            "인생은 짧다. 미소 지으며 살아라."
    };

    private String[] adviceAuthors = {
            "- 센세카 -",
            "- 엘리노어 루스벨트 -",
            "- 헨리 포드 -",
            "- 스티브 잡스 -",
            "- 토마스 에디슨 -",
            "- 파울로 코엘료 -",
            "- 조지 버나드 쇼 -",
            "- 달라이 라마 -",
            "- 벤자민 프랭클린 -",
            "- 어니스트 헤밍웨이 -",
            "- 마야 안젤루 -",
            "- 알버트 아인슈타인 -",
            "- 넬슨 만델라 -",
            "- 공자 -",
            "- 윈스턴 처칠 -",
            "- 파블로 피카소 -",
            "- 마크 트웨인 -",
            "- 오프라 윈프리 -",
            "- 마이클 조던 -",
            "- 로버트 프로스트 -",
            "- 안토니 로빈스 -",
            "- 찰스 다윈 -",
            "- 프랭크 시나트라 -",
            "- 시어도어 루스벨트 -",
            "- 루이 파스퇴르 -",
            "- 마크 저커버그 -",
            "- 존 F. 케네디 -",
            "- 공자 -",
            "- 스티브 잡스 -",
            "- 스티브 잡스 -",
            "- 마하트마 간디 -",
            "- 파울로 코엘료 -",
            "- 마크 트웨인 -",
            "- 존 레논 -",
            "- 스피노자 -",
            "- 링컨 -",
            "- 소크라테스 -",
            "- 지그 지글러 -",
            "- 존 맥스웰 -",
            "- 알버트 아인슈타인 -",
            "- 데일 카네기 -",
            "- 헨리 포드 -",
            "- 월트 디즈니 -",
            "- 빈스 롬바르디 -",
            "- 랄프 월도 에머슨 -",
            "- 아리스토텔레스 -",
            "- 피터 드러커 -",
            "- 마하트마 간디 -",
            "- 달라이 라마 -",
            "- 붓다 -"
    };

    // 음악 추천 배열 (50개) - 제목, 아티스트, 장르 순서
    private String[][] musicRecommendations = {
            {"Shape of You", "Ed Sheeran", "팝"},
            {"愛にできることはまだあるかい", "RADWIMPS", "일본"},
            {"Spring Day", "BTS", "K-POP"},
            {"Clair de Lune", "Claude Debussy", "클래식"},
            {"River Flows in You", "Yiruma", "뉴에이지"},
            {"The Girl from Ipanema", "Stan Getz", "재즈"},
            {"Thinking Out Loud", "Ed Sheeran", "R&B"},
            {"Lose Yourself", "Eminem", "랩"},
            {"Perfect", "Ed Sheeran", "팝"},
            {"Pretender", "Official髭男dism", "일본"},
            {"Dynamite", "BTS", "K-POP"},
            {"Canon in D", "Johann Pachelbel", "클래식"},
            {"Kiss the Rain", "Yiruma", "뉴에이지"},
            {"Fly Me to the Moon", "Frank Sinatra", "재즈"},
            {"All of Me", "John Legend", "R&B"},
            {"Old Town Road", "Lil Nas X", "랩"},
            {"Someone Like You", "Adele", "팝"},
            {"Lemon", "米津玄師", "일본"},
            {"Boy With Luv", "BTS", "K-POP"},
            {"Ave Maria", "Franz Schubert", "클래식"},
            {"May Be", "Yiruma", "뉴에이지"},
            {"Take Five", "Dave Brubeck", "재즈"},
            {"Stay With Me", "Sam Smith", "R&B"},
            {"God's Plan", "Drake", "랩"},
            {"Hello", "Adele", "팝"},
            {"感電", "米津玄師", "일본"},
            {"Life Goes On", "BTS", "K-POP"},
            {"Für Elise", "Ludwig van Beethoven", "클래식"},
            {"Hope", "Yiruma", "뉴에이지"},
            {"Autumn Leaves", "Miles Davis", "재즈"},
            {"Blinding Lights", "The Weeknd", "R&B"},
            {"Sicko Mode", "Travis Scott", "랩"},
            {"Bad Guy", "Billie Eilish", "팝"},
            {"打上花火", "DAOKO", "일본"},
            {"Permission to Dance", "BTS", "K-POP"},
            {"Symphony No. 9", "Ludwig van Beethoven", "클래식"},
            {"Spring Waltz", "Yiruma", "뉴에이지"},
            {"Blue in Green", "Miles Davis", "재즈"},
            {"Watermelon Sugar", "Harry Styles", "R&B"},
            {"HUMBLE.", "Kendrick Lamar", "랩"},
            {"Levitating", "Dua Lipa", "팝"},
            {"夜に駆ける", "YOASOBI", "일본"},
            {"Butter", "BTS", "K-POP"},
            {"The Four Seasons", "Antonio Vivaldi", "클래식"},
            {"Dream", "Yiruma", "뉴에이지"},
            {"So What", "Miles Davis", "재즈"},
            {"When I Was Your Man", "Bruno Mars", "R&B"},
            {"Rockstar", "Post Malone", "랩"},
            {"Don't Start Now", "Dua Lipa", "팝"},
            {"恋", "星野源", "일본"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("AdvicePrefs", MODE_PRIVATE);

        // 뷰 초기화
        initializeViews();

        // 오늘의 명언 표시
        showTodaysAdvice();

        // 버튼 리스너 설정
        setupButtonListeners();
    }

    private void initializeViews() {
        tvAdviceText = findViewById(R.id.tv_advice_text);
        tvAdviceAuthor = findViewById(R.id.tv_advice_author);
        tvMusicTitle = findViewById(R.id.tv_music_title);
        tvMusicArtist = findViewById(R.id.tv_music_artist);
        tvMusicGenre = findViewById(R.id.tv_music_genre);
        btnMusic = findViewById(R.id.btn_music);
        btnBack = findViewById(R.id.btn_back);
        cardMusic = findViewById(R.id.card_music);
    }

    private void setupButtonListeners() {
        // 뒤로가기 버튼 (상단)
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료하고 메인으로 돌아가기
            }
        });

        // 음악 추천 버튼 (하단)
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTodaysMusic();
            }
        });
    }

    private void showTodaysAdvice() {
        String today = getCurrentDate();
        String savedDate = sharedPreferences.getString("saved_date", "");
        int savedIndex = sharedPreferences.getInt("advice_index", -1);

        // 오늘 날짜와 저장된 날짜가 다르거나 저장된 인덱스가 없으면 새로운 명언 선택
        if (!today.equals(savedDate) || savedIndex == -1) {
            // 새로운 랜덤 인덱스 생성
            Random random = new Random();
            int newIndex = random.nextInt(adviceTexts.length);

            // SharedPreferences에 저장
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("saved_date", today);
            editor.putInt("advice_index", newIndex);
            editor.apply();

            savedIndex = newIndex;
        }

        // 명언과 작가 표시
        tvAdviceText.setText(adviceTexts[savedIndex]);
        tvAdviceAuthor.setText(adviceAuthors[savedIndex]);
    }

    private void showTodaysMusic() {
        String today = getCurrentDate();
        String savedMusicDate = sharedPreferences.getString("saved_music_date", "");
        int savedMusicIndex = sharedPreferences.getInt("music_index", -1);

        // 오늘 날짜와 저장된 음악 날짜가 다르거나 저장된 인덱스가 없으면 새로운 음악 선택
        if (!today.equals(savedMusicDate) || savedMusicIndex == -1) {
            // 새로운 랜덤 인덱스 생성
            Random random = new Random();
            int newIndex = random.nextInt(musicRecommendations.length);

            // SharedPreferences에 저장
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("saved_music_date", today);
            editor.putInt("music_index", newIndex);
            editor.apply();

            savedMusicIndex = newIndex;
        }

        // 음악 정보 표시
        String[] musicInfo = musicRecommendations[savedMusicIndex];
        tvMusicTitle.setText(musicInfo[0]);
        tvMusicArtist.setText(musicInfo[1]);
        tvMusicGenre.setText(musicInfo[2]);

        // 음악 카드 표시
        cardMusic.setVisibility(View.VISIBLE);

        // 버튼 텍스트 변경
        btnMusic.setText("🎵 오늘의 음악 보기");
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}