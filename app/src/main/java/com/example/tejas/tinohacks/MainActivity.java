package com.example.tejas.tinohacks;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.tejas.tinohacks.R.id.signOut;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Policy> policies;


    private RecyclerView rv;

    public GoogleApiClient mGoogleApiClient;



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

//        reference.removeValue();
//
//        new UpdateArticlesTask().execute("https://en.wikinews.org/wiki/Category:Politics_and_conflicts");

        //double currTime = System.currentTimeMillis();
        /*while (UpdateArticlesTask.titles.size() < 10 || UpdateArticlesTask.articles.size() < 10) {
            // waits
        }


        for (int i = 0; i < 10; i++) {
            reference.child(String.valueOf(i)).child("title").setValue(UpdateArticlesTask.titles.get(i));
            reference.child(String.valueOf(i)).child("article").setValue(UpdateArticlesTask.articles.get(i));
        }*/

        //Toast.makeText(this, "Past Firebase", Toast.LENGTH_SHORT).show();



        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        ItemClickSupport.addTo(rv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), detailedActivity.class);
                i.putExtra("policyID", position);
                startActivity(i);
            }
        });

        initializeData();
        //initializeAdapter();
    }





   @Override
    public void onBackPressed(){
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }


       /* FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {

                            }
                        });*/



    private void initializeData(){

        policies = new ArrayList<>();



        /*reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    //Toast.makeText(MainActivity.this, d.child("title").getValue().toString() + " |||||| " + d.child("article").getValue().toString(), Toast.LENGTH_LONG).show();
                    policies.add(new Policy(d.child("title").getValue().toString(), d.child("article").getValue().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    //Toast.makeText(MainActivity.this, d.child("title").getValue().toString() + " |||||| " + d.child("article").getValue().toString(), Toast.LENGTH_LONG).show();
                    policies.add(new Policy(d.child("title").getValue(String.class), d.child("article").getValue(String.class).substring(0, 100) + "..."));
                    /*for(DataSnapshot d1: d.getChildren()) {
                        policies.add(new Policy(d.child("title").getValue(String.class), d.child("article").getValue(String.class)));
                    }*/
                }

                //Toast.makeText(MainActivity.this, policies.toString(), Toast.LENGTH_LONG).show();

                RVAdapter adapter = new RVAdapter(policies);
                rv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*
        for (int i = 0; i < 10; i++) {
            policies.add(new Policy(reference.child(String.valueOf(i)).child("title").toString(), reference.child(String.valueOf(i)).child("article").toString()));
        }*/


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