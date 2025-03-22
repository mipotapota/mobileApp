package com.example.edittexttest;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    EditText eText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        eText = (EditText) findViewById(R.id.edittext);
        textView = (TextView) findViewById(R.id.textView);
    }
    public void onClicked(View v){
        String str = eText.getText().toString();
        textView.setText(str);
    }
}