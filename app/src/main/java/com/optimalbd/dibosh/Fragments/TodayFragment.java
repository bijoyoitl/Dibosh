package com.optimalbd.dibosh.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.optimalbd.dibosh.Adapter.EventAdapter;
import com.optimalbd.dibosh.Adapter.IDaysAdapter;
import com.optimalbd.dibosh.Adapter.NewsAdapter;
import com.optimalbd.dibosh.Database.DateManager;
import com.optimalbd.dibosh.Database.EventManager;
import com.optimalbd.dibosh.Database.IDaysManager;
import com.optimalbd.dibosh.Database.NDaysManager;
import com.optimalbd.dibosh.DayDetailsActivity;
import com.optimalbd.dibosh.Model.DateTime;
import com.optimalbd.dibosh.Model.PersonalEvent;
import com.optimalbd.dibosh.Model.IDays;
import com.optimalbd.dibosh.NewsApi;
import com.optimalbd.dibosh.NewsModel.NewsMain;
import com.optimalbd.dibosh.NonScrollListView;
import com.optimalbd.dibosh.PersonalDetailsActivity;
import com.optimalbd.dibosh.R;
import com.optimalbd.dibosh.Ulility.DateShow;
import com.optimalbd.dibosh.Ulility.DiboshSharePreference;
import com.optimalbd.dibosh.Ulility.InternetConnectionCheck;
import com.optimalbd.dibosh.Ulility.ListUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {

    TextView personalTV;
    TextView nationalTV;
    TextView internationalTV;

    Long time23;
    Long time12;
    String lang;

    NonScrollListView personaListView;
    NonScrollListView nationalListView;
    NonScrollListView internationalListView;
    NonScrollListView newsListView;

    ArrayList<IDays> iDaysTodayArrayList;
    ArrayList<IDays> nDaysTodayArrayList;
    ArrayList<PersonalEvent> pPersonalEventTodayArrayList;


    IDaysAdapter iDaysAdapter;
    IDaysAdapter nDaysAdapter;

    IDaysManager iDaysManager;
    NDaysManager nDaysManager;
    EventManager eventManager;
    DateManager dateManager;

    DateTime dateTime;

    DiboshSharePreference preference;
    FirebaseAnalytics firebaseAnalytics;


    //news

    String baseUrl = "http://api.deshinewsbd.com/";
    NewsApi newsApi;

    public TodayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if (InternetConnectionCheck.isConnect(getActivity())) {
            firebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
            firebaseAnalytics.setAnalyticsCollectionEnabled(true);
            firebaseAnalytics.setMinimumSessionDuration(5000);
            firebaseAnalytics.setSessionTimeoutDuration(1000000);

            firebaseAnalytics.setCurrentScreen(getActivity(), "TF", "Today Days");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        setHasOptionsMenu(true);

        personalTV = (TextView) view.findViewById(R.id.personalTV);
        nationalTV = (TextView) view.findViewById(R.id.naTV);
        internationalTV = (TextView) view.findViewById(R.id.intTV);

        personaListView = (NonScrollListView) view.findViewById(R.id.personalListView);
        nationalListView = (NonScrollListView) view.findViewById(R.id.nationalListView);
        internationalListView = (NonScrollListView) view.findViewById(R.id.internationalListView);

        iDaysTodayArrayList = new ArrayList<>();
        nDaysTodayArrayList = new ArrayList<>();
        pPersonalEventTodayArrayList = new ArrayList<>();

        dateManager = new DateManager(getActivity());
        iDaysManager = new IDaysManager(getActivity());
        nDaysManager = new NDaysManager(getActivity());
        eventManager = new EventManager(getActivity());

        preference = new DiboshSharePreference(getActivity());
        lang = preference.getLanguage();

        String todayDate23 = DateShow.currentDate();
        String todayDate12 = DateShow.currentDate12();

        time23 = DateShow.longDate23(todayDate23);
        time12 = DateShow.longDate23(todayDate12);

        String iTodayIds = dateManager.getTodayIntDays(time12, time23);
        String nTodayIds = dateManager.getTodayNationalDays(time12, time23);
        String pTodayIds = dateManager.getTodayPersonalDays(time12, time23);

        iDaysTodayArrayList = iDaysManager.getAllIntUpComingDays(iTodayIds);
        nDaysTodayArrayList = nDaysManager.getAllUpComingNDayS(nTodayIds);
        pPersonalEventTodayArrayList = eventManager.getAllUpComingEvent(pTodayIds);


        if (pPersonalEventTodayArrayList.size() == 0) {
            personalTV.setVisibility(View.VISIBLE);
            personaListView.setVisibility(View.GONE);
        } else {
            EventAdapter eventAdapter = new EventAdapter(getActivity(), pPersonalEventTodayArrayList, lang);
            personaListView.setAdapter(eventAdapter);
        }


        if (iDaysTodayArrayList.size() == 0) {
            internationalTV.setVisibility(View.VISIBLE);
            internationalListView.setVisibility(View.GONE);
        } else {
            iDaysAdapter = new IDaysAdapter(getActivity(), iDaysTodayArrayList, lang, 1);
            internationalListView.setAdapter(iDaysAdapter);
        }

        if (nDaysTodayArrayList.size() == 0) {
            nationalTV.setVisibility(View.VISIBLE);
            nationalListView.setVisibility(View.GONE);
        } else {
            nDaysAdapter = new IDaysAdapter(getActivity(), nDaysTodayArrayList, lang, 1);
            nationalListView.setAdapter(nDaysAdapter);
        }



        internationalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String dayId = iDaysTodayArrayList.get(i).getId();
                Intent intent = new Intent(getActivity(), DayDetailsActivity.class);
                intent.putExtra("type", "1");
                intent.putExtra("dayId", dayId);
                intent.putExtra("from", "2");
                startActivity(intent);
            }
        });

        nationalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String dayId = nDaysTodayArrayList.get(i).getId();
                Intent intent = new Intent(getActivity(), DayDetailsActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("from", "2");
                intent.putExtra("dayId", dayId);
                startActivity(intent);
            }
        });
        personaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = pPersonalEventTodayArrayList.get(i).getId();
                Intent intent = new Intent(getActivity(), PersonalDetailsActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("from", "2");
                startActivity(intent);
            }
        });


        return view;
    }



}
