package com.example.expirynotifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Handler handler = new Handler();
        if(currentUser!=null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(Welcome.this,Dashboard.class);
                    intent.putExtra("email",mAuth.getCurrentUser().getEmail());
                    intent.putExtra("uid",mAuth.getCurrentUser().getUid());
                    startActivity(intent);
                    Welcome.this.finish();
                }
            }, 2500);
        }
        else
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent in = new Intent(Welcome.this,MainActivity.class);
                    startActivity(in);
                    Welcome.this.finish();
                }
            }, 2500);
        }

    }


}