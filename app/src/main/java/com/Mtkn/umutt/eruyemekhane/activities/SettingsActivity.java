package com.Mtkn.umutt.eruyemekhane.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.view.MenuItem;

import com.Mtkn.umutt.eruyemekhane.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat
    {
        @Override
        public void onCreatePreferences(Bundle bundle, String rootKey) {
            setPreferencesFromResource(R.xml.main_preference,rootKey);

            SwitchPreferenceCompat pref_hideMorning =(SwitchPreferenceCompat) findPreference(getString(R.string.pref_hideMorning));
            SwitchPreferenceCompat pref_hideEvening =(SwitchPreferenceCompat) findPreference(getString(R.string.pref_hideEvening));

            pref_hideMorning.setOnPreferenceChangeListener((preference, o) ->
            {

                if(o.toString().equals("true"))//Eğer switch aktifleştirilmiş ise
                {
                   pref_hideEvening.setChecked(false);
                }
                return true;
            });

            pref_hideEvening.setOnPreferenceChangeListener((preference, o) -> {

                if(o.toString().equals("true"))//Eğer switch aktifleştirilmiş ise
                {
                    pref_hideMorning.setChecked(false);
                }
                return true;
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            startActivity(new Intent(this,MainActivity.class));
            this.overridePendingTransition(0, 0);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }


}
