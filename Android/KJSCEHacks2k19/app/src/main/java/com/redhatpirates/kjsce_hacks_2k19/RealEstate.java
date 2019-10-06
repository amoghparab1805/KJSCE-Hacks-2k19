package com.redhatpirates.kjsce_hacks_2k19;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RealEstate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate);
        final TextView link1,link2,link3;
        final Button submit;
        final ProgressBar pbar;
        RecyclerView rview;

        submit=findViewById(R.id.submit);
        pbar=findViewById(R.id.pbar);
        link1=findViewById(R.id.link1);

        link2=findViewById(R.id.link2);
        link3=findViewById(R.id.link3);
        final LinearLayout layout=findViewById(R.id.layout);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbar.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
                Handler handler =new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pbar.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                        layout.setVisibility(View.VISIBLE);
                        link1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(link1.getText().toString()));
                                startActivity(browserIntent);
                            }
                        });
                        link2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(link2.getText().toString()));
                                startActivity(browserIntent);
                            }
                        });
                        link3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(link3.getText().toString()));
                                startActivity(browserIntent);
                            }
                        });

                    }
                },2500);
            }
        });
    }
}
