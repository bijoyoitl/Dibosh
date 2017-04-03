package com.optimalbd.dibosh;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.optimalbd.dibosh.Database.DateManager;
import com.optimalbd.dibosh.Database.DiboshNotificationManager;
import com.optimalbd.dibosh.Database.EventManager;
import com.optimalbd.dibosh.DialogFragments.EditEventDialouge;
import com.optimalbd.dibosh.Model.PersonalEvent;
import com.optimalbd.dibosh.Notification.DaysAlertReceiver;

import java.util.ArrayList;

public class PersonalDetailsActivity extends AppCompatActivity {
Context context;
    TextView nameTextView;
    TextView dateTextView;
    TextView eventTextView;
    TextView relationshipTextView;
    TextView titleTextView;


    ArrayList<PersonalEvent> personalEventArrayList;

    String id;
    String eventId;
    String from;
    EventManager eventManager;
    Toolbar toolbar;
    PersonalEvent personalEvent;
    DateManager dateManager;
    DiboshNotificationManager diboshNotificationManager;

    String typePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        this.context=this;

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        eventTextView = (TextView) findViewById(R.id.eventTextView);
        relationshipTextView = (TextView) findViewById(R.id.relationshipTextView);
        titleTextView = (TextView) findViewById(R.id.titleTextView);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        personalEventArrayList = new ArrayList<>();
        eventManager = new EventManager(this);
        dateManager = new DateManager(this);
        diboshNotificationManager= new DiboshNotificationManager(this);
        id = getIntent().getStringExtra("id");
        from = getIntent().getStringExtra("from");


        personalEventArrayList = eventManager.getSingleEvent(id);

        if (personalEventArrayList == null) {
            Log.e("PA", " array of personalEvent is null !");
        } else {
            eventId = personalEventArrayList.get(0).getId();
            String name = personalEventArrayList.get(0).getName();
            String date = personalEventArrayList.get(0).getDate();
            String event = personalEventArrayList.get(0).getType();
            typePosition = personalEventArrayList.get(0).getTypePosition();
            String relationship = personalEventArrayList.get(0).getRelationship();

            nameTextView.setText(name);
            dateTextView.setText(date);
            eventTextView.setText(event);
            relationshipTextView.setText(relationship);

            titleTextView.setText(name + "`s " + event);
        }


    }

    public void sendQuotes(View view) {
        Intent intent = new Intent(PersonalDetailsActivity.this, QuotesActivity.class);
        intent.putExtra("typePosition", typePosition);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (from.equals("1")) {
                    Intent intent = new Intent(PersonalDetailsActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void edit(View view) {

        personalEvent = new PersonalEvent(personalEventArrayList.get(0).getName(), personalEventArrayList.get(0).getType(), personalEventArrayList.get(0).getDate(), personalEventArrayList.get(0).getRelationship(), personalEventArrayList.get(0).getTypePosition(), personalEventArrayList.get(0).getRelationPosition(),personalEventArrayList.get(0).getDay(),personalEventArrayList.get(0).getMonth());
        android.app.FragmentManager manager = getFragmentManager();
        EditEventDialouge dialog = new EditEventDialouge(personalEvent, id);
        dialog.show(manager, "edit_Dialog");

    }

    public void delete(View view) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Delete...");
        builder.setMessage("Do you want to delete this Event ?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long success = eventManager.deleteEvent(eventId);
                if (success > 0) {
                    dateManager.deletePersonalDateTime(eventId);
                    diboshNotificationManager.deleteEventNotification(eventId);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(context, DaysAlertReceiver.class);
                    intent.putExtra("tableId", "3");
                    intent.putExtra("eventId", eventId);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(eventId), intent, 0);
                    alarmManager.cancel( pendingIntent);

                    Toast.makeText(PersonalDetailsActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PersonalDetailsActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                } else {
                    Toast.makeText(PersonalDetailsActivity.this, "Delete Fail !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
