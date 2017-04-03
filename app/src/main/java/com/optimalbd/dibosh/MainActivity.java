package com.optimalbd.dibosh;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.optimalbd.dibosh.Database.PersonalManager;
import com.optimalbd.dibosh.Fragments.HolidayFragment;
import com.optimalbd.dibosh.Fragments.IDaysFragment;
import com.optimalbd.dibosh.Fragments.NDayFragment;
import com.optimalbd.dibosh.Fragments.NewsFragment;
import com.optimalbd.dibosh.Fragments.PersonalFragment;
import com.optimalbd.dibosh.Fragments.UpComingFragment;
import com.optimalbd.dibosh.Fragments.TodayFragment;
import com.optimalbd.dibosh.Ulility.DiboshSharePreference;
import com.optimalbd.dibosh.Ulility.ExitDialog;
import com.optimalbd.dibosh.Ulility.InternetConnectionCheck;
import com.optimalbd.dibosh.Ulility.LocaleHelper;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, EasyPermissions.PermissionCallbacks {

    Context context;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    TextView dateTV;
    LinearLayout dateLayout;
    ListView drawerListView;
    String lang;
    String[] drawerItem;
    DiboshSharePreference diboshSharePreference;
    android.support.v4.app.FragmentManager fragmentManager;

    Switch aSwitch;
    TextView switchTV;

    private SearchView searchView;
    private MenuItem searchMenuItem;

    PersonalManager personalManager;
    FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onResume() {
        super.onResume();

        if (InternetConnectionCheck.isConnect(this)) {
            firebaseAnalytics = FirebaseAnalytics.getInstance(this);

            firebaseAnalytics.setAnalyticsCollectionEnabled(true);
            firebaseAnalytics.setMinimumSessionDuration(5000);
            firebaseAnalytics.setSessionTimeoutDuration(1000000);
            firebaseAnalytics.setCurrentScreen(this, "MA", "Main Screen");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerListView = (ListView) findViewById(R.id.drawerListView);
        aSwitch = (Switch) findViewById(R.id.switchButton);
        switchTV = (TextView) findViewById(R.id.switchTV);
        dateTV = (TextView) findViewById(R.id.dateTV);
        dateLayout = (LinearLayout) findViewById(R.id.dateLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerItem = getResources().getStringArray(R.array.drawer_item);
        diboshSharePreference = new DiboshSharePreference(context);
        lang = diboshSharePreference.getLanguage();
        personalManager = new PersonalManager(this);

        dateLayout.setVisibility(View.VISIBLE);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        ArrayAdapter<String> drawerArrayAdapter = new ArrayAdapter<String>(context, R.layout.drawer_item, R.id.titleID, drawerItem);
        drawerListView.setAdapter(drawerArrayAdapter);

        drawerListView.setOnItemClickListener(new SlideMenuClickListener());

        if (savedInstanceState == null) {
            displayView(1);

        }

        if (lang.equals("bn")) {
            switchTV.setText("Switch to English");
            aSwitch.setTextOff("EN");
            aSwitch.setTextOn("BN");
        } else {
            switchTV.setText("Switch to Bangla");
            aSwitch.setTextOff("EN");
            aSwitch.setTextOn("BN");
        }

    }

    public void onSwitchClicked(View view) {

        boolean checked = ((Switch) view).isChecked();

        if (checked) {
            if (lang.equals("bn")) {
                diboshSharePreference.saveLanguage("en");
                updateViews("en");
            } else {
                diboshSharePreference.saveLanguage("bn");
                updateViews("bn");
            }

        } else {
            if (lang.equals("bn")) {
                diboshSharePreference.saveLanguage("en");
                updateViews("en");
            } else {
                diboshSharePreference.saveLanguage("bn");
                updateViews("bn");
            }
        }


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
    }

    public class SlideMenuClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            dateTV.setText(adapterView.getItemAtPosition(i) + "");
            displayView(i);
        }
    }


    public void displayView(int i) {
        android.support.v4.app.Fragment fragment2 = null;
        switch (i) {
            case 0:
                fragment2 = new TodayFragment();
                break;
            case 1:
                fragment2 = new UpComingFragment();
                break;
            case 2:
                fragment2 = new NewsFragment();
                break;
            case 3:
                fragment2 = new IDaysFragment();
                break;
            case 4:
                fragment2 = new NDayFragment();
                break;
            case 5:
                fragment2 = new HolidayFragment();
                break;
            case 6:
                fragment2 = new PersonalFragment();
                break;
            default:
                break;
        }


        if (fragment2 != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentF, fragment2).commit();
            drawerLayout.closeDrawer(navigationView);
        } else {
            drawerLayout.closeDrawer(navigationView);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_change_language:
                showChangeLangDialog();
                return true;
            case R.id.action_profile:
                Intent intent = new Intent(context, ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                return true;
            case R.id.about_us:
                startActivity(new Intent(context, AboutUsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.language_dialog, null);
        dialogBuilder.setView(dialogView);

        final Spinner spinner1 = (Spinner) dialogView.findViewById(R.id.spinner1);

        dialogBuilder.setTitle(getResources().getString(R.string.lang_dialog_title));
        dialogBuilder.setMessage(getResources().getString(R.string.lang_dialog_message));

        dialogBuilder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int langpos = spinner1.getSelectedItemPosition();
                switch (langpos) {
                    case 0: //English
                        diboshSharePreference.saveLanguage("en");
                        updateViews("en");
                        return;
                    case 1: //bangla
                        diboshSharePreference.saveLanguage("bn");
                        updateViews("bn");
                        return;
                    default: //By default set to english
                        diboshSharePreference.saveLanguage("bn");
                        updateViews("bn");
                }
            }
        });
        dialogBuilder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void updateViews(String languageCode) {
        LocaleHelper.setLocale(this, languageCode);
        recreate();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            android.app.FragmentManager manager = getFragmentManager();
            ExitDialog dialog = new ExitDialog();
            dialog.show(manager, "Exit_Dialog");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
