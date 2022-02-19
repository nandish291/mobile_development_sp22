package com.example.numad22sp_nandishmurugeshi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RView extends RecyclerView.Adapter<RViewHolder>  {

    private final ArrayList<RViewCard> itemList;

    private ItemClickListener clickListener;

    public RView(ArrayList<RViewCard> itemList) {
        this.itemList = itemList;
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new RViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        RViewCard card =itemList.get(position);

        holder.itemName.setText(card.getItemName());
        holder.itemUrl.setText(card.getItemUrl());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

