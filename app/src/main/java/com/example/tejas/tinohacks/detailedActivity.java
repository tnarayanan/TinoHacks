package com.example.tejas.tinohacks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class detailedActivity extends AppCompatActivity {

    TextView title;
    TextView article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Bundle extras = getIntent().getExtras();
        int policyID = extras.getInt("policyID");

        title = (TextView) findViewById(R.id.title);
        article = (TextView) findViewById(R.id.article);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference("policies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ChildDataSnapshot: dataSnapshot.getChildren()){
                    title.setText(ChildDataSnapshot.child("title").getValue(String.class));
                    article.setText(ChildDataSnapshot.child("article").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
