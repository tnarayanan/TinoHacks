package com.example.tejas.tinohacks;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


import net.sf.classifier4J.summariser.SimpleSummariser;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class detailedActivity extends AppCompatActivity {

    TextView title;
    TextView article;
    Button forum;
    Button protest;
    RadioGroup rg;
    RadioButton happy, sad;

    BarChart barChart;

    private int currPosVal, currNegVal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Bundle extras = getIntent().getExtras();
        final int policyID = extras.getInt("policyID");

        title = (TextView) findViewById(R.id.title);
        article = (TextView) findViewById(R.id.article);
        forum = (Button) findViewById(R.id.forum);
        protest = (Button) findViewById(R.id.protest);

        rg = (RadioGroup) findViewById(R.id.rg);
        happy = (RadioButton) findViewById(R.id.happy);
        sad = (RadioButton) findViewById(R.id.sad);

        barChart = (BarChart) findViewById(R.id.barGraph);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference().child("policies").child(String.valueOf(policyID));

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {



                reference.child("0").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        currNegVal = dataSnapshot.getValue(Integer.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                reference.child("1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        currPosVal = dataSnapshot.getValue(Integer.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



                if (checkedId == R.id.sad) { // Neg
                    reference.child("0").setValue(currNegVal + 1);
                } else if (checkedId == R.id.happy) { // Pos
                    reference.child("1").setValue(currPosVal + 1);
                }


                ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();

                barEntries.add(new BarEntry(currNegVal, 0));
                barEntries.add(new BarEntry(currPosVal, 1));
                BarDataSet barDataSet = new BarDataSet(barEntries, "Satisfaction");

                ArrayList<String> xAxis = new ArrayList<String>();

                xAxis.add("Unsatisfied");
                xAxis.add("Satisfied");

                BarData data = new BarData(xAxis, barDataSet);
                barChart.setData(data);

                barChart.setTouchEnabled(true);
                barChart.setDragEnabled(true);
                barChart.setScaleEnabled(true);




                /*
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                String series1 = "Unsatisfied";
                String series2 = "Satisfied";

                String category1 = "Satisfaction";

                dataset.addValue(currNegVal, series1, category1);
                dataset.addValue(currPosVal, series2, category1);

                JFreeChart chart = ChartFactory.createBarChart("Satisfaction", // chart
                        // title
                        "", // domain axis label
                        "# of Users", // range axis label
                        dataset, // data
                        PlotOrientation.VERTICAL, // orientation
                        false, // include legend
                        false, // tooltips?
                        false // URLs?
                );

                CategoryPlot plot = (CategoryPlot) chart.getPlot();

                //final ChartComposite frame = new ChartComp*/

            }
        });

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

        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForumActivity.class);
                i.putExtra("policyID", policyID);
                startActivity(i);
            }
        });

        protest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProtestOrganization.class);
                i.putExtra("policyID", policyID);
                startActivity(i);
            }
        });


    }
}
