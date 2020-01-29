package com.example.wastagebin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewOffersResolverActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView viewOffersResolverRecyclerView;
    ViewOffersResolverRecyclerViewAdapter mAdapter;
    RelativeLayout noMessageRelativeLayout;
    List<PojoClassDetector> items = new ArrayList<>();
    ImageView filterImageView;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offers_resolver);


        Log.e("user",HomeActivity.loggedInUsername);
        Log.e("pwd",HomeActivity.loggedInPassword);

        filterImageView = (ImageView) findViewById(R.id.filterImageView);

        filterImageView.setOnClickListener(this);
        noMessageRelativeLayout = (RelativeLayout) findViewById(R.id.noMessageRelativeLayout);
        viewOffersResolverRecyclerView = (RecyclerView) findViewById(R.id.viewOffersResolverRecyclerView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        viewOffersResolverRecyclerView.setLayoutManager(mLayoutManager);
        //   viewOffersResolverRecyclerView.setAdapter(mAdapter);

        RequestQueue queue = Volley.newRequestQueue(this);

        //creating a string request to send request to the url
        StringRequest sr = new StringRequest(Request.Method.GET, APIs.getWastageResolver,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("content");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("Wastage");
                                String description = jsonObject2.getString("description");
                                String id = jsonObject2.getString("id");
                                String status = jsonObject2.getString("status");
                                JSONObject jsonObject3 = jsonObject2.getJSONObject("location");

                                String address = jsonObject3.getString("address");
                                String city = jsonObject3.getString("city");
                                String zipcode = jsonObject3.getString("zipcode");
                                String state = jsonObject3.getString("state");
                                String country = jsonObject3.getString("country");

                                double latitude=jsonObject3.getDouble("latitude");
                                double longitude=jsonObject3.getDouble("longitude");


                                Log.e("status", status);

                                if (status.equals("Reported")) {

                                    PojoClassDetector pojo = new PojoClassDetector();
                                    pojo.setAddress(address);
                                    pojo.setCity(city);
                                    pojo.setState(state);
                                    pojo.setCountry(country);
                                    pojo.setDescription(description);
                                    pojo.setId(id);
                                    pojo.setZipcode(zipcode);
                                    pojo.setLatitude(latitude);
                                    pojo.setLongitude(longitude);

                                    items.add(pojo);
                                }
                            }
                            mAdapter = new ViewOffersResolverRecyclerViewAdapter(ViewOffersResolverActivity.this, items);
                            viewOffersResolverRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                            if (items.size() == 0) {
                                viewOffersResolverRecyclerView.setVisibility(View.INVISIBLE);
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
                Log.e("abc", base64EncodedCredentials);

                HashMap<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;
            }
        };
        queue.add(sr);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_filter_dialog, null);
        builder.setView(dialogView);

        builder.setCancelable(false);
        Button applyButton = (Button) dialogView.findViewById(R.id.applyButton);
        Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        final EditText dateEditText = (EditText) dialogView.findViewById(R.id.dateEditText);
        final EditText cityEditText = (EditText) dialogView.findViewById(R.id.cityEditText);
        final AlertDialog dialog = builder.create();
        dialog.show();


        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewOffersResolverActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateEditText.setText(year+"/"+(month+1)+"/"+dayOfMonth);

                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });


        applyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //  dialog.dismiss();

                items.clear();
                mAdapter.notifyDataSetChanged();
                final String city = cityEditText.getText().toString();
                final String date = dateEditText.getText().toString();

                Log.e("city", city);
                Log.e("date", date);
                RequestQueue queue = Volley.newRequestQueue(ViewOffersResolverActivity.this);

                //creating a string request to send request to the url
                StringRequest sr = new StringRequest(Request.Method.GET, APIs.getWastageResolver + "?city=" + city +"&date="+date,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("HttpClient", "success! response: " + response.toString());
                                dialog.dismiss();
                                cityEditText.setText("");
                                dateEditText.setText("");
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("content");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        JSONObject jsonObject2 = jsonObject1.getJSONObject("Wastage");
                                        String description = jsonObject2.getString("description");
                                        String id = jsonObject2.getString("id");
                                        String status = jsonObject2.getString("status");
                                        JSONObject jsonObject3 = jsonObject2.getJSONObject("location");

                                        String address = jsonObject3.getString("address");
                                        String city = jsonObject3.getString("city");
                                        String zipcode = jsonObject3.getString("zipcode");
                                        String state = jsonObject3.getString("state");
                                        String country = jsonObject3.getString("country");
                                        double latitude=jsonObject3.getDouble("latitude");
                                        double longitude = jsonObject3.getDouble("longitude");

                                        Log.e("status", status);

                                        if (status.equals("Reported")) {

                                            PojoClassDetector pojo = new PojoClassDetector();
                                            pojo.setAddress(address);
                                            pojo.setCity(city);
                                            pojo.setState(state);
                                            pojo.setCountry(country);
                                            pojo.setDescription(description);
                                            pojo.setId(id);
                                            pojo.setZipcode(zipcode);
                                            pojo.setLatitude(latitude);
                                            pojo.setLongitude(longitude);

                                            items.add(pojo);
                                        }
                                    }

                                    Log.e("size", String.valueOf(items.size()));
                                    mAdapter = new ViewOffersResolverRecyclerViewAdapter(ViewOffersResolverActivity.this, items);
                                    viewOffersResolverRecyclerView.setAdapter(mAdapter);
                                    mAdapter.notifyDataSetChanged();
                                    if (items.size() == 0) {
                                        Log.e("j", "hello");
                                        viewOffersResolverRecyclerView.setVisibility(View.INVISIBLE);
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

//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> param = new HashMap<>();
//                        // The key in the below Key - Value pair should be the same as the Key in API
//                        param.put("city", "kjk");
//                        //param.put("date", date);
//                        //param.put("role", role);
//                        return param;
//                    }

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
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }
}
