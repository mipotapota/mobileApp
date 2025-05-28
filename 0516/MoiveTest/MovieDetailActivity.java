package com.example.movietest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MovieDetailActivity extends Activity {
    private TextView titleText, yearText, directorText, ratingText, countryText;
    private Button editButton, deleteButton;
    private DatabaseHelper dbHelper;
    private Movie currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        dbHelper = new DatabaseHelper(this);

        titleText = findViewById(R.id.titleText);
        yearText = findViewById(R.id.yearText);
        directorText = findViewById(R.id.directorText);
        ratingText = findViewById(R.id.ratingText);
        countryText = findViewById(R.id.countryText);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);

        int movieId = getIntent().getIntExtra("movie_id", -1);
        loadMovieData(movieId);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailActivity.this, AddEditMovieActivity.class);
                intent.putExtra("movie_id", currentMovie.getId());
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentMovie != null) {
            loadMovieData(currentMovie.getId());
        }
    }

    private void loadMovieData(int movieId) {
        List<Movie> movies = dbHelper.getAllMovies();
        for (Movie movie : movies) {
            if (movie.getId() == movieId) {
                currentMovie = movie;
                titleText.setText("제목: " + movie.getTitle());
                yearText.setText("연도: " + movie.getYear());
                directorText.setText("감독: " + movie.getDirector());
                ratingText.setText("평점: " + movie.getRating());
                countryText.setText("국가: " + movie.getCountry());
                break;
            }
        }
    }

    private void showDeleteConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("영화 삭제");
        builder.setMessage("정말로 이 영화를 삭제하시겠습니까?");
        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.deleteMovie(currentMovie);
                Toast.makeText(MovieDetailActivity.this, "영화가 삭제되었습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("취소", null);
        builder.show();
    }
}
