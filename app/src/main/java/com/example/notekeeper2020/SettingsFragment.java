package com.example.notekeeper2020;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        requireActivity().setTitle("Settings");
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity2 mainActivity = (MainActivity2) getActivity();
        assert mainActivity != null;
        mainActivity.updateNavHeader();
    }
}