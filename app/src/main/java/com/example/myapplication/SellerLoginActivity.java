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

import com.example.myapplication.Model.Sellers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SellerLoginActivity extends AppCompatActivity {

    private EditText sInputName, sInputPassword;
    private Button sLoginButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Sellers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        sLoginButton = (Button) findViewById(R.id.slogin_btn);
        sInputPassword = (EditText) findViewById(R.id.slogin_password_input);
        sInputName = (EditText) findViewById(R.id.slogin_name_input);
        loadingBar = new ProgressDialog(this);

        sLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }

    private void LoginUser()
    {
        String name = sInputName.getText().toString();
        String password = sInputPassword.getText().toString();

        if(TextUtils.isEmpty(name)){
            sInputName.setError("Name Required");
            Toast.makeText(this,"Please Enter Your User Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            sInputPassword.setError("Please Enter Strong Password");
            Toast.makeText(this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please Wait, while We Are Checking The Credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(name, password);
        }
    }

    private void AllowAccessToAccount(final String name, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(name).exists())
                {
                    Sellers sellersData =dataSnapshot.child(parentDbName).child(name).getValue(Sellers.class);

                    if (sellersData.getName().equals(name))
                    {
                        if (sellersData.getPassword().equals(password))
                        {
                            Toast.makeText(SellerLoginActivity.this, "Logging Successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(SellerLoginActivity.this,"Password is Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(SellerLoginActivity.this, "Account With This" + name + "User Name Do Not Exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}