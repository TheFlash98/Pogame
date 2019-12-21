package com.example.sarthak.pogame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.PathInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.view.animation.Animation.AnimationListener;

public class PlayGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

    }

    public void animate(View view) {
        ImageView carView  = (ImageView)(findViewById(R.id.car));
        ImageView potholeView = (ImageView)(findViewById(R.id.pothole));
        ObjectAnimator animation = ObjectAnimator.ofFloat(carView, "translationY", -2000f);
        animation.setDuration(6000);
        animation.start();
        if(CheckCollision(carView, potholeView )) {
            animation.cancel();
        }
        //        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -1000);
//        animation.setDuration(1000);
//        animation.setFillAfter(false);
//        view.startAnimation(animation);
    }

    public void remove(View view) {
        ImageView pothole = (ImageView)(findViewById(R.id.pothole));
        ObjectAnimator animation = ObjectAnimator.ofFloat(pothole, "translationX", 1000f);
        animation.setDuration(4000);
        animation.start();
    }
    public boolean CheckCollision(View v1,View v2) {
        Rect R1=new Rect(v1.getLeft(), v1.getTop(), v1.getRight(), v1.getBottom());
        Rect R2=new Rect(v2.getLeft(), v2.getTop(), v2.getRight(), v2.getBottom());
        return R1.intersect(R2);
    }
}

