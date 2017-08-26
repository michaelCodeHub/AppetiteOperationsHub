package com.appetite.appetiteoperationsapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mike on 11/08/17.
 */

public class Login extends AppCompatActivity
{
    String username,password;
    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        new Sansation().overrideFonts(getApplicationContext(),findViewById(R.id.bottonLayout));

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = EditText.class.cast(findViewById(R.id.editText2)).getText().toString();
                password = EditText.class.cast(findViewById(R.id.editText2)).getText().toString();

                username = "mike@brandmoustache.com";
                password = "1";
                progressDialog = new CustomProgressDialog(Login.this, AlertDialog.THEME_HOLO_LIGHT);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                network();
            }
        });
    }

    public void network()
    {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .addFormDataPart("type", "2")
                .build();

        Request request = new Request.Builder()
                .url("http://103.76.231.154/appetite/login.php")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String x = response.body().string();

                if(x.equals("0"))
                {
                    Toast.makeText(getApplicationContext(),"Incorrect username and password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    App.editor.putString("userid",x).commit();
                    getJson();
                }
            }
        });
    }

    public void getJson()
    {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .addFormDataPart("type", "2")
                .build();

        Request request = new Request.Builder()
                .url("http://103.76.231.154/appetite/getJson.php")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String x = response.body().string();

                if(x.equals("0"))
                {
                    Toast.makeText(getApplicationContext(),"Incorrect username and password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.e("json",x);
                }
                progressDialog.dismiss();
                startActivity(new Intent(Login.this,Home.class));
            }
        });
    }

}
