package com.example.tollwisedriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tollwisedriver.databinding.ActivityLoginBinding;
import com.example.tollwisedriver.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.Charset;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private EditText nama, nomor_hp, nomor_e_toll, nomor_sim, plat_nomor, email,password;
    private Drivers drivers;
    private DatabaseReference database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        nama = findViewById(R.id.nama_lengkap);
        nomor_hp = findViewById(R.id.nomor_hp);
        nomor_e_toll = findViewById(R.id.nomor_etoll);
        nomor_sim = findViewById(R.id.nomor_sim);
        plat_nomor = findViewById(R.id.plat_nomor);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();


    }

    private void register(String nama, String nomor_hp,
                          String nomor_e_toll, String nomor_sim,
                          String plat_nomor,String email, String password){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                            +"lmnopqrstuvwxyz";
                    Random rnd = new Random();
                    StringBuilder key = new StringBuilder(4);
                    for (int i = 0; i < 4; i++)
                        key.append(chars.charAt(rnd.nextInt(chars.length())));

                    if(user != null){
                        E_tolls e_toll = new E_tolls(nomor_e_toll,null,0);
                        database.child("Drivers").push().setValue(new Drivers(
                               e_toll, nama, nomor_hp, nomor_e_toll, nomor_sim,
                                plat_nomor,email, password)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Register berhasil"
                                        , Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Data belum ditambahkan ke database"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        });

                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nama)
                                .build();
                        user.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });

                    }else{
                        Toast.makeText(getApplicationContext(), "Register gagal"
                                , Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage()
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }


    private void reload() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void go_regist(View view) {

        if(email.length() > 0 && password.length() >0
                && nama.length() >0 && nomor_hp.length() >0
                && nomor_sim.length() >0 && nomor_e_toll.length() >0
                && plat_nomor.length() >0){

            register(nama.getText().toString(), nomor_hp.getText().toString(),
                    nomor_e_toll.getText().toString(), nomor_sim.getText().toString(),
                    plat_nomor.getText().toString(), email.getText().toString(),
                    password.getText().toString());


        }else{
            Toast.makeText(getApplicationContext(), "Data tidak boleh kosong!"
                    , Toast.LENGTH_SHORT).show();
        }
    }

    public void go_login(View view) {
        finish();
    }
}