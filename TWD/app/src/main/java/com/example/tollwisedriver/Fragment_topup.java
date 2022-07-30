package com.example.tollwisedriver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tollwisedriver.databinding.FragmentTopupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class Fragment_topup extends Fragment {

    DatabaseReference database;

    private FragmentTopupBinding binding;
    private  double sld;

    private Drivers drivers = new Drivers();
    private  E_tolls e_tolls = new E_tolls();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            drivers.setKey(bundle.getString("key"));
            Toast.makeText(getContext(), drivers.getKey(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTopupBinding.inflate(inflater, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            drivers.setKey(bundle.getString("key"));
            Toast.makeText(getContext(), drivers.getKey(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
        }


        database = FirebaseDatabase.getInstance().getReference().child("Drivers").child(drivers.getKey()).child("e_tolls");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String nomor = snapshot.child("nomor_etoll").getValue().toString();
                String berlaku_sampai = snapshot.child("berlaku_sampai").getValue().toString();
                String saldo = snapshot.child("saldo").getValue().toString();

                e_tolls.setNomor_etoll(nomor);
                e_tolls.setBerlaku_sampai(berlaku_sampai);
                e_tolls.setSaldo(Integer.valueOf(saldo));
                sld = Double.parseDouble(saldo);



                binding.btnTopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String topup = binding.saldoTopup.getText().toString();
                        Toast.makeText(getContext(), topup, Toast.LENGTH_SHORT).show();

                        double tp = Double.parseDouble(topup);

                        sld = sld + tp;
                        int saldo_sekarang = (int) sld;

                        String curr_saldo = String.valueOf(saldo_sekarang);

                        updateData(e_tolls.getNomor_etoll(),e_tolls.getBerlaku_sampai(), curr_saldo);

                        HomeFragment homeFragment = new HomeFragment();
                        FragmentManager mFragmentManager = getFragmentManager();
                        Bundle bundle = new Bundle();
                        bundle.putString("key", drivers.getKey());
                        homeFragment.setArguments(bundle);
                        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.container, homeFragment, HomeFragment.class.getSimpleName());
                        mFragmentTransaction.addToBackStack(null);
                        mFragmentTransaction.commit();

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }

    private  void updateData(String nomor, String berlaku_sampai, String saldo){
        HashMap e_toll = new HashMap();
        e_toll.put("nomor", nomor);
        e_toll.put("berlaku_sampai", berlaku_sampai);
        e_toll.put("saldo",saldo);

        database = FirebaseDatabase.getInstance().getReference().child("Drivers").child(drivers.getKey()).child("e_tolls");
        database.updateChildren(e_toll).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Toast.makeText(getContext(), "top up berhasil", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
