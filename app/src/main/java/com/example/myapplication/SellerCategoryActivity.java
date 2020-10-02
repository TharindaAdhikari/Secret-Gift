package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SellerCategoryActivity extends AppCompatActivity {

    private ImageView clothes, chocolate, cake, freshFlowers;
    private ImageView toys, perfume, watch, shoes;
    private ImageView mobilePhones;
    private ImageView ring,bracelet,mug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_category);

        clothes = (ImageView) findViewById(R.id.clothes);
        chocolate = (ImageView) findViewById(R.id.choco);
        cake = (ImageView) findViewById(R.id.cake);
        freshFlowers = (ImageView) findViewById(R.id.fresh_flowers);

        toys = (ImageView) findViewById(R.id.toy);
        perfume = (ImageView) findViewById(R.id.perfume);
        watch = (ImageView) findViewById(R.id.watch);
        shoes = (ImageView) findViewById(R.id.shoes);

        mobilePhones = (ImageView) findViewById(R.id.mobile_phones);

        ring = (ImageView) findViewById(R.id.ring);
        bracelet = (ImageView) findViewById(R.id.bracelet);
        mug = (ImageView) findViewById(R.id.mug);


        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryActivity.this, SellerAddProductActivity.class);
                intent.putExtra("category", "clothes");
                startActivity(intent);
            }
        });

        chocolate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryActivity.this, SellerAddProductActivity.class);
                intent.putExtra("category", "chockolate");
                startActivity(intent);
            }
        });

        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryActivity.this, SellerAddProductActivity.class);
                intent.putExtra("category", "cake");
                startActivity(intent);
            }
        });

        freshFlowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryActivity.this, SellerAddProductActivity.class);
                intent.putExtra("category", "freshFlowers");
                startActivity(intent);
            }
        });

        toys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryActivity.this, SellerAddProductActivity.class);
                intent.putExtra("category", "Toys");
                startActivity(intent);
            }
        });

        perfume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryActivity.this, SellerAddProductActivity.class);
                intent.putExtra("category", "Perfume");
                startActivity(intent);
            }
        });

        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryActivity.this, SellerAddProductActivity.class);
                intent.putExtra("category", "watch");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryActivity.this, SellerAddProductActivity.class);
                intent.putExtra("category", "shoes");
                startActivity(intent);
            }
        });

        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryActivity.this, SellerAddProductActivity.class);
                intent.putExtra("category", "mobilePhones");
                startActivity(intent);
            }
        });

        ring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryActivity.this, SellerAddProductActivity.class);
                intent.putExtra("category", "Rings");
                startActivity(intent);
            }
        });

        bracelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryActivity.this, SellerAddProductActivity.class);
                intent.putExtra("category", "Bracelet");
                startActivity(intent);
            }
        });

        mug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryActivity.this, SellerAddProductActivity.class);
                intent.putExtra("category", "Mug");
                startActivity(intent);
            }
        });

    }
}