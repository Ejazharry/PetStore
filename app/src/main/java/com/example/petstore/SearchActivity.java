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
import android.widget.Button;
import android.widget.EditText;

import com.example.petstore.ViewHolder.petViewHolder;
import com.example.petstore.model.petsstore;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchActivity extends AppCompatActivity {

    private Button SearchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputText = findViewById(R.id.search);
        SearchBtn = findViewById(R.id.SearchBtn);
        searchList = findViewById(R.id.searchList);
        searchList.setLayoutManager(new LinearLayoutManager(SearchActivity.this));


        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SearchInput = inputText.getText().toString();
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Pets");

        FirebaseRecyclerOptions<petsstore> options = new FirebaseRecyclerOptions.Builder<petsstore>()
                .setQuery(reference.orderByChild("breedname").startAt(SearchInput), petsstore.class).build();

        FirebaseRecyclerAdapter<petsstore, petViewHolder> adapter = new FirebaseRecyclerAdapter<petsstore, petViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull petViewHolder petViewHolder, int i, @NonNull final petsstore petsstore) {
                petViewHolder.textBreedName.setText(petsstore.getBreedname());
                petViewHolder.txtpetPrice.setText("Rs: " + petsstore.getPrice());
                Picasso.get().load(petsstore.getImage()).into(petViewHolder.imageView);

                petViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this, PetDetailsActivity.class);
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