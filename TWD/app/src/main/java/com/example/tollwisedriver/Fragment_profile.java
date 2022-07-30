package com.example.tollwisedriver;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tollwisedriver.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Fragment_profile extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference database;

    ImageButton log_out;

    private  Drivers drivers = new Drivers();


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
        Bundle bundle = getArguments();
        drivers.setKey(bundle.getString("key"));

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference();

        binding.namaLengkap.setText(drivers.getKey());
        database.child("Drivers").child(drivers.getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.namaLengkap.setText(snapshot.child("nama").getValue().toString());
                binding.email.setText(snapshot.child("email").getValue().toString());
                binding.nomorHp.setText(snapshot.child("nomor_hp").getValue().toString());
                binding.platNomor.setText(snapshot.child("plat_nomor").getValue().toString());
                binding.nomorSim.setText(snapshot.child("nomor_sim").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.namaLengkap.setText(drivers.getNama());
        auth = FirebaseAuth.getInstance();
        log_out = binding.logout;
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent mainActivity = new Intent(getActivity(), LoginActivity.class);
                startActivity(mainActivity);
            }
        });
        return binding.getRoot();
    }



}
