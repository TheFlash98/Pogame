package com.example.sarthak.pogame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class Result extends AppCompatActivity {

    OkHttpClient okHttpClient = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String url = "http://10.70.26.227:5000/api/v1/result";
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        score = 3 - extras.getInt("score");
        ImageView imageView = findViewById(R.id.imageView);
        if(score == 1) {
            imageView.setImageResource(R.drawable.spade);
        } else if(score == 2) {
            imageView.setImageResource(R.drawable.clubs);
        } else if(score == 3) {
            imageView.setImageResource(R.drawable.diamond);
        }
        try {
            post(scoreJson(score), url);
        } catch (IOException e) {
        }
    }

    void post(String json, String url) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, json);
        System.out.println(requestBody);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                System.out.println(response.code());
            }
        });

    }

    String scoreJson(int score) {

        return "{\"reward\":\"" + score + "\","
                + "\"id\":\"" + 12345 + "\""
                +"}";
    }
}
