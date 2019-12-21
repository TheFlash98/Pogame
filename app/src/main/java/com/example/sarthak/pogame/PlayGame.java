package com.example.sarthak.pogame;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.graphics.Path;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.PathInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class PlayGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

    }

    public void animate(View view) {
        ImageView carView  = (ImageView)(findViewById(R.id.car));
        ObjectAnimator animation = ObjectAnimator.ofFloat(carView, "translationY", -2000f);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Path path = new Path();
//            path.moveTo(carView.getX(), carView.getY());
//            path.cubicTo(100, -100, -500, -500, 0, -200);
//            PathInterpolator pathInterpolator = new PathInterpolator(path);
//            animation.setInterpolator(pathInterpolator);
//        }
        animation.setDuration(6000);
        animation.start();

//        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -1000);
//        animation.setDuration(1000);
//        animation.setFillAfter(false);
//        view.startAnimation(animation);
    }
}

