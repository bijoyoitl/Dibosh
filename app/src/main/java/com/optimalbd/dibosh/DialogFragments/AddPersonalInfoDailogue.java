package com.optimalbd.dibosh.DialogFragments;

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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.optimalbd.dibosh.Database.PersonalManager;
import com.optimalbd.dibosh.Model.Personal;
import com.optimalbd.dibosh.Notification.BirthMarriageReceiver;
import com.optimalbd.dibosh.R;
import com.optimalbd.dibosh.Ulility.BirthDayYearCalculate;
import com.optimalbd.dibosh.Ulility.DateShow;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ripon on 1/5/2017.
 */

public class AddPersonalInfoDailogue extends DialogFragment {

    PersonalManager personalManager;
    int sYear;
    int sMonth;
    int sDay;
    boolean mStatus = false;

    String name;
    String birthDate;
    String mDate = "";
    int m = 0;

    int aDay;
    int aMonth;
    int aYear;

    int mDay;
    int mMonth;
    int mYear;

    Calendar calendar;

    int anniversary=0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.personal_info, null);
        dialogBuilder.setView(dialogView);

        final Context context = getActivity();
        personalManager = new PersonalManager(context);
        calendar = Calendar.getInstance();

        final TextView titleTextView = (TextView) dialogView.findViewById(R.id.titleTextView);
        final LinearLayout mDateLayout = (LinearLayout) dialogView.findViewById(R.id.mDateLayout);
        final EditText nameET = (EditText) dialogView.findViewById(R.id.nameET);
        final EditText birthDateET = (EditText) dialogView.findViewById(R.id.birthDateET);
        final EditText marriageDateET = (EditText) dialogView.findViewById(R.id.marriedDateET);

        final CheckBox yesCheckBox = (CheckBox) dialogView.findViewById(R.id.yesCheckBox);
        final CheckBox noCheckBox = (CheckBox) dialogView.findViewById(R.id.noCheckBox);
        final Button submitButton = (Button) dialogView.findViewById(R.id.submitButton);

        titleTextView.setText(R.string.add_personal_info);

        birthDateET.setOnClickListener(new View.OnClickListener() {
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

                        aYear = selectedYear;
                        aMonth = selectedMonth + 1;
                        aDay = selectedDay;

                        m = sMonth + 1;
                        birthDateET.setText(DateShow.dateFormat(sDay, sMonth, sYear));

                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        marriageDateET.setOnClickListener(new View.OnClickListener() {
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

                        mYear = selectedYear;
                        mMonth = selectedMonth + 1;
                        mDay = selectedDay;

                        int month = sMonth + 1;
                        marriageDateET.setText(DateShow.dateFormat(sDay, sMonth, sYear));

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        noCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (yesCheckBox.isChecked()) {
                    yesCheckBox.setChecked(false);
                }
                mDateLayout.setVisibility(View.GONE);
                mStatus = false;

            }
        });

        yesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noCheckBox.isChecked()) {
                    noCheckBox.setChecked(false);
                }
                mDateLayout.setVisibility(View.VISIBLE);
                mStatus = true;
            }
        });

        dialogBuilder.setCancelable(false);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameET.getText().toString().trim();
                birthDate = birthDateET.getText().toString().trim();

                if (name.equals("")) {
                    Toast.makeText(context, R.string.enter_your_name, Toast.LENGTH_SHORT).show();
                } else if (birthDate.equals("")) {
                    Toast.makeText(context, R.string.enter_date, Toast.LENGTH_SHORT).show();
                } else if (!(noCheckBox.isChecked() || yesCheckBox.isChecked())) {
                    Toast.makeText(context, "Please select Marital Status", Toast.LENGTH_SHORT).show();
                } else {
                    String bDate = aDay + "/" + aMonth + "/" + aYear;

                    long bron = DateShow.longBirthDate(bDate);
                    long today = DateShow.longBirthDate(DateShow.currentDate());

                    if (mStatus) {
                        mDate = marriageDateET.getText().toString().trim();
                        if (mDate.equals("")) {
                            Toast.makeText(context, R.string.m_date, Toast.LENGTH_SHORT).show();
                        } else {
                            String maDate = mDay + "/" + mMonth + "/" + mYear;
                            long marriage = DateShow.longBirthDate(maDate);
                            anniversary=mAnniversary();
                            if (marriage < bron || marriage > today) {
                                Toast.makeText(context, "Marriage date must be after born and before Today!", Toast.LENGTH_SHORT).show();
                            } else {
                                addPersonalInfo(name, birthDate, mStatus, mDate);
                            }
                        }
                    } else {
                        if (bron > today) {
                            Toast.makeText(context, "Birthday date must be before Today!", Toast.LENGTH_SHORT).show();
                        } else {
                            addPersonalInfo(name, birthDate, mStatus, mDate);
                        }
                    }

                }

            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        return alertDialog;
    }

    private void notification(Long time, String type) {

        Context context = getActivity();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), BirthMarriageReceiver.class);
        intent.putExtra("age", addAge());
        intent.putExtra("type", type);
        if (type.equals("2")) {
            intent.putExtra("mYear", mAnniversary());
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(type), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

    }

    private int addAge() {
        int age;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, aYear);
        calendar.set(Calendar.MONTH, aMonth);
        calendar.set(Calendar.DAY_OF_MONTH, aDay);

        age = BirthDayYearCalculate.getAge(calendar.getTime());

//        Log.e("Ad", " age : " + age);

        return age;
    }

    private int mAnniversary() {
        int year;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DAY_OF_MONTH, mDay);

        year = BirthDayYearCalculate.getAge(calendar.getTime());

//        Log.e("Ad", " Anniversary : " + year);

        return year;
    }

    private void addPersonalInfo(String name, String birthDate, boolean mStatus, String mDate) {
        String birthdate = aDay + "/" + aMonth + "/" + calendar.get(Calendar.YEAR);
        String birthNotiTime = "09:05:00";
        String t = birthdate + " " + birthNotiTime;
        long birthNotificationTime = DateShow.longDate23(t);
        long marriageNotificationTime = 0;

        if (!mDate.equals("")) {
            String maDate = mDay + "/" + mMonth + "/" + calendar.get(Calendar.YEAR);
            String mTime = "09:10:00";

            String notiDate = maDate + " " + mTime;
            marriageNotificationTime = DateShow.longDate23(notiDate);

            SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy HH:mm:ss", Locale.ENGLISH);
            Log.e("AD", " n time : " + dateFormat.format(marriageNotificationTime));

        }


        Personal personal = new Personal(name, birthDate, mStatus, mDate, birthNotificationTime, marriageNotificationTime,addAge(),anniversary);
        long s = personalManager.addPersonalInfo(personal);

        if (s > 0) {
            if (birthNotificationTime > System.currentTimeMillis()) {
                notification(birthNotificationTime, "1");
            }

            if (!mDate.equals("")) {
                if (marriageNotificationTime > System.currentTimeMillis()) {
                    notification(marriageNotificationTime, "2");
                }
            }

            Toast.makeText(getActivity(), R.string.save_info, Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
            getActivity().recreate();
        } else {
            Toast.makeText(getActivity(), R.string.failed_info, Toast.LENGTH_SHORT).show();
        }
    }
}
