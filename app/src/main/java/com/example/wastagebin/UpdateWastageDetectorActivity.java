package com.example.wastagebin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateWastageDetectorActivity extends AppCompatActivity {
    RecyclerView updateWastageDetectorRecyclerView;
    UpdateWastageDetectorRecyclerViewAdapter mAdapter;
    List<PojoClassDetector> items = new ArrayList<>();
    RelativeLayout noMessageRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_wastage_detector);

        updateWastageDetectorRecyclerView = (RecyclerView) findViewById(R.id.updateWastageDetectorRecyclerView);
        noMessageRelativeLayout=(RelativeLayout)findViewById(R.id.noWastageRelativeLayout);



        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        updateWastageDetectorRecyclerView.setLayoutManager(mLayoutManager);
        //updateWastageDetectorRecyclerView.setAdapter(mAdapter);


        RequestQueue queue = Volley.newRequestQueue(this);

        //creating a string request to send request to the url
        StringRequest sr = new StringRequest(Request.Method.GET, APIs.createAndPostWastage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            JSONArray jsonArray= jsonObject.getJSONArray("content");
                            for (int i = 0; i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject jsonObject2= jsonObject1.getJSONObject("Wastage");
                                String description = jsonObject2.getString("description");
                                String id= jsonObject2.getString("id");
                                String status = jsonObject2.getString("status");
                                JSONObject jsonObject3 =jsonObject2.getJSONObject("location");

                                String address=jsonObject3.getString("address");
                                String city = jsonObject3.getString("city");
                                String zipcode = jsonObject3.getString("zipcode");
                                String state = jsonObject3.getString("state");
                                String country = jsonObject3.getString("country");

                                if(status.equals("Reported")) {

                                    PojoClassDetector pojo = new PojoClassDetector();
                                    pojo.setAddress(address);
                                    pojo.setCity(city);
                                    pojo.setState(state);
                                    pojo.setCountry(country);
                                    pojo.setDescription(description);
                                    pojo.setId(id);
                                    pojo.setZipcode(zipcode);

                                    items.add(pojo);
                                }

                            }
                            mAdapter = new UpdateWastageDetectorRecyclerViewAdapter(UpdateWastageDetectorActivity.this,items);
                            updateWastageDetectorRecyclerView.setAdapter(mAdapter);
                            if(items.size()==0){
                                updateWastageDetectorRecyclerView.setVisibility(View.INVISIBLE);
                                noMessageRelativeLayout.setVisibility(View.VISIBLE);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                }) {




            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = HomeActivity.loggedInUsername + ":" + HomeActivity.loggedInPassword;
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Log.e("abc",base64EncodedCredentials);

                HashMap<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;
            }

        };
        queue.add(sr);
    }
}