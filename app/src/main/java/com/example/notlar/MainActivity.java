package com.example.notlar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.search.SearchBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener {


    FloatingActionButton add_note;

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<MyData> dataList;

    private FirebaseFirestore firestore;
    FirebaseAuth fireAuth;
    FirebaseUser fireUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_note = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerVieww();


        fireAuth = FirebaseAuth.getInstance();
        fireUser = fireAuth.getCurrentUser();


        add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteAdd.class);
                startActivity(intent);
            }
        });

    }

    public void recyclerVieww() {
        dataList = new ArrayList<>();
        adapter = new MyAdapter(dataList, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("Notlar")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            String notesTitle = document.getString("nt");
                            String notes = document.getString("nts");
                            MyData data = new MyData(notes, notesTitle);
                            dataList.add(data);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, NoteAdd.class);
        intent.putExtra("nt", dataList.get(position).getTxtTitle());
        intent.putExtra("nts", dataList.get(position).getTxtNotes());
        startActivity(intent);
    }
}
