package com.example.tejas.tinohacks;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProtestListActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String title;
    private String message;
    int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protest_list);

        ListView listView = (ListView) findViewById(R.id.listView);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProtestListActivity.this, android.R.layout.simple_list_item_1);
        final ArrayList<CustomEvent> data = new ArrayList<>();

        database.getReference("Protests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot Policy: dataSnapshot.getChildren()){

                    String index = Policy.getKey();

                    //Toast.makeText(ProtestListActivity.this, "POLICY " + Policy.toString(), Toast.LENGTH_SHORT).show();


                    for(DataSnapshot addresses: Policy.getChildren()){

                        //Toast.makeText(ProtestListActivity.this, "ADDRESSES " + addresses.toString(), Toast.LENGTH_SHORT).show();

                        for (DataSnapshot timeSnapshots: addresses.getChildren()) {
                            CustomEvent currCustomEvent = new CustomEvent();
                            database.getReference("policies").child(Policy.getKey()).child("title").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //adapter.add(dataSnapshot.getValue().toString());
                                    title = dataSnapshot.getValue().toString();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            adapter.add(title);
                            currCustomEvent.setTitle(title);
                            currCustomEvent.setIndex(n);
                            currCustomEvent.setAddress(addresses.getKey());
                            currCustomEvent.setAttendees(timeSnapshots.getValue(Integer.class));
                            currCustomEvent.setTime(timeSnapshots.getKey());
                            data.add(currCustomEvent);
                            n++;
                        }






                        // Policy.getKey() + "/n" + addresses.getKey()
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String index = adapter.getItem(position);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("About Protest:");
                for (CustomEvent currCustomEvent : data) {
                    if (currCustomEvent.getIndex() == position) {
                        // correct item
                        builder.setMessage("Date: " + currCustomEvent.getTime() + "\n"
                                            + "Address: " + currCustomEvent.getAddress() + "\n"
                                            + "Article: " + currCustomEvent.getTitle());
                        break;
                    }
                }



                // Set up the input
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });


    }
}
