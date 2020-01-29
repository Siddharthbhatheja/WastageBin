package com.example.wastagebin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Authenticator;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;



public class ReportWastageDetectorActivity extends AppCompatActivity implements View.OnClickListener {
    EditText cityEditText, stateEditText, zipcodeEditText, countryEditText, addressEditText, descriptionEditText;
    Button submitWastageButton;
    RelativeLayout sendCurrentLocationLinearLayout;
    RelativeLayout locationRecordedRelativeLayout;
    //ImageView selectPhotoImageView;
    String ServerResult, photo;
    String PicturePath = "";
    Uri uploadCameraImage, selectedImage;
    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 4;
    GPSTracker gpsTracker;

    double recordedLatitude = 0.0;
    double recordedLongitude = 0.0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_wastage_detector);

        Log.e("user",HomeActivity.loggedInUsername);
        Log.e("pwd",HomeActivity.loggedInPassword);

        cityEditText = (EditText) findViewById(R.id.cityEditText);
        stateEditText = (EditText) findViewById(R.id.stateEditText);
        zipcodeEditText = (EditText) findViewById(R.id.zipcodeEditText);
        countryEditText = (EditText) findViewById(R.id.countryEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
      //  selectPhotoImageView = (ImageView) findViewById(R.id.selectPhotoImageView);
        locationRecordedRelativeLayout = (RelativeLayout) findViewById(R.id.locationRecordedRelativeLayout);
        sendCurrentLocationLinearLayout = (RelativeLayout) findViewById(R.id.sendCurrentLocationRelativeLayout);

        sendCurrentLocationLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCurrentLocationLinearLayout.setVisibility(View.INVISIBLE);
                locationRecordedRelativeLayout.setVisibility(View.VISIBLE);

                gpsTracker = new GPSTracker(ReportWastageDetectorActivity.this);
                if (gpsTracker.canGetLocation()) {
                    recordedLatitude = gpsTracker.getLatitude();
                    recordedLongitude = gpsTracker.getLongitude();
                    Log.e("CurrentLatlng", recordedLatitude + recordedLongitude + ";");
                } else {
                    gpsTracker.showSettingsAlert(ReportWastageDetectorActivity.this);
                }
            }
        });


