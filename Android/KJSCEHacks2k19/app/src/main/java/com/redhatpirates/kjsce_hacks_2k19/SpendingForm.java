package com.redhatpirates.kjsce_hacks_2k19;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpendingForm extends AppCompatActivity {
    AutoCompleteTextView mAmount,mDate;
    Button mSubmit,mDatePicker;
    String month;
    private int mYear, mMonth, mDay;
    Spinner mSpin;
    String typeA;
    String json,dateF;
    Gson gson;
    SharedPreferences spref;
    String[] expense = {"Necessities","Shopping","Bills","Tax","Insurance"};
    TextInputLayout dateV;
    UserInfo user;
    MoneySpent ms;
    LinearLayout category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_form);
        mSubmit = findViewById(R.id.submit);
        mSpin = findViewById(R.id.spinner);
        mAmount = findViewById(R.id.amount);
        category=findViewById(R.id.category);
        dateV=findViewById(R.id.dateV);
        mDate = findViewById(R.id.date);
        mDatePicker = findViewById(R.id.date_picker);
        Date c = Calendar.getInstance().getTime();
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        gson = new Gson();
        json = spref.getString("user", "");
        final UserDetails ud = gson.fromJson(json, UserDetails.class);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar c2 = Calendar.getInstance();
        mYear = c2.get(Calendar.YEAR);
        mMonth = c2.get(Calendar.MONTH);
        mDay = c2.get(Calendar.DAY_OF_MONTH);
        final String formattedDate = df.format(c);
        mDate.setText(formattedDate);
        typeA=getIntent().getStringExtra("type");
        final ProgressBar pbar;
        pbar=findViewById(R.id.pbar);
        if(typeA.equals("expense"))
        {
            mSpin.setAdapter(new ArrayAdapter<>(SpendingForm.this,android.R.layout.simple_spinner_dropdown_item,expense));
            category.setVisibility(View.VISIBLE);
            }
        else if(typeA.equals("income"))
        {
mSpin.setVisibility(View.GONE);
category.setVisibility(View.GONE);
        }
        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(SpendingForm.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                switch (monthOfYear+1){

                                    case 1: month = "Jan";
                                        break;
                                    case 2: month = "Feb";
                                        break;
                                    case 3: month = "Mar";
                                        break;
                                    case 4: month = "Apr";
                                        break;
                                    case 5: month = "May";
                                        break;
                                    case 6: month = "Jun";
                                        break;
                                    case 7: month = "Jul";
                                        break;
                                    case 8: month = "Aug";
                                        break;
                                    case 9: month = "Sep";
                                        break;
                                    case 10: month = "Oct";
                                        break;
                                    case 11: month = "Nov";
                                        break;
                                    case 12: month = "Dec";
                                        break;

                                }
                                dateV.setVisibility(View.VISIBLE);
                                mDate.setText(dayOfMonth + "-" + month + "-" + year);
                                dateF=dayOfMonth + "-" + month + "-" + year;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbar.setVisibility(View.VISIBLE);
                mSubmit.setVisibility(View.GONE);
                if(typeA.equals("income")){
                   ms=new MoneySpent(ud.getUid(),mAmount.getText().toString(),dateF,"income");
                }
                else
                {
                    ms=new MoneySpent(ud.getUid(),mAmount.getText().toString(),dateF,""+mSpin.getSelectedItem().toString().toLowerCase());
                }
                RetrofitInterface ri = RetrofitInstance.getInstance().create(RetrofitInterface.class);
                final Call<MoneySpent> moneySpent = ri.addExpenditure(ms);
                moneySpent.enqueue(new Callback<MoneySpent>() {
                    @Override
                    public void onResponse(Call<MoneySpent> call, Response<MoneySpent> response) {
                        if(response.body()!=null)
                        {
                            startActivity(new Intent(SpendingForm.this,BottomNavigationMain.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SpendingForm.this,"Something went wrong",Toast.LENGTH_LONG).show();
                            pbar.setVisibility(View.GONE);
                            mSubmit.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MoneySpent> call, Throwable t) {
                        Toast.makeText(SpendingForm.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
                        pbar.setVisibility(View.GONE);
                        mSubmit.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });

    }
}