package com.example.numad22sp_nandishmurugeshi;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RViewHolder extends RecyclerView.ViewHolder {

    public TextView itemName;
    public TextView itemUrl;

    public RViewHolder(@NonNull View itemView, final ItemClickListener clickListener) {
        super(itemView);
        itemName = itemView.findViewById(R.id.name);
        itemUrl = itemView.findViewById(R.id.url);

        itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        clickListener.itemClick(position);
                    }
                }
            }
        });
        itemUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        clickListener.itemClick(position);
                    }
                }
            }
        });
    }
}
