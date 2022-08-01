package com.example.tollwisedriver;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tollwisedriver.databinding.ActivityMainBinding;
import com.example.tollwisedriver.databinding.FragmentHomeBinding;
import com.example.tollwisedriver.databinding.FragmentRewardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private FragmentHomeBinding homeBinding;
    private String key_user_etoll;

    private Drivers drivers;
    private  Info_perjalanans perjalanans = new Info_perjalanans();

    String channelnotif = "channelku" ;
    String channelid = "default" ;
    String message, name, key_p, key_d;

    Integer number;
    private double diskon;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        homeBinding = FragmentHomeBinding.inflate(getLayoutInflater());

        setContentView(activityMainBinding.getRoot());
        drivers = new Drivers();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             drivers.setKey(extras.getString("key"));
             key_d = drivers.getKey();
        }else{
            Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                realtime();
            }
        }).start();


        activityMainBinding.navBottom.getMenu().getItem(2).setEnabled(false);

        activityMainBinding.actionText.setVisibility(View.INVISIBLE);
        DatabaseReference driver = database.child("Drivers").child(drivers.getKey());
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

        HomeFragment homeFragment = new HomeFragment();
        Fragment_info_perjalanan fragment_info_perjalanan= new Fragment_info_perjalanan();
        Fragment_sim_digital fragment_sim_digital = new Fragment_sim_digital();
        Fragment_profile fragment_profile = new Fragment_profile();
        Fragment_reward fragment_reward = new Fragment_reward();
        Fragment_topup fragment_topup = new Fragment_topup();

        Bundle b = new Bundle();
        b.putString("key", drivers.getKey());
        b.putString("key_p", perjalanans.getId());
        b.putDouble("diskon", diskon);
        homeFragment.setArguments(b);
        makeCurrentFragment(homeFragment);

        activityMainBinding.navBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.home):
                        activityMainBinding.linearLayout.setVisibility(View.VISIBLE);
                        activityMainBinding.foto.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setVisibility(View.GONE);
                        activityMainBinding.actionText.setText("");
                        Bundle b = new Bundle();
                        b.putString("key", drivers.getKey());
                        homeFragment.setArguments(b);
                        makeCurrentFragment(homeFragment);
                        break;
                    case (R.id.perjalanan):
                        activityMainBinding.linearLayout.setVisibility(View.GONE);
                        activityMainBinding.foto.setVisibility(View.GONE);
                        activityMainBinding.actionText.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setText("Real Time Perjalanan");
                        Bundle c = new Bundle();
                        c.putString("key", drivers.getKey());
                        c.putString("key_p", key_p);
                        c.putDouble("diskon", diskon);
                        fragment_info_perjalanan.setArguments(c);
                        makeCurrentFragment(fragment_info_perjalanan);
                        break;
                    case (R.id.fab):
                        activityMainBinding.linearLayout.setVisibility(View.GONE);
                        activityMainBinding.foto.setVisibility(View.GONE);
                        activityMainBinding.actionText.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setText("Sim Digital");
                        Bundle d = new Bundle();
                        d.putString("key", drivers.getKey());
                        d.putString("key_p", perjalanans.getId());
                        fragment_sim_digital.setArguments(d);
                        makeCurrentFragment(fragment_sim_digital);
                        break;
                    case (R.id.notification):
                        activityMainBinding.linearLayout.setVisibility(View.GONE);
                        activityMainBinding.foto.setVisibility(View.GONE);
                        activityMainBinding.actionText.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setText("Reward");
                        Bundle e = new Bundle();
                        e.putString("key", drivers.getKey());
                        e.putString("key_p", perjalanans.getId());
                        e.putDouble("diskon", diskon);
                        fragment_reward.setArguments(e);
                        makeCurrentFragment(fragment_reward);
                        break;
                    case (R.id.profile):
                        activityMainBinding.linearLayout.setVisibility(View.GONE);
                        activityMainBinding.foto.setVisibility(View.GONE);
                        activityMainBinding.actionText.setVisibility(View.VISIBLE);
                        activityMainBinding.actionText.setText("Profile");
                        Bundle f = new Bundle();
                        f.putString("key", drivers.getKey());
                        fragment_profile.setArguments(f);
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



    public void realtime(){
        DatabaseReference database1 = FirebaseDatabase.getInstance().getReference().child("User_tolls").child(drivers.getKey());
        database1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                key_user_etoll = snapshot.getKey();
                activityMainBinding.jmlPnp.setText(key_user_etoll);

                String a =  activityMainBinding.jmlPnp.getText().toString();
                if(a!= null){
                    activityMainBinding.jmlPnp.setText(a);
                    Query query = FirebaseDatabase.getInstance().getReference()
                            .child("Info_perjalanans").orderByChild("status_perjalanan").equalTo(true);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot childSnapshot : snapshot.getChildren()){
                                key_p = childSnapshot.getKey();
                                name = childSnapshot.child("jumlah_penumpang").getValue().toString();
                            }
                            activityMainBinding.jmlPnp.setText(key_p);
                            perjalanans.setId(activityMainBinding.jmlPnp.getText().toString());
                            activityMainBinding.jmlPnp.setText(name);

                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, channelid)
                                    .setSmallIcon(R.drawable.logo)
                                    .setContentTitle("Hallo Pengendara Bijak!");

                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                int importance = NotificationManager.IMPORTANCE_HIGH;
                                NotificationChannel notificationChannel = new
                                        NotificationChannel(channelnotif, "contoh channel", importance);
                                notificationChannel.enableLights(true);
                                notificationChannel.setLightColor(Color.RED);
                                mBuilder.setChannelId(channelnotif);
                                assert mNotificationManager != null;
                                mNotificationManager.createNotificationChannel(notificationChannel);
                            }

                            if(name != null) {
                                number = Integer.valueOf(name);
                                if (number == 4) {
                                    diskon = 0.15;
                                    message = "Kendaraan anda berpenumpang sebanyak " +
                                            name + " orang. \n Klaim reward Anda sekarang!";

                                    mBuilder.setContentText(message);
                                    assert mNotificationManager != null;
                                    mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());

                                }else if(number == 3) {
                                    message = "Kendaraan anda berpenumpang sebanyak " +
                                            name + " orang. \n Klaim reward Anda sekarang!";

                                    diskon = 0.10;
                                    mBuilder.setContentText(message);
                                    assert mNotificationManager != null;
                                    mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
                                }else if(number == 2) {
                                    message = "Kendaraan anda berpenumpang sebanyak " +
                                            name + " orang. \n Klaim reward Anda sekarang!";

                                    diskon = 0.05;
                                    mBuilder.setContentText(message);
                                    assert mNotificationManager != null;
                                    mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
                                }else if (number == 1) {

                                    message = "Kendaraan anda berpenumpang sebanyak " +
                                            name + " orang. \n Klaim reward Anda sekarang!";

                                    message = "Anda terdeteksi berpenumpang tunggal \n Mohon Gunakan Kendaraan Umum!";
                                    mBuilder.setContentText(message);
                                    assert mNotificationManager != null;
                                    mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
                                }
                            }
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

    }
}