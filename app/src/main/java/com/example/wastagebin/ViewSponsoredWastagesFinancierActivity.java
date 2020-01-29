package com.example.wastagebin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

public class ViewSponsoredWastagesFinancierActivity extends AppCompatActivity {
    RecyclerView viewSponsoredWastagesFinancierRecyclerView;
    ViewSponsoredWastagesFinancierRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sponsored_wastages_financier);

        viewSponsoredWastagesFinancierRecyclerView = (RecyclerView) findViewById(R.id.yourSponsoredOffersFinancierRecyclerView);
        mAdapter = new ViewSponsoredWastagesFinancierRecyclerViewAdapter(ViewSponsoredWastagesFinancierActivity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        viewSponsoredWastagesFinancierRecyclerView.setLayoutManager(mLayoutManager);
        viewSponsoredWastagesFinancierRecyclerView.setAdapter(mAdapter);

    }
}
