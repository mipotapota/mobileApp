package com.example.videolistapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private ListView listView;
    private List<String> videoList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        videoList = new ArrayList<>();

        // 권한 확인 및 요청
        if (checkPermission()) {
            loadVideoFiles();
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        // Android 13 (API 33) 이상에서는 READ_MEDIA_VIDEO 권한 사용
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED;
        } else {
            // Android 12 이하에서는 READ_EXTERNAL_STORAGE 권한 사용
            return ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_MEDIA_VIDEO},
                    PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadVideoFiles();
            } else {
                Toast.makeText(this, "동영상 파일에 접근하려면 권한이 필요합니다.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadVideoFiles() {
        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        // 조회할 컬럼들 지정
        String[] projection = {
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION
        };

        // 파일명으로 정렬
        String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " ASC";

        Cursor cursor = resolver.query(uri, projection, null, null, sortOrder);

        if (cursor != null && cursor.moveToFirst()) {
            videoList.clear();

            do {
                // 동영상 파일명 가져오기
                int nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                String fileName = cursor.getString(nameIndex);

                // 추가 정보도 가져올 수 있음 (선택사항)
                int sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
                long fileSize = cursor.getLong(sizeIndex);

                int durationIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                long duration = cursor.getLong(durationIndex);

                // 파일 크기와 재생시간 정보를 포함한 문자열 생성
                String displayText = fileName;
                if (fileSize > 0) {
                    displayText += " (" + formatFileSize(fileSize) + ")";
                }

                videoList.add(displayText);

            } while (cursor.moveToNext());

            cursor.close();

            // 어댑터 설정
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    videoList);
            listView.setAdapter(adapter);

            // 결과 표시
            if (videoList.isEmpty()) {
                Toast.makeText(this, "저장된 동영상 파일이 없습니다.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, videoList.size() + "개의 동영상 파일을 찾았습니다.",
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "동영상 파일을 찾을 수 없습니다.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // 파일 크기를 읽기 쉬운 형태로 변환
    private String formatFileSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.1f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.1f MB", size / (1024.0 * 1024.0));
        } else {
            return String.format("%.1f GB", size / (1024.0 * 1024.0 * 1024.0));
        }
    }
}