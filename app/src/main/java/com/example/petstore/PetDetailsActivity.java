package com.example.petstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.petstore.model.petsstore;
import com.example.petstore.prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PetDetailsActivity extends AppCompatActivity
{
private Button addToCartButton;
private ImageView petImage;
private ElegantNumberButton numberButton;
private TextView petPrice, petDescription, breedName;
private String petId = "", state = "Normal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        petId = getIntent().getStringExtra("pid");


        addToCartButton = (Button) findViewById(R.id.petaddtoCart);
        petImage = (ImageView) findViewById(R.id.petImageDetails);
        numberButton = (ElegantNumberButton) findViewById(R.id.numberButton);
        petPrice = (TextView) findViewById(R.id.petPriceDetail);
        petDescription = (TextView) findViewById(R.id.petDescriptionDetails);
        breedName = (TextView) findViewById(R.id.petBreedNameDetails) ;

        getProductDetails(petId);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(state.equals("Order Placed") || state.equals("Order Shipped"))
                {
                    Toast.makeText(PetDetailsActivity.this, "You can order once your order is delivered!!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    addingPetsToCartList();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        CheckOrderState();
    }

    private void addingPetsToCartList()


    {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate =currentdate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String , Object> cartMap = new HashMap<>();

        cartMap.put("pid", petId);
        cartMap.put("breedname", breedName.getText().toString());
        cartMap.put("price", petPrice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberButton.getNumber());
        cartMap.put("discount", "");

        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Pets").child(petId).updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone()).child("Pets").child(petId).updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                          if(task.isSuccessful())
                                          {
                                              Toast.makeText(PetDetailsActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();

                                              Intent intent = new Intent(PetDetailsActivity.this,HomeActivity.class);
                                              startActivity(intent);
                                          }
                                        }
                                    });
                        }
                    }
                });



    }

    private void getProductDetails(final String petId)
    {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Pets");

        productRef.child(petId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    petsstore pets =  snapshot.getValue(petsstore.class);

                    breedName.setText(pets.getBreedname());
                    petPrice.setText(pets.getPrice());
                    petDescription.setText(pets.getDescription());
                    Picasso.get().load(pets.getImage()).into(petImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CheckOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String shippingState = snapshot.child("State").getValue().toString();

                    if(shippingState.equals("shipped"))
                    {

                        state = "Order Shipped";



                    }
                    else if(shippingState.equals("Not Shipped"))
                    {
                        state = "Order Placed";

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}