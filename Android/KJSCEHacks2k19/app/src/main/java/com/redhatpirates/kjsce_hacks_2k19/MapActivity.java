package com.redhatpirates.kjsce_hacks_2k19;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    SharedPreferences spref;
    Double latlocA, longlocA;
    String namelocA, viclocA;
    Double latlocB, longlocB;
    String namelocB, viclocB;
    private final String key = "AIzaSyAbZe5m5mq-bCbEa4X_RZwck74HaM9K0g4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment smap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        smap.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        if (!spref.getString("latitude", "vbvbv").equals("vbvbv")) {
            String urlATM = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + spref.getString("latitude", "10") + "," + spref.getString("longitude", "10") + "&rankby=distance&types=atm&sensor=false&key=AIzaSyAbZe5m5mq-bCbEa4X_RZwck74HaM9K0g4";
            System.out.println("asdada" + urlATM);
            final String urlBank = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + spref.getString("latitude", "10") + "," + spref.getString("longitude", "10") + "&rankby=distance&types=bank&sensor=false&key=AIzaSyAbZe5m5mq-bCbEa4X_RZwck74HaM9K0g4";
            StringRequest sr = new StringRequest(urlATM, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj1 = new JSONObject(response);
                        JSONArray obj2 = obj1.getJSONArray("results");
                        JSONObject obj3 = obj2.getJSONObject(0);

                        JSONObject obj4 = obj3.getJSONObject("geometry");
                        JSONObject obj5 = obj4.getJSONObject("location");
                        latlocA = obj5.getDouble("lat");
                        longlocA = obj5.getDouble("lng");
                        namelocA = obj3.getString("name");
                        viclocA = obj3.getString("vicinity");
                        map = googleMap;
                        LatLng me = new LatLng(Double.parseDouble(spref.getString("latitude", "19.0729174")), Double.parseDouble(spref.getString("longitude", "72.9008512")));
                        map.addMarker(new MarkerOptions().position(me).title("YOU"));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(me,15));
                        map.animateCamera(CameraUpdateFactory.zoomIn());
                        System.out.println("asas" + latlocA);
                        LatLng atm = new LatLng(latlocA, longlocA);
                        map.addMarker(new MarkerOptions().position(atm).title("ATM").snippet(namelocA).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        map.moveCamera(CameraUpdateFactory.newLatLng(atm));
                        StringRequest sr1 = new StringRequest(urlBank, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj1 = new JSONObject(response);
                                    JSONArray obj2 = obj1.getJSONArray("results");
                                    JSONObject obj3 = obj2.getJSONObject(0);

                                    JSONObject obj4 = obj3.getJSONObject("geometry");
                                    JSONObject obj5 = obj4.getJSONObject("location");
                                    latlocB = obj5.getDouble("lat");
                                    longlocB = obj5.getDouble("lng");
                                    namelocB = obj3.getString("name");
                                    viclocB = obj3.getString("vicinity");

                                    LatLng bank=new LatLng(latlocB,longlocB);
                                    map.addMarker(new MarkerOptions().position(bank).title("BANK").snippet(namelocB).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                                    map.moveCamera(CameraUpdateFactory.newLatLng(bank));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                        RequestQueue q1 = Volley.newRequestQueue(MapActivity.this);
                        q1.add(sr1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            RequestQueue q = Volley.newRequestQueue(MapActivity.this);
            q.add(sr);



        }
    }
}