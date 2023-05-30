package com.example.notlar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class NoteAdd extends AppCompatActivity {
    EditText notes, notTitle;
    Button kaydet, sil;
    FirebaseFirestore mFireStore;
    FirebaseAuth fireAuth;
    FirebaseUser fireUser;
    String txtNotes, txtnotTitle;
    HashMap<String, Object> hMap;
    MainActivity ma;
    String nts, nt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        mFireStore = FirebaseFirestore.getInstance();
        fireAuth = FirebaseAuth.getInstance();

        kaydet = findViewById(R.id.kaydet);
        sil = findViewById(R.id.sil);

        notTitle = findViewById(R.id.notBaslik);
        notes = findViewById(R.id.notIcerik);

        Intent intent = getIntent();
        nts = intent.getStringExtra("nts");
        nt = intent.getStringExtra("nt");

        if (nts != null && nt != null) {
            notTitle.setText(nt);
            notes.setText(nts);
        }
        ma = (MainActivity) getIntent().getSerializableExtra("MainActivity");



        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String silinecekBaslik = notTitle.getText().toString();

                // Başlığa göre belgeleri sorgula
                mFireStore.collection("Notlar")
                        .whereEqualTo("nt", silinecekBaslik)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                       document.getReference().delete();
                                    }
                                    Toast.makeText(NoteAdd.this, "Not Silindi", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(NoteAdd.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(NoteAdd.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNotes = notes.getText().toString();
                txtnotTitle = notTitle.getText().toString();

                fireUser = fireAuth.getCurrentUser();

                if (!txtNotes.isEmpty() && !txtnotTitle.isEmpty()) {
                    hMap = new HashMap<>();
                    hMap.put("nts", txtNotes);
                    hMap.put("nt", txtnotTitle);

                    mFireStore.collection("Notlar").document()
                            .set(hMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(NoteAdd.this, "Not Kaydedildi", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(NoteAdd.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    Intent intent = new Intent(NoteAdd.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NoteAdd.this, "Başlık ve Not alanları boş bırakılamaz", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
