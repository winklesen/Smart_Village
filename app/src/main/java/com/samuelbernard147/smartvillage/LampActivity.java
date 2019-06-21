package com.samuelbernard147.smartvillage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.samuelbernard147.smartvillage.adapter.LampAdapter;
import com.samuelbernard147.smartvillage.model.Lamp;
import com.samuelbernard147.smartvillage.viewmodel.LampViewModel;

import java.util.ArrayList;

public class LampActivity extends AppCompatActivity {
    SwipeRefreshLayout refresh;
    LampAdapter adapter;
    ProgressBar pb;
    LampViewModel lampViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.smart_lamp));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new LampAdapter(this);
        adapter.notifyDataSetChanged();

        RecyclerView rv = findViewById(R.id.rv_card_lamp);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        pb = findViewById(R.id.pb_lamp);
        showLoading(true);

        refresh = findViewById(R.id.swipeRefreshLamp);
        refresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                        lampViewModel = ViewModelProviders.of(LampActivity.this).get(LampViewModel.class);
                        lampViewModel.setLamp();
                        lampViewModel.getLamp().observe(LampActivity.this, getLamp);
                        adapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });

        lampViewModel = ViewModelProviders.of(this).get(LampViewModel.class);
        lampViewModel.setLamp();
        lampViewModel.getLamp().observe(this, getLamp);
    }

    private Observer<ArrayList<Lamp>> getLamp = new Observer<ArrayList<Lamp>>() {
        @Override
        public void onChanged(ArrayList<Lamp> items) {
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