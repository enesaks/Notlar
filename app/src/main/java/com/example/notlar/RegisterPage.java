package com.example.notlar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class RegisterPage extends AppCompatActivity {
    Button btnRegister,btnLoginPage;
    EditText edtPassaword,edtEmail;
    String passaword,email;
    private FirebaseAuth fireauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        btnLoginPage = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        edtPassaword = findViewById(R.id.edtPassaword);
        edtEmail = findViewById(R.id.edtEmail);

        fireauth = FirebaseAuth.getInstance();

        btnLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPage.this,LoginPage.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passaword = edtPassaword.getText().toString();
                email = edtEmail.getText().toString();

                if (!TextUtils.isEmpty(passaword) && !TextUtils.isEmpty(email)){
                    fireauth.createUserWithEmailAndPassword(email,passaword).
                            addOnCompleteListener(RegisterPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText( RegisterPage.this,"Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterPage.this,LoginPage.class);
                                        startActivity(intent);
                                    }
                                    else
                                        Toast.makeText(RegisterPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }else{
                    Toast.makeText(RegisterPage.this, "Lütfen Boşlukları Doldurunuz!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}