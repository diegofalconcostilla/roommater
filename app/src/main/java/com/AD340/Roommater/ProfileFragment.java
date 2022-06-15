package com.AD340.Roommater;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    ProfileData profileData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView name = view.findViewById(R.id.name);
        TextView age = view.findViewById(R.id.age);
        if (this.profileData != null) {
            name.setText("Welcome" + this.profileData.getName());
            age.setText("" + this.profileData.getAge());
        }

        return view;
    }

    public void setProfileData(ProfileData profileData) {
        this.profileData = profileData;
    }
}
