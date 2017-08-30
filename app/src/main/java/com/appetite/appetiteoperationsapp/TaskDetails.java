package com.appetite.appetiteoperationsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Clementia on 8/29/2017.
 */

public class TaskDetails extends AppCompatActivity
{
    TextView title,deadline,description;
    private TaskInfo taskInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetails);

        taskInfo = (TaskInfo) getIntent().getSerializableExtra("info");

        title = TextView.class.cast(findViewById(R.id.title));
        deadline = TextView.class.cast(findViewById(R.id.deadline));
        description = TextView.class.cast(findViewById(R.id.description));

        title.setText(taskInfo.name);
        deadline.setText(taskInfo.client_deadline);
        description.setText(taskInfo.description);

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskDetails.this, AssignTask.class);
                intent.putExtra("info",taskInfo);
                startActivity(intent);
            }
        });
    }
}
