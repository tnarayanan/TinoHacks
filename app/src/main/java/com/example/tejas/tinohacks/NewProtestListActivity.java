package com.example.tejas.tinohacks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewProtestListActivity extends AppCompatActivity {

    private static ArrayAdapter<String> adapter;
    private static ArrayList<CustomEvent> events;
    ListView listView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference protestsRef = database.getReference("Protests");
    DatabaseReference policiesRef = database.getReference("policies");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_protest_list);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        events = new ArrayList<>();

        // Array adapter will display TITLE

        // When clicked, alert dialog will display the ADDRESS, TIME/DATE, NUMBER OF ATTENDEES




        // array adapter

        protestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot policySnapshot : dataSnapshot.getChildren()) {

                    String currID = policySnapshot.getKey();


                    for (DataSnapshot addressSnapshot : policySnapshot.getChildren()) {


                        for (DataSnapshot timeSnapshot : addressSnapshot.getChildren()) {
                            policiesRef.child(currID).child("title").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    adapter.add(dataSnapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }


                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setAdapter(adapter);




        // Custom event initialization

        protestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot policySnapshot : dataSnapshot.getChildren()) {

                    for (DataSnapshot addressSnapshot : policySnapshot.getChildren()) {

                        for (DataSnapshot timeSnapshot : addressSnapshot.getChildren()) {
                            CustomEvent currEvent = new CustomEvent();

                            currEvent.setTime(timeSnapshot.getKey());
                            currEvent.setAddress(addressSnapshot.getKey());
                            currEvent.setAttendees(timeSnapshot.getValue(Integer.class));

                            events.add(currEvent);
                        }


                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomEvent currEvent = events.get(position);
                new AlertDialog.Builder(NewProtestListActivity.this)
                        .setTitle("Protest Info")
                        .setMessage(Html.fromHtml("<b>Address: </b>" + currEvent.getAddress() + "\n" + "<b>Date: </b>" + currEvent.getTime() + "\n" + "<b># of Attendees: </b>" + currEvent.getAttendees()))
                        .show();
            }
        });
    }
}
