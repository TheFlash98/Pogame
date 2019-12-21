package com.example.sarthak.pogame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static final String url = "http://10.70.26.227:5000/api/v1/potholes";
    OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayGame.class);
                startActivity(intent);
            }
        };

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(buttonOnClickListener);

        // Set value for score
        try {
            run(url);
        } catch (IOException e) {}

    }

    void run(String url) throws IOException {
//        RequestBody requestBody = RequestBody.create(JSON, json);
//        System.out.println(requestBody);
        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                System.out.println(response.code());
                final TextView textView = (TextView) findViewById(R.id.score);
                textView.setText(response.body().string());
            }
        });
    }
}
