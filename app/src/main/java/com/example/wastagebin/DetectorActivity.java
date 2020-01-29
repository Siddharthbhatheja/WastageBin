package com.example.wastagebin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetectorActivity extends AppCompatActivity implements View.OnClickListener {
    Button reportWastageButton, updateWastageButton, deleteWastageButton;
ArrayList<String>status_list;
ImageView menuImageView;
TextView userTextView;
String detectorReceived="_",resolverReceived="_",financerReceived="_";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detector);
//        Intent i = getIntent();
        menuImageView=(ImageView)findViewById(R.id.menuImageView);
        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Logout"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DetectorActivity.this);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Intent intent  = new Intent(DetectorActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
//
//         detectorReceived=i.getStringExtra("detector");
//         resolverReceived=i.getStringExtra("resolver");
//         financerReceived=i.getStringExtra("financer");
//
//
//
//        if(detectorReceived.equals("Detector")&&resolverReceived.equals("Resolver")&&financerReceived.equals("Financer")){
//            status_list.add("Detector");
//            status_list.add("Resolver");
//            status_list.add("Financer");
//        }
//        else if(detectorReceived.equals("Detector")&&resolverReceived.equals("Resolver")) {
//            status_list.add("Detector");
//            status_list.add("Resolver");
//        }
//        else if(detectorReceived.equals("Detector")&&financerReceived.equals("Financer")){
//            status_list.add("Detector");
//            status_list.add("Financer");
//        }
//        else if(resolverReceived.equals("Resolver")&&financerReceived.equals("Financer")){
//            status_list.add("Resolver");
//            status_list.add("Financer");
//        }
//        else if(detectorReceived.equals("Detector")){
//            status_list.add("Detector");
//        }
//        else if(resolverReceived.equals("Resolver")){
//            status_list.add("Resolver");
//        }
//        else if(financerReceived.equals("Financer")){
//            status_list.add("Financer");
//        }
userTextView=(TextView)findViewById(R.id.userTextView);
        reportWastageButton = (Button) findViewById(R.id.reportWastageButton);

        updateWastageButton = (Button) findViewById(R.id.updateWastageButton);
        deleteWastageButton = (Button) findViewById(R.id.deleteWastageButton);
        reportWastageButton.setOnClickListener(this);

        userTextView.setText(HomeActivity.loggedInUsername);
        updateWastageButton.setOnClickListener(this);
        deleteWastageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reportWastageButton:
                Intent reportWastageDetectorIntent = new Intent(this, ReportWastageDetectorActivity.class);
                startActivity(reportWastageDetectorIntent);
                break;

            case R.id.updateWastageButton:
                Intent updateWastageDetectorIntent = new Intent(this, UpdateWastageDetectorActivity.class);
                startActivity(updateWastageDetectorIntent);
                break;

            case R.id.deleteWastageButton:
                Intent deleteWastageDetectorIntent = new Intent(this, DeleteWastageDetectorActivity.class);
                startActivity(deleteWastageDetectorIntent);
                break;
        }
    }
}
