package com.samuelbernard147.smartvillage;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.samuelbernard147.smartvillage.preference.SettingsPreference;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup rgOptions;
    RadioButton rbIn, rbEn;
    Button btnSave;
    String language;
    Switch swPanic, swFlood, swFire;

    private SettingsPreference mSettingPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
//        rgOptions = findViewById(R.id.rg_options);
//        rbIn = findViewById(R.id.rb_in);
//        rbEn = findViewById(R.id.rb_en);
//        btnSave = findViewById(R.id.btn_save);
//        btnSave.setOnClickListener(this);

        swFlood = findViewById(R.id.switch_flood);
        swPanic = findViewById(R.id.switch_panic);
        swFire = findViewById(R.id.switch_fire);

//        Menampilkan backbutton
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.settings));
        }

        mSettingPreference = new SettingsPreference(this);

//        Cek data preference
//        if (mSettingPreference.getLang().isEmpty()) {
//            language = Locale.getDefault().getLanguage();
//        } else {
//            language = mSettingPreference.getLang();
//        }

//        setupRadio(language);
        setupSwitch();

        swPanic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSettingPreference.setPanic(isChecked);
            }
        });

        swFire.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSettingPreference.setFire(isChecked);
            }
        });

        swFlood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSettingPreference.setFlood(isChecked);
            }
        });
    }

    void setupSwitch() {
        swPanic.setChecked(mSettingPreference.getPanic());
        swFlood.setChecked(mSettingPreference.getFlood());
        swFire.setChecked(mSettingPreference.getFire());
    }

    //    Set radio button yang aktif
//    void setupRadio(String lang) {
//        if (lang.equals("en")) {
//            rbEn.setChecked(true);
//            rbIn.setChecked(false);
//        } else {
//            rbEn.setChecked(false);
//            rbIn.setChecked(true);
//        }
//    }

    //    Untuk back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void setLanguage(String language) {
        Locale mLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.locale = mLocale;
        res.updateConfiguration(config, dm);
        Intent i = new Intent(SettingsActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //    Set bahasa di preference
        mSettingPreference.setLang(language);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.btn_save) {
//            mSettingPreference.setPanic(swPanic.isChecked());
//            mSettingPreference.setFlood(swFlood.isChecked());
//            mSettingPreference.setFire(swFire.isChecked());
//            Intent i = new Intent(SettingsActivity.this, MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            switch (rgOptions.getCheckedRadioButtonId()) {
//                case R.id.rb_en:
//                    setLanguage("en");
//                    break;
//                case R.id.rb_in:
//                    setLanguage("in");
//                    break;
//            }
//        }
    }
}