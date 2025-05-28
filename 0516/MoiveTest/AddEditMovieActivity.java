package com.example.movietest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddEditMovieActivity extends Activity {
    private EditText titleEdit, yearEdit, directorEdit, ratingEdit, countryEdit;
    private Button saveButton;
    private DatabaseHelper dbHelper;
    private Movie currentMovie;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_movie);

        dbHelper = new DatabaseHelper(this);

        titleEdit = findViewById(R.id.titleEdit);
        yearEdit = findViewById(R.id.yearEdit);
        directorEdit = findViewById(R.id.directorEdit);
        ratingEdit = findViewById(R.id.ratingEdit);
        countryEdit = findViewById(R.id.countryEdit);
        saveButton = findViewById(R.id.saveButton);

        // 편집 모드인지 확인
        int movieId = getIntent().getIntExtra("movie_id", -1);
        if (movieId != -1) {
            isEditMode = true;
            loadMovieData(movieId);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMovie();
            }
        });
    }

    private void loadMovieData(int movieId) {
        // 실제 구현에서는 ID로 영화를 찾는 메소드가 필요합니다
        // 여기서는 간단히 구현
        List<Movie> movies = dbHelper.getAllMovies();
        for (Movie movie : movies) {
            if (movie.getId() == movieId) {
                currentMovie = movie;
                titleEdit.setText(movie.getTitle());
                yearEdit.setText(String.valueOf(movie.getYear()));
                directorEdit.setText(movie.getDirector());
                ratingEdit.setText(String.valueOf(movie.getRating()));
                countryEdit.setText(movie.getCountry());
                break;
            }
        }
    }

    private void saveMovie() {
        String title = titleEdit.getText().toString().trim();
        String yearStr = yearEdit.getText().toString().trim();
        String director = directorEdit.getText().toString().trim();
        String ratingStr = ratingEdit.getText().toString().trim();
        String country = countryEdit.getText().toString().trim();

        if (title.isEmpty() || yearStr.isEmpty() || director.isEmpty() ||
                ratingStr.isEmpty() || country.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int year = Integer.parseInt(yearStr);
            double rating = Double.parseDouble(ratingStr);

            if (isEditMode) {
                currentMovie.setTitle(title);
                currentMovie.setYear(year);
                currentMovie.setDirector(director);
                currentMovie.setRating(rating);
                currentMovie.setCountry(country);
                dbHelper.updateMovie(currentMovie);
                Toast.makeText(this, "영화가 수정되었습니다", Toast.LENGTH_SHORT).show();
            } else {
                Movie movie = new Movie(title, year, director, rating, country);
                dbHelper.addMovie(movie);
                Toast.makeText(this, "영화가 추가되었습니다", Toast.LENGTH_SHORT).show();
            }
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "연도와 평점은 숫자로 입력해주세요", Toast.LENGTH_SHORT).show();
        }
    }
}