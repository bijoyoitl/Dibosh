package com.optimalbd.dibosh;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.optimalbd.dibosh.Database.PersonalManager;
import com.optimalbd.dibosh.DialogFragments.AddPersonalInfoDailogue;
import com.optimalbd.dibosh.DialogFragments.UpdatePersonalInfoDialog;
import com.optimalbd.dibosh.Model.Personal;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    Context context;

    TextView nameTextView;
    TextView birthDayTextView;
    TextView mStatusTextView;
    TextView marriageDateTextView;
    LinearLayout mDateLayout;

    PersonalManager personalManager;
    ArrayList<Personal> personalArrayList;

    String name;
    String bDate;
    String mStatus;
    String mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.context = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDateLayout=(LinearLayout)findViewById(R.id.mDateLayout);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        birthDayTextView = (TextView) findViewById(R.id.birthDayTextView);
        mStatusTextView = (TextView) findViewById(R.id.mStatusTextVIew);
        marriageDateTextView = (TextView) findViewById(R.id.marrigeDateTextView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.profile);

        personalManager = new PersonalManager(context);
        personalArrayList = new ArrayList<>();

        if (personalManager.isEmpty()) {
            FragmentManager fragmentManager = getFragmentManager();
            AddPersonalInfoDailogue dailogue = new AddPersonalInfoDailogue();
            dailogue.show(fragmentManager, "add_dialog");
        } else {
            personalArrayList = personalManager.getPersonalInfo();
            if (personalArrayList != null) {
                name = personalArrayList.get(0).getName();
                bDate = personalArrayList.get(0).getBirthDate();
                mStatus = personalArrayList.get(0).getMaStatus();
                mDate = personalArrayList.get(0).getM_date();
            }
            if (name != null) {
                nameTextView.setText(name);
            }
            if (bDate != null) {
                birthDayTextView.setText(bDate);
            }

            if (mStatus != null) {
                if (mStatus.equals("1")) {
                    mStatusTextView.setText(R.string.married);
                    if (mDate != null) {
                        marriageDateTextView.setText(mDate);
                    }

                } else {
                    mStatusTextView.setText(R.string.unmarried);
                    mDateLayout.setVisibility(View.GONE);
                }
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateInfo(View view) {

        if (personalArrayList.size()==0){
            Toast.makeText(context, "Please First Enter Your Personal Holiday!", Toast.LENGTH_SHORT).show();
        }else {
            Personal personal = new Personal(personalArrayList.get(0).getName(), personalArrayList.get(0).getBirthDate(), personalArrayList.get(0).getMaStatus(), personalArrayList.get(0).getM_date(),personalArrayList.get(0).getAge(),personalArrayList.get(0).getAnniversary());
            android.app.FragmentManager manager = getFragmentManager();
            String id = personalArrayList.get(0).getId();
            UpdatePersonalInfoDialog dialog = new UpdatePersonalInfoDialog(personal, id);
            dialog.show(manager, "update_Dialog");
        }
    }
}
