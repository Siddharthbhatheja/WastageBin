package com.example.wastagebin;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SponsorWastageFinancierRecyclerViewAdapter extends RecyclerView.Adapter<SponsorWastageFinancierRecyclerViewAdapter.MyViewHolder> {
    Context context;
    List<PojoClassFinancier> items;

    public SponsorWastageFinancierRecyclerViewAdapter(Context context, List<PojoClassFinancier> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public SponsorWastageFinancierRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_financier_available_offers_recyclerview_item, parent, false);
        return new SponsorWastageFinancierRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final PojoClassFinancier pojo = items.get(position);
        holder.wastageIdTextView.setText(pojo.getId());
        Log.e("harry", pojo.getOfferStatus());
        holder.costTextView.setText(String.valueOf(pojo.getCost()));
        holder.daysRequiredTextView.setText(String.valueOf(pojo.getDaysRequired()));
        holder.offerStatusTextView.setText(pojo.getOfferStatus());





        RequestQueue abc = Volley.newRequestQueue(context);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, APIs.financierBaseURL+"financer/wastages/"+pojo.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1=jsonObject.getJSONObject("Wastage");
                            String description=jsonObject1.getString("description");
                            JSONObject jsonObject2=jsonObject1.getJSONObject("location");
                            String address=jsonObject2.getString("address");
                            String city = jsonObject2.getString("city");
                            String state= jsonObject2.getString("state");
                            String country = jsonObject2.getString("country");



                            holder.locationTextView.setText(address+","+city+","+state+","+country);
                            holder.descriptionTextView.setText(description);

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
        abc.add(stringRequest);











        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RequestQueue requestQueue = Volley.newRequestQueue(context);


                //Log.e("requestBody", mRequestBody);

                StringRequest stringRequest = new StringRequest(Request.Method.PUT, APIs.financierBaseURL + "financer/offers/" + pojo.getOfferId() + "/accept",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("success", response);

                                Toast.makeText(context, "Offer Accepted", Toast.LENGTH_SHORT).show();
                                items.remove(position);
                                notifyDataSetChanged();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                        Log.e("error", "dsf");
                        // error
                    }
                }) {

                    /**
                     Passing some request headers
                     * */
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        String credentials = HomeActivity.loggedInUsername + ":" + HomeActivity.loggedInPassword;
                        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                        Log.e("abc", base64EncodedCredentials);

                        HashMap<String, String> headers = new HashMap<>();

                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", "Basic " + base64EncodedCredentials);
                        return headers;
                    }


                };

                requestQueue.add(stringRequest);


            }
        });

//
//        RequestQueue abc = Volley.newRequestQueue(context);
//
//        //creating a string request to send request to the url
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, APIs.resolverBaseUrl+"resolver/wastages/"+pojo.getId(),
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("HttpClient", "success! response: " + response.toString());
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray jsonArray= jsonObject.getJSONArray("content");
//                            for (int i = 0; i<jsonArray.length();i++){
//                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                                JSONObject jsonObject2= jsonObject1.getJSONObject("wastage");
//                                String description = jsonObject2.getString("description");
//                                String id= jsonObject2.getString("id");
//                                String status = jsonObject2.getString("status");
//                                JSONObject jsonObject3 =jsonObject2.getJSONObject("location");
//
//                                String address=jsonObject3.getString("address");
//                                String city = jsonObject3.getString("city");
//
//                                String state = jsonObject3.getString("state");
//                                String country = jsonObject3.getString("country");
//
//                                holder.locationTextView.setText(address+","+city+","+state+","+country);
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("HttpClient", "error: " + error.toString());
//                    }
//                }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                String credentials = "user7" + ":" + "password7";
//                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                Log.e("abc",base64EncodedCredentials);
//
//                HashMap<String, String> headers = new HashMap<>();
//
//                headers.put("Authorization", "Basic " + base64EncodedCredentials);
//                return headers;
//            }
//        };
//        abc.add(stringRequest);


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView costTextView, daysRequiredTextView, offerStatusTextView, wastageIdTextView, locationTextView, descriptionTextView;
        LinearLayout itemLinearLayout;
        Button acceptButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            costTextView = (TextView) itemView.findViewById(R.id.costTextView);
            daysRequiredTextView = (TextView) itemView.findViewById(R.id.daysRequiredTextView);
            offerStatusTextView = (TextView) itemView.findViewById(R.id.offerStatusTextView);
            wastageIdTextView = (TextView) itemView.findViewById(R.id.wastageIdTextView);
            itemLinearLayout = (LinearLayout) itemView.findViewById(R.id.itemLinearLayout);
            locationTextView = (TextView) itemView.findViewById(R.id.locationTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            acceptButton = (Button) itemView.findViewById(R.id.acceptButton);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}