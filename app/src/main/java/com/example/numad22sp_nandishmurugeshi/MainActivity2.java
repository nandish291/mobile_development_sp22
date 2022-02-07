package com.example.numad22sp_nandishmurugeshi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView = findViewById(R.id.pressed);
        textView.setText(getString(R.string.pressed," "));
    }

    public void onClickButton(View view) {
        TextView textView = findViewById(R.id.pressed);
        Button button = findViewById(view.getId());
        textView.setText(getString(R.string.pressed, button.getText()));
    }
}