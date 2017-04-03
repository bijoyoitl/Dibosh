package com.optimalbd.dibosh.Fragments;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.optimalbd.dibosh.Adapter.EventAdapter;
import com.optimalbd.dibosh.Database.EventManager;
import com.optimalbd.dibosh.Database.PersonalManager;
import com.optimalbd.dibosh.DialogFragments.AddEventDialogue;
import com.optimalbd.dibosh.DialogFragments.AddPersonalInfoDailogue;
import com.optimalbd.dibosh.EventInCalenderActivity;
import com.optimalbd.dibosh.Model.PersonalEvent;
import com.optimalbd.dibosh.Model.Personal;
import com.optimalbd.dibosh.PersonalDetailsActivity;
import com.optimalbd.dibosh.R;
import com.optimalbd.dibosh.Ulility.DiboshSharePreference;
import com.optimalbd.dibosh.Ulility.InternetConnectionCheck;

import java.util.ArrayList;
import android.support.design.widget.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment implements SearchView.OnQueryTextListener{

    FloatingActionButton add_fab;
    FloatingActionButton cal_fab;
    PersonalManager personalManager;
    EventManager eventManager;
    ArrayList<PersonalEvent> personalEventArrayList;
    ArrayList<Personal> personalArrayList;
    ListView eventListView;

    SearchView searchView;
    MenuItem searchMenuItem;

    EventAdapter eventAdapter;

    String lang;
    DiboshSharePreference preference;

    FirebaseAnalytics firebaseAnalytics;

    public PersonalFragment() {
        // Required empty public constructor
    }
    public static PersonalFragment newInstance() {
        PersonalFragment fragment = new PersonalFragment();

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (InternetConnectionCheck.isConnect(getActivity())){
            firebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
            firebaseAnalytics.setAnalyticsCollectionEnabled(true);
            firebaseAnalytics.setMinimumSessionDuration(5000);
            firebaseAnalytics.setSessionTimeoutDuration(1000000);

            firebaseAnalytics.setCurrentScreen(getActivity(),"PF","Personal Event");
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        setHasOptionsMenu(true);

        add_fab = (FloatingActionButton) view.findViewById(R.id.add_fab);
        cal_fab = (FloatingActionButton) view.findViewById(R.id.cal_fab);
        eventListView = (ListView) view.findViewById(R.id.eventListView);

        add_fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFabBack)));
        cal_fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFabBack)));

        personalEventArrayList = new ArrayList<>();
        personalArrayList = new ArrayList<>();
        preference = new DiboshSharePreference(getActivity());

        personalManager = new PersonalManager(getActivity());
        eventManager = new EventManager(getActivity());

        lang = preference.getLanguage();

        if (personalManager.isEmpty()) {
            android.app.FragmentManager manager = getActivity().getFragmentManager();
            AddPersonalInfoDailogue dialog = new AddPersonalInfoDailogue();
            dialog.show(manager, "add_Dialog");

        } else {
            personalEventArrayList = eventManager.getAllEvent();
            eventAdapter = new EventAdapter(getActivity(), personalEventArrayList, lang);
            eventListView.setAdapter(eventAdapter);
            eventAdapter.notifyDataSetChanged();
        }

        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Task()).start();
            }
        });


        cal_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EventInCalenderActivity.class);
                startActivity(intent);
            }
        });

        personalArrayList = personalManager.getPersonalInfo();


        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = personalEventArrayList.get(i).getId();
                Intent intent = new Intent(getActivity(), PersonalDetailsActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("from","2");
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<PersonalEvent> arrayList = new ArrayList<>();

        for (PersonalEvent event : personalEventArrayList) {

            String name;
            String date;

                name = event.getName().toLowerCase();
                date = event.getDate().toLowerCase();

            if (name.contains(newText) || date.contains(newText)) {
                arrayList.add(event);
            }

        }

        eventAdapter.setFilter(arrayList);

        return true;
    }


    private class Task implements Runnable {

        @Override
        public void run() {
            android.app.FragmentManager manager = getActivity().getFragmentManager();
            AddEventDialogue dialog = new AddEventDialogue();
            dialog.show(manager, "Event_Dialog");
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (android.support.v7.widget.SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(this);
    }

    public void refreshPage() {
        getActivity().recreate();
    }

}
