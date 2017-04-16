package com.example.tejas.tinohacks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Policy> policies;
    private RecyclerView rv;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference reference = database.getReference("policies");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        reference.removeValue();


        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        ItemClickSupport.addTo(rv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
            }
        });

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){

        policies = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    policies.add(new Policy(d.getKey(), d.getValue().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(policies);
        rv.setAdapter(adapter);
    }

    private static String accessStrings(String url) throws IOException { // one long string of all info in paragraph
        String allInfo = "";
        URL website = new URL(url);
        BufferedReader html = new BufferedReader(
                new InputStreamReader(website.openStream()));
        String inputLine;

        while ((inputLine = html.readLine()) != null) {
            Document doc = Jsoup.parse(inputLine);
            String curLine = doc.getElementsByTag("p").text();
            if (curLine != null) {
                allInfo += curLine;
            }
        }

        html.close();
        return allInfo;
    }
}