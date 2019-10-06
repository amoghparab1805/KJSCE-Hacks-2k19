package com.redhatpirates.kjsce_hacks_2k19;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class PredictionFragment extends Fragment {

Button realEstate,jewellery,stocks,MutualFunds;
    public PredictionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_prediction, container, false);
        realEstate=v.findViewById(R.id.realEstate);
        jewellery=v.findViewById(R.id.jewellery);
        stocks=v.findViewById(R.id.stocks);
        MutualFunds=v.findViewById(R.id.MutualFunds);
        realEstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),RealEstate.class));
            }
        });
        jewellery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(),OtherInvestment.class);
                in.putExtra("type","metal");
                startActivity(in);
            }
        });
        stocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(),Stocks.class);
                in.putExtra("type","stocks");
                startActivity(in);
            }
        });
        MutualFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(),MutualFunds.class);
                in.putExtra("type","mutual");
                startActivity(in);
            }
        });
        return v;
    }
}
