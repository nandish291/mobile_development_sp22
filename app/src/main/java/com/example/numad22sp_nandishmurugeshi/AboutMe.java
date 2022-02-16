package com.example.numad22sp_nandishmurugeshi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        TextView view = findViewById(R.id.myinfo);

        view.setText(R.string.name_email);
    }
}