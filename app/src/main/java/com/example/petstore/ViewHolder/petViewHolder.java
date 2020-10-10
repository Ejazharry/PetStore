package com.example.petstore.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petstore.R;
import com.example.petstore.interfaces.ItemClickListener;

public class petViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView textBreedName, txtPetDescription, txtpetPrice;
    public ImageView imageView;
    public  ItemClickListener listener;
    public petViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.pet_image);
        textBreedName = (TextView) itemView.findViewById(R.id.breed_name);
        txtpetPrice = (TextView) itemView.findViewById(R.id.pet_price);
    }

    public  void setItemClickListener(ItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);
    }
}
