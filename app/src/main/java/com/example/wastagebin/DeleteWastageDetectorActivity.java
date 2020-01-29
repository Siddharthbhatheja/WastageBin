package com.example.wastagebin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.sax.RootElement;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
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

public class DeleteWastageDetectorActivity extends AppCompatActivity {
    RecyclerView deleteWastageDetectorRecyclerView;
    DeleteWastageDetectorRecyclerViewAdapter mAdapter;
    List<PojoClassDetector> items=new ArrayList<>();
    RelativeLayout noWastageRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_wastage_detector);

        noWastageRelativeLayout=(RelativeLayout)findViewById(R.id.noWastageRelativeLayout);
        deleteWastageDetectorRecyclerView=(RecyclerView)findViewById(R.id.deleteWastageDetectorRecyclerView);
        mAdapter = new DeleteWastageDetectorRecyclerViewAdapter(DeleteWastageDetectorActivity.this,items);
        deleteWastageDetectorRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        deleteWastageDetectorRecyclerView.setLayoutManager(mLayoutManager);



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
                                String status=jsonObject2.getString("status");
                                JSONObject jsonObject3 =jsonObject2.getJSONObject("location");

                                String address=jsonObject3.getString("address");
                                String city = jsonObject3.getString("city");
                                String zipcode = jsonObject3.getString("zipcode");
                                String state = jsonObject3.getString("state");
                                String country = jsonObject3.getString("country");

                                double latitude=Float.parseFloat(jsonObject3.getString("latitude"));
                                double longitude=Float.parseFloat(jsonObject3.getString("longitude"));

                                if(status.equals("Reported")) {

                                    PojoClassDetector pojo = new PojoClassDetector();
                                    pojo.setAddress(address);
                                    pojo.setCity(city);
                                    pojo.setState(state);
                                    pojo.setCountry(country);
                                    pojo.setDescription(description);
                                    pojo.setId(id);
                                    pojo.setZipcode(zipcode);
                                    pojo.setStatus(status);
                                    pojo.setLatitude(latitude);
                                    pojo.setLongitude(longitude);
                                    items.add(pojo);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                            if(items.size()==0){
                                deleteWastageDetectorRecyclerView.setVisibility(View.INVISIBLE);
                                noWastageRelativeLayout.setVisibility(View.VISIBLE);
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