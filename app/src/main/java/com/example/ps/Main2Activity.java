package com.example.ps;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {

    EditText etName, etDesc , etTime;
    Button btnAddTask, btnCancel;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etName = findViewById(R.id.editTextName);
        etDesc = findViewById(R.id.editTextDescription);
        etTime = findViewById(R.id.editTextReminder);

        btnAddTask = findViewById(R.id.buttonAdd);
        btnCancel = findViewById(R.id.buttonCancel);


        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                String time = etTime.getText().toString();


                if (name.length() == 0) {
                    Toast.makeText(getBaseContext(), "Name cannot be empty", Toast.LENGTH_LONG).show();
                } else if (desc.length() == 0) {
                    Toast.makeText(getBaseContext(), "Description cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    DBHelper db = new DBHelper(Main2Activity.this);
                    db.insertTask(name, desc);
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.SECOND, Integer.parseInt(time));


                    Intent intent = new Intent(Main2Activity.this, ScheduleNotificationReceiver.class);
                    intent.putExtra("name", name);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(Main2Activity.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                    AlarmManager a = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                    a.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                    Toast.makeText(getBaseContext(), "Task Inserted", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
            //button Cancel
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }
}

