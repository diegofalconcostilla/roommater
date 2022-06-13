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
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        TextView name = view.findViewById(R.id.id_name);
        TextView age = view.findViewById(R.id.id_age);
        TextView zip = view.findViewById(R.id.id_zip);

        if (this.profileData != null) {
            name.setText(this.profileData.getName());
            age.setText("" + this.profileData.getAge());
            zip.setText(this.profileData.getZip());
        }
        return view;
    }

    public void setProfileData(ProfileData profileData) {
        this.profileData = profileData;
    }
}
