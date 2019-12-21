package com.example.sarthak.pogame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        int score = 3 - extras.getInt("score");
        ImageView imageView = findViewById(R.id.imageView);
        if(score == 1) {
            imageView.setImageResource(R.drawable.spade);
        } else if(score == 2) {
            imageView.setImageResource(R.drawable.clubs);
        } else if(score == 3) {
            imageView.setImageResource(R.drawable.diamond);
        }

    }
}