//        selectPhotoImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(ReportWastageDetectorActivity.this);
//                LayoutInflater inflater = ReportWastageDetectorActivity.this.getLayoutInflater();
//                View dialogView = inflater.inflate(R.layout.custom_photo_selector, null);
//                builder.setView(dialogView);
//                Button cancelButton = (Button) dialogView.findViewById(R.id.cancelButton);
//                ImageView cameraImageView = (ImageView) dialogView.findViewById(R.id.cameraImageView);
//                ImageView galleryImageView = (ImageView) dialogView.findViewById(R.id.galleryImageView);
//                final AlertDialog dialog = builder.create();
//                dialog.show();
//                cancelButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                cameraImageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(intent, REQUEST_CAMERA);
//                    }
//                });
//                galleryImageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(Intent.createChooser(galleryIntent, "Select File"), SELECT_FILE);
//                    }
//                });
//
//
//            }
//        });
//

        submitWastageButton = (Button) findViewById(R.id.submitWastageButton);
        submitWastageButton.setOnClickListener(this);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == SELECT_FILE) {
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                PicturePath = cursor.getString(columnIndex);
//                Log.d("PicturePath : ", PicturePath);
//                cursor.close();
////                selectPhotoImageView.setImageBitmap(BitmapFactory.decodeFile(PicturePath));
//
//                //    new FileUploadToServerTask().execute("");
//
//            } else if (requestCode == REQUEST_CAMERA) {
//                onCaptureImageResult(data);
//
//            }
//        }
//
//    }
//
//
//    private void onCaptureImageResult(Intent data) {
//        Bitmap bitMapImage = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        bitMapImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Uri tempUri = getImageUri(getApplicationContext(), bitMapImage);
//
//        // CALL THIS METHOD TO GET THE ACTUAL PATH
//        File finalFile = new File(getRealPathFromURI(tempUri));
//        PicturePath = String.valueOf(finalFile);
//  //      selectPhotoImageView.setImageBitmap(bitMapImage);
//
//        //  new FileUploadToServerTask().execute("");
//
//    }
//
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//
//    public String getRealPathFromURI(Uri uri) {
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//        return cursor.getString(idx);
//    }


//    private class FileUpload extends  AsyncTask<String, Void,String>{
//
//        @Override
//        protected String doInBackground(String... strings) {
//            URL url = new URL(postTarget);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            String auth = "Bearer " + oauthToken;
//            connection.setRequestProperty("Authorization", basicAuth);
//
//            String boundary = UUID.randomUUID().toString();
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//
//            DataOutputStream request = new DataOutputStream(uc.getOutputStream());
//
//            request.writeBytes("--" + boundary + "\r\n");
//            request.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n");
//            request.writeBytes(fileDescription + "\r\n");
//
//            request.writeBytes("--" + boundary + "\r\n");
//            request.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.fileName + "\"\r\n\r\n");
//            request.write(FileUtils.readFileToByteArray(file));
//            request.writeBytes("\r\n");
//
//            request.writeBytes("--" + boundary + "--\r\n");
//            request.flush();
//            int respCode = connection.getResponseCode();
//
//            switch(respCode) {
//                case 200:
//                    //all went ok - read response
//        ...
//                    break;
//                case 301:
//                case 302:
//                case 307:
//                    //handle redirect - for example, re-post to the new location
//        ...
//                    break;
//    ...
//                default:
//                    //do something sensible
//            }
//        }
//    }
//


//    private class FileUploadToServerTask extends AsyncTask<String, Void, String> {
//        ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog=ProgressDialog.show(ReportWastageDetectorActivity.this, "" ,"Please wait...", false, false);
//        }
//        @Override
//        protected String doInBackground(String... params) {
//
//            try {
//                String SourceFileUri = PicturePath;
//                Log.e("source",SourceFileUri);
//                String FileNameAtServer = "file";
//                HttpURLConnection httpURLConnection = null;
//                DataOutputStream dataOutputStream = null;
//                String lineEnd = "\r\n";
//                String twoHyphens = "--";
//                String boundary = "*****";
//                int bytesRead, bytesAvailable, bufferSize;
//                byte[] buffer;
//                int maxBufferSize = 1 * 1024 * 1024;
//                File SourceFile = new File(SourceFileUri);
//
//                if (SourceFile.isFile()) {
//                    try {
//                        Log.e("enter","entering");
//                        String upLoadServerUrl=APIs.baseUrl+"detector/images/create";
//                        // open a URL connection to the Servlet
//                        FileInputStream fileInputStream = new FileInputStream(SourceFile);
//                        URL Url = new URL(upLoadServerUrl);
//
//                        // Open a HTTP connection to the URL
//                        httpURLConnection = (HttpURLConnection) Url.openConnection();
//                        httpURLConnection.setDoInput(true); // Allow Inputs
//                        httpURLConnection.setDoOutput(true); // Allow Outputs
//                        httpURLConnection.setUseCaches(false); // Don't use a Cached Copy
//                        httpURLConnection.setRequestMethod("POST");
//
//
//
//                        String credentials = "user7" + ":" + "password7";
//                        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                        httpURLConnection.setRequestProperty ("Authorization", "Basic " + base64EncodedCredentials);
//                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
//                        httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
//                        httpURLConnection.setRequestProperty("Content-Type",
//                                "multipart/form-data;boundary=" + boundary);
//
//                        httpURLConnection.setRequestProperty(FileNameAtServer, SourceFileUri);
//                        httpURLConnection.setRequestProperty("title","sdkfjd");
//                        dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
//                        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                        //dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + SourceFileUri + "\"" + lineEnd);
//                        dataOutputStream.writeBytes("Content-Disposition: form-data; name=" + "\"" + FileNameAtServer + "\"" + ";filename=\"" + SourceFileUri + "\"" + lineEnd);
//
//                        dataOutputStream.writeBytes(lineEnd);
//
//                        // create a buffer of maximum size
//                        bytesAvailable = fileInputStream.available();
//
//                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                        buffer = new byte[bufferSize];
//
//                        // read file and write it into form...
//                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//                        while (bytesRead > 0) {
//                            dataOutputStream.write(buffer, 0, bufferSize);
//                            bytesAvailable = fileInputStream.available();
//                            bufferSize = Math
//                                    .min(bytesAvailable, maxBufferSize);
//                            bytesRead = fileInputStream.read(buffer, 0,
//                                    bufferSize);
//
//                        }
//
//                        // send multipart form data necesssary after file
//                        // data...
//                        dataOutputStream.writeBytes(lineEnd);
//                        dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens
//                                + lineEnd);
//
//                        // Responses from the server (code and message)
//                        int serverResponseCode = httpURLConnection.getResponseCode();
//                        final String serverResponseMessage = httpURLConnection.getResponseMessage();
//                        Log.e("responseCode",String.valueOf(serverResponseCode));
//
//                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                        try {
//                            ServerResult = bufferedReader.readLine();
//                            Log.e("server",ServerResult);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//
//                        if (serverResponseCode == 200) {
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//Log.e("response","dksfldsjakfj;dslkjaflds");
//                                    //     Toast.makeText(getApplicationContext(), serverResponseMessage, Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//
//                        // close the streams //
//                        fileInputStream.close();
//                        dataOutputStream.flush();
//                        dataOutputStream.close();
//
//                    } catch (Exception e) {
//
//                        // dialog.dismiss();
//                        e.printStackTrace();
//                        Log.e("error22", e.toString());
//                    }
//                    // dialog.dismiss();
//
//                } // End else block
//
//
//            } catch (Exception ex) {
//                // dialog.dismiss();
//                ex.printStackTrace();
//            }
//            return ServerResult;
//        }
//
//        @Override
//        protected void onPostExecute(String result)
//        {
//            progressDialog.dismiss();
//            Log.d("ServerResult : ", result);
//            JSONObject jsonObject;
//            try {
//                jsonObject = new JSONObject(result);
//                photo = jsonObject.getString("path");
//                Log.e("photo",photo);
//            } catch (JSONException e)
//            {  e.printStackTrace();  }
//        }
//
//
//
//        @Override
//        protected void onProgressUpdate(Void... values)
//        {
//
//        }
//    }

    @Override
    public void onClick(View v) {
        String city = cityEditText.getText().toString();
        String state = stateEditText.getText().toString();
        final int zipcode = Integer.parseInt(zipcodeEditText.getText().toString());
        String country = countryEditText.getText().toString();
        String address = addressEditText.getText().toString();
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
            location.put("zipcode", zipcode);
            location.put("latitude", recordedLatitude);
            location.put("longitude", recordedLongitude);

            body.put("description", description);
            body.put("location", location);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = body.toString();

        Log.e("requestBody", mRequestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIs.createAndPostWastage,
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


                        progressDialog.dismiss();
                        Toast.makeText(ReportWastageDetectorActivity.this, "Wastage Reported", Toast.LENGTH_LONG).show();
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
