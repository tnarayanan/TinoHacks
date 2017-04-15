package com.example.tejas.tinohacks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Policy> policies;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        policies = new ArrayList<>();
        policies.add(new Policy("WALL", "Mexico will pay"));
        policies.add(new Policy("ROAR", "Arte bear de moon"));
        policies.add(new Policy("UN GATO", "meow"));
        policies.add(new Policy("WALL", "Mexico will pay"));
        policies.add(new Policy("ROAR", "Arte bear de moon"));
        policies.add(new Policy("UN GATO", "meow"));
        policies.add(new Policy("WALL", "Mexico will pay"));
        policies.add(new Policy("ROAR", "Arte bear de moon"));
        policies.add(new Policy("UN GATO", "meow"));
        policies.add(new Policy("WALL", "Mexico will pay"));
        policies.add(new Policy("GUAUAUAUAUAUAUAUAUAUAUAUA aidhfaoidbgoiab sadnfoiasdhfio ddaf", "A long long long test to see if this will work with multiple lines"));
        policies.add(new Policy("UN GATO", "meow"));
        policies.add(new Policy("WALL", "Mexico will pay"));
        policies.add(new Policy("ROAR", "Arte bear de moon"));
        policies.add(new Policy("UN GATO", "meow"));
        policies.add(new Policy("WALL", "Mexico will pay"));
        policies.add(new Policy("ROAR", "Arte bear de moon"));
        policies.add(new Policy("UN GATO", "meow"));

    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(policies);
        rv.setAdapter(adapter);
    }
}