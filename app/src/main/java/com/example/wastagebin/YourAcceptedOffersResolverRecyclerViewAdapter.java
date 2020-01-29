package com.example.wastagebin;

import android.app.AlertDialog;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class YourAcceptedOffersResolverRecyclerViewAdapter extends RecyclerView.Adapter<YourAcceptedOffersResolverRecyclerViewAdapter.MyViewHolder> {
    Context context;
    List<PojoClassResolver> items;

    public YourAcceptedOffersResolverRecyclerViewAdapter(Context context, List<PojoClassResolver> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public YourAcceptedOffersResolverRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_resolver_accepted_offers_recyclerview, parent, false);
        return new YourAcceptedOffersResolverRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final YourAcceptedOffersResolverRecyclerViewAdapter.MyViewHolder holder, final int position) {

        final PojoClassResolver pojo = items.get(position);
        holder.wastageIdTextView.setText(pojo.getId());
        holder.costTextView.setText(String.valueOf(pojo.getCost()));
        holder.daysRequiredTextView.setText(String.valueOf(pojo.getDaysRequired()));
        holder.offerStatusTextView.setText(pojo.getOfferStatus());

        if (pojo.offerStatus.equals("Accepted")) {
            holder.resolveButton.setVisibility(View.VISIBLE);
        }

        holder.resolveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue jkl = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, APIs.resolverBaseUrl + "resolver/wastages/" + pojo.getId() + "/resolve",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("HttpClient", "success! response: " + response.toString());
                                items.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Wastage Resolved", Toast.LENGTH_SHORT).show();

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
                jkl.add(stringRequest);

            }
        });


        RequestQueue abc = Volley.newRequestQueue(context);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, APIs.resolverBaseUrl + "resolver/wastages/" + pojo.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("Wastage");
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("location");
                            String address = jsonObject2.getString("address");
                            String city = jsonObject2.getString("city");
                            String state = jsonObject2.getString("state");
                            String country = jsonObject2.getString("country");


                            holder.locationTextView.setText(address + "," + city + "," + state + "," + country);


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
        abc.add(stringRequest);


        holder.itemLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(context).inflate(R.layout.custom_confirmation_delete_offer_dialog, null);
                builder.setView(view);

                builder.setCancelable(false);

                Button yesButton = (Button) view.findViewById(R.id.yes);
                Button noButton = (Button) view.findViewById(R.id.no);

                Log.e("now", pojo.getId());
                final AlertDialog dialog = builder.create();
                dialog.show();
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        dialog.dismiss();
                        //Toast.makeText(context, "Yes", Toast.LENGTH_LONG).show();
                        RequestQueue queue = Volley.newRequestQueue(context);
                        StringRequest dr = new StringRequest(Request.Method.DELETE, APIs.resolverBaseUrl + "resolver/offers/" + pojo.getOfferId(),
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // response
                                        Log.e("response", response);
                                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                                        items.remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Offer Deleted", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // error.

                                    }
                                }
                        ) {
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
                        queue.add(dr);


                    }
                });
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        //Toast.makeText(context, "No", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView costTextView, daysRequiredTextView, offerStatusTextView, wastageIdTextView, locationTextView;
        LinearLayout itemLinearLayout;
        Button resolveButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            costTextView = (TextView) itemView.findViewById(R.id.costTextView);
            daysRequiredTextView = (TextView) itemView.findViewById(R.id.daysRequiredTextView);
            offerStatusTextView = (TextView) itemView.findViewById(R.id.offerStatusTextView);
            wastageIdTextView = (TextView) itemView.findViewById(R.id.wastageIdTextView);
            itemLinearLayout = (LinearLayout) itemView.findViewById(R.id.itemLinearLayout);
            locationTextView = (TextView) itemView.findViewById(R.id.locationTextView);
            resolveButton = (Button) itemView.findViewById(R.id.resolveButton);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}