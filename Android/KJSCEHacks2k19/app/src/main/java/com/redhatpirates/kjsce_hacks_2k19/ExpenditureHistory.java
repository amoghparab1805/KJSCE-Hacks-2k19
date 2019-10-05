package com.redhatpirates.kjsce_hacks_2k19;

        import android.content.SharedPreferences;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.widget.Toast;

        import com.google.gson.Gson;

        import java.util.ArrayList;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class ExpenditureHistory extends AppCompatActivity {

    String json;
    Gson gson;
    SharedPreferences spref;
    RecyclerView spendingsRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_history);
        spendingsRecycler=findViewById(R.id.spendingsRecycler);

        json = spref.getString("user", "");
        UserDetails ud = gson.fromJson(json, UserDetails.class);
        RetrofitInterface ri = RetrofitInstance.getInstance().create(RetrofitInterface.class);
        final Call<ArrayList<MoneySpent>> moneySpent = ri.getExpenditures(new MoneySpent(ud.getUid()));
        moneySpent.enqueue(new Callback<ArrayList<MoneySpent>>() {
            @Override
            public void onResponse(Call<ArrayList<MoneySpent>> call, Response<ArrayList<MoneySpent>> response) {
                ArrayList<MoneySpent>ms=response.body();
                if(ms.size()!=0)
                {
                    spendingsRecycler.setLayoutManager(new LinearLayoutManager(ExpenditureHistory.this));
                    spendingsRecycler.setAdapter(new SpendingsAdapter(ms));
                }
                else
                {
                    Toast.makeText(ExpenditureHistory.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MoneySpent>> call, Throwable t) {
                Toast.makeText(ExpenditureHistory.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
