package com.example.wastagebin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class WastageUpdateDetectorActivity extends AppCompatActivity implements View.OnClickListener {
    EditText cityEditText, stateEditText, zipcodeEditText, countryEditText, descriptionEditText, addressEditText;
    Button updateButton;
RelativeLayout sendCurrentLocationRelativeLayout, recordedLocationRelativeLayout;
    String wastageId;
    GPSTracker gpsTracker;
    double recordedLatitude=0.0;
    double recordedLongitude=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wastage_update_detector);

        Intent i = getIntent();

        addressEditText = (EditText) findViewById(R.id.addressEditText);
        cityEditText = (EditText) findViewById(R.id.cityEditText);
        stateEditText = (EditText) findViewById(R.id.stateEditText);
        zipcodeEditText = (EditText) findViewById(R.id.zipcodeEditText);
        countryEditText = (EditText) findViewById(R.id.countryEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        updateButton = (Button) findViewById(R.id.updateButton);


        sendCurrentLocationRelativeLayout=(RelativeLayout)findViewById(R.id.sendCurrentLocationRelativeLayout);
        recordedLocationRelativeLayout=(RelativeLayout)findViewById(R.id.locationRecordedRelativeLayout);
        addressEditText.setText(i.getStringExtra("address"));
        cityEditText.setText(i.getStringExtra("city"));
        stateEditText.setText(i.getStringExtra("state"));
        zipcodeEditText.setText(i.getStringExtra("zipcode"));
        countryEditText.setText(i.getStringExtra("country"));
        descriptionEditText.setText(i.getStringExtra("description"));

        wastageId = i.getStringExtra("id");
        Log.e("receivedid", wastageId);


        sendCurrentLocationRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCurrentLocationRelativeLayout.setVisibility(View.INVISIBLE);
                recordedLocationRelativeLayout.setVisibility(View.VISIBLE);

                gpsTracker = new GPSTracker(WastageUpdateDetectorActivity.this);
                if (gpsTracker.canGetLocation()) {
                    recordedLatitude = gpsTracker.getLatitude();
                    recordedLongitude =gpsTracker.getLongitude();
                    Log.e("CurrentLatlng", recordedLatitude + recordedLongitude + ";");
                } else {
                    gpsTracker.showSettingsAlert(WastageUpdateDetectorActivity.this);
                }
            }
        });


        updateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String address = addressEditText.getText().toString();
        String city = cityEditText.getText().toString();
        int zipcode = Integer.parseInt(zipcodeEditText.getText().toString());
        String state = stateEditText.getText().toString();
        String country = countryEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        Log.e("zip", String.valueOf(zipcode));
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        JSONObject location = new JSONObject();

        JSONObject body = new JSONObject();
        try {
            location.put("address", address);
            location.put("city", city);
            location.put("country", country);
            location.put("state", state);
            location.put("latitude",recordedLatitude);
            location.put("longitude",recordedLongitude);

            location.put("zipcode", zipcode);

            body.put("description", description);
            body.put("location", location);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = body.toString();

        Log.e("requestBody", mRequestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, APIs.baseUrl+"detector/wastages/"+wastageId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("success", response);


                        addressEditText.setText("");
                        cityEditText.setText("");
                        zipcodeEditText.setText("");
                        stateEditText.setText("");
                        countryEditText.setText("");
                        descriptionEditText.setText("");

                        Intent intent=new Intent(WastageUpdateDetectorActivity.this,UpdateWastageDetectorActivity.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                        Toast.makeText(WastageUpdateDetectorActivity.this, "Wastage Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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


            @Override
            public byte[] getBody() throws AuthFailureError {
                String your_string_json = mRequestBody; // put your json
                return your_string_json.getBytes();
            }
        };

        requestQueue.add(stringRequest);


    }
}
