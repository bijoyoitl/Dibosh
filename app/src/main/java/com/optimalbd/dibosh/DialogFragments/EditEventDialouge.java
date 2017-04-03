package com.optimalbd.dibosh.DialogFragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.optimalbd.dibosh.Notification.DaysAlertReceiver;
import com.optimalbd.dibosh.Database.DateManager;
import com.optimalbd.dibosh.Database.DiboshNotificationManager;
import com.optimalbd.dibosh.Database.EventManager;
import com.optimalbd.dibosh.Model.DateTime;
import com.optimalbd.dibosh.Model.NotificationTime;
import com.optimalbd.dibosh.Model.PersonalEvent;
import com.optimalbd.dibosh.R;
import com.optimalbd.dibosh.Ulility.DateShow;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ripon on 1/8/2017.
 */

public class EditEventDialouge extends DialogFragment {
    int sYear;
    int sMonth;
    int sDay;
    int fMonth;

    String nTime = "";

    EventManager eventManager;
    DiboshNotificationManager diboshNotificationManager;
    Calendar calendar;
    DateManager dateManager;

    PersonalEvent personalEvent;
    String eventId;
    String[] date;

    public EditEventDialouge() {
    }

    @SuppressLint("ValidFragment")
    public EditEventDialouge(PersonalEvent personalEvent, String eventId) {
        this.personalEvent = personalEvent;
        this.eventId = eventId;
    }

    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_event_layout, null);
        dialogBuilder.setView(dialogView);

        final Context context = getActivity();

        eventManager = new EventManager(context);
        dateManager = new DateManager(context);
        diboshNotificationManager = new DiboshNotificationManager(context);
        calendar = Calendar.getInstance();

        final TextView titleTextView = (TextView) dialogView.findViewById(R.id.titleTextView);
        final EditText nameET = (EditText) dialogView.findViewById(R.id.nameET);
        final Spinner typeSpinner = (Spinner) dialogView.findViewById(R.id.eventTypeSpinner);
        final EditText dateET = (EditText) dialogView.findViewById(R.id.dateET);
        final Spinner relationshipSpinner = (Spinner) dialogView.findViewById(R.id.relationshipSpinner);
        final Button submitButton = (Button) dialogView.findViewById(R.id.submitButton);
        final Button cancelButton = (Button) dialogView.findViewById(R.id.cancelButton);

        titleTextView.setText(R.string.edit_event);
        nameET.setText(personalEvent.getName());
        dateET.setText(personalEvent.getDate());
        date = personalEvent.getDate().split("/");

        sDay = personalEvent.getDay();
        sMonth = personalEvent.getMonth();
        sYear = Integer.parseInt(date[2]);


        int tp = Integer.parseInt(personalEvent.getTypePosition());
        int rp = Integer.parseInt(personalEvent.getRelationPosition());

        typeSpinner.setSelection(tp);
        relationshipSpinner.setSelection(rp);


        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = sYear;
                int month = sMonth;
                int day = sDay;

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {

                        sYear = selectedYear;
                        sMonth = selectedMonth;
                        sDay = selectedDay;

//                        sMonth = fMonth + 1;
                        dateET.setText(DateShow.dateFormat(sDay, sMonth, sYear));

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type;
                String relation;
                String name = nameET.getText().toString().trim();
                String date = dateET.getText().toString().trim();
                String eventType = typeSpinner.getSelectedItem().toString();
                String eventTypeP = String.valueOf(typeSpinner.getSelectedItemPosition());
                String relationship = relationshipSpinner.getSelectedItem().toString();
                String relationshipP = String.valueOf(relationshipSpinner.getSelectedItemPosition());

                if (eventType.equals(typeSpinner.getItemAtPosition(0) + "")) {
                    type = "";
                } else {
                    type = eventType;
                }

                if (relationship.equals(relationshipSpinner.getItemAtPosition(0) + "")) {
                    relation = "";
                } else {
                    relation = relationship;
                }


                if (name.equals("")) {
                    Toast.makeText(context, R.string.enter_your_name, Toast.LENGTH_SHORT).show();
                } else if (type.equals("")) {
                    Toast.makeText(context, R.string.type, Toast.LENGTH_SHORT).show();
                } else if (date.equals("")) {
                    Toast.makeText(context, R.string.enter_date, Toast.LENGTH_SHORT).show();
                } else if (relation.equals("")) {
                    Toast.makeText(context, R.string.relationship, Toast.LENGTH_SHORT).show();
                } else {
                    String mDate = DateShow.dayMonthFormat(sDay, sMonth);

                    long longDate = DateShow.longDate(date);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    PersonalEvent personalEvent = new PersonalEvent(name, type, date, mDate, longDate, relation, eventTypeP, relationshipP, sDay, sMonth);
                    long s = eventManager.updateEvent(personalEvent, eventId);

                    if (s > 0) {
                        updateDateTime(eventId, sDay, sMonth, date);
                        getDialog().dismiss();
                        Toast.makeText(context, R.string.save_info, Toast.LENGTH_SHORT).show();
                        refreshPage();
                    } else {
                        Toast.makeText(context, R.string.failed_info, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();

        return alertDialog;
    }

    private void updateDateTime(String id, int day, int month, String date) {
        String datef = day + "/" + (month + 1) + "/" + calendar.get(Calendar.YEAR);

        String time = "23:59:00";
        String finalDate = datef + " " + time;

        Long lDate = DateShow.longDate23(finalDate);

        String notificationDate = date + " " + "07:30:00";
        Long notificationDateTime = DateShow.longDate23(notificationDate);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

        DateTime dateTime = new DateTime(id, datef, lDate);
        dateManager.upDatePersonalDateTime(dateTime, eventId);

        String nID = diboshNotificationManager.getNotificationId("3", eventId);
        NotificationTime notificationTime = new NotificationTime("3", eventId, notificationDateTime);

        long s = diboshNotificationManager.UpdatePersonalNotification(notificationTime, nID);

        if (s > 0) {
            if (notificationDateTime > System.currentTimeMillis()) {
                notification("3", eventId, notificationDateTime);
            }
        }


    }


    public void refreshPage() {
        getActivity().recreate();

    }

    private void notification(String tableId, String eventId, Long time) {

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), DaysAlertReceiver.class);
        intent.putExtra("tableId", tableId);
        intent.putExtra("eventId", eventId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        long notification_time = time;
        alarmManager.set(AlarmManager.RTC_WAKEUP, notification_time, pendingIntent);

    }

}

