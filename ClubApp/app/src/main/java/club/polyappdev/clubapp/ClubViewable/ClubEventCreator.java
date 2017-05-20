package club.polyappdev.clubapp.ClubViewable;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.health.PidHealthStats;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import club.polyappdev.clubapp.Models.Event;
import club.polyappdev.clubapp.R;

import static android.R.attr.filter;
import static android.R.attr.key;

public class ClubEventCreator extends AppCompatActivity {

    Button cancelButton;
    Button doneButton;
    EditText eventName;
    EditText description;
    EditText when;
    EditText where;
    EditText tags;
    Date date = new Date();
    String stringDate;
    CheckBox hasFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_event_creator);
        cancelButton = (Button) findViewById(R.id.cancel);
        doneButton = (Button) findViewById(R.id.done);
        eventName = (EditText) findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);
        description.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(300)
        });
        where = (EditText) findViewById(R.id.location);
        when = (EditText) findViewById(R.id.time);
        tags = (EditText) findViewById(R.id.tags);
        hasFood = (CheckBox) findViewById(R.id.food);
        final Context context = this;


        doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!eventName.getText().toString().equals("") && !where.getText().toString().equals("") && !when.getText().toString().equals("")) {
                            createEvent();
                            finish();
                        }else{
                            Toast.makeText(context, "You are missing required fields.", Toast.LENGTH_LONG).show();
                        }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        when.setCursorVisible(false);
        when.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    pickDate();
                }
            }
        });
    }

    public void pickDate(){
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.date_picker_layout, null) ;
        final CalendarView calendar = (CalendarView) layout.findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                date.setYear(year);
                date.setMonth(month);
                date.setDate(dayOfMonth);
            }
        });
        new AlertDialog.Builder(ClubEventCreator.this)
                .setTitle("Select Date")
                .setView(layout)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        pickTime();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void pickTime(){
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.time_picker_layout, null) ;
        final TimePicker time = (TimePicker) layout.findViewById(R.id.timePicker);
        new AlertDialog.Builder(ClubEventCreator.this)
                .setTitle("Select Time")
                .setView(layout)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        date.setHours(time.getCurrentHour());
                        date.setMinutes(time.getCurrentMinute());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd 'at' h:mm a");
                        stringDate = dateFormat.format(date);
                        when.setText(stringDate);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private Event createEvent() {

            String keywords = tags.getText().toString();
            String[] keywordArray = keywords.split(",");
            List<String> keywordList = new ArrayList<>();
            for (String keyword : keywordArray) {
                keywordList.add(keyword.trim());
            }
            Event event = new Event();
            event.setTitle(eventName.getText().toString());
            event.setDescription(description.getText().toString());
            event.setStringLoc(where.getText().toString());
            event.setDate(date);
            event.setFood(hasFood.isChecked());
            event.setKeywords(keywordList);

            //TODO do something with this data

            Log.i("event", "" + event.getTitle() + " " + event.getDescription() + " " + event.getDate() + " " + event.getStringLoc() + " " + event.getKeywords() + " " + event.isFood());
            return event;
    }


}
