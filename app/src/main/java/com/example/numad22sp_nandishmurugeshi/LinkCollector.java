package com.example.numad22sp_nandishmurugeshi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LinkCollector extends AppCompatActivity {

    private final ArrayList<RViewCard> itemList= new ArrayList<>();
    private RecyclerView recyclerView;
    private RView rView;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton actionButton;
    EditText urlName;
    EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        init(savedInstanceState);
        createView();

        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                RViewCard temp = itemList.remove(position);
                Snackbar snackbar =Snackbar.make(findViewById(R.id.recyclerView), "URL deleted"
                        , Snackbar.LENGTH_SHORT);
                snackbar.setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemList.add(temp);
                        rView.notifyDataSetChanged();
                    }
                });
                snackbar.show();
                rView.notifyItemRemoved(position);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Size",itemList.size());
        for (int i=0; i < itemList.size(); i++) {
            outState.putString("linkName "+i,itemList.get(i).getItemName());
            outState.putString("linkUrl "+i,itemList.get(i).getItemUrl());
        }
    }

    private void init(Bundle savedInstanceState) {
        if(savedInstanceState !=null && savedInstanceState.containsKey("Size")) {
            int size =savedInstanceState.getInt("Size");

            for (int i=0; i < size; i++) {
                RViewCard card = new RViewCard(savedInstanceState.getString("linkName "+i),
                        savedInstanceState.getString("linkUrl "+i));
                itemList.add(card);
            }
        }
    }

    private void createView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        rView = new RView(itemList);
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void itemClick(int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(itemList.get(position).getItemUrl()));
                try {
                    startActivity(intent);
                }catch (ActivityNotFoundException e) {
                    Snackbar.make(findViewById(R.id.recyclerView),
                            "Invalid URI",Snackbar.LENGTH_SHORT).show();
                }
            }
        };

        rView.setClickListener(itemClickListener);
        recyclerView.setAdapter(rView);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void addItemToList() {
        String urlString = url.getText().toString();
        if(!urlString.startsWith("https://")) {
            urlString ="https://" + urlString;
        }
        RViewCard card = new RViewCard(urlName.getText().toString(), urlString);
        itemList.add(card);
        rView.notifyItemInserted(itemList.size() - 1);
        Snackbar.make(findViewById(R.id.recyclerView),
                "Url added successfully", Snackbar.LENGTH_SHORT).show();
    }



    public void addItem(View view) {
        AlertDialog.Builder urlDialog = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        urlName = new EditText(this);
        urlName.setHint("URL name");
        url = new EditText(this);
        url.setHint("URL");
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(urlName);
        layout.addView(url);
        urlDialog.setTitle("Enter new URL");
        urlDialog.setView(layout);

        urlDialog.setPositiveButton(
                "Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean valid = true;
                        if(urlName.getText().toString().isEmpty()) {
                            urlName.setError("Name Cannot be Empty");
                            valid = false;
                        }
                        if(url.getText().toString().isEmpty()) {
                            url.setError("URL cannot be empty");
                            valid = false;
                        }
                        if (valid) {
                            addItemToList();
                        } else {
                            Snackbar.make(findViewById(R.id.recyclerView),
                                    "Invalid input, url not added", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        urlDialog.show();
    }


}