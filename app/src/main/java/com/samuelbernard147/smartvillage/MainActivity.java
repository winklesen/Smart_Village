package com.samuelbernard147.smartvillage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.samuelbernard147.smartvillage.preference.SettingsPreference;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView cardLamp, cardAgriculture, cardAbout, cardHelp;
    ImageButton ibtnLamp, ibtnAgriculture, ibtnAbout, ibtnHelp;
    TextView tvLight, tvReservoir, tvHumidity;
    ImageView imgReservoir;
    ProgressBar pbStatus;
    Toolbar toolbar;
    SwipeRefreshLayout refresh;
    SettingsPreference settingsPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fadein);

        toolbar = findViewById(R.id.tb_main);
        toolbar.setTitle("Smart Village");
        setSupportActionBar(toolbar);

        cardLamp = findViewById(R.id.card_lamp);
        cardAgriculture = findViewById(R.id.card_agriculture);
        cardAbout = findViewById(R.id.card_about);
        cardHelp = findViewById(R.id.card_help);

        cardLamp.startAnimation(animFadeIn);
        cardAgriculture.startAnimation(animFadeIn);
        cardAbout.startAnimation(animFadeIn);
        cardHelp.startAnimation(animFadeIn);

        ibtnLamp = findViewById(R.id.ibtn_lamp);
        ibtnAgriculture = findViewById(R.id.ibtn_agriculture);
        ibtnAbout = findViewById(R.id.ibtn_about);
        ibtnHelp = findViewById(R.id.ibtn_help);

        ibtnLamp.setOnClickListener(this);
        ibtnAgriculture.setOnClickListener(this);
        ibtnAbout.setOnClickListener(this);
        ibtnHelp.setOnClickListener(this);

        tvLight = findViewById(R.id.tv_light);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvReservoir = findViewById(R.id.tv_reservoir);
        imgReservoir = findViewById(R.id.img_reservoir);
        pbStatus = findViewById(R.id.pb_status);
        refresh = findViewById(R.id.refresh_main);

        refresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadHumidity();
                        loadLamp();
                        loadReservoir();
                        refresh.setRefreshing(false);
                    }
                }, 2000); //4000 millisecond = 4 detik
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pbStatus.setVisibility(View.VISIBLE);
        loadHumidity();
        loadLamp();
        loadReservoir();
        setupNotif(this);
    }

    void setupNotif(Context context) {
        settingsPreference = new SettingsPreference(context);
        settingsPreference.setPanic(settingsPreference.getPanic());
        settingsPreference.setFlood(settingsPreference.getFlood());
        settingsPreference.setFire(settingsPreference.getFire());
//        if (settingsPreference.getPanic()){
//            notifHelper.startDispatcherPanic();
//        }
//        if (settingsPreference.getFlood()){
//            notifHelper.startDispatcherFlood();
//        }
//        if (settingsPreference.getFire()){
//            notifHelper.startDispatcherFire();
//        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_lamp:
                LampActivity lampActivity = new LampActivity();
                pindahActivity(lampActivity);
                break;
            case R.id.ibtn_agriculture:
                AgricultureActivity agricultureActivity = new AgricultureActivity();
                pindahActivity(agricultureActivity);
                break;
            case R.id.ibtn_about:
                AboutActivity aboutActivity = new AboutActivity();
                pindahActivity(aboutActivity);
                break;
            case R.id.ibtn_help:
                HelpActivity helpActivity = new HelpActivity();
                pindahActivity(helpActivity);
                break;
        }
    }

    void pindahActivity(Context context) {
        Intent i = new Intent(MainActivity.this, context.getClass());
        startActivity(i);
    }

    void loadLamp() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://smartvillage.starbhaktefa.com/public/api/lampu";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONArray responseObject = new JSONArray(result);
                    int countLamp = responseObject.length();
                    String lamp = String.format(getResources().getString(R.string.connected_light), countLamp);
                    tvLight.setText(lamp);
                    pbStatus.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    Log.d("LAMP DASHBOARD", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    void loadReservoir() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://smartvillage.starbhaktefa.com/public/api/waduk";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONArray responseObject = new JSONArray(result);
                    int status = responseObject.getJSONObject(0).getInt("kapasitas");
                    setReservoir(status);
                    pbStatus.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    Log.d("RESERVOIR DASHBOARD", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    void loadHumidity() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://smartvillage.starbhaktefa.com/public/api/agriculture";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONArray responseObject = new JSONArray(result);
                    int humidityJSON = responseObject.getJSONObject(0).getInt("ph");
                    String humidity = String.format(getResources().getString(R.string.current_humidity), humidityJSON);
                    tvHumidity.setText(humidity);
                    pbStatus.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    Log.d("HUMIDITY DASHBOARD", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    void setReservoir(int status) {
        if (status >= 200) {
            tvReservoir.setText(getResources().getString(R.string.reservoir_status_filled));
            imgReservoir.setImageResource(R.drawable.water);
        } else {
            tvReservoir.setText(getResources().getString(R.string.reservoir_status_unfilled));
            imgReservoir.setImageResource(R.drawable.water_off);
        }
    }
}