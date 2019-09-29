package com.redhatpirates.kjsce_hacks_2k19;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class InformationForm extends AppCompatActivity {
    String type,pno;
    AutoCompleteTextView userName,email,phone,age,pwd,cpwd;
    Button save;
    FirebaseUser user ;
    TextInputLayout layoutEmail,layoutPhone;
    SharedPreferences spref;
    SharedPreferences.Editor editor;
    FirebaseFirestore fdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_form);
        type=getIntent().getStringExtra("type");
        pno=getIntent().getStringExtra("phone");
        fdb=FirebaseFirestore.getInstance();
        layoutEmail = (TextInputLayout) findViewById(R.id.layoutEmail);
        layoutPhone = (TextInputLayout) findViewById(R.id.layoutPhone);
        userName=findViewById(R.id.username);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        age=findViewById(R.id.register_age);
        pwd=findViewById(R.id.pwd);
        cpwd=findViewById(R.id.cpwd);
        save=findViewById(R.id.save);
        user = FirebaseAuth.getInstance().getCurrentUser();
        spref = getApplicationContext().getSharedPreferences("userName", MODE_PRIVATE);
        editor = spref.edit();
        if(type.equals("phone")){
            layoutEmail.setHint("Enter Email(optional)");
            phone.setText(pno);
            phone.setEnabled(false);
        }
        else
        {   userName.setText(user.getDisplayName());
            email.setText(user.getEmail());
            email.setEnabled(false);
            layoutPhone.setHint("Enter Phone No.(optional)");
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userName.getText().toString().equals("") || age.getText().toString().equals("")||pwd.getText().toString().equals("")){
                    Toast.makeText(InformationForm.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
                }
                else if(!cpwd.getText().toString().equals(pwd.getText().toString())){
                    Toast.makeText(InformationForm.this,"Passwords do not match",Toast.LENGTH_LONG).show();
                    pwd.setText("");
                    cpwd.setText("");
                }
                else
                {
                    //Todo user info post request
save.setVisibility(View.GONE);
                        fdb.collection("Users").document(userName.getText().toString()).set(new UserDetails(userName.getText().toString(),age.getText().toString(),email.getText().toString(),pwd.getText().toString(),phone.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                editor.putString("userName",userName.getText().toString());
                                editor.commit();
                                startActivity(new Intent(InformationForm.this,MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                save.setVisibility(View.VISIBLE);
                                Toast.makeText(InformationForm.this,"Something went wrong",Toast.LENGTH_LONG).show();
                            }
                        });

                }
            }
        });
    }
}
