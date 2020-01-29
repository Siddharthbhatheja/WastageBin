package com.example.wastagebin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class UpdateWastageDetectorRecyclerViewAdapter extends RecyclerView.Adapter<UpdateWastageDetectorRecyclerViewAdapter.MyViewHolder>{
    Context context;
    List<PojoClassDetector> items;

    public UpdateWastageDetectorRecyclerViewAdapter(Context context,List<PojoClassDetector> items) {
        this.context = context;
        this.items=items;
    }

    @NonNull
    @Override
    public UpdateWastageDetectorRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_wastage_recyclerview_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UpdateWastageDetectorRecyclerViewAdapter.MyViewHolder holder, final int position) {
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
             //   Toast.makeText(context, String.valueOf(position)+"item clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, WastageUpdateDetectorActivity.class);
                intent.putExtra("address",pojo.getAddress());
                intent.putExtra("city",pojo.getCity());
                intent.putExtra("zipcode",pojo.getZipcode());
                intent.putExtra("state",pojo.getState());
                intent.putExtra("country",pojo.getCountry());
                intent.putExtra("description",pojo.getDescription());
                intent.putExtra("id",pojo.getId());
                context.startActivity(intent);
                ((Activity)context).finish();
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