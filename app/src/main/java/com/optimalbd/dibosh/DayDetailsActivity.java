package com.optimalbd.dibosh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.optimalbd.dibosh.Database.DiboshNotificationManager;
import com.optimalbd.dibosh.Database.IDaysManager;
import com.optimalbd.dibosh.Database.NDaysManager;
import com.optimalbd.dibosh.Model.IDays;
import com.optimalbd.dibosh.Ulility.DiboshSharePreference;
import com.optimalbd.dibosh.Ulility.InternetConnectionCheck;
import com.optimalbd.dibosh.Ulility.LocaleHelper;

import java.util.ArrayList;

public class DayDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    DiboshSharePreference sharePreference;
    TextView dateTV;
    TextView nameTV;
    TextView detailsTV;
    TextView readTextView;
    TextView notificationTime;
    TextView occasionTV;
    TextView oTV;
    TextView dTV;
    TextView textSizeTextView;
    String dayId = "";
    String day = "";
    String from;
    ArrayList<IDays> singleItemArrayList;
    IDaysManager iDaysManager;
    NDaysManager nDaysManager;
    DiboshNotificationManager notificationManager;
    Typeface font;
    String lang;
    String a;
    LinearLayout dateLayout;
    SeekBar sizeSeekBar;
    int textSIze = 0;

    FirebaseAnalytics firebaseAnalytics;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dateTV = (TextView) findViewById(R.id.dateTV);
        nameTV = (TextView) findViewById(R.id.nameTV);
        readTextView = (TextView) findViewById(R.id.readTextView);
        notificationTime = (TextView) findViewById(R.id.notificationTimeTV);
        detailsTV = (TextView) findViewById(R.id.detailsTV);
        occasionTV = (TextView) findViewById(R.id.occasionTV);
        dateLayout = (LinearLayout) findViewById(R.id.dateLayout);
        oTV = (TextView) findViewById(R.id.oTV);
        dTV = (TextView) findViewById(R.id.dTV);
        sizeSeekBar = (SeekBar) findViewById(R.id.sizeSeekBar);
        textSizeTextView = (TextView) findViewById(R.id.textSizeTextView);

        setSupportActionBar(toolbar);

        sharePreference = new DiboshSharePreference(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dayId = getIntent().getStringExtra("dayId");
        day = getIntent().getStringExtra("type");
        from = getIntent().getStringExtra("from");


        lang = sharePreference.getLanguage();
        dateLayout.setVisibility(View.VISIBLE);

        singleItemArrayList = new ArrayList<>();
        iDaysManager = new IDaysManager(this);
        nDaysManager = new NDaysManager(this);
        notificationManager = new DiboshNotificationManager(this);

        if (day.equals("1")) {
            singleItemArrayList = iDaysManager.getAllSingleIDayInfo(dayId);

            if (lang.equals("en")) {
                dateTV.setText(singleItemArrayList.get(0).getDate_en());
                nameTV.setText(singleItemArrayList.get(0).getName_en());
                occasionTV.setText(singleItemArrayList.get(0).getOccasion_eng());
                detailsTV.setText(singleItemArrayList.get(0).getDetails_en());
            } else {
                dateTV.setText(singleItemArrayList.get(0).getDate_bn());
                nameTV.setText(singleItemArrayList.get(0).getName_bn());

                if (singleItemArrayList.get(0).getDetails_bn() == null) {
                    detailsTV.setText(singleItemArrayList.get(0).getDetails_en());
                } else {
                    detailsTV.setText(singleItemArrayList.get(0).getDetails_bn());
                }

                if (singleItemArrayList.get(0).getOccasion_ban() == null) {
                    occasionTV.setText(singleItemArrayList.get(0).getOccasion_eng());
                } else {
                    occasionTV.setText(singleItemArrayList.get(0).getOccasion_ban());
                }

            }

        } else {
            singleItemArrayList = nDaysManager.getAllSingleNDayInfo(dayId);
            if (lang.equals("en")) {
                dateTV.setText(singleItemArrayList.get(0).getDate_en());
                nameTV.setText(singleItemArrayList.get(0).getName_en());
                occasionTV.setText(singleItemArrayList.get(0).getOccasion_eng());
                detailsTV.setText(singleItemArrayList.get(0).getDetails_en());

            } else {
                dateTV.setText(singleItemArrayList.get(0).getDate_bn());
                nameTV.setText(singleItemArrayList.get(0).getName_bn());

                if (singleItemArrayList.get(0).getDetails_bn() == null) {
                    detailsTV.setText(singleItemArrayList.get(0).getDetails_en());
                } else {
                    detailsTV.setText(singleItemArrayList.get(0).getDetails_bn());
                }

                if (singleItemArrayList.get(0).getOccasion_ban() == null) {
                    occasionTV.setText(singleItemArrayList.get(0).getOccasion_eng());
                } else {
                    occasionTV.setText(singleItemArrayList.get(0).getOccasion_ban());
                }
            }

        }


        if (lang.equals("en")) {
            a = "1";
        } else {
            a = "2";
        }

        readTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a.equals("1")) {
                    dateTV.setText(singleItemArrayList.get(0).getDate_bn());
                    nameTV.setText(singleItemArrayList.get(0).getName_bn());

                    readTextView.setText("ইংরেজিতে পড়ুন");
                    oTV.setText("উপলক্ষ :");
                    dTV.setText("বর্ণনা :");
                    textSizeTextView.setText("টেক্সট সাইজ :");
                    if (singleItemArrayList.get(0).getDetails_bn() == null) {
                        detailsTV.setText(singleItemArrayList.get(0).getDetails_en());
                    } else {
                        detailsTV.setText(singleItemArrayList.get(0).getDetails_bn());
                    }

                    if (singleItemArrayList.get(0).getOccasion_ban() == null) {
                        occasionTV.setText(singleItemArrayList.get(0).getOccasion_eng());
                    } else {
                        occasionTV.setText(singleItemArrayList.get(0).getOccasion_ban());
                    }
                    a = "2";
                } else {
                    readTextView.setText("Read in Bangla");
                    oTV.setText("Occasion :");
                    dTV.setText("Details :");
                    textSizeTextView.setText("Text Size :");
                    dateTV.setText(singleItemArrayList.get(0).getDate_en());
                    nameTV.setText(singleItemArrayList.get(0).getName_en());
                    occasionTV.setText(singleItemArrayList.get(0).getOccasion_eng());
                    detailsTV.setText(singleItemArrayList.get(0).getDetails_en());
                    a = "1";
                }
            }
        });
        sizeSeekBar.setProgress(22);
        sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSIze = progress;
                nameTV.setTextSize(textSIze);
                occasionTV.setTextSize(textSIze - 2);
                detailsTV.setTextSize(textSIze - 5);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (InternetConnectionCheck.isConnect(this)) {
            firebaseAnalytics = FirebaseAnalytics.getInstance(this);

            firebaseAnalytics.setAnalyticsCollectionEnabled(true);
            firebaseAnalytics.setMinimumSessionDuration(5000);
            firebaseAnalytics.setSessionTimeoutDuration(1000000);
            firebaseAnalytics.setCurrentScreen(this, "DA", "Day Details Screen");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (from.equals("1")) {
                    Intent intent = new Intent(DayDetailsActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    finish();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
