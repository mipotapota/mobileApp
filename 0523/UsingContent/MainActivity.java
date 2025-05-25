package com.example.usingcontent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView imageCounter, imageInfo;
    private Button permissionButton, previousButton, nextButton;
    private List<ImageInfo> imageList;
    private int currentImageIndex = 0;
    private static final int PERMISSION_REQUEST_CODE = 1;

    // 권한 배열
    private String[] requiredPermissions;

    // 이미지 정보를 담는 내부 클래스
    private static class ImageInfo {
        String path;
        String name;
        long size;
        long dateAdded;

        ImageInfo(String path, String name, long size, long dateAdded) {
            this.path = path;
            this.name = name;
            this.size = size;
            this.dateAdded = dateAdded;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        initializeViews();
        imageList = new ArrayList<>();

        // Android 버전에 따른 권한 설정
        setupPermissions();

        // 권한 확인 및 요청
        checkAndRequestPermissions();
    }

    private void initializeViews() {
        imageView = findViewById(R.id.picture);
        imageCounter = findViewById(R.id.imageCounter);
        imageInfo = findViewById(R.id.imageInfo);
        permissionButton = findViewById(R.id.permissionButton);
        previousButton = findViewById(R.id.previous);
        nextButton = findViewById(R.id.next);
    }

    private void setupPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API 33+)
            requiredPermissions = new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES
            };
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0+ (API 23+)
            requiredPermissions = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
        } else {
            // Android 6.0 미만에서는 런타임 권한이 필요 없음
            requiredPermissions = new String[]{};
        }
    }

    private void checkAndRequestPermissions() {
        if (requiredPermissions.length == 0) {
            // Android 6.0 미만
            loadAllImages();
            return;
        }

        List<String> permissionsToRequest = new ArrayList<>();

        // 각 권한이 허용되었는지 확인
        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (permissionsToRequest.isEmpty()) {
            // 모든 권한이 이미 허용됨
            loadAllImages();
            updateUIState(true);
        } else {
            // 권한 요청
            ActivityCompat.requestPermissions(this,
                    permissionsToRequest.toArray(new String[0]),
                    PERMISSION_REQUEST_CODE);
            updateUIState(false);
        }
    }

    private void updateUIState(boolean hasPermission) {
        if (hasPermission) {
            permissionButton.setVisibility(View.GONE);
            previousButton.setEnabled(true);
            nextButton.setEnabled(true);
        } else {
            permissionButton.setVisibility(View.VISIBLE);
            previousButton.setEnabled(false);
            nextButton.setEnabled(false);
            updateImageCounter("권한이 필요합니다");
            updateImageInfo("저장소 접근 권한을 허용해주세요");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;
            StringBuilder deniedPermissions = new StringBuilder();

            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    deniedPermissions.append(getPermissionName(permissions[i])).append(" ");
                }
            }

            if (allGranted) {
                Toast.makeText(this, "모든 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
                loadAllImages();
                updateUIState(true);
            } else {
                Toast.makeText(this,
                        "이미지를 보려면 저장소 권한이 필요합니다.\n거부된 권한: " + deniedPermissions.toString(),
                        Toast.LENGTH_LONG).show();
                updateUIState(false);
            }
        }
    }

    private String getPermissionName(String permission) {
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                return "저장소 읽기";
            case Manifest.permission.READ_MEDIA_IMAGES:
                return "미디어 이미지 읽기";
            default:
                return permission;
        }
    }

    private void loadAllImages() {
        try {
            imageList.clear();

            // 내부 저장소와 외부 저장소의 모든 이미지 로드
            int internalCount = loadImagesFromUri(MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            int externalCount = loadImagesFromUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            if (!imageList.isEmpty()) {
                currentImageIndex = 0;
                displayCurrentImage();
                String message = String.format("총 %d개의 이미지를 찾았습니다.\n(내부: %d개, 외부: %d개)",
                        imageList.size(), internalCount, externalCount);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "장치에 이미지가 없습니다!", Toast.LENGTH_LONG).show();
                updateImageCounter("이미지가 없습니다");
                updateImageInfo("갤러리에 이미지를 추가해보세요");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "이미지 로드 중 오류가 발생했습니다: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            updateImageCounter("로드 오류 발생");
            updateImageInfo("");
        }
    }

    private int loadImagesFromUri(Uri uri) {
        Cursor cursor = null;
        int loadedCount = 0;

        try {
            String[] projection = new String[]{
                    MediaStore.Images.ImageColumns._ID,
                    MediaStore.Images.ImageColumns.DATA,
                    MediaStore.Images.ImageColumns.DISPLAY_NAME,
                    MediaStore.Images.ImageColumns.SIZE,
                    MediaStore.Images.ImageColumns.DATE_ADDED
            };

            cursor = getContentResolver().query(
                    uri,
                    projection,
                    MediaStore.Images.ImageColumns.SIZE + " > 0", // 0바이트 파일 제외
                    null,
                    MediaStore.Images.ImageColumns.DATE_ADDED + " DESC"
            );

            if (cursor != null && cursor.getCount() > 0) {
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                int nameColumnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME);
                int sizeColumnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE);
                int dateColumnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED);

                while (cursor.moveToNext()) {
                    String imagePath = (dataColumnIndex >= 0) ? cursor.getString(dataColumnIndex) : null;
                    String imageName = (nameColumnIndex >= 0) ? cursor.getString(nameColumnIndex) : "Unknown";
                    long imageSize = (sizeColumnIndex >= 0) ? cursor.getLong(sizeColumnIndex) : 0;
                    long dateAdded = (dateColumnIndex >= 0) ? cursor.getLong(dateColumnIndex) : 0;

                    if (imagePath != null && imageSize > 0) {
                        File imageFile = new File(imagePath);
                        if (imageFile.exists() && imageFile.canRead()) {
                            imageList.add(new ImageInfo(imagePath, imageName, imageSize, dateAdded));
                            loadedCount++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            String storageType = uri.equals(MediaStore.Images.Media.INTERNAL_CONTENT_URI) ? "내부" : "외부";
            Toast.makeText(this, storageType + " 저장소 이미지 로드 실패: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return loadedCount;
    }

    private void displayCurrentImage() {
        if (imageList.isEmpty()) {
            updateImageCounter("이미지가 없습니다");
            updateImageInfo("");
            return;
        }

        try {
            ImageInfo currentImage = imageList.get(currentImageIndex);
            File imageFile = new File(currentImage.path);

            if (imageFile.exists() && imageFile.canRead()) {
                // 메모리 효율적인 이미지 로딩
                Bitmap bitmap = loadOptimizedBitmap(currentImage.path);

                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    updateImageCounter((currentImageIndex + 1) + " / " + imageList.size());

                    // 상세 정보 표시
                    String sizeInfo = formatFileSize(currentImage.size);
                    String info = String.format("%s\n크기: %s", currentImage.name, sizeInfo);
                    updateImageInfo(info);
                } else {
                    Toast.makeText(this, "이미지를 로드할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    updateImageCounter("로드 실패");
                    updateImageInfo("");
                }
            } else {
                Toast.makeText(this, "이미지 파일에 접근할 수 없습니다.", Toast.LENGTH_SHORT).show();
                // 접근할 수 없는 파일은 목록에서 제거
                imageList.remove(currentImageIndex);
                if (currentImageIndex >= imageList.size() && currentImageIndex > 0) {
                    currentImageIndex--;
                }
                if (!imageList.isEmpty()) {
                    displayCurrentImage();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "이미지 표시 중 오류가 발생했습니다: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            updateImageCounter("표시 오류");
            updateImageInfo("");
        }
    }

    private Bitmap loadOptimizedBitmap(String imagePath) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();

            // 먼저 이미지 크기만 확인
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, options);

            // 적절한 샘플 크기 계산 (메모리 사용량 최적화)
            options.inSampleSize = calculateInSampleSize(options, 1000, 800);
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565; // 메모리 사용량 감소

            return BitmapFactory.decodeFile(imagePath, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            // 메모리 부족 시 더 작은 크기로 재시도
            return loadOptimizedBitmap(imagePath, 4);
        }
    }

    private Bitmap loadOptimizedBitmap(String imagePath, int sampleSize) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = sampleSize;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            return BitmapFactory.decodeFile(imagePath, options);
        } catch (OutOfMemoryError e) {
            if (sampleSize < 8) {
                return loadOptimizedBitmap(imagePath, sampleSize * 2);
            }
            return null;
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private String formatFileSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.1f KB", bytes / 1024.0);
        } else {
            return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        }
    }

    private void updateImageCounter(String text) {
        imageCounter.setText(text);
    }

    private void updateImageInfo(String text) {
        if (imageInfo != null) {
            imageInfo.setText(text);
        }
    }

    public void displayPreviousImage(View v) {
        if (imageList.isEmpty()) {
            Toast.makeText(this, "표시할 이미지가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentImageIndex > 0) {
            currentImageIndex--;
        } else {
            // 순환: 마지막 이미지로 이동
            currentImageIndex = imageList.size() - 1;
            Toast.makeText(this, "마지막 이미지로 이동했습니다.", Toast.LENGTH_SHORT).show();
        }
        displayCurrentImage();
    }

    public void displayNextImage(View v) {
        if (imageList.isEmpty()) {
            Toast.makeText(this, "표시할 이미지가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentImageIndex < imageList.size() - 1) {
            currentImageIndex++;
        } else {
            // 순환: 첫 번째 이미지로 이동
            currentImageIndex = 0;
            Toast.makeText(this, "첫 번째 이미지로 이동했습니다.", Toast.LENGTH_SHORT).show();
        }
        displayCurrentImage();
    }

    public void refreshImages(View v) {
        Toast.makeText(this, "이미지 목록을 새로고침합니다...", Toast.LENGTH_SHORT).show();
        checkAndRequestPermissions();
    }

    public void requestPermissionsManually(View v) {
        checkAndRequestPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 앱이 다시 활성화될 때 권한 상태 확인
        if (imageList.isEmpty()) {
            checkAndRequestPermissions();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 메모리 정리
        if (imageView != null && imageView.getDrawable() != null) {
            imageView.setImageDrawable(null);
        }
    }
}
