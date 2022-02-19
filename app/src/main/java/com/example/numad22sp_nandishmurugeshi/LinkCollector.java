package com.example.numad22sp_nandishmurugeshi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LinkCollector extends AppCompatActivity {

    private ArrayList<RViewCard> itemList= new ArrayList<>();
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
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void init(Bundle savedInstanceState) {

    }

    private void createView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        rView = new RView(itemList);
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void itemClick(int position) {
                //attributions bond to the item has been changed
                itemList.get(position).itemClick(position);

                rView.notifyItemChanged(position);
            }

            @Override
            public void itemCheckClick(int position) {
                //attributions bond to the item has been changed
                itemList.get(position).itemCheckClick(position);

                rView.notifyItemChanged(position);
            }
        };

        rView.setClickListener(itemClickListener);
        recyclerView.setAdapter(rView);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void addItemToList() {
        RViewCard card = new RViewCard(urlName.getText().toString(), url.getText().toString()
                , false);
        itemList.add(card);
        rView.notifyItemInserted(itemList.size() - 1);
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