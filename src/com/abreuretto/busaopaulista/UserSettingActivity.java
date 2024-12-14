package com.abreuretto.busaopaulista;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.abreuretto.busaopaulista.R;
 
public class UserSettingActivity extends PreferenceActivity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        addPreferencesFromResource(R.xml.settings);
 
    }
}

