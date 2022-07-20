package com.example.tollwisedriver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    ImageButton back;
    CardView next;
    TextView info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        back = findViewById(R.id.arrow_back);
        next = findViewById(R.id.next);
        info = findViewById(R.id.action_text);




    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void next(View view) {
        info.setText("Real Time Perjalanan");
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final Fragment_info_perjalanan fragment_info_perjalanan = new Fragment_info_perjalanan();

        fragmentTransaction.replace(R.id.container, fragment_info_perjalanan).commit();
    }


}