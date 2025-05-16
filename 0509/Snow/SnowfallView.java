package com.example.snow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnowfallView extends View {
    private Bitmap backgroundImage;
    private List<Snowflake> snowflakes;
    private Random random;
    private Paint snowPaint;
    private int snowflakeCount = 100;
    private int snowflakeSize = 8;
    private boolean isRunning = true;
    private long lastUpdateTime;

    public SnowfallView(Context context) {
        super(context);
        init();
    }

    public SnowfallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 배경 이미지 로드
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.snow);

        // 눈송이용 페인트 객체 초기화
        snowPaint = new Paint();
        snowPaint.setColor(Color.WHITE);
        snowPaint.setAntiAlias(true);

        random = new Random();
        snowflakes = new ArrayList<>();

        // 애니메이션 시작
        lastUpdateTime = System.currentTimeMillis();
        startAnimation();
    }

    public void setSnowflakeCount(int count) {
        this.snowflakeCount = count;
        resetSnowflakes();
    }

    public void setSnowflakeSize(int size) {
        this.snowflakeSize = size;
        resetSnowflakes();
    }

    private void resetSnowflakes() {
        snowflakes.clear();
        createSnowflakes();
    }

    private void createSnowflakes() {
        for (int i = 0; i < snowflakeCount; i++) {
            snowflakes.add(createRandomSnowflake());
        }
    }

    private Snowflake createRandomSnowflake() {
        float x = random.nextFloat() * getWidth();
        float y = random.nextFloat() * getHeight();
        float size = (random.nextFloat() * snowflakeSize) + 2;
        float speed = (random.nextFloat() * 3) + 1;
        float horizontalDrift = random.nextFloat() * 2 - 1; // -1 ~ 1 사이 값

        return new Snowflake(x, y, size, speed, horizontalDrift);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (backgroundImage != null) {
            backgroundImage = Bitmap.createScaledBitmap(backgroundImage, w, h, true);
        }
        resetSnowflakes();
    }

    private void startAnimation() {
        post(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    long now = System.currentTimeMillis();
                    long elapsed = now - lastUpdateTime;

                    updateSnowflakes(elapsed / 1000f);
                    lastUpdateTime = now;

                    invalidate();
                    postDelayed(this, 16); // 약 60fps
                }
            }
        });
    }

    private void updateSnowflakes(float deltaTime) {
        int width = getWidth();
        int height = getHeight();

        for (Snowflake snowflake : snowflakes) {
            // 눈송이 위치 업데이트
            snowflake.y += snowflake.speed * deltaTime * 50;
            snowflake.x += snowflake.horizontalDrift * deltaTime * 20;

            // 화면 밖으로 나가면 상단에서 다시 시작
            if (snowflake.y > height) {
                snowflake.y = -snowflake.size;
                snowflake.x = random.nextFloat() * width;
            }

            // 좌우 경계 확인
            if (snowflake.x < -snowflake.size) {
                snowflake.x = width;
            } else if (snowflake.x > width) {
                snowflake.x = -snowflake.size;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 배경 이미지 그리기
        if (backgroundImage != null) {
            canvas.drawBitmap(backgroundImage, 0, 0, null);
        }

        // 눈송이 그리기
        for (Snowflake snowflake : snowflakes) {
            canvas.drawCircle(snowflake.x, snowflake.y, snowflake.size, snowPaint);
        }
    }

    public void pause() {
        isRunning = false;
    }

    public void resume() {
        if (!isRunning) {
            isRunning = true;
            lastUpdateTime = System.currentTimeMillis();
            startAnimation();
        }
    }

    // 눈송이 클래스
    private static class Snowflake {
        float x;
        float y;
        float size;
        float speed;
        float horizontalDrift;

        Snowflake(float x, float y, float size, float speed, float horizontalDrift) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speed = speed;
            this.horizontalDrift = horizontalDrift;
        }
    }
}