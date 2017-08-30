package com.appetite.appetiteoperationsapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Toast;

import com.appetite.appetiteoperationsapp.Database.AppContract;
import com.appetite.appetiteoperationsapp.Database.Database;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Clementia on 8/29/2017.
 */

public class AssignTask extends AppCompatActivity implements CalendarView.OnDateChangeListener {
    MaterialBetterSpinner spinner;
    ArrayList<String> users = new ArrayList<>();
    Toolbar toolbar;

    HashMap<String, String> userids = new HashMap<>();

    CalendarView calendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assigntask);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);

        final TaskInfo taskInfo = (TaskInfo) getIntent().getSerializableExtra("info");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse(taskInfo.client_deadline);

            Log.e("date",d.getDate()+"");

            calendarView.setMaxDate(d.getTime());
            calendarView.setMinDate(System.currentTimeMillis()-1000);

        }
        catch (ParseException ex) {
            Log.e("error",ex.toString());
        }

        toolbar.setTitle(taskInfo.name);

        spinner = (MaterialBetterSpinner) findViewById(R.id.spinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, users);

        Database database = new Database(getApplicationContext());
        database.open();
        Cursor cursor = database.getAllUsers();
        while (cursor.moveToNext())
        {
            userids.put(cursor.getString(4),cursor.getString(2));
            users.add(cursor.getString(4));
        }
        cursor.close();
        database.close();

        spinner.setAdapter(arrayAdapter);

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("mike",spinner.getText().toString());

                network(taskInfo, now(calendarView.getDate()),userids.get(spinner.getText().toString()));
            }
        });
    }

    public static String now(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public void network(final TaskInfo taskInfo, final String date, final String empid)
    {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("taskid", taskInfo.taskid)
                .addFormDataPart("deadline", date)
                .addFormDataPart("employeeid", empid)
                .build();

        Request request = new Request.Builder()
                .url("http://103.76.231.154/appetite/assignTask.php")
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
                    Log.e("eeeee",x);
                    Toast.makeText(getApplicationContext(),"Incorrect username and password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ContentValues cv = new ContentValues();
                    cv.put(AppContract.TaskProgressEntry.SNO , taskInfo.id);
                    cv.put(AppContract.TaskProgressEntry.TASKID , taskInfo.taskid);
                    cv.put(AppContract.TaskProgressEntry.STATUS , 0);
                    cv.put(AppContract.TaskProgressEntry.START_TIME , "");
                    cv.put(AppContract.TaskProgressEntry.END_TIME , "");
                    cv.put(AppContract.TaskProgressEntry.DEADLINE , date);
                    cv.put(AppContract.TaskProgressEntry.EMPLOYEE , empid);

                    Database database = new Database(getApplicationContext());
                    database.open();

                    database.addTaskProgress(cv);
                    database.updateStatusInTaskTable(taskInfo.taskid);

                    database.close();

                    startActivity(new Intent(AssignTask.this,Home.class));
                }
            }
        });
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

    }
}
