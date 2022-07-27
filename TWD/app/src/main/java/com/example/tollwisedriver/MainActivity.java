package com.example.tollwisedriver;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tollwisedriver.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private String key_user_etoll;

    String channelnotif = "channelku" ;
    String channelid = "default" ;
    String message;

    TextView txt;



    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.arrowBack.setVisibility(View.INVISIBLE);
        activityMainBinding.actionText.setVisibility(View.INVISIBLE);

        DatabaseReference driver = database.child("Drivers").child("dvr1");
        driver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("nama").getValue().toString();
                activityMainBinding.namaTxt.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference database1 = FirebaseDatabase.getInstance().getReference().child("User_tolls").child("dvr1");
       database1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                key_user_etoll = snapshot.getKey();
                activityMainBinding.jmlPnp.setText(key_user_etoll);

                String a =  activityMainBinding.jmlPnp.getText().toString();
                if(a!= null){
                    activityMainBinding.jmlPnp.setText(a);

                    DatabaseReference perjalanan = database.child("Perjalanans").child(a);
                    perjalanan.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.child("jumlah_penumpang").getValue().toString();
                            activityMainBinding.jmlPnp.setText(name);

                            Integer number = Integer.valueOf(name);
                            if(number == 4){

                                message = "Kendaraan anda berpenumpang sebanyak " +
                                        snapshot.child("jumlah_penumpang").getValue().toString() + " orang. \n Klaim reward Anda sekarang!";

                            }else if(number == 1){
                                message ="Anda terdeteksi berpenumpang tunggal \n Mohon Gunakan Kendaraan Umum!";
                            }
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity. this, channelid )
                                    .setSmallIcon(R.drawable.logo)
                                    .setContentTitle( "Hai Pengendara bijak!" )
                                    .setContentText( message );
                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE ) ;
                            if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                                int importance = NotificationManager. IMPORTANCE_HIGH ;
                                NotificationChannel notificationChannel = new
                                        NotificationChannel( channelnotif , "contoh channel" , importance) ;
                                notificationChannel.enableLights( true ) ;
                                notificationChannel.setLightColor(Color. RED ) ;
                                mBuilder.setChannelId( channelnotif ) ;
                                assert mNotificationManager != null;
                                mNotificationManager.createNotificationChannel(notificationChannel) ;
                            }
                            assert mNotificationManager != null;
                            mNotificationManager.notify(( int ) System. currentTimeMillis (), mBuilder.build()) ;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else{
                    activityMainBinding.namaTxt.setText("haha");
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });





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