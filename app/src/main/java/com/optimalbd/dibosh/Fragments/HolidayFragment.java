package com.optimalbd.dibosh.Fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.optimalbd.dibosh.Adapter.HolidayAdapter;
import com.optimalbd.dibosh.Database.HolidayManager;
import com.optimalbd.dibosh.Model.Holiday;
import com.optimalbd.dibosh.R;
import com.optimalbd.dibosh.Ulility.DiboshSharePreference;
import com.optimalbd.dibosh.Ulility.HTTPGET;
import com.optimalbd.dibosh.Ulility.InternetConnectionCheck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HolidayFragment extends Fragment {

    DiboshSharePreference preference;
    HolidayManager holidayManager;
    ArrayList<Holiday> holidayArrayList;
    String lang;
    HolidayAdapter holidayAdapter;
    ListView holiDayListView;
    String mode;
    ImageView viewByList;
    ImageView viewByTable;
    LinearLayout viewAsLayout;
    TextView titleTextView;
    Calendar calendar;
    ProgressBar loading_progressBar;


    public HolidayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_holiday, container, false);

        holidayManager = new HolidayManager(getActivity());
        preference = new DiboshSharePreference(getActivity());
        holidayArrayList = new ArrayList<>();
        calendar = Calendar.getInstance();

        holiDayListView = (ListView) view.findViewById(R.id.holidayListView);
        viewByTable = (ImageView) view.findViewById(R.id.viewByTable);
        viewByList = (ImageView) view.findViewById(R.id.viewByList);
        viewAsLayout = (LinearLayout) view.findViewById(R.id.viewAsLayout);
        titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        loading_progressBar=(ProgressBar)view.findViewById(R.id.loading_progressBar);

        lang = preference.getLanguage();
        mode = preference.getViewAsMode();
        titleTextView.setText(getResources().getString(R.string.holiday_list) + " - " + calendar.get(Calendar.YEAR));

        if (holidayManager.isEmpty()) {
            if (InternetConnectionCheck.isConnect(getActivity())) {
                new UpdateDateTask(getActivity()).execute();
            } else {
                Toast.makeText(getActivity(), "No Internet Connection! \n First time you need to connect. Please connect..", Toast.LENGTH_LONG).show();
            }
        } else {
            if (InternetConnectionCheck.isConnect(getActivity())) {
                holidayArrayList = holidayManager.getAllHoliday();
                holidayAdapter = new HolidayAdapter(getActivity(), holidayArrayList, lang, mode);
                holiDayListView.setAdapter(holidayAdapter);
                viewAsLayout.setVisibility(View.VISIBLE);
                new UpdateDateTask(getActivity()).execute();
            } else {
                holidayArrayList = holidayManager.getAllHoliday();
                holidayAdapter = new HolidayAdapter(getActivity(), holidayArrayList, lang, mode);
                holiDayListView.setAdapter(holidayAdapter);
                viewAsLayout.setVisibility(View.VISIBLE);
            }
        }

        viewByList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.saveViewAsMode("1");
                holidayArrayList = holidayManager.getAllHoliday();
                holidayAdapter = new HolidayAdapter(getActivity(), holidayArrayList, lang, "1");
                holiDayListView.setAdapter(holidayAdapter);
                viewAsLayout.setVisibility(View.VISIBLE);
            }
        });


        viewByTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.saveViewAsMode("2");
                holidayArrayList = holidayManager.getAllHoliday();
                holidayAdapter = new HolidayAdapter(getActivity(), holidayArrayList, lang, "2");
                holiDayListView.setAdapter(holidayAdapter);
                viewAsLayout.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    public class UpdateDateTask extends AsyncTask<Void, Integer, String> {
        Context context;
        String up;
        String upUrl = "http://optimalbd.com/dibosh_holiday/";

        public UpdateDateTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {
            String response = "";
            response = new HTTPGET().SendHttpRequest(upUrl);

            try {
                JSONObject jsonObject = new JSONObject(response);
                up = jsonObject.getString("last_update");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (preference.getLastUpdatedDate().equals("")) {
                preference.saveLastUpdatedDate(up);
                new HolidayTask(context).execute();
            } else {
                if (up.equals(preference.getLastUpdatedDate())) {
                    holidayArrayList = holidayManager.getAllHoliday();
                    HolidayAdapter holidayAdapter = new HolidayAdapter(getActivity(), holidayArrayList, lang, mode);
                    holiDayListView.setAdapter(holidayAdapter);
                    viewAsLayout.setVisibility(View.VISIBLE);
                    loading_progressBar.setVisibility(View.GONE);
                } else {
                    preference.saveLastUpdatedDate(up);
                    new HolidayTask(context).execute();
                }
            }
        }
    }

    public class HolidayTask extends AsyncTask<Void, Integer, String> {
        String hdUrl = "http://optimalbd.com/dibosh_holiday/?getdata";
        Holiday holiday;
        private Context context;
        String dayIds = "";

        public HolidayTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {
            String response = "";
            if (InternetConnectionCheck.isConnect(context)) {
                response = new HTTPGET().SendHttpRequest(hdUrl);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String dayId = jsonObject1.getString("id");
                        String name_en = jsonObject1.getString("name_en");
                        String name_bn = jsonObject1.getString("name_bn");
                        String date_en = jsonObject1.getString("date_en");
                        String date_bn = jsonObject1.getString("date_bn");
                        String date = jsonObject1.getString("date");
                        String day_en = jsonObject1.getString("day_en");
                        String day_bn = jsonObject1.getString("day_bn");

                        holiday = new Holiday(dayId, name_en, name_bn, date_en, date_bn, date, day_en, day_bn);
                        if (holidayManager.holidayIdExits(dayId).equals("0")) {
                            holidayManager.addHoliday(holiday);
                        }

                        holidayArrayList.add(holiday);
                    }

                    for (Holiday holiday : holidayArrayList) {
                        if (dayIds.equals("")) {
                            dayIds += holiday.getId();
                        } else {
                            dayIds += ',' + holiday.getId();
                        }
                    }

                    holidayManager.deleteHolidayInfo(dayIds);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            HolidayAdapter holidayAdapter = new HolidayAdapter(getActivity(), holidayArrayList, lang, mode);
            holiDayListView.setAdapter(holidayAdapter);
            viewAsLayout.setVisibility(View.VISIBLE);
            loading_progressBar.setVisibility(View.GONE);
        }
    }

}
