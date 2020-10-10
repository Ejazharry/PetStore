package com.example.petstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petstore.ViewHolder.CartViewHolder;
import com.example.petstore.model.Cart;
import com.example.petstore.prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button ProceedButton;
    private TextView totalAmount, txtMsg1;
    private int FinalTotalPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ProceedButton = (Button) findViewById(R.id.proceedButton);
        totalAmount = (TextView) findViewById(R.id.totalPrice);
        txtMsg1 = (TextView) findViewById(R.id.msg1);

        ProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                totalAmount.setText("Total: Rs "+ String.valueOf(FinalTotalPrice) + "/-");
                Intent intent = new Intent(CartActivity.this,FinalOrderActivity.class);
                intent.putExtra("Total Price", String.valueOf(FinalTotalPrice));
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {


        super.onStart();
        CheckOrderState();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Pets"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {
                 cartViewHolder.txtPetQuantity.setText( "Quantity: "+cart.getQuantity());
                cartViewHolder.txtPetPrice.setText("Price: " +cart.getPrice());
                cartViewHolder.txtBreedName.setText("Breed: "+ cart.getBreedname());

                int oneTypePetPrice = ((Integer.valueOf(cart.getPrice()))) * Integer.valueOf(cart.getQuantity());
                FinalTotalPrice = FinalTotalPrice + oneTypePetPrice;
                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]
                                {
                                  "Edit",
                                  "Remove"
                                };

                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("More");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0)
                                {
                                    Intent intent = new Intent(CartActivity.this, PetDetailsActivity.class);
                                    intent.putExtra("pid", cart.getPid());
                                    startActivity(intent);

                                }
                                if(which == 1)
                                {
                                    cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Pets").child(cart.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful())
                                             {
                                                 Toast.makeText(CartActivity.this, "Item Removed", Toast.LENGTH_SHORT).show();
                                             }
                                        }
                                    });
                                }
                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitemslayout, parent, false);
            CartViewHolder holder = new CartViewHolder(view);
            return holder;

            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();

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
                 String UserName = snapshot.child("UserName").getValue().toString();
                 String totalPrice = snapshot.child("TotalAmount").getValue().toString();

                 if(shippingState.equals("shipped"))
                 {


                     recyclerView.setVisibility(View.GONE);
                     txtMsg1.setVisibility(View.VISIBLE);
                     txtMsg1.setText("Dear " + UserName + ", your order is shipping. \n Please pay Rs: " + totalPrice + " at the time of delivery.");
                     totalAmount.setText("Order Details");
                     ProceedButton.setVisibility(View.GONE);
                 }
                 else if(shippingState.equals("Not Shipped"))
                 {
                     recyclerView.setVisibility(View.GONE);
                     txtMsg1.setVisibility(View.VISIBLE);
                     //totalAmount.setText("Dear " + UserName + ", your order is in progress. Please Wait For Conformation.");
                     totalAmount.setText("Order Details");
                     ProceedButton.setVisibility(View.GONE);

                 }

             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}