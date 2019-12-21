package com.example.sarthak.pogame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class PollingActivity extends AppCompatActivity {

    private static final String url = "http://10.70.26.227:5000/api/v1/potholes?id=12345";
    OkHttpClient okHttpClient = new OkHttpClient();
    private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);

        Bundle extras = getIntent().getExtras();
        if(getIntent().hasExtra("value"))
            count += extras.getInt("value");

        if(count==3){
            Intent intent = new Intent(PollingActivity.this, PlayGame.class);
            startActivity(intent);
        }

        while(true){
            try{
                run(url);
            } catch (IOException e) {}

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Intent intent = new Intent(PollingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    void run(String url) throws IOException {
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
                if(response.isSuccessful()){
                    Intent intent = new Intent(PollingActivity.this, ConfirmActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}