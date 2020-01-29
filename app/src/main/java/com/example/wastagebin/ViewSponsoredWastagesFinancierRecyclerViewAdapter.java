package com.example.wastagebin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewSponsoredWastagesFinancierRecyclerViewAdapter extends RecyclerView.Adapter<ViewSponsoredWastagesFinancierRecyclerViewAdapter.MyViewHolder>{
    Context context;

    public ViewSponsoredWastagesFinancierRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewSponsoredWastagesFinancierRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_wastage_recyclerview_item,parent,false);
        return new ViewSponsoredWastagesFinancierRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSponsoredWastagesFinancierRecyclerViewAdapter.MyViewHolder holder, int position) {

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}

