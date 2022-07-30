package com.example.tollwisedriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tollwisedriver.databinding.ActivityLoginBinding;
import com.example.tollwisedriver.databinding.ActivityMainBinding;
import com.example.tollwisedriver.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private EditText email,password;
    private Drivers drivers;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private String mail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding =   ActivityLoginBinding.inflate(getLayoutInflater());

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

    }

    private void login(String email, String password){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    reload();
                }else{
                    Toast.makeText(getApplicationContext(), "Login gagal"
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
        drivers = new Drivers();
        Query query = database.child("Drivers").orderByChild("email").equalTo(email.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    binding.email.setText(childSnapshot.getKey());
                    drivers.setKey(binding.email.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("key", drivers.getKey());

                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void regist(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    public void go_login(View view) {
        if(email.length() > 0 && password.length() >0){
            login(email.getText().toString(), password.getText().toString());
        }else{
            Toast.makeText(getApplicationContext(), "Email dan Password tidak boleh kosong!"
                    , Toast.LENGTH_SHORT).show();
        }
    }
}