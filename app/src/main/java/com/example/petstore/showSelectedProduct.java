package com.example.petstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petstore.ViewHolder.petViewHolder;
import com.example.petstore.model.petsstore;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class showSelectedProduct extends AppCompatActivity {
    private String CategoryName;
    private RecyclerView searchList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_selected_product);

        CategoryName = getIntent().getExtras().get("category").toString();
        searchList = findViewById(R.id.categoryList);
        searchList.setLayoutManager(new LinearLayoutManager(showSelectedProduct.this));
    }
    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Pets");

        FirebaseRecyclerOptions<petsstore> options = new FirebaseRecyclerOptions.Builder<petsstore>()
                .setQuery(reference.orderByChild("category").startAt(CategoryName).endAt(CategoryName), petsstore.class).build();

        FirebaseRecyclerAdapter<petsstore, petViewHolder> adapter = new FirebaseRecyclerAdapter<petsstore, petViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull petViewHolder petViewHolder, int i, @NonNull final petsstore petsstore) {
                petViewHolder.textBreedName.setText(petsstore.getBreedname());
                petViewHolder.txtpetPrice.setText("Rs: " + petsstore.getPrice());
                Picasso.get().load(petsstore.getImage()).into(petViewHolder.imageView);

                petViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(showSelectedProduct.this, PetDetailsActivity.class);
                        intent.putExtra("pid",petsstore.getPid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public petViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pets_layout, parent, false);
                petViewHolder holder = new petViewHolder(view);
                return holder;
            }
        };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}