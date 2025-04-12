package com.example.checkbox5;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView imageView, imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
    }
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        int id = view.getId();
        if(id == R.id.checkBox){
            if(checked)imageView.setImageResource(R.drawable.sand1);
            else imageView.setImageResource(0);
        }
        else if(id==R.id.checkBox2){
            if(checked)imageView2.setImageResource(R.drawable.sand2);
            else imageView2.setImageResource(0);
        }
    }
}