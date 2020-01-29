package com.example.wastagebin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameEditText, passwordEditText;
    Button loginButton;
    TextView registerUserTextView;

    public static String loggedInUsername, loggedInPassword;
    List<String> statusList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Retrieving Username from SharedPreferences
        SharedPreferences retrievedUsernameSharedPreference = getSharedPreferences("userPref", MODE_PRIVATE);
        String retrievedUsername = retrievedUsernameSharedPreference.getString("Username", "");

        //Retrieving Password from SharedPreferences
        SharedPreferences retrievedPasswordSharedPreference = getSharedPreferences("pwdPref", MODE_PRIVATE);
        String retrievedPassword = retrievedPasswordSharedPreference.getString("Password", "");

//        //Checking if the User is logged in or not
//        if (!retrievedUsername.equals("") && !retrievedPassword.equals("")) {
//            Intent i = new Intent(HomeActivity.this, DetectorActivity.class);
//            i.putExtra("Username", retrievedUsername);
//            startActivity(i);
//            finish();
//        }


        initialize();

        setTextFieldErrors();


    }

    private void setTextFieldErrors() {
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                usernameEditText.setError(null);
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordEditText.setError(null);
            }
        });


    }

    private void initialize() {
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerUserTextView = (TextView) findViewById(R.id.registerUserTextView);

        registerUserTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.loginButton:

                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "Please select Relevant fields", Toast.LENGTH_SHORT).show();
                } else {
                    String credentials = username + ":" + password;
                    String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    RequestQueue requestQueue = Volley.newRequestQueue(this);

                    final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
                    progressDialog.setMessage("Signing in Please wait....");
                    progressDialog.show();
                    progressDialog.setCancelable(false);


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, APIs.baseUsersUrl + "/authenticate/" + username + "?credentials=" + base64EncodedCredentials,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    loggedInUsername = username;
                                    loggedInPassword = password;

//                                    SharedPreferences usernamePref = getSharedPreferences("userPref", MODE_PRIVATE);
//                                    SharedPreferences.Editor editorUsername = usernamePref.edit();
//                                    editorUsername.putString("Username", username);
//                                    editorUsername.commit();// commit is important here.
//
//                                    //Storing Password in SharedPreferences
//                                    SharedPreferences passwordPref = getSharedPreferences("pwdPref", MODE_PRIVATE);
//                                    SharedPreferences.Editor editorPassword = passwordPref.edit();
//                                    editorPassword.putString("Password", password);
//                                    editorPassword.commit();// commit is important here.


                                    List<String> status = Collections.singletonList(response);
                                    try {
                                        JSONArray abc = new JSONArray(response);
                                        for (int i = 0; i < abc.length(); i++) {
                                            Log.e("status", (String) abc.get(i));
                                            statusList.add((String) abc.get(i));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    for (int i = 0; i < statusList.size(); i++) {
                                        Log.e("statuslist", statusList.get(i));
                                    }

                                    for (int i = 0; i < status.size(); i++) {
                                        // Log.e("status",status.get(i));
                                    }

                                    Intent intent;

                                    if (statusList.contains("Detector") && statusList.contains("Resolver") && status.contains("Financer")) {

                                        intent = new Intent(HomeActivity.this, DetectorActivity.class);
                                        intent.putExtra("detector", "Detector");
                                        intent.putExtra("resolver", "Resolver");
                                        intent.putExtra("financer", "Financer");
                                        startActivity(intent);
                                        finish();
                                    } else if (statusList.contains("Detector") && statusList.contains("Resolver")) {
                                        intent = new Intent(HomeActivity.this, DetectorActivity.class);
                                        intent.putExtra("detector", "Detector");
                                        intent.putExtra("resolver", "Resolver");
                                        intent.putExtra("financer", "");

                                        startActivity(intent);
                                        finish();
                                    } else if (statusList.contains("Detector") && statusList.contains("Financer")) {
                                        intent = new Intent(HomeActivity.this, DetectorActivity.class);
                                        intent.putExtra("detector", "Detector");
                                        intent.putExtra("resolver", "");
                                        intent.putExtra("financer", "Financer");

                                        startActivity(intent);
                                        finish();
                                    } else if (statusList.contains("Financer") && statusList.contains("Resolver")) {
                                        intent = new Intent(HomeActivity.this, ResolverActivity.class);
                                        intent.putExtra("detector", "");
                                        intent.putExtra("financer", "Financer");
                                        intent.putExtra("resolver", "Resolver");
                                        startActivity(intent);
                                        finish();
                                    } else if (statusList.contains("Detector")) {

                                        intent = new Intent(HomeActivity.this, DetectorActivity.class);

                                        intent.putExtra("detector", "Detector");
                                        intent.putExtra("resolver", "");
                                        intent.putExtra("financer", "");
                                        startActivity(intent);
                                        finish();
                                    } else if (statusList.contains("Resolver")) {
                                        intent = new Intent(HomeActivity.this, ResolverActivity.class);
                                        intent.putExtra("detector", "");
                                        intent.putExtra("resolver", "resolver");
                                        intent.putExtra("financer", "");
                                        startActivity(intent);
                                        finish();
                                    } else if (statusList.contains("Financer")) {
                                        intent = new Intent(HomeActivity.this, FinancierActivity.class);
                                        intent.putExtra("financer", "financer");
                                        intent.putExtra("detector", "");
                                        intent.putExtra("resolver", "");
                                        startActivity(intent);
                                        finish();
                                    }
                                    Log.e("success", response);


                                    usernameEditText.setText("");
                                    passwordEditText.setText("");

                                    progressDialog.dismiss();
                                    Toast.makeText(HomeActivity.this, "Login Successful", Toast.LENGTH_LONG).show();


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();

                            Toast.makeText(HomeActivity.this, "User Not Found", Toast.LENGTH_LONG).show();

                            // error
                            usernameEditText.setText("");
                            passwordEditText.setText("");
                        }
                    }) {

                        /**
                         * Passing some request headers
                         */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {


                            //  Log.e("abc", base64EncodedCredentials);

                            HashMap<String, String> headers = new HashMap<>();

                            headers.put("Content-Type", "application/json");

                            return headers;
                        }


                    };

                    requestQueue.add(stringRequest);

                }

                break;

            case R.id.registerUserTextView:
                Intent registerUserIntent = new Intent(this, SignUpActivity.class);
                startActivity(registerUserIntent);
                break;
        }
    }
}
