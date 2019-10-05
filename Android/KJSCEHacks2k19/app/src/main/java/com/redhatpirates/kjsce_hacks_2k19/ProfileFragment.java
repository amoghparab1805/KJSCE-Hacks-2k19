package com.redhatpirates.kjsce_hacks_2k19;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences spref;
        final FirebaseUser mUser;
        mUser= FirebaseAuth.getInstance().getCurrentUser();
        final SharedPreferences.Editor editor;
        String json;
        Gson gson;
        Button logout;
        ImageView barrierE,barrierP,userImage;
        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        spref = v.getContext().getSharedPreferences("user", MODE_PRIVATE);
        editor = spref.edit();
        gson = new Gson();
        barrierE=v.findViewById(R.id.barrierE);
        barrierP=v.findViewById(R.id.barrierP);
        json = spref.getString("user", "");
        UserDetails ud = gson.fromJson(json, UserDetails.class);
        TextView name,email,age,phone,gender;
        LinearLayout emailLayout,phoneLayout;
        logout=v.findViewById(R.id.logout);
        name= v.findViewById(R.id.name);
        email= v.findViewById(R.id.email);
        phone= v.findViewById(R.id.phone);
        age= v.findViewById(R.id.age);
        userImage=v.findViewById(R.id.userImage);
        gender= v.findViewById(R.id.gender);
        emailLayout=v.findViewById(R.id.emailL);
        phoneLayout=v.findViewById(R.id.phoneL);
        if(ud.getPhoneNumber().equals(""))
        {
            emailLayout.setVisibility(View.VISIBLE);
            email.setText(ud.getEmail());
            barrierE.setVisibility(View.VISIBLE);
            Glide.with(v.getContext())
                    .load(ud.getPhotoURL())
                    .into(userImage);

        }
        else
        {
            phoneLayout.setVisibility(View.VISIBLE);
            phone.setText(ud.getPhoneNumber());
            barrierP.setVisibility(View.VISIBLE);
        }
        name.setText(ud.getDisplayName());
        age.setText(ud.getAge());
        switch(ud.getGender())
        {
            case "M":gender.setText("Male");
            break;
            case "F":gender.setText("Female");
            break;
            case "O":gender.setText("Other");
            break;
            default:gender.setText("Gender");
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                mUser.delete();
                startActivity(new Intent(v.getContext(),MainActivity.class));
                getActivity().finish();

            }
        });
        return v;
    }

}
