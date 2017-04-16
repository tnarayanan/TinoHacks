package com.example.tejas.tinohacks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.tejas.tinohacks.MainActivity.policyID;

public class CongratulationsActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public String policyMessage;
    public String policyTitle;
    public int koolCounter;
    public boolean koolBool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);

        //Bundle extras = getIntent().getExtras();
        //int policyID = extras.getInt("policyID");

        //Toast.makeText(this, ProtestOrganization.userAddress, Toast.LENGTH_LONG).show();

        koolBool = true;

        database.getReference("Protests").child(String.valueOf(policyID)).child(ProtestOrganization.userAddress).child(ProtestOrganization.protestDate + "---" + ProtestOrganization.protestTime).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()&&koolBool){
                    int current = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                    database.getReference("Protests").child(String.valueOf(policyID)).child(ProtestOrganization.userAddress).child(ProtestOrganization.protestDate + "---" + ProtestOrganization.protestTime).setValue(current=current+1);
                    koolBool = false;

                } else if(!dataSnapshot.exists()&&koolBool){
                    database.getReference("Protests").child(String.valueOf(policyID)).child(ProtestOrganization.userAddress).child(ProtestOrganization.protestDate + "---" + ProtestOrganization.protestTime).setValue(1);
                    koolBool = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



//
//        database.getReference("Protests").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ChildDataSnapshot:dataSnapshot.getChildren()){
//                    if(ChildDataSnapshot.getKey().toString().equals(policyTitle)){
//                        koolCounter++;
//                    }
//                    for(DataSnapshot ChildSnapshotTwo:ChildDataSnapshot.getChildren()){
//                        if(ChildSnapshotTwo.getKey().toString().equals(ProtestOrganization.userAddress)){
//                            koolCounter++;
//                        }
//                        for(DataSnapshot ChildSnapshotThree:ChildDataSnapshot.getChildren()){
//                            if(ChildSnapshotThree.getKey().toString().equals(ProtestOrganization.protestDate+" "+ProtestOrganization.protestTime)){
//                                koolCounter++;
//                            }
//                            for(DataSnapshot ChildSnapshotFour:ChildDataSnapshot.getChildren()){
//                                if(koolCounter==3){
//                                    ChildSnapshotFour.getRef().setValue("1");
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

    public void goHome(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void toMap(View view){
        Intent j = new Intent(getApplicationContext(), NewProtestListActivity.class);
        startActivity(j);
    }


}
