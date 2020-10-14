package com.example.petstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminEditProductsActivity extends AppCompatActivity {

    private EditText EditBreedName, EditPrice, EditDescription;
    private Button SaveEdittedProduct, DeleteEditButton;
    private ImageView imageView;
    private String petId = "";
    private DatabaseReference productsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_products);
        petId = getIntent().getStringExtra("pid");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Pets").child(petId);

        EditBreedName = (EditText) findViewById(R.id.Edit_breed_name);
        EditPrice = (EditText) findViewById(R.id.Edit_pet_price);
        EditDescription = (EditText) findViewById(R.id.Edit_pet_Description);

        SaveEdittedProduct = (Button) findViewById(R.id.Edit_Pet_Save_Button);
        DeleteEditButton = findViewById(R.id.Edit_Pet_Delete_Button);

        imageView = (ImageView) findViewById(R.id.Edit_pet_image);


        displayProductInformation();

        SaveEdittedProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        DeleteEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });



    }

    private void deleteProduct()
    {
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Intent intent  = new Intent(AdminEditProductsActivity.this, AdminCategoryActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(AdminEditProductsActivity.this, "Product Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveChanges()
    {
        String Editedbreedname = EditBreedName.getText().toString();
        String EditedPrice = EditPrice.getText().toString();
        String EditedDescription = EditDescription.getText().toString();

        if(Editedbreedname.equals(""))
        {
            Toast.makeText(this, "Please Enter Breed Name", Toast.LENGTH_SHORT).show();
        }
        else if(EditedPrice.equals(""))
        {
            Toast.makeText(this, "Please Enter Price", Toast.LENGTH_SHORT).show();
        }
        else if(EditedDescription.equals(""))
        {
            Toast.makeText(this, "Please Enter Description", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", petId);
            productMap.put("description", EditedDescription);
            productMap.put("breedname", Editedbreedname);
            productMap.put("price", EditedPrice);

            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  if(task.isSuccessful())
                  {
                      Toast.makeText(AdminEditProductsActivity.this, "Changed Successfully", Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(AdminEditProductsActivity.this,AdminCategoryActivity.class);
                      startActivity(intent);
                      finish();
                  }
                  else {
                      Toast.makeText(AdminEditProductsActivity.this, "Error! Try Again", Toast.LENGTH_SHORT).show();
                  }
                }
            });
        }
    }

    private void displayProductInformation() {

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    String breedName = snapshot.child("breedname").getValue().toString();
                    String Price = snapshot.child("price").getValue().toString();
                    String PDescription = snapshot.child("description").getValue().toString();
                    String PImage = snapshot.child("image").getValue().toString();

                    EditBreedName.setText(breedName);
                    EditPrice.setText(Price);
                    EditDescription.setText(PDescription);
                    Picasso.get().load(PImage).into(imageView);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}