package com.example.wastagebin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewOffersResolverRecyclerViewAdapter extends RecyclerView.Adapter<ViewOffersResolverRecyclerViewAdapter.MyViewHolder> {
    Context context;
    List<PojoClassDetector> items;

    public ViewOffersResolverRecyclerViewAdapter(Context context, List<PojoClassDetector> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewOffersResolverRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_current_offers_resolver_recyclerview_item, parent, false);
        return new ViewOffersResolverRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewOffersResolverRecyclerViewAdapter.MyViewHolder holder, final int position) {
        final PojoClassDetector pojo = items.get(position);
        holder.addressTextView.setText(pojo.getAddress());
        holder.cityTextView.setText(pojo.getCity());
        holder.zipcodeTextView.setText(pojo.getZipcode());
        holder.stateTextView.setText(pojo.getState());
        holder.countryTextView.setText(pojo.getCountry());
        holder.descriptionTextView.setText(pojo.getDescription());

        Log.e("mouse",String.valueOf(pojo.getLatitude()));

        holder.showInMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent mapsIntent =new Intent(context, MapsActivity.class);
               mapsIntent.putExtra("latitude",String.valueOf(pojo.getLatitude()));
               mapsIntent.putExtra("longitude",String.valueOf(pojo.getLongitude()));
                Log.e("mouse",String.valueOf(pojo.getLatitude()));
                Log.e("mouse",String.valueOf(pojo.getLongitude()));
               context.startActivity(mapsIntent);
            }
        });
        holder.makeOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(context).inflate(R.layout.custom_make_offer_resolver_dialog, null);
                builder.setView(view);
                builder.setCancelable(false);

                Button makeOffer = (Button) view.findViewById(R.id.makeOffer);
                Button cancel = (Button) view.findViewById(R.id.cancel);
                final EditText enterAmountEditText =(EditText)view.findViewById(R.id.enterAmountEditText);
                final EditText enterTimeRequiredEditText=(EditText)view.findViewById(R.id.enterTimeRequiredEditText);

                final AlertDialog dialog = builder.create();
                dialog.show();



                makeOffer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String wastageId=pojo.getId();
                        final int daysRequired = Integer.parseInt(enterTimeRequiredEditText.getText().toString());
                        final double cost=Float.parseFloat(enterAmountEditText.getText().toString());
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Registering Please wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        RequestQueue requestQueue = Volley.newRequestQueue(context);

                        JSONObject offerItems =new JSONObject();
                        try {
                            offerItems.put("cost",cost);
                            offerItems.put("daysRequired",daysRequired);
                            offerItems.put("wasteId",wastageId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        items.remove(position);
                        notifyDataSetChanged();
                        final String mRequestBody = offerItems.toString();

                        Log.e("requestBody", mRequestBody);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIs.resolverOffer,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.e("success",response);


                                        progressDialog.dismiss();
                                        Toast.makeText(context,"Offer Made",Toast.LENGTH_LONG).show();

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Log.e("error","dsf");
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
                                Log.e("abc",base64EncodedCredentials);

                                HashMap<String, String> headers = new HashMap<>();

                                headers.put("Content-Type", "application/json");
                                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                                return headers;
                            }


                            @Override
                            public byte[] getBody() throws AuthFailureError {
                                String your_string_json = mRequestBody; // put your json
                                return your_string_json.getBytes();
                            }
                        };

                        requestQueue.add(stringRequest);









































                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                      //  Toast.makeText(context, "No", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cityTextView, stateTextView, countryTextView, descriptionTextView, addressTextView, zipcodeTextView;
        LinearLayout itemLinearLayout;
        Button makeOfferButton, showInMapsButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTextView = (TextView) itemView.findViewById(R.id.cityTextView);
            stateTextView = (TextView) itemView.findViewById(R.id.stateTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            countryTextView = (TextView) itemView.findViewById(R.id.countryTextView);
            itemLinearLayout = (LinearLayout) itemView.findViewById(R.id.itemLinearLayout);
            addressTextView = (TextView) itemView.findViewById(R.id.addressTextView);
            zipcodeTextView = (TextView) itemView.findViewById(R.id.zipcodeTextView);
            makeOfferButton = (Button) itemView.findViewById(R.id.makeOfferButton);
            showInMapsButton=(Button)itemView.findViewById(R.id.showInMapsButton);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}