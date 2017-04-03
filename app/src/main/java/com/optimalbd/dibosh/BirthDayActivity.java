package com.optimalbd.dibosh;

import android.app.KeyguardManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import junit.framework.Assert;

public class BirthDayActivity extends AppCompatActivity {

    ImageView birthdayIV;
    Context context;
    MediaPlayer mediaPlayer;
    TextView ageTV;
    int age;
    int mAge;
    String type;

    PowerManager.WakeLock wl;
    PowerManager pm;
    KeyguardManager.KeyguardLock kl;
    KeyguardManager km;
    LinearLayout dateLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pm = (PowerManager) getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "INFO");
        wl.acquire();

        km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("name");
        kl.disableKeyguard();

        setContentView(R.layout.activity_birth_day);

        this.context = this;
        birthdayIV = (ImageView) findViewById(R.id.birthDayImageView);
        ageTV = (TextView) findViewById(R.id.ageTV);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dateLayout = (LinearLayout) findViewById(R.id.dateLayout);

        setSupportActionBar(toolbar);
        dateLayout.setVisibility(View.VISIBLE);


        type = getIntent().getStringExtra("type");
        age = getIntent().getIntExtra("age", 0);

        if (type.equals("2")) {
            mAge = getIntent().getIntExtra("mYear", 0);
        }
        if (type.equals("1")) {
            ageTV.setText("Today Your " + age + "th BirthDay");
        } else {
            ageTV.setText("Today Your " + mAge + "th Marriage Anniversary");
        }

        if (type.equals("1")) {
            Glide.with(context).load(R.drawable.birthday).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(birthdayIV);
        } else {
            Glide.with(context).load(R.drawable.wedding).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(birthdayIV);
        }

        if (mediaPlayer == null) {
            if (type.equals("1")) {
                mediaPlayer = MediaPlayer.create(context, R.raw.birthday);
            } else {
                mediaPlayer = MediaPlayer.create(context, R.raw.wedding);
            }
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } else {
            mediaPlayer.start();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                wl.release();
            }
        });
    }

    public void close(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        finish();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        wl.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        wl.release();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        wl.acquire();//must call this!
    }

    @Override
    public void onBackPressed() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        super.onBackPressed();
    }

}
