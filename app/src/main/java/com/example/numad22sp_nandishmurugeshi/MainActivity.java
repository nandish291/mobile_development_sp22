package com.example.numad22sp_nandishmurugeshi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.numad22sp_nandishmurugeshi.atYourService.AtYourService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void aboutMe(View view) {
        Intent intent = new Intent(this, AboutMe.class);
        startActivity(intent);
    }

    public void clickyClick(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void onLinkCollector(View view) {
        Intent intent = new Intent(this, LinkCollector.class);
        startActivity(intent);
    }

    public void onLocationClick(View view) {
        Intent intent = new Intent(this, LocationView.class);
        startActivity(intent);
    }

    public void onCLickAtYourService(View view) {
        Intent intent = new Intent(this, AtYourService.class);
        startActivity(intent);
    }
}
