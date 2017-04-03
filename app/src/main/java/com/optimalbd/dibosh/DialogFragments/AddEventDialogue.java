package com.optimalbd.dibosh.DialogFragments;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by ripon on 1/5/2017.
 */

public class AddEventDialogue extends DialogFragment {

    int sYear;
    int sMonth;
    int sDay;
    int fMonth;

    String datef;
    String nTime = "";

    EventManager eventManager;
    Calendar calendar;
    DateManager dateManager;
    DiboshNotificationManager diboshNotificationManager;

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

        titleTextView.setText(R.string.add_event);


        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {

                        sYear = selectedYear;
                        sMonth = selectedMonth;
                        sDay = selectedDay;

                        fMonth = sMonth + 1;
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
                }  else {
                    String mDatet = DateShow.dayMonthFormat(sDay, sMonth);
                    long longDate = DateShow.longDate(date);
//
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                    Log.e("AD", " date add : " + dateFormat.format(longDate));

                    PersonalEvent personalEvent = new PersonalEvent(name, type, date, mDatet, longDate, relation, eventTypeP, relationshipP,sDay,sMonth);
                    long s = eventManager.addEvent(personalEvent);
                    if (s > 0) {
                        int id = eventManager.getLastInsertId();
                        addDateTime(id, sDay, fMonth);
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

    private void addDateTime(int id, int day, int month) {
        String tableId = "3";
        String datef = day + "/" + month + "/" + calendar.get(Calendar.YEAR);

        String time = "23:59:00";
        String finalDate = datef + " " + time;

        String notificationDate = datef + " " + "07:30:00";

        Long lDate = DateShow.longDate23(finalDate);
        Long notificationDateTime = DateShow.longDate23(notificationDate);

        if (dateManager.pEventIdExits(String.valueOf(id)).equals("0")) {
            DateTime dateTime = new DateTime(id + "", datef, lDate);
            dateManager.addPersonalDate(dateTime);
        }

        if (diboshNotificationManager.notificationEventIdExits(tableId, id + "").equals("0")) {
            NotificationTime notificationTime1 = new NotificationTime(tableId, id + "", notificationDateTime);
            diboshNotificationManager.addNotification(notificationTime1);
            if (notificationDateTime>System.currentTimeMillis()) {
                notification(tableId, id + "", notificationDateTime);
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

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), Integer.parseInt(eventId), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long notification_time = time;
        alarmManager.set(AlarmManager.RTC_WAKEUP, notification_time, pendingIntent);

    }


}
