package com.example.serwisauta2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class CarAdapter extends FirebaseRecyclerAdapter<Car, CarAdapter.CarViewHolder>
{
    public CarAdapter(@NonNull FirebaseRecyclerOptions<Car> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CarViewHolder holder, int position, @NonNull Car Car)
    {
        holder.carname.setText(Car.getCar_name());
        holder.mileage.setText(Car.getMileage());
        holder.vin.setText(Car.getVin());
        Glide.with(holder.img.getContext()).load(Car.getProfileImageUrl()).into(holder.img);
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlecar,parent,false);
       return new CarViewHolder(view);
    }

    class CarViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView carname, mileage, vin;
        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.default_picture);
            carname = itemView.findViewById(R.id.car_name_list);
            mileage = itemView.findViewById(R.id.car_mileage_list);
            vin = itemView.findViewById(R.id.car_vin_list);
        }
    }
}
