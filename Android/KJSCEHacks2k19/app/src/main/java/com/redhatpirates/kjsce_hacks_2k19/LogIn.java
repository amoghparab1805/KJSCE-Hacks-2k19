package com.redhatpirates.kjsce_hacks_2k19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        TextView userDetail;
        TextView password;
        userDetail=findViewById(R.id.userDetail);
        password=findViewById(R.id.password);
        Button logIn;
        logIn=findViewById(R.id.button_login);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,MainActivity.class));
                finish();
                //Todo Login post requests ,add details to shared pref and intent to home page
            }
        });

    }
}



