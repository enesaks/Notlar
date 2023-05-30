package com.example.notlar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    Button btnLogin,btnRegister;
    EditText edtEmail,edtPassaword;
    String passaword,email;
    FirebaseAuth fireAuth;
    FirebaseUser fireUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        edtPassaword = findViewById(R.id.edtPassaword);
        edtEmail = findViewById(R.id.edtEmail);

        fireAuth = FirebaseAuth.getInstance();

        fireAuth.getCurrentUser();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passaword = edtPassaword.getText().toString();
                email = edtEmail.getText().toString();
                if (!TextUtils.isEmpty(passaword) && !TextUtils.isEmpty(email)){
                    fireAuth.signInWithEmailAndPassword(email,passaword).
                            addOnSuccessListener(LoginPage.this, new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    fireUser = fireAuth.getCurrentUser();
                                    Toast.makeText(LoginPage.this, "Giriş BAşarılı", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginPage.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(LoginPage.this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    Toast.makeText(LoginPage.this, "Email ve Şifre Boş Olamaz!", Toast.LENGTH_SHORT).show();
                }


            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this,RegisterPage.class);
                    startActivity(intent);
            }
        });


    }
}