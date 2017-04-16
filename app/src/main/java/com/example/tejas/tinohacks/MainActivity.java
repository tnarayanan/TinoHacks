package com.example.tejas.tinohacks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_main);

        reference.removeValue();

        new UpdateArticlesTask().execute("https://en.wikinews.org/wiki/Category:Politics_and_conflicts");

        while (!UpdateArticlesTask.finishedUpdating) {
            // waits
        }


        for (int i = 0; i < 10; i++) {
            reference.child(String.valueOf(i)).child("title").setValue(UpdateArticlesTask.titles.get(i));
            reference.child(String.valueOf(i)).child("article").setValue(UpdateArticlesTask.articles.get(i));
        }



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

    /*private static String accessStrings(String url) throws IOException { // one long string of all info in paragraph
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

    private static String accessTitles(String url) throws IOException {
        String allInfo = "";
        URL website = new URL(url);
        BufferedReader html = new BufferedReader(
                new InputStreamReader(website.openStream()));
        String inputLine;

        while ((inputLine = html.readLine()) != null) {
            Document doc = Jsoup.parse(inputLine);
            String curLine = doc.getElementsByTag("h1").text();
            if (curLine != null) {
                return curLine;
            }
        }

        return allInfo;
    }

    private static void updateArticles() throws IOException {
        URL website = new URL("https://en.wikinews.org/wiki/Category:Politics_and_conflicts");
        BufferedReader br = new BufferedReader(
                new InputStreamReader(website.openStream()));
        String inputLine;

        int counter = 0;

        while ((inputLine = br.readLine()) != null) {

            Document doc = Jsoup.parse(inputLine);
            String storeString = doc.getElementsByTag("a").attr("href");



            if (storeString.length() > 8 && storeString.substring(0,6).equals("/wiki/") && counter < 10) {
                String goodUrl = "en.wikinews.org" + storeString;

                articles.add(accessStrings(goodUrl));
                titles.add(accessTitles(goodUrl));

                counter++;

            }

            if (counter == 10) {
                break;
            }

        }
        br.close();
    }*/
}