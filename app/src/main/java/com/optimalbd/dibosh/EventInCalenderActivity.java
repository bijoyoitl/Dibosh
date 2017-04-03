package com.optimalbd.dibosh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.optimalbd.dibosh.Adapter.EventAdapter;
import com.optimalbd.dibosh.Database.DateManager;
import com.optimalbd.dibosh.Database.EventManager;
import com.optimalbd.dibosh.Model.DateTime;
import com.optimalbd.dibosh.Model.PersonalEvent;
import com.optimalbd.dibosh.Ulility.DiboshSharePreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventInCalenderActivity extends AppCompatActivity {
    Context context;
    ListView calenderListView;

    Calendar currentCalendar;

    long dateMilli;
    ArrayList<PersonalEvent> personalEventArrayList;
    ArrayList<DateTime> markEventArrayList;
    CompactCalendarView compactCalendarView;

    SimpleDateFormat df;
    SimpleDateFormat dateFormat;
    TextView monthTextView;
    TextView mTextView;
    SimpleDateFormat dateFormatForMonth;
    Toolbar toolbar;
    String lang;

    LinearLayout dateLayout;
    EventManager eventManager;
    EventAdapter eventAdapter;
    DateManager dateManager;
    DiboshSharePreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_in_calender);

        this.context = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        calenderListView = (ListView) findViewById(R.id.calenderListView);
        monthTextView = (TextView) findViewById(R.id.dateTV);
        mTextView = (TextView) findViewById(R.id.monthTV);
        dateLayout = (LinearLayout) findViewById(R.id.dateLayout);
        dateLayout.setVisibility(View.VISIBLE);

        eventManager = new EventManager(context);
        dateManager = new DateManager(context);
        personalEventArrayList = new ArrayList<>();
        markEventArrayList = new ArrayList<>();
        preference = new DiboshSharePreference(context);
        currentCalendar = Calendar.getInstance();

        lang = preference.getLanguage();
        df = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH);
        dateFormatForMonth = new SimpleDateFormat("MMM-yyyy", Locale.ENGLISH);
        monthTextView.setText(dateFormatForMonth.format(currentCalendar.getTime()));

        dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        mTextView.setText(dateFormat.format(currentCalendar.getTime()));

        markEventArrayList = dateManager.getAllLongDate();

        for (int i = 0; i < markEventArrayList.size(); i++) {
            int color;
            Long date = markEventArrayList.get(i).getDate();

            if (currentCalendar.getTimeInMillis() > date) {
                color = Color.GREEN;
            } else {
                color = Color.BLUE;
            }
            Event ev1 = new Event(color, date);
            compactCalendarView.addEvent(ev1);
        }
        calenderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String eventId = personalEventArrayList.get(position).getId();
                Intent intent = new Intent(context, PersonalDetailsActivity.class);
                intent.putExtra("id", eventId);
                intent.putExtra("from", "2");
                startActivity(intent);
            }
        });
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                mTextView.setText(dateFormat.format(dateClicked));

                String selectDate = df.format(dateClicked);
                Log.e("ECA", " selected date : " + selectDate);
                String eventIds = eventManager.getAllCalenderEventIds(selectDate);

                personalEventArrayList = eventManager.getAllUpComingEvent(eventIds);
                eventAdapter = new EventAdapter(context, personalEventArrayList, lang);
                calenderListView.setAdapter(eventAdapter);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthTextView.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

    }

    public void preClick(View view) {
        compactCalendarView.showPreviousMonth();
    }

    public void nextClick(View view) {
        compactCalendarView.showNextMonth();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String date = df.format(currentCalendar.getTime());
        String eventIds = eventManager.getAllCalenderEventIds(date);

        personalEventArrayList = eventManager.getAllUpComingEvent(eventIds);
        eventAdapter = new EventAdapter(context, personalEventArrayList, lang);
        calenderListView.setAdapter(eventAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
