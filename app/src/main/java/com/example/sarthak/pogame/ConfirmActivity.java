package com.example.sarthak.pogame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class ConfirmActivity extends AppCompatActivity {

    private static final String url = "http://10.70.26.227:5000/api/v1/";
    OkHttpClient okHttpClient = new OkHttpClient();
    private static final String confirm = "http://10.70.26.227:5000/api/v1/";
    private static final String deny = "http://10.70.26.227:5000/api/v1/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Button yes = (Button) findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    run(confirm);
                } catch (IOException e) {}

                Intent intent = new Intent(ConfirmActivity.this, PollingActivity.class);
                intent.putExtra("value", 1);
                startActivity(intent);
            }
        });

        Button no = (Button) findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    run(deny);
                } catch (IOException e) {}

                Intent intent = new Intent(ConfirmActivity.this, PollingActivity.class);
                startActivity(intent);
            }
        });
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
                    Intent intent = new Intent(ConfirmActivity.this, ConfirmActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
