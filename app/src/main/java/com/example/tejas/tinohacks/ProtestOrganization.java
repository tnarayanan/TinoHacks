package com.example.tejas.tinohacks;

import android.content.Intent;
import android.icu.util.Calendar;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;


public class ProtestOrganization extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static String userAddress;
    public static String protestTime;
    public static String protestDate;

    public static String policy;

    public void submitButton(View view){
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker1);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();

        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        final EditText protestAddress = (EditText) findViewById(R.id.protestAddress);

        userAddress = protestAddress.getText().toString();

        protestDate = month + " " + day + ", " + year;

        String am_pm = "";

        if(hour<11){
            am_pm = "AM";
        }else{
            am_pm = "PM";
            hour = 24-hour;
        }

        protestTime = hour + ":" + minute + " " + am_pm;




        /*database.getReference("Policies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childDataSnapshot:dataSnapshot.getChildren()){
                    database.getReference("Protests").child(childDataSnapshot.getKey().toString()).setValue(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        Intent i = new Intent(this,CongratulationsActivity.class);
        startActivity(i);


    }






    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protest_organization);
    }
}
