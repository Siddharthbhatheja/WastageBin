package com.example.wastagebin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ResolverActivity extends AppCompatActivity implements View.OnClickListener{
    Button viewOffersButton, yourAcceptedOffersButton, revokeOfferButton;
    ArrayList<String>status_list;
    String detectorReceived="",resolverReceived="",financerReceived="";
    TextView userTextView;
    ImageView menuImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolver);

        menuImageView=(ImageView)findViewById(R.id.menuImageView);
        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Logout"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ResolverActivity.this);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Intent intent  = new Intent(ResolverActivity.this, HomeActivity.class);
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

        viewOffersButton=(Button)findViewById(R.id.viewOffersButton);
        yourAcceptedOffersButton=(Button)findViewById(R.id.yourAcceptedOffersButton);
        //revokeOfferButton=(Button)findViewById(R.id.revokeOfferButton);

        viewOffersButton.setOnClickListener(this);
        yourAcceptedOffersButton.setOnClickListener(this);


        userTextView=(TextView)findViewById(R.id.userTextView);
        userTextView.setText(HomeActivity.loggedInUsername);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.viewOffersButton:
                Intent viewOffersResolverIntent = new Intent(this, ViewOffersResolverActivity.class);
                startActivity(viewOffersResolverIntent);
                break;
            case R.id.yourAcceptedOffersButton:
                Intent yourAcceptedOffersResolverIntent = new Intent(this, YourAcceptedOffersResolverActivity.class);
                startActivity(yourAcceptedOffersResolverIntent);
                break;


        }
    }
}
