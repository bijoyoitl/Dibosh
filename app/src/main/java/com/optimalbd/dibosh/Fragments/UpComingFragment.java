package com.optimalbd.dibosh.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.optimalbd.dibosh.Adapter.EventAdapter;
import com.optimalbd.dibosh.Adapter.IDaysAdapter;
import com.optimalbd.dibosh.Database.DateManager;
import com.optimalbd.dibosh.Database.EventManager;
import com.optimalbd.dibosh.Database.IDaysManager;
import com.optimalbd.dibosh.Database.NDaysManager;
import com.optimalbd.dibosh.DayDetailsActivity;
import com.optimalbd.dibosh.Model.IDays;
import com.optimalbd.dibosh.Model.PersonalEvent;
import com.optimalbd.dibosh.NonScrollListView;
import com.optimalbd.dibosh.PersonalDetailsActivity;
import com.optimalbd.dibosh.R;
import com.optimalbd.dibosh.Ulility.DateShow;
import com.optimalbd.dibosh.Ulility.DiboshSharePreference;
import com.optimalbd.dibosh.Ulility.InternetConnectionCheck;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment {

    TextView personalTV;
    TextView nationalTV;
    TextView internationalTV;

    NonScrollListView pNonScrollListView;
    NonScrollListView nNonScrollListView;
    NonScrollListView iNonScrollListView;

    String lang;
    Long time59;
    String todayDate;
    String nEventIds;
    String intEventIds;
    String personalEventIds;

    ArrayList<PersonalEvent> personalEventUPArrayList;
    ArrayList<IDays> nDayUPArrayList;

    ArrayList<IDays> nDaysArrayList;
    ArrayList<IDays> iDaysUPArrayList;

    EventManager eventManager;
    NDaysManager nDaysManager;
    IDaysManager iDaysManager;
    DateManager dateManager;


    IDaysAdapter iDaysAdapter;
    IDaysAdapter nDaysAdapter;
    EventAdapter eventAdapter;

    DiboshSharePreference preference;

    Calendar calendar;

    FirebaseAnalytics firebaseAnalytics;

    public UpComingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if (InternetConnectionCheck.isConnect(getActivity())){
            firebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
            firebaseAnalytics.setAnalyticsCollectionEnabled(true);
            firebaseAnalytics.setMinimumSessionDuration(5000);
            firebaseAnalytics.setSessionTimeoutDuration(1000000);

            firebaseAnalytics.setCurrentScreen(getActivity(),"TF","Upcoming Days");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        personalTV = (TextView) view.findViewById(R.id.personalTV);
        nationalTV = (TextView) view.findViewById(R.id.nationalTV);
        internationalTV = (TextView) view.findViewById(R.id.internationalTV);

        pNonScrollListView = (NonScrollListView) view.findViewById(R.id.pLV);
        nNonScrollListView = (NonScrollListView) view.findViewById(R.id.nLV);
        iNonScrollListView = (NonScrollListView) view.findViewById(R.id.iLV);


        personalEventUPArrayList = new ArrayList<>();
        nDaysArrayList = new ArrayList<>();
        nDayUPArrayList = new ArrayList<>();
        iDaysUPArrayList = new ArrayList<>();


        eventManager = new EventManager(getActivity());
        nDaysManager = new NDaysManager(getActivity());
        iDaysManager = new IDaysManager(getActivity());
        dateManager = new DateManager(getActivity());

        preference = new DiboshSharePreference(getActivity());
        lang = preference.getLanguage();

        calendar = Calendar.getInstance();

        todayDate = DateShow.currentDate();
        time59 = DateShow.longDate23(todayDate);

        nEventIds = dateManager.getUpcomingNDays(time59);
        intEventIds = dateManager.getUpcomingIntDays(time59);
        personalEventIds = dateManager.getUpcomingPDays(time59);


        personalEventUPArrayList = eventManager.getAllUpComingEvent(personalEventIds);
        nDayUPArrayList = nDaysManager.getAllUpComingNDayS(nEventIds);
        iDaysUPArrayList = iDaysManager.getAllIntUpComingDays(intEventIds);


        if (personalEventUPArrayList.size() != 0) {
            eventAdapter = new EventAdapter(getActivity(), personalEventUPArrayList, lang);
            pNonScrollListView.setAdapter(eventAdapter);

        } else {
            personalTV.setVisibility(View.VISIBLE);
            pNonScrollListView.setVisibility(View.GONE);
        }

        if (iDaysUPArrayList.size() != 0) {
            iDaysAdapter = new IDaysAdapter(getActivity(), iDaysUPArrayList, lang, 1);
            iNonScrollListView.setAdapter(iDaysAdapter);
        } else {
            iNonScrollListView.setVisibility(View.GONE);
            internationalTV.setVisibility(View.VISIBLE);

        }

        if (nDayUPArrayList.size() != 0) {
            nDaysAdapter = new IDaysAdapter(getActivity(), nDayUPArrayList, lang, 1);
            nNonScrollListView.setAdapter(nDaysAdapter);
        } else {
            nationalTV.setVisibility(View.VISIBLE);
            nNonScrollListView.setVisibility(View.GONE);
        }


        iNonScrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String dayId = iDaysUPArrayList.get(i).getId();
                Intent intent = new Intent(getActivity(), DayDetailsActivity.class);
                intent.putExtra("type", "1");
                intent.putExtra("from", "2");
                intent.putExtra("dayId", dayId);
                startActivity(intent);
            }
        });

        nNonScrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String dayId = nDayUPArrayList.get(i).getId();
                Intent intent = new Intent(getActivity(), DayDetailsActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("from", "2");
                intent.putExtra("dayId", dayId);
                startActivity(intent);
            }
        });

        pNonScrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = personalEventUPArrayList.get(i).getId();
                Intent intent = new Intent(getActivity(), PersonalDetailsActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("from", "2");
                startActivity(intent);
            }
        });

        return view;
    }

}
