package com.redhatpirates.kjsce_hacks_2k19;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    static SharedPreferences spref;
    boolean connection;
    public FirebaseAuth mAuth;
    String json;
    Gson gson;
    String lats, longitudes;
    private LocationManager lms;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        edit=spref.edit();
        mAuth= FirebaseAuth.getInstance();
        gson = new Gson();
        json = spref.getString("user", "");
        UserDetails ud = gson.fromJson(json, UserDetails.class);
        connection = haveNetworkConnection();
        lms = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getlocation();
        if (!connection) {
            Toast.makeText(MainActivity.this, "Please check your internet connection and try again", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, NoInternet.class);
            startActivity(intent);
            finish();
        }
        else
        {
            if(spref.getString("token","zxbczxvn23512743kglfgndfj").equals("zxbczxvn23512743kglfgndfj")||mAuth.getCurrentUser() == null ){
                startActivity(new Intent(MainActivity.this,SignInOptions.class));
                finish();
            }
            else
            {

                startActivity(new Intent(MainActivity.this,BottomNavigationMain.class));
                finish();
            }
        }
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())

                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void getlocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        Location locations = lms.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (locations != null) {
            lats = ""+locations.getLatitude();
            longitudes =""+ locations.getLongitude();
            edit.putString("latitude",lats);
            edit.putString("longitude",longitudes);
            edit.commit();
        }
    }
}
