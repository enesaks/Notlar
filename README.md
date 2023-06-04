# Notlar
 Notlar Uygulaması ![video](https://github.com/enesaks/Notlar/assets/98012557/7280440f-0745-4ad7-a36a-68653f33ce5f)
 (https://github.com/enesaks/Notlar/assets/98012557/1ffd47e0-1157-4458-ad81-0f80582ea5ae)

Giriş Kontrolu yapan ve üye kaydı tutan basit not uygulaması



#Giriş Kontorlü Kodları 

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

 #Üye Ekleme Kodları 
 
 
 
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
 
 
 
#Notların Kaydırılarak Eklendiği RecyclerView'ın Çalıştığı Fonksiyon 
 
 
 
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
 
 
 buna ek olarak RecyclerView'ın adapter kodları ;
 
 

    package com.example.notlar;

 

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final ItemClickListener itemClickListener;

    private List<MyData> dataList;





    public MyAdapter(List<MyData> dataList,ItemClickListener itemClickListener) {
        this.dataList = dataList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MyData data = dataList.get(position);
        holder.bindData(data);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView notes,noteTitle;

        public MyViewHolder(@NonNull View itemView,ItemClickListener itemClickListener) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.itemTitle);
            notes = itemView.findViewById(R.id.itemNotes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            itemClickListener.onItemClick(pos);
                        }
                    }
                }
            });
        }


        public void bindData(MyData data) {
            noteTitle.setText(data.getTxtTitle());
            notes.setText(data.getTxtNotes());
        }
    }


}
 
 
 
 #Notların FireBase'e Kayıt Edilmesi İçin Olan Kodalal 
 
 
 
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
 
 #Notların FireBase'den Silinmesi için Olan Kodlar
 
 
 
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
 
 
