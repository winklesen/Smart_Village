package com.samuelbernard147.smartvillage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.samuelbernard147.smartvillage.adapter.AgricultureAdapter;
import com.samuelbernard147.smartvillage.fragment.DatePickerFragment;
import com.samuelbernard147.smartvillage.model.Humidity;
import com.samuelbernard147.smartvillage.viewmodel.HumidityViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class AgricultureActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener {
    SwipeRefreshLayout refresh;
    AgricultureAdapter adapter;
    ProgressBar pb;
    HumidityViewModel humidityViewModel;
//    ImageButton btnDate;
    TextView tvDate;
    CardView cardDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agriculture);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.agriculture));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new AgricultureAdapter(this);
        adapter.notifyDataSetChanged();

        RecyclerView rv = findViewById(R.id.rv_card_agri);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        pb = findViewById(R.id.pb_agri);
        showLoading(true);

        refresh = findViewById(R.id.swipeRefreshAgri);
        refresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                        humidityViewModel = ViewModelProviders.of(AgricultureActivity.this).get(HumidityViewModel.class);
                        setToday();
                        humidityViewModel.getHumidity().observe(AgricultureActivity.this, getHumidity);
                        adapter.notifyDataSetChanged();
                    }
                }, 2000); //4000 millisecond = 4 detik
            }
        });

        humidityViewModel = ViewModelProviders.of(this).get(HumidityViewModel.class);
        setToday();
        humidityViewModel.getHumidity().observe(this, getHumidity);

        cardDate = findViewById(R.id.head_info);
        cardDate.setOnClickListener(this);

        tvDate = findViewById(R.id.tv_date);
    }

    void refreshViewModel(int day, int month) {
        humidityViewModel = ViewModelProviders.of(this).get(HumidityViewModel.class);
        humidityViewModel.sethumidity(day, month);
        humidityViewModel.getHumidity().observe(this, getHumidity);
        adapter.notifyDataSetChanged();
    }

    void setToday() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        humidityViewModel.sethumidity(date, month + 1);
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        refreshViewModel(dayOfMonth, month + 1);
        Log.e("On Dialog Set", "Month : " + month + "Day : " + dayOfMonth);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.head_info:
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), datePickerFragment.DATE_PICKER_TAG);
                break;
        }
    }

    private Observer<ArrayList<Humidity>> getHumidity = new Observer<ArrayList<Humidity>>() {
        @Override
        public void onChanged(ArrayList<Humidity> items) {
            if (items != null) {
                adapter.setData(items);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}