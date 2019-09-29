package com.redhatpirates.kjsce_hacks_2k19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogInSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_sign_up);
        Button logIn,signUp;
        logIn=findViewById(R.id.LogIn);
        signUp=findViewById(R.id.SignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInSignUp.this,SignInOptions.class));
                finish();
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInSignUp.this,LogIn.class));
                finish();
            }
        });
    }
}
