package com.example.wastagebin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FinancierActivity extends AppCompatActivity implements View.OnClickListener{
    Button sponsorWastageButton, viewSponsoredWastagesButton;
    ArrayList<String>status_list;
    TextView userTextView;
    String detectorReceived="",resolverReceived="",financerReceived="";
    ImageView menuImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financier);
userTextView=(TextView)findViewById(R.id.userTextView);
userTextView.setText(HomeActivity.loggedInUsername);


        menuImageView=(ImageView)findViewById(R.id.menuImageView);
        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Logout"};

                AlertDialog.Builder builder = new AlertDialog.Builder(FinancierActivity.this);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Intent intent  = new Intent(FinancierActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
//        Intent i = getIntent();
//
//         detectorReceived=i.getStringExtra("detector");
//         resolverReceived=i.getStringExtra("resolver");
//         financerReceived=i.getStringExtra("financer");
//
//        Log.e("dr",detectorReceived);
//        Log.e("rr",resolverReceived);
//        Log.e("fr",financerReceived);
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


        sponsorWastageButton=(Button)findViewById(R.id.sponsorWastageButton);
    //    viewSponsoredWastagesButton=(Button)findViewById(R.id.viewSponsoredWastagesButton);

        sponsorWastageButton.setOnClickListener(this);
      //  viewSponsoredWastagesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sponsorWastageButton:
                Intent sponsorWastageIntent = new Intent(this, SponsorWastageFinancierActivity.class);
                startActivity(sponsorWastageIntent);

                break;
//            case R.id.viewSponsoredWastagesButton:
//                Intent viewSponsoredWastagesIntent = new Intent(this, ViewSponsoredWastagesFinancierActivity.class);
//                startActivity(viewSponsoredWastagesIntent);
//                break;
        }
    }
}
