package com.example.sarthak.pogame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static final String url = "http://10.70.26.227:5000/api/v1/info?id=12345";
    OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PollingActivity.class);
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

                ImageView imageView1 = findViewById(R.id.imageView3);
                ImageView imageView2 = findViewById(R.id.imageView4);
                ImageView imageView3 = findViewById(R.id.imageView5);

                String s = (response.body().string());
                try {
                    JSONObject resp = new JSONObject(s);
                    System.out.println(resp);

                    JSONObject jsonRootObject = new JSONObject(s);
                    JSONArray jsonArray = jsonRootObject.getJSONArray("collection");

                    int arr[] = new int[jsonArray.length()];
                    for(int i=0; i < jsonArray.length(); i++){
                        arr[i] = (jsonArray.getInt(i));

                        switch (arr[i]){
                            case 1: MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    ImageView imageView = findViewById(R.id.imageView2);
                                    imageView.setImageResource(R.drawable.spade);
                                    }
                                });


                            case 2: MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    ImageView imageView = findViewById(R.id.imageView3);
                                    imageView.setImageResource(R.drawable.diamond);
                                    }
                                });
                                break;
                            case 3: MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    ImageView imageView = findViewById(R.id.imageView4);
                                    imageView.setImageResource(R.drawable.clubs);
                                    }
                                });
                                break;
                            case 4: MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    ImageView imageView = findViewById(R.id.imageView5);
                                    imageView.setImageResource(R.drawable.hearts);
                                    }
                                });

                        }
                    }



                } catch (JSONException j){
                    System.out.println(j.getMessage());
                }
            }
        });
    }
}
