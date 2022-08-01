package com.example.tollwisedriver;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tollwisedriver.databinding.FragmentRewardBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Fragment_reward extends Fragment{


    private FragmentRewardBinding binding;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private Drivers drivers = new Drivers();
    private  Info_perjalanans perjalanans = new Info_perjalanans();

    private double diskon;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRewardBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        if(bundle != null) {
            drivers.setKey(bundle.getString("key"));
            perjalanans.setId(bundle.getString("key_p"));
            diskon = bundle.getDouble("diskon");
        }
        String disc = String.valueOf((int)(diskon * 100));

        if(diskon != 0.0){
            binding.persen.setText(disc +  "%");
            binding.selamat.setText(R.string.punya_reward);
            binding.selamat.setTextSize(30);
            binding.thropy.setVisibility(View.VISIBLE);
            binding.dapatDiskon.setVisibility(View.VISIBLE);
            binding.klaim.setVisibility(View.VISIBLE);
            binding.persen.setVisibility(View.VISIBLE);
        }

        binding.klaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.child("Info_perjalanans").child(perjalanans.getId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        perjalanans = snapshot.getValue(Info_perjalanans.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                database.child("Info_perjalanans").child(perjalanans.getId())
                        .setValue(perjalanans).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Reward berhasil di klaim",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return binding.getRoot();
    }



}
