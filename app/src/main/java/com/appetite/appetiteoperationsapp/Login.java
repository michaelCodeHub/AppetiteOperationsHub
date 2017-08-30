package com.appetite.appetiteoperationsapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appetite.appetiteoperationsapp.Database.AppContract;
import com.appetite.appetiteoperationsapp.Database.Database;

import org.json.JSONArray;
import org.json.JSONObject;

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
    Database database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        database = new Database(getApplicationContext());

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
                if(getPermission())
                {
                    network();
                }
            }
        });

        getPermission();
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
                    App.editor.putBoolean("login",true).commit();
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
                    try {

                        database.open();

                        JSONObject jsonObject = new JSONObject(x);
                        JSONArray usersArray = jsonObject.getJSONArray("users");
                        for (int i = 0; i < usersArray.length(); i++) {
                            JSONObject usersObject = usersArray.getJSONObject(i);

                            ContentValues cv = new ContentValues();
                            cv.put(AppContract.UserEntry.SNO , usersObject.getString("sno"));
                            cv.put(AppContract.UserEntry.USERID , usersObject.getString("id"));
                            cv.put(AppContract.UserEntry.TYPE , usersObject.getString("type"));
                            cv.put(AppContract.UserEntry.NAME , usersObject.getString("name"));

                            database.addUser(cv);

//                            getContentResolver().insert(AppContract.UserEntry.CONTENT_URI , cv);
                        }

                        JSONArray clientsArray = jsonObject.getJSONArray("clients");
                        for (int i = 0; i < clientsArray.length(); i++) {
                            JSONObject clientsObject = clientsArray.getJSONObject(i);

                            ContentValues cv = new ContentValues();
                            cv.put(AppContract.ClientsEntry.SNO , clientsObject.getString("sno"));
                            cv.put(AppContract.ClientsEntry.CLIENTID , clientsObject.getString("id"));
                            cv.put(AppContract.ClientsEntry.NAME , clientsObject.getString("name"));
                            cv.put(AppContract.ClientsEntry.ADDRESS , clientsObject.getString("location"));
                            cv.put(AppContract.ClientsEntry.NUMBER , clientsObject.getString("contact"));

                            database.addClient(cv);
//                            getContentResolver().insert(AppContract.ClientsEntry.CONTENT_URI , cv);
                        }

                        JSONArray tasksArray = jsonObject.getJSONArray("tasks");
                        for (int i = 0; i < tasksArray.length(); i++) {
                            JSONObject tasksObject = tasksArray.getJSONObject(i);

                            ContentValues cv = new ContentValues();
                            cv.put(AppContract.TaskEntry.SNO , tasksObject.getString("sno"));
                            cv.put(AppContract.TaskEntry.NAME , tasksObject.getString("tasktitle"));
                            cv.put(AppContract.TaskEntry.TASKID , tasksObject.getString("taskid"));
                            cv.put(AppContract.TaskEntry.CLIENTID , tasksObject.getString("clientid"));
                            cv.put(AppContract.TaskEntry.CLIENT_DEADLINE , tasksObject.getString("deadline"));
                            cv.put(AppContract.TaskEntry.DESCRIPTION , tasksObject.getString("description"));
                            cv.put(AppContract.TaskEntry.STATUS , tasksObject.getString("status"));

                            database.addTask(cv);
//                            getContentResolver().insert(AppContract.TaskEntry.CONTENT_URI , cv);
                        }


                        JSONArray taskProgressArray = jsonObject.getJSONArray("taskprogress");
                        for (int i = 0; i < taskProgressArray.length(); i++) {
                            JSONObject taskProgressObject = taskProgressArray.getJSONObject(i);

                            ContentValues cv = new ContentValues();
                            cv.put(AppContract.TaskProgressEntry.SNO , taskProgressObject.getString("sno"));
                            cv.put(AppContract.TaskProgressEntry.TASKID , taskProgressObject.getString("taskid"));
                            cv.put(AppContract.TaskProgressEntry.STATUS , taskProgressObject.getString("status"));
                            cv.put(AppContract.TaskProgressEntry.START_TIME , taskProgressObject.getString("starttime"));
                            cv.put(AppContract.TaskProgressEntry.END_TIME , taskProgressObject.getString("finishtime"));
                            cv.put(AppContract.TaskProgressEntry.DEADLINE , taskProgressObject.getString("deadline"));
                            cv.put(AppContract.TaskProgressEntry.EMPLOYEE , taskProgressObject.getString("employeeid"));

                            database.addTaskProgress(cv);

//                            getContentResolver().insert(AppContract.TaskProgressEntry.CONTENT_URI , cv);
                        }
                        database.open();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("mike",e.toString());
                    }
                }
                progressDialog.dismiss();
                startActivity(new Intent(Login.this,Home.class));
            }
        });
    }

    private boolean getPermission()
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Login.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    11);
            return false;
        }
        else
        {
            return true;
        }
    }
}
