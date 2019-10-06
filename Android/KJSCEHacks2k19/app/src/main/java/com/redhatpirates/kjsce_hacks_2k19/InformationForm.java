package com.redhatpirates.kjsce_hacks_2k19;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationForm extends AppCompatActivity {
    String type, pno;
    AutoCompleteTextView userfName, userlName, email, phone,age;
    Button save;
    FirebaseUser user;
    TextInputLayout layoutEmail, layoutPhone;
    SharedPreferences spref;
    SharedPreferences.Editor editor;
    ProgressBar pbar;
    UserDetails ud;
    String [] genderlist = {"Female","Male","Other"};
    Spinner mSpin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_form);
        pbar = findViewById(R.id.pbar);
        type = getIntent().getStringExtra("type");
        pno = getIntent().getStringExtra("phone");
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutPhone = findViewById(R.id.layoutPhone);
        userfName = findViewById(R.id.userfname);
        userlName = findViewById(R.id.userlname);
        age=findViewById(R.id.age);
        email = findViewById(R.id.email);
        mSpin = findViewById(R.id.gender_spinner);
        phone = findViewById(R.id.phone);
        save = findViewById(R.id.save);
        mSpin.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,genderlist));
        user = FirebaseAuth.getInstance().getCurrentUser();
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        editor = spref.edit();
        if (type.equals("phone")) {
            layoutPhone.setVisibility(View.VISIBLE);
            phone.setText(pno);
            phone.setEnabled(false);
        } else {
            layoutEmail.setVisibility(View.VISIBLE);
            userfName.setText(user.getDisplayName().split(" ")[0]);
            userlName.setText(user.getDisplayName().split(" ")[1]);
            email.setText(user.getEmail());
            email.setEnabled(false);

        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userfName.getText().toString().equals("") || userlName.getText().toString().equals("") ||age.getText().toString().equals("")) {
                    Toast.makeText(InformationForm.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    if (type.equals("phone")) {
                        ud = new UserDetails(""+userfName.getText().toString()+" "+userlName.getText().toString(),"",phone.getText().toString(),user.getUid(),user.getProviderId(),"",age.getText().toString(),genderlist[(int) mSpin.getSelectedItemId()].substring(0,1),"");
                    } else {
                        ud = new UserDetails(""+userfName.getText().toString()+" "+userlName.getText().toString(),user.getEmail(),"",user.getUid(),user.getProviderId(),user.getPhotoUrl().toString(),age.getText().toString(),genderlist[(int) mSpin.getSelectedItemId()].substring(0,1),"");
                    }
                    save.setVisibility(View.GONE);
                    pbar.setVisibility(View.VISIBLE);
                    RetrofitInterface ri = RetrofitInstance.getInstance().create(RetrofitInterface.class);
                    Call<UserDetails> userDetailsCall = ri.signIn(ud);
                    Handler handler =new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            String json = gson.toJson(ud);
                            editor.putString("user", json);
                            editor.putString("token", ud.getDisplayName());
                            editor.commit();
                            startActivity(new Intent(InformationForm.this, MainActivity.class));
                            finish();
                        }
                    },2500);

//                    userDetailsCall.enqueue(new Callback<UserDetails>() {
//                        @Override
//                        public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
//                            if(response.body()!=null) {
//                                Toast.makeText(InformationForm.this, "Welcome", Toast.LENGTH_LONG).show();
//                                editor.putString("token", response.body().getToken());
//                                Gson gson = new Gson();
//                                String json = gson.toJson(ud);
//                                editor.putString("user", json);
//                                editor.commit();
//                                startActivity(new Intent(InformationForm.this, MainActivity.class));
//                                finish();
//                            }
//                            else
//                            {
//                                Toast.makeText(InformationForm.this,"Something went wrong",Toast.LENGTH_LONG).show();
//                                save.setVisibility(View.VISIBLE);
//                                pbar.setVisibility(View.GONE);
//                            }
//                        }
//                        @Override
//                        public void onFailure(Call<UserDetails> call, Throwable t) {
//                            Toast.makeText(InformationForm.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
//                            save.setVisibility(View.VISIBLE);
//                            pbar.setVisibility(View.GONE);
//                        }
//                    });
                }
            }
        });
    }
}
