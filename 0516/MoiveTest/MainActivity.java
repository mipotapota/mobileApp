package com.example.movietest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import java.util.List;

public class MainActivity extends Activity {
    private ListView listView;
    private ArrayAdapter<Movie> adapter;
    private DatabaseHelper dbHelper;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        Button addButton = findViewById(R.id.addButton);

        // 영화 목록 로드
        loadMovies();

        // 영화 추가 버튼 클릭 이벤트
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditMovieActivity.class);
                startActivity(intent);
            }
        });

        // 리스트뷰 아이템 클릭 이벤트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = movies.get(position);
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra("movie_id", selectedMovie.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMovies(); // 액티비티가 다시 시작될 때마다 목록 새로고침
    }

    private void loadMovies() {
        movies = dbHelper.getAllMovies();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movies);
        listView.setAdapter(adapter);
    }
}