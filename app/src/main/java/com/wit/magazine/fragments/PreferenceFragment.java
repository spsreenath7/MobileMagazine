package com.wit.magazine.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wit.magazine.R;
import com.wit.magazine.activities.HomeActivity;
import com.wit.magazine.activities.MapActivity;
import com.wit.magazine.models.UserPreference;

import static com.wit.magazine.activities.HomeActivity.app;

public class PreferenceFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    HomeActivity activity;
    public View v;
    UserPreference userpref;

    TextView region;
    Spinner spinner;
    Switch all;
    CheckBox entertainment, business, health, science, sports, technology;
    Button savepref;
    ImageButton mapsButton;

    String conuntSelected;
    public DatabaseReference databaseReference;

    public PreferenceFragment(){

    }

    public static PreferenceFragment newInstance() {
        PreferenceFragment fragment = new PreferenceFragment();
        return fragment;
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        userpref =app.userPreference;
        databaseReference = FirebaseDatabase.getInstance().getReference("UserPreference").child(app.fireBaseUser);
        this.activity = (HomeActivity) context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(userpref == null){
            new UserPreference(true, false, false, false, false, false, false, "ie", "Ireland", 10);
        }
//        databaseReference = FirebaseDatabase.getInstance().getReference("UserPreference").child(app.fireBaseUser);
//
//        activity.showLoader("downloading users");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                UserPreference dbUserPref = dataSnapshot.getValue(UserPreference.class);
//                if(dbUserPref != null){
//                    userpref =dbUserPref;
//                }
//                activity.hideLoader();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_preference, parent, false);
        getActivity().setTitle("User Preference");
        region =(TextView) v.findViewById(R.id.textViewCountry) ;
        all = (Switch) v.findViewById(R.id.switchAll) ;
        entertainment = (CheckBox)v.findViewById(R.id.checkEntertainment);
        business = (CheckBox)v.findViewById(R.id.checkBusiness);
        health = (CheckBox)v.findViewById(R.id.checkHealth);
        science = (CheckBox)v.findViewById(R.id.checkScience);
        sports = (CheckBox)v.findViewById(R.id.checkSports);
        technology = (CheckBox)v.findViewById(R.id.checkTechnology);
        savepref = (Button)v.findViewById(R.id.buttonSavePref) ;
        mapsButton =(ImageButton)v.findViewById(R.id.imageButtonMaps) ;

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.articleCount,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner= v.findViewById(R.id.spinnerArticleCount);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        updateView();

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MapActivity.class));
            }
        });

        savepref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPreference  userPreferences =collectPreference();
                databaseReference.setValue(userPreferences);
                Toast.makeText(getActivity(),"Saved sucessfully !! ",Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    private UserPreference collectPreference() {
//        Log.v("magazine","App country: "+app.userPreference.setCountryname(););
//        Log.v("magazine","App country code: "+app.countryCode);
        UserPreference userPreference = new UserPreference(all.isChecked(), business.isChecked(), entertainment.isChecked(), science.isChecked(), health.isChecked(), sports.isChecked(), technology.isChecked(), app.userPreference.getCountrycode(), app.userPreference.getCountryname(), Integer.parseInt(conuntSelected));
        return userPreference;
    }

    private void updateView() {
        region.setText(userpref.getCountryname()+", "+userpref.getCountrycode());

        all.setChecked(userpref.isAll());

        entertainment.setActivated(userpref.isEntertainment());
        business.setActivated(userpref.isBusiness());
        health.setActivated(userpref.isHealth());
        science.setActivated(userpref.isScience());
        sports.setActivated(userpref.isSports());
        technology.setActivated(userpref.isTechnology());

//        spinner.setSelection(userpref.getArticlecount()/5);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        conuntSelected =  parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
