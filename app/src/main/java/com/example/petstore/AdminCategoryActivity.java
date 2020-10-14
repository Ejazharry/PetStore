package com.example.petstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {
     private ImageView dogs, cats, fishes, birds, horse, rabbits, food, accessories, medicines;

     private Button LogoutBtn, CheckOrdersButton, EditProductBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        dogs = (ImageView) findViewById(R.id.dog);
        cats = (ImageView) findViewById(R.id.caticon);
        fishes = (ImageView ) findViewById(R.id.fishes);

        birds = (ImageView ) findViewById(R.id.birds);
        horse = (ImageView ) findViewById(R.id.horse);
        rabbits = (ImageView ) findViewById(R.id.rabbits);

        food = (ImageView ) findViewById(R.id.food);
        accessories = (ImageView ) findViewById(R.id.accessories);
        medicines = (ImageView ) findViewById(R.id.medicines);

        LogoutBtn = (Button) findViewById(R.id.AdminLogOutButton);
        CheckOrdersButton = (Button) findViewById(R.id.checkOrdersBtn);
        EditProductBtn = (Button) findViewById(R.id.AlterProductsButton);


        EditProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });



        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminNewOrderActivity.class);
                startActivity(intent);
            }
        });

        dogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category", "dogs");
                startActivity(intent);
            }
        });
        cats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category", "cats");
                startActivity(intent);
            }
        });
        fishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category", "fishes");
                startActivity(intent);
            }
        });




        birds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category", "birds");
                startActivity(intent);
            }
        });
        horse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category", "horse");
                startActivity(intent);
            }
        });
        rabbits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category", "Rabbits");
                startActivity(intent);
            }
        });





        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category", "food");
                startActivity(intent);
            }
        });
        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category", "accessories");
                startActivity(intent);
            }
        });
        medicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category", "medicines");
                startActivity(intent);
            }
        });

    }
}