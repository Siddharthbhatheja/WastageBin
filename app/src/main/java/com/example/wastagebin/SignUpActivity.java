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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameEditText, passwordEditText;
    //RadioGroup roleRadioGroup;
    Button signUpButton;
    RequestQueue requestQueue;
    RadioButton selectedRoleRadioButton;
    CheckBox detectorCheckBox, resolverCheckBox, financierCheckBox;
    List<String> roles = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
        requestQueue = Volley.newRequestQueue(this);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        detectorCheckBox = (CheckBox) findViewById(R.id.detectorCheckBox);
        resolverCheckBox = (CheckBox) findViewById(R.id.resolerCheckBox);
        financierCheckBox = (CheckBox) findViewById(R.id.financierCheckBox);
        //  roleRadioGroup = (RadioGroup) findViewById(R.id.roleRadioGroup);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpButton:
                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                if (detectorCheckBox.isChecked()) {
                    roles.add(detectorCheckBox.getText().toString());
                }

                if (resolverCheckBox.isChecked()) {
                    roles.add(resolverCheckBox.getText().toString());
                }

                if (financierCheckBox.isChecked()) {
                    roles.add("Financer");
                }

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || roles.size() == 0) {
                    Toast.makeText(this, "Please Select Relevant Fields", Toast.LENGTH_LONG).show();
                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                    progressDialog.setMessage("Registering Please wait....");
                    progressDialog.show();
                    progressDialog.setCancelable(false);


                    //RequestQueue requestQueue = Volley.newRequestQueue(this);


                    JSONObject rolesObject = new JSONObject();


                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < roles.size(); i++) {
                        jsonArray.put(roles.get(i));
                    }


                    try {
                        rolesObject.put("name", "hgh");
                        rolesObject.put("username", username);
                        rolesObject.put("password", password);
                        rolesObject.put("roles", jsonArray);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final String mRequestBody = rolesObject.toString();

                    Log.e("requestBody", mRequestBody);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, APIs.baseUsersUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.e("success", response);


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

HomeActivity.loggedInUsername=username;
HomeActivity.loggedInPassword=password;

                                    usernameEditText.setText("");
                                    passwordEditText.setText("");

                                    progressDialog.dismiss();

                                    Intent intent;

                                    if (detectorCheckBox.isChecked() && resolverCheckBox.isChecked() && financierCheckBox.isChecked()) {
                                        intent = new Intent(SignUpActivity.this, DetectorActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (detectorCheckBox.isChecked() && resolverCheckBox.isChecked()) {
                                        intent = new Intent(SignUpActivity.this, DetectorActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (detectorCheckBox.isChecked() && financierCheckBox.isChecked()) {
                                        intent = new Intent(SignUpActivity.this, DetectorActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (financierCheckBox.isChecked() && resolverCheckBox.isChecked()) {
                                        Log.e("achieved", "yes");
                                        intent = new Intent(SignUpActivity.this, ResolverActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (detectorCheckBox.isChecked()) {
                                        intent = new Intent(SignUpActivity.this, DetectorActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (financierCheckBox.isChecked()) {
                                        intent = new Intent(SignUpActivity.this, FinancierActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (resolverCheckBox.isChecked()) {
                                        intent = new Intent(SignUpActivity.this, ResolverActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }


                                    Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_LONG).show();
                                }
                            }, new Response.ErrorListener() {


                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            // error
                            usernameEditText.setText("");
                            passwordEditText.setText("");
                            detectorCheckBox.setSelected(false);
                            detectorCheckBox.setChecked(false);
                            resolverCheckBox.setSelected(false);
                            resolverCheckBox.setChecked(false);
                            financierCheckBox.setSelected(false);
                            financierCheckBox.setChecked(false);
                        }
                    }) {

                        /**
                         Passing some request headers
                         * */

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {


                            HashMap<String, String> headers = new HashMap<>();

                            headers.put("Content-Type", "application/json");

                            return headers;
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            String your_string_json = mRequestBody; // put your json
                            return your_string_json.getBytes();
                        }
                    };

                    requestQueue.add(stringRequest);

//                }


                    //        int selectedId = roleRadioGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    //      selectedRoleRadioButton = (RadioButton) findViewById(selectedId);

                    //     final String role = selectedRoleRadioButton.getText().toString();


                }
        }
    }
}