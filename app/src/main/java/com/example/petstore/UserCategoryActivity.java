package com.example.petstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class UserCategoryActivity extends AppCompatActivity {

    private ImageView dogs, cats, fishes, birds, horse, rabbits, food, accessories, medicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);

        dogs = (ImageView) findViewById(R.id.users_dog);
        cats = (ImageView) findViewById(R.id.users_caticon);
        fishes = (ImageView ) findViewById(R.id.users_fishes);

        birds = (ImageView ) findViewById(R.id.users_birds);
        horse = (ImageView ) findViewById(R.id.users_horse);
        rabbits = (ImageView ) findViewById(R.id.users_rabbits);

        food = (ImageView ) findViewById(R.id.users_food);
        accessories = (ImageView ) findViewById(R.id.users_accessories);
        medicines = (ImageView ) findViewById(R.id.users_medicines);







        dogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserCategoryActivity.this, showSelectedProduct.class);
                intent.putExtra("category", "dogs");
                startActivity(intent);
            }
        });
        cats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserCategoryActivity.this, showSelectedProduct.class);
                intent.putExtra("category", "cats");
                startActivity(intent);
            }
        });
        fishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserCategoryActivity.this, showSelectedProduct.class);
                intent.putExtra("category", "fishes");
                startActivity(intent);
            }
        });




        birds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserCategoryActivity.this, showSelectedProduct.class);
                intent.putExtra("category", "birds");
                startActivity(intent);
            }
        });
        horse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserCategoryActivity.this, showSelectedProduct.class);
                intent.putExtra("category", "horse");
                startActivity(intent);
            }
        });
        rabbits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserCategoryActivity.this, showSelectedProduct.class);
                intent.putExtra("category", "Rabbits");
                startActivity(intent);
            }
        });





        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserCategoryActivity.this, showSelectedProduct.class);
                intent.putExtra("category", "food");
                startActivity(intent);
            }
        });
        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserCategoryActivity.this, showSelectedProduct.class);
                intent.putExtra("category", "accessories");
                startActivity(intent);
            }
        });
        medicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserCategoryActivity.this, showSelectedProduct.class);
                intent.putExtra("category", "medicines");
                startActivity(intent);
            }
        });

    }
    }
