package com.example.wastagebin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.LinearLayout;

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

public class SponsorWastageFinancierActivity extends AppCompatActivity {
    RecyclerView sponsorWastageFinancierRecyclerView;
    SponsorWastageFinancierRecyclerViewAdapter mAdapter;
    List<PojoClassFinancier> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_wastage_financier);

        sponsorWastageFinancierRecyclerView = (RecyclerView) findViewById(R.id.sponsorWastageFinancierRecyclerView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        sponsorWastageFinancierRecyclerView.setLayoutManager(mLayoutManager);
        //    sponsorWastageFinancierRecyclerView.setAdapter(mAdapter);

        RequestQueue queue = Volley.newRequestQueue(this);

        //creating a string request to send request to the url
        StringRequest sr = new StringRequest(Request.Method.GET, APIs.getOffersFinancier,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("content");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("Offer");
                                String cost = jsonObject2.getString("cost");
                                String daysRequired = jsonObject2.getString("daysRequired");
                                String offerId = jsonObject2.getString("id");
                                String resolverId = jsonObject2.getString("resolverId");
                                String offerStatus = jsonObject2.getString("offerStatus");
                                String wasteId = jsonObject2.getString("wasteId");
                                //   Log.e("days", id);


                                if (offerStatus.equals("PendingAcceptance")) {
                                    PojoClassFinancier pojo = new PojoClassFinancier();
                                    pojo.setCost(Float.parseFloat(cost));
                                    pojo.setDaysRequired(Integer.parseInt(daysRequired));
                                    pojo.setId(wasteId);
                                    pojo.setResolverId(resolverId);

                                    pojo.setOfferStatus(offerStatus);

                                    pojo.setOfferId(offerId);
                                    items.add(pojo);


                                }
                                //  }
                            }
                            mAdapter = new SponsorWastageFinancierRecyclerViewAdapter(SponsorWastageFinancierActivity.this, items);
                            sponsorWastageFinancierRecyclerView.setAdapter(mAdapter);
                            //sponsorWastageFinancierRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();


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
                Log.e("abc", base64EncodedCredentials);

                HashMap<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;
            }
        };
        queue.add(sr);

    }
}
