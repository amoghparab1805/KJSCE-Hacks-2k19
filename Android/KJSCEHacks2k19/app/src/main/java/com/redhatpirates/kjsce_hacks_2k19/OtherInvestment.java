package com.redhatpirates.kjsce_hacks_2k19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class OtherInvestment extends AppCompatActivity {
Intent in;
String other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_investment);
        in=getIntent();
        other=in.getStringExtra("type");
        GraphView linegraph = (GraphView) findViewById(R.id.line_graph);
        LineGraphSeries<DataPoint> lineSeries = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        linegraph.addSeries(lineSeries);

    }
}
