package com.example.tejas.tinohacks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.sf.classifier4J.summariser.SimpleSummariser;

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
        DatabaseReference reference = database.getReference().child("policies").child(String.valueOf(policyID));


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                title.setText(dataSnapshot.child("title").getValue().toString());

                SimpleSummariser summariser = new SimpleSummariser();
                article.setText(summariser.summarise(dataSnapshot.child("article").getValue().toString(), 3));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
