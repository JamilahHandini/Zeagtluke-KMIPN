package com.example.tollwisedriver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tollwisedriver.databinding.FragmentPelanggaranBinding;
import com.example.tollwisedriver.databinding.FragmentRegisterBinding;

public class Fragment_pelanggaran extends Fragment {

    private FragmentPelanggaranBinding binding;

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
        binding = FragmentPelanggaranBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
