package com.example.tollwisedriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

<<<<<<< HEAD
=======
import com.example.tollwisedriver.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


>>>>>>> 42cd362 (unknown)
public class MainActivity extends AppCompatActivity {
    private ImageButton back;
    private CardView next;
    private TextView info;

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        back = findViewById(R.id.arrow_back);
        next = findViewById(R.id.next);
        info = findViewById(R.id.real_time_p);
=======

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
                        makeCurrentFragment(homeFragment);
                        break;
                    case (R.id.perjalanan):
                        makeCurrentFragment(fragment_info_perjalanan);
                        break;
                    case (R.id.placeholder):
                        makeCurrentFragment(fragment_sim_digital);
                        break;
                    case (R.id.notification):
                        makeCurrentFragment(fragment_sim_digital);
                        break;
                    case (R.id.profile):
                        makeCurrentFragment(fragment_profile);
                        break;
                }
                return false;
            }
        });



>>>>>>> 42cd362 (unknown)
    }


    public void back(View view) {
    }

    public void next(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final Fragment_info_perjalanan fragment_info_perjalanan = new Fragment_info_perjalanan();
        fragmentTransaction.replace(R.id.container, fragment_info_perjalanan).commit();
    }
<<<<<<< HEAD
=======


    private void makeCurrentFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
>>>>>>> 42cd362 (unknown)
}