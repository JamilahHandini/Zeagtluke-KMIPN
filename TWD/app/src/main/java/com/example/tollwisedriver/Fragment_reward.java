package com.example.tollwisedriver;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tollwisedriver.databinding.FragmentRewardBinding;


public class Fragment_reward extends Fragment{


    private FragmentRewardBinding binding;
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
        diskon = bundle.getDouble("diskon");
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
        return binding.getRoot();
    }



}
