package com.example.tejas.tinohacks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class detailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Bundle extras = getIntent().getExtras();
        int policyID = extras.getInt("policyID");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
    }
}
