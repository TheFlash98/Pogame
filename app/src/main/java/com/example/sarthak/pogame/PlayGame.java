package com.example.sarthak.pogame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

public class PlayGame extends AppCompatActivity {

    public boolean[] moved = new boolean[3];
    public int[] collision = new int[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

    }

    public void animate(View view) {
        final ImageView carView  = (findViewById(R.id.car));
        final ImageView potholeView = (findViewById(R.id.pothole));
        final ImageView potholeView2 = (findViewById(R.id.pothole2));
        final ImageView potholeView3 = (findViewById(R.id.pothole3));
        ValueAnimator animation = ObjectAnimator.ofFloat(carView, "translationY", -2000f);
        animation.setDuration(10000);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator a) {
                int score = 0;
                for(int i = 0; i < 3; i++) {
                    score += collision[i];
                }

            }

            @Override
            public void onAnimationStart(Animator a) {

            }

            @Override
            public void onAnimationCancel(Animator a) {

            }

            @Override
            public void onAnimationRepeat(Animator a) {

            }

        });
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                System.out.println(checkCollision(carView, potholeView));
                double fraction = animation.getCurrentPlayTime()/(animation.getDuration()*1.0);
                int new_bottom = (int)(carView.getBottom() - fraction*(carView.getY()+2000));
                int new_top = (int)(carView.getTop() - fraction*(carView.getY()+2000));
//                for(int i = 0; i < 3; i++) {
//                    if(moved[i] && collision[i]) {
//                        collision[i] = 0;
//                    }
//                }
                if(!moved[0]) {
                    Rect R1 = new Rect(carView.getLeft(), new_top, carView.getRight(), new_bottom);
                    Rect R2 = new Rect(potholeView.getLeft(), potholeView.getTop(), potholeView.getRight(), potholeView.getBottom());
                    if(R1.intersect(R2)) {
                        collision[0] = 1;
                    }
                }
                if(!moved[1]) {
                    Rect R1 = new Rect(carView.getLeft(), new_top, carView.getRight(), new_bottom);
                    Rect R2 = new Rect(potholeView2.getLeft(), potholeView2.getTop(), potholeView2.getRight(), potholeView2.getBottom());
                    if(R1.intersect(R2)) {
                        collision[1] = 1;
                    }
                }
                if(!moved[2]) {
                    Rect R1 = new Rect(carView.getLeft(), new_top, carView.getRight(), new_bottom);
                    Rect R2 = new Rect(potholeView3.getLeft(), potholeView3.getTop(), potholeView3.getRight(), potholeView3.getBottom());
                    if(R1.intersect(R2)) {
                        collision[2] = 1;
                    }
                }
                for(int i = 0; i < 3; i++) {
                    System.out.print(collision[i] + " ");
                }
            }

        });
        animation.start();
//        if(CheckCollision(carView, potholeView )) {
//            animation.cancel();
//        }
    }

    public void remove(View view) {
        if(view.getId() == R.id.pothole) {
            ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", 1000f);
            animation.setDuration(4000);
            animation.start();
            moved[0] = true;
        } else if(view.getId() == R.id.pothole3) {
            ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", 1000f);
            animation.setDuration(2000);
            animation.start();
            moved[2] = true;
        } else if(view.getId() == R.id.pothole2) {
            ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", -1000f);
            animation.setDuration(10000);
            animation.start();
            moved[1] = true;
        }

    }
    public boolean checkCollision(View v1,View v2) {
        Rect R1 = new Rect(v1.getLeft(), v1.getTop(), v1.getRight(), v1.getBottom());
        Rect R2 = new Rect(v2.getLeft(), v2.getTop(), v2.getRight(), v2.getBottom());
        return R1.intersect(R2);
    }
}

