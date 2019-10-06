package com.redhatpirates.kjsce_hacks_2k19;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }
    TextView income,expense,balance;
    Button addI,addE,history;
    String json;
    Gson gson;
    SharedPreferences spref;
    long expenseA,incomeA,totalA;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_dashboard, container, false);
        income=v.findViewById(R.id.text_view_income);
        gson = new Gson();
        expense=v.findViewById(R.id.text_view_expense);
        balance=v.findViewById(R.id.text_view_balance);
        addI=v.findViewById(R.id.button_add_income);
        spref = v.getContext().getSharedPreferences("user", MODE_PRIVATE);
        history=v.findViewById(R.id.button_history);
        addE=v.findViewById(R.id.button_add_expense);
        json = spref.getString("user", "");
        UserDetails ud = gson.fromJson(json, UserDetails.class);
        RetrofitInterface ri = RetrofitInstance.getInstance().create(RetrofitInterface.class);
        final Call<ArrayList<MoneySpent>> moneySpent = ri.getExpenditures(new MoneySpent(ud.getUid()));
//        moneySpent.enqueue(new Callback<ArrayList<MoneySpent>>() {
//            @Override
//            public void onResponse(Call<ArrayList<MoneySpent>> call, Response<ArrayList<MoneySpent>> response) {
//                ArrayList<MoneySpent>ms=response.body();
//                if(response.body()!=null)
//                {expenseA=0;
//                incomeA=0;
//                    for(MoneySpent ms1:ms)
//                    {
//                        if(ms1.getExpenditure_type().equals("income"))
//                        {
//                           incomeA+=incomeA;
//                        }
//                        else
//                        {
//                            expenseA+=expenseA;
//                        }
//                    }
//                    income.setText(""+incomeA);
//                    expense.setText(""+expenseA);
//                    totalA=incomeA-expenseA;
//                    balance.setText(""+totalA);
//
//                }
//                else
//                {
//                    Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<MoneySpent>> call, Throwable t) {
//                Toast.makeText(getActivity(),""+t.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ExpenditureHistory.class));

            }
        });
        addE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(getActivity(),SpendingForm.class);
                in.putExtra("type","expense");
                startActivity(in);
            }
        });
        addI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(getActivity(),SpendingForm.class);
                in.putExtra("type","income");
                startActivity(in);
            }
        });
        return v;
    }

}
