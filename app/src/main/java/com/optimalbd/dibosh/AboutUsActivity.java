package com.optimalbd.dibosh;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {
    Toolbar toolbar;
    Context context;
    TextView m_TextView;
    TextView b_TextView;
    TextView h_TextView;
    TextView a_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        this.context = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About Us");

        m_TextView = (TextView) findViewById(R.id.mTextView);
        if (m_TextView != null) {
            m_TextView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        b_TextView = (TextView) findViewById(R.id.bTextView);
        if (b_TextView != null) {
            b_TextView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        h_TextView = (TextView) findViewById(R.id.hTextView);
        if (h_TextView != null) {
            h_TextView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        a_TextView = (TextView) findViewById(R.id.aTextView);
        if (a_TextView != null) {
            a_TextView.setMovementMethod(LinkMovementMethod.getInstance());
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
}
