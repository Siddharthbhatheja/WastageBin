package com.example.wastagebin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteWastageDetectorRecyclerViewAdapter extends RecyclerView.Adapter<DeleteWastageDetectorRecyclerViewAdapter.MyViewHolder>{
    Context context;
    List<PojoClassDetector> items;

    public DeleteWastageDetectorRecyclerViewAdapter(Context context,List<PojoClassDetector> items) {
        this.context = context;
        this.items=items;
    }

    @NonNull
    @Override
    public DeleteWastageDetectorRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_wastage_recyclerview_item,parent,false);
        return new DeleteWastageDetectorRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DeleteWastageDetectorRecyclerViewAdapter.MyViewHolder holder, final int position) {
        final PojoClassDetector pojo=items.get(position);
        holder.addressTextView.setText(pojo.getAddress());
        holder.cityTextView.setText(pojo.getCity());
        holder.zipcodeTextView.setText(pojo.getZipcode());
        holder.stateTextView.setText(pojo.getState());
        holder.countryTextView.setText(pojo.getCountry());
        holder.descriptionTextView.setText(pojo.getDescription());

        holder.itemLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                View view=LayoutInflater.from(context).inflate(R.layout.custom_confirmation_dialog,null);
                builder.setView(view);

               builder.setCancelable(false);

                Button yesButton=(Button)view.findViewById(R.id.yes);
                Button noButton=(Button)view.findViewById(R.id.no);

                final AlertDialog dialog=builder.create();
                dialog.show();

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(context,"Yes",Toast.LENGTH_LONG).show();
                        RequestQueue queue = Volley.newRequestQueue(context);
                        StringRequest dr = new StringRequest(Request.Method.DELETE, APIs.baseUrl+"detector/wastages/"+pojo.getId(),
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        // response
                                        Log.e("response",response);
                                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                                        items.remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Wastage Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // error.

                                    }
                                }
                        )
                        {
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
                        //Toast.makeText(context,"No",Toast.LENGTH_LONG).show();
                    }
                });








            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cityTextView, stateTextView,  countryTextView,descriptionTextView,addressTextView,zipcodeTextView;
        LinearLayout itemLinearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTextView=(TextView)itemView.findViewById(R.id.cityTextView);
            stateTextView=(TextView)itemView.findViewById(R.id.stateTextView);
            descriptionTextView=(TextView)itemView.findViewById(R.id.descriptionTextView);
            countryTextView=(TextView)itemView.findViewById(R.id.countryTextView);
            itemLinearLayout=(LinearLayout)itemView.findViewById(R.id.itemLinearLayout);
            addressTextView=(TextView)itemView.findViewById(R.id.addressTextView);
            zipcodeTextView=(TextView)itemView.findViewById(R.id.zipcodeTextView);

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}