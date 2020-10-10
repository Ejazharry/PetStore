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

import com.example.petstore.model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrderActivity extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_order);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        ordersList = findViewById(R.id.ordersList);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options = new FirebaseRecyclerOptions.Builder<AdminOrders>()
        .setQuery(ordersRef, AdminOrders.class).build();

        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder> adapter = new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrdersViewHolder adminOrdersViewHolder, final int i, @NonNull final AdminOrders adminOrders) {
                adminOrdersViewHolder.userName.setText("Name: " + adminOrders.getUserName());
                adminOrdersViewHolder.userPhone.setText("Phone: " + adminOrders.getPhone());
                adminOrdersViewHolder.userTotalPrice.setText("Total Price: " + adminOrders.getTotalAmount());
                adminOrdersViewHolder.userAddress.setText("Address: " + adminOrders.getAddress());
                adminOrdersViewHolder.userCity.setText("City: " + adminOrders.getCity());
                adminOrdersViewHolder.userDate.setText("Date: " + adminOrders.getDate() );
                adminOrdersViewHolder.userTime.setText("Time: " + adminOrders.getTime());
                adminOrdersViewHolder.ShowOrdersButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uId = getRef(i).getKey();
                        Intent intent = new Intent(AdminNewOrderActivity.this, AdminUserProducts.class);
                        intent.putExtra("uid",uId );
                        startActivity(intent);
                    }
                });

                adminOrdersViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Shipped",
                                        "Not Shipped"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrderActivity.this);
                        builder.setTitle("Shipment Details");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                               if(i == 0)
                               {
                                   String uId = getRef(i).getKey();
                                   RemoveOrder(uId);
                               }
                               else
                               {
                                   finish();
                               }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
              return new AdminOrdersViewHolder(view) ;
            };
        };

        ordersList.setAdapter(adapter);
        adapter.startListening();

    }



    public static class  AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {

        public TextView userName, userPhone, userTotalPrice, userDate, userAddress, userCity, userTime;
        public Button ShowOrdersButton;

        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.order_user_name);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDate = itemView.findViewById(R.id.order_date);
            userPhone = itemView.findViewById(R.id.order_phone_number);
            userAddress = itemView.findViewById(R.id.order_address);
            userCity = itemView.findViewById(R.id.order_city);
            userTime = itemView.findViewById(R.id.order_time);
            ShowOrdersButton = itemView.findViewById(R.id.showProducts);



        }
    }

    private void RemoveOrder(String uId)
    {
        ordersRef.child(uId).removeValue();
    }
}