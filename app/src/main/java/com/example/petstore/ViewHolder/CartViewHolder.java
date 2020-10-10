package com.example.petstore.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.petstore.R;
import com.example.petstore.interfaces.ItemClickListener;

public class CartViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtBreedName, txtPetPrice, txtPetQuantity;

    private ItemClickListener itemClickListener;

    public CartViewHolder(View itemView)
    {
        super(itemView);
        txtBreedName = itemView.findViewById(R.id.cartBreedName);
        txtPetPrice = itemView.findViewById(R.id.cartPrice);
        txtPetQuantity = itemView.findViewById(R.id.cartQuantity);
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
