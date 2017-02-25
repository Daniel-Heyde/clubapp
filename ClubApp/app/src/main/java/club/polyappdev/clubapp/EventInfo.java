package club.polyappdev.clubapp;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import club.polyappdev.clubapp.Models.Event;

public class EventInfo extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle dataBundle = intent.getExtras();
        String name = dataBundle.getString("eventName");
        String description = dataBundle.getString("eventDesc");
        String stringLoc = dataBundle.getString("eventStrLoc");
        Long dateLong = dataBundle.getLong("eventDate");
        Date date = new Date();
        date.setTime(dateLong);
        String dateString = DateFormat.getDateInstance().format(date);
        String timeString = DateFormat.getTimeInstance().format(date);

        String dateTime = dateString + " at " + timeString;



        String clubName = dataBundle.getString("eventClub");




        setContentView(R.layout.activity_event);
        TextView nameText = (TextView) findViewById(R.id.eventName);
        TextView descText = (TextView) findViewById(R.id.eventDesc);
        TextView locText = (TextView) findViewById(R.id.eventLoc);
        TextView dateText = (TextView) findViewById(R.id.eventDate);
        TextView clubText = (TextView) findViewById(R.id.eventClub);
        nameText.setText(name);
        descText.setText(description);
        locText.setText(stringLoc);
        dateText.setText(dateTime);
        clubText.setText(clubName);
    }


}
