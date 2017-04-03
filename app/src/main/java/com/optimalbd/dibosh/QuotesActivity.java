package com.optimalbd.dibosh;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.optimalbd.dibosh.Adapter.QuotesAdapter;
import com.optimalbd.dibosh.Database.QuotesManager;
import com.optimalbd.dibosh.Model.Quotes;

import java.util.ArrayList;

public class QuotesActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView quotesListView;
    ArrayList<Quotes> quotesArrayList;
    Context context;

    QuotesAdapter quotesAdapter;
    QuotesManager quotesManager;

    String typePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        this.context = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        quotesListView = (ListView) findViewById(R.id.quotesListView);
        quotesArrayList = new ArrayList<>();
        quotesManager = new QuotesManager(context);

        typePosition = getIntent().getStringExtra("typePosition");

        quotesArrayList = quotesManager.getAllQuotes(typePosition);
        quotesAdapter = new QuotesAdapter(context, quotesArrayList);
        quotesListView.setAdapter(quotesAdapter);
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

}
