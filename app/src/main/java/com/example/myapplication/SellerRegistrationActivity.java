package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SellerRegistrationActivity extends AppCompatActivity {

    private Button sCreateAccountButton;
    private EditText sInputName, sInputPhoneNumber, sInputPassword, sInputEmail, sInputAddress;
    private ProgressDialog loadingBar;
    private Button sellerLoginBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);

        sCreateAccountButton = (Button) findViewById(R.id.sregister_btn);
        sInputName = (EditText) findViewById(R.id.sregister_username_input);
        sInputPassword = (EditText) findViewById(R.id.sregister_password_input);
        sInputPhoneNumber = (EditText) findViewById(R.id.sregister_phone_number_input);
        sInputEmail = (EditText) findViewById(R.id.sregister_email_input);
        sInputAddress = (EditText) findViewById(R.id.sregister_address_input);
        loadingBar = new ProgressDialog(this);
        sellerLoginBegin = findViewById(R.id.main_seller_login_btn);

        sCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });

        sellerLoginBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void CreateAccount() {
        String name = sInputName.getText().toString();
        String phone = sInputPhoneNumber.getText().toString();
        String password = sInputPassword.getText().toString();
        String email = sInputEmail.getText().toString();
        String address = sInputAddress.getText().toString();

        if(TextUtils.isEmpty(name)){
            sInputName.setError("Name Required");
            Toast.makeText(this,"Please Enter your name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){
            sInputPhoneNumber.setError("Please Enter Valid Phone Number");
            Toast.makeText(this,"Please Enter your phone number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            sInputPassword.setError("Please Enter Strong Password");
            Toast.makeText(this,"Please Enter your password",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){
            sInputEmail.setError("Email Required");
            Toast.makeText(this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(address)){
            sInputAddress.setError("Address Required");
            Toast.makeText(this,"Please Enter your Address",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while We Are Checking The Credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateName(name, phone, password, email , address);
        }
    }

    private void ValidateName(final String name, final String phone, final String password , final String email , final String address) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Sellers").child(name).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);
                    userdataMap.put("email", email);
                    userdataMap.put("address", address);


                    RootRef.child("Sellers").child(name).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SellerRegistrationActivity.this, "Congratulations, Your Account Has Been Created", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                loadingBar.dismiss();
                                Toast.makeText(SellerRegistrationActivity.this, "Network Error: Please try again after Few time", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(SellerRegistrationActivity.this, "Your" + name + "Already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(SellerRegistrationActivity.this, "Please Try Again, Using Another Name", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SellerRegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}