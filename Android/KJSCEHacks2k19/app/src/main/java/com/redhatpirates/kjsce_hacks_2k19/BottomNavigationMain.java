package com.redhatpirates.kjsce_hacks_2k19;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.redhatpirates.kjsce_hacks_2k19.R;

public class BottomNavigationMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment tempFragment = null;

                switch(item.getItemId()){
                    case R.id.profile:
                        tempFragment = new ProfileFragment();
                        break;
                    case R.id.dashboard:
                        tempFragment = new DashboardFragment();
                        break;
                    case R.id.atm:
                        tempFragment = new ATMFragment();
                        break;
//                    case R.id.status:
//                        tempFragment = new StatusFragment();
//                        break;
//                    case R.id.log_out:
//                        tempFragment = new LogOutFragment();
//                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, tempFragment).commit();
                return true;
            }
        });
    }
}
