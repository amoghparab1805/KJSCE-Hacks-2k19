package com.redhatpirates.kjsce_hacks_2k19;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    CardView loan,investment,general;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_chat, container, false);
        loan=v.findViewById(R.id.loan);
        investment=v.findViewById(R.id.investment);
        general=v.findViewById(R.id.general);
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://red-hat-pirates-video-call.herokuapp.com/"));
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(browserIntent);
            }
        });
        investment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(browserIntent);
            }
        });
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(browserIntent);
            }
        });
        return v;
    }

}
