package com.example.petstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petstore.prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class FinalOrderActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, cityEditText, addressEditText;
    private Button confirmOrder;
    private String totalAmount = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order);
        totalAmount = getIntent().getStringExtra("Total Price");
        confirmOrder = (Button) findViewById(R.id.deliveryOrder);
        nameEditText = (EditText) findViewById(R.id.deliveryName);
        phoneEditText = (EditText) findViewById(R.id.deliveryPhone);
        addressEditText = (EditText) findViewById(R.id.deliveryAddress);
        cityEditText = (EditText) findViewById(R.id.deliveryCity);

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckDeliveryDetails();
            }
        });




    }

    private void CheckDeliveryDetails()
    {

        if(TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(this, "Name Is Required!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Phone Is Required!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Address Is Required!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(this, "City Is Required!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            confirmUserOrder();
        }
    }

    private void confirmUserOrder()
    {

        final String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate =currentdate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());
        HashMap<String, Object> ordersMap = new HashMap<>();

        ordersMap.put("TotalAmount", totalAmount);
        ordersMap.put("UserName", nameEditText.getText().toString());
        ordersMap.put("Phone", phoneEditText.getText().toString());
        ordersMap.put("Address", addressEditText.getText().toString());
        ordersMap.put("City", cityEditText.getText().toString());
        ordersMap.put("Date", saveCurrentDate);
        ordersMap.put("Time", saveCurrentTime);
        ordersMap.put("State", "Not Shipped");

        orderRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(Prevalent.currentOnlineUser.getPhone()).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {

                                        Toast.makeText(FinalOrderActivity.this, "Hurrey !! Your Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(FinalOrderActivity.this,HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });


    }
}