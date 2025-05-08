package com.example.hotelsurvey;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        barChart = findViewById(R.id.barChart);

        int[] results = getIntent().getIntArrayExtra("SURVEY_RESULTS");

        if (results == null) return;

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < results.length; i++) {
            int answer = results[i];
            if (answer != -1) {
                entries.add(new BarEntry(i + 1, answer + 1)); // 0~4 -> 1~5
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "응답 점수 (1~5점)");
        dataSet.setColor(getResources().getColor(R.color.purple_700));
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(6f);

        barChart.getAxisRight().setEnabled(false);

        barChart.invalidate(); // Refresh
    }
}
