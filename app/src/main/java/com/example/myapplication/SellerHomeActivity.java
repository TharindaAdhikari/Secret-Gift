package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Products;
import com.example.myapplication.ViewHolder.ItemViewHolder;
import com.example.myapplication.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.paperdb.Paper;

public class SellerHomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unverifiedProductRef;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   // mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_add:
                    Intent intent = new Intent(SellerHomeActivity.this, SellerCategoryActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_logout:
                    final FirebaseAuth mAuth;
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();

                    Intent intentlo = new Intent(SellerHomeActivity.this, MainActivity.class);
                    intentlo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentlo);
                    finish();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        //BottomNavigationView navView = findViewById(R.id.nav_view);

        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.replace(R.id.content, FirstFragment.newInstance("What","Ever"));
        //transaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        unverifiedProductRef = FirebaseDatabase.getInstance().getReference().child("Products");


        recyclerView = findViewById(R.id.seller_home_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(unverifiedProductRef.orderByChild("sid")
                .equalTo("1234"),Products.class).build();

        FirebaseRecyclerAdapter<Products, ItemViewHolder>adapter = new FirebaseRecyclerAdapter<Products, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final Products model) {
                holder.txtProductName.setText(model.getPname());
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductStatus.setText("State :" + model.getProductState());
                holder.txtProductPrice.setText("Price = Rs." + model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String productID = model.getPid();

                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(SellerHomeActivity.this);
                        builder.setTitle("Do you want to Delete this Product. Are you Sure?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {
                                if (position == 0){
                                    deleteProduct(productID);
                                }
                                if (position == 1){

                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view, parent, false);
                ItemViewHolder holder = new ItemViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void deleteProduct(String productID) {
        unverifiedProductRef.child(productID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SellerHomeActivity.this, "That item has been Deleted Successfully..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}