package com.example.tollwisedriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.tollwisedriver.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.arrowBack.setVisibility(View.INVISIBLE);
        activityMainBinding.actionText.setVisibility(View.INVISIBLE);


        HomeFragment homeFragment = new HomeFragment();
        Fragment_info_perjalanan fragment_info_perjalanan= new Fragment_info_perjalanan();
        Fragment_sim_digital fragment_sim_digital = new Fragment_sim_digital();
        Fragment_profile fragment_profile = new Fragment_profile();
        Fragment_reward fragment_reward = new Fragment_reward();

        makeCurrentFragment(homeFragment);

        activityMainBinding.navBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.home):
                        activityMainBinding.linearLayout.setVisibility(View.VISIBLE);
                        activityMainBinding.foto.setVisibility(View.VISIBLE);
                        makeCurrentFragment(homeFragment);
                        break;
                    case (R.id.perjalanan):
                        activityMainBinding.linearLayout.setVisibility(View.INVISIBLE);
                        activityMainBinding.foto.setVisibility(View.INVISIBLE);
                        activityMainBinding.arrowBack.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setText("Real Time Perjalanan");
                        makeCurrentFragment(fragment_info_perjalanan);
                        break;
                    case (R.id.placeholder):
                        activityMainBinding.linearLayout.setVisibility(View.INVISIBLE);
                        activityMainBinding.foto.setVisibility(View.INVISIBLE);
                        activityMainBinding.arrowBack.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setText("Sim Digital");
                        makeCurrentFragment(fragment_sim_digital);
                        break;
                    case (R.id.notification):
                        activityMainBinding.linearLayout.setVisibility(View.INVISIBLE);
                        activityMainBinding.foto.setVisibility(View.INVISIBLE);
                        activityMainBinding.arrowBack.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setText("Reward");
                        makeCurrentFragment(fragment_reward);
                        break;
                    case (R.id.profile):
                        activityMainBinding.linearLayout.setVisibility(View.INVISIBLE);
                        activityMainBinding.foto.setVisibility(View.INVISIBLE);
                        activityMainBinding.arrowBack.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setText("Profile");
                        makeCurrentFragment(fragment_profile);
                        break;
                }
                return false;
            }
        });



    }


    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void next(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final Fragment_info_perjalanan fragment_info_perjalanan = new Fragment_info_perjalanan();
        fragmentTransaction.replace(R.id.container, fragment_info_perjalanan).commit();
    }



    private void makeCurrentFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}