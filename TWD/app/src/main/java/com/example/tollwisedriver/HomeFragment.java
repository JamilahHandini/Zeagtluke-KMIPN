package com.example.tollwisedriver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.tollwisedriver.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {


    private DatabaseReference database;
    private  Drivers drivers = new Drivers();

    private  Info_perjalanans perjalanans = new Info_perjalanans();

    private String key_p;
    private  double sld, diskon;

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        drivers.setKey(bundle.getString("key"));
        key_p= bundle.getString("key_p");
        sld = bundle.getDouble("diskon");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance().getReference().child("Drivers").child(drivers.getKey()).child("e_tolls");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String saldo_sekarang = snapshot.child("saldo").getValue().toString();
                String nomor = snapshot.child("nomor_etoll").getValue().toString();
                String berlaku_sampai = snapshot.child("berlaku_sampai").getValue().toString();
                binding.saldoEtoll.setText(saldo_sekarang);
                binding.nomorEtoll.setText(nomor);
                binding.berlakuSampai.setText(berlaku_sampai);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.infoPerjalanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_info_perjalanan fragment_info_perjalanan = new Fragment_info_perjalanan();
                Bundle b = new Bundle();
                b.putString("key", drivers.getKey());
                b.putString("key_p", perjalanans.getId());
                b.putDouble("diskon", diskon);
                fragment_info_perjalanan.setArguments(b);
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container, fragment_info_perjalanan, Fragment_topup.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
            }
        });

        binding.pelanggaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_pelanggaran fragment_pelanggaran = new Fragment_pelanggaran();
                Bundle b = new Bundle();
                b.putString("key", drivers.getKey());
                b.putString("key_p", perjalanans.getId());
                b.putDouble("diskon", diskon);
                fragment_pelanggaran.setArguments(b);
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container, fragment_pelanggaran, Fragment_topup.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
            }
        });

        binding.simDigital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_sim_digital fragment_sim_digital = new Fragment_sim_digital();
                Bundle b = new Bundle();
                b.putString("key", drivers.getKey());
                b.putString("key_p", perjalanans.getId());
                b.putDouble("diskon", diskon);
                fragment_sim_digital.setArguments(b);
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container, fragment_sim_digital, Fragment_topup.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
            }
        });

        binding.reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_reward fragment_reward = new Fragment_reward();
                Bundle b = new Bundle();
                b.putString("key", drivers.getKey());
                b.putString("key_p", perjalanans.getId());
                b.putDouble("diskon", diskon);
                fragment_reward.setArguments(b);
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container, fragment_reward, Fragment_topup.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
            }
        });

        binding.topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_topup fragment_topup = new Fragment_topup();
                FragmentManager mFragmentManager = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("key", drivers.getKey());
                fragment_topup.setArguments(bundle);
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container, fragment_topup, Fragment_topup.class.getSimpleName());
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
            }
        });
    }
}