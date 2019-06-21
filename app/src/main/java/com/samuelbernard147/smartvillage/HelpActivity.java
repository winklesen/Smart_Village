package com.samuelbernard147.smartvillage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import com.samuelbernard147.smartvillage.adapter.ExpendableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpActivity extends AppCompatActivity {
    ExpendableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.help));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expListView = findViewById(R.id.elv_about);
        prepareListData();
        listAdapter = new ExpendableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeader.add(getResources().getString(R.string.agriculture));
        listDataHeader.add(getResources().getString(R.string.irrigation));
        listDataHeader.add(getResources().getString(R.string.security_system));
        listDataHeader.add(getResources().getString(R.string.smart_lamp));

        List<String> agriculture = new ArrayList<String>();
        agriculture.add(getResources().getString(R.string.petunjukAgriculture));

        List<String> irrigation = new ArrayList<String>();
        irrigation.add(getResources().getString(R.string.petunjukIrrigation));

        List<String> streetLamp = new ArrayList<String>();
        streetLamp.add(getResources().getString(R.string.petunjukStreetLamp));

        List<String> securitySsytem = new ArrayList<String>();
        securitySsytem.add(getResources().getString(R.string.petunjukSecuritySystem));

        listDataChild.put(listDataHeader.get(0), agriculture);
        listDataChild.put(listDataHeader.get(1), irrigation);
        listDataChild.put(listDataHeader.get(2), securitySsytem);
        listDataChild.put(listDataHeader.get(3), streetLamp);
    }
}