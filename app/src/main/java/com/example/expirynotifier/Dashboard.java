package com.example.expirynotifier;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Dashboard extends AppCompatActivity
{
    //TextView emailhome,uidhome;
    String id = "";
    private Button bt;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //emailhome=(TextView)findViewById(R.id.email_home);
        //uidhome=(TextView)findViewById(R.id.uidhome);



        bt=(Button)findViewById(R.id.add);

        id = getIntent().getStringExtra("uid");

        //emailhome.setText(getIntent().getStringExtra("email"));
        //uidhome.setText("UID :"+ getIntent().getStringExtra("uid"));

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,addItem.class);
                //intent.putExtra("uid",getIntent().getStringExtra("uid"));
                startActivity(intent);
            }
        });


                final PeriodicWorkRequest myWorkBuilder =
                        new PeriodicWorkRequest.Builder(MyWorker.class, 15,
                                TimeUnit.MINUTES).addTag("ashu").build();

                WorkManager.getInstance().enqueueUniquePeriodicWork("ashu", ExistingPeriodicWorkPolicy.KEEP, myWorkBuilder);


    }

    public void logoutprocess(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Dashboard.this,MainActivity.class));
    }



    public void yourItem(View view) {

        Intent intent=new Intent(Dashboard.this,yourItems.class);
        intent.putExtra("uid",getIntent().getStringExtra("uid"));
        startActivity(intent);

        //startActivity(new Intent(Dashboard.this, yourItems.class));
    }



    @SuppressLint("SimpleDateFormat")
    public void searchHere(View view) {
        Date todayDat = new Date();
        String todayDate;
        todayDate = new SimpleDateFormat("MM/dd/yy").format(todayDat);
        processsearch(todayDate);
        //Toast.makeText(getApplicationContext(),todayDate,Toast.LENGTH_LONG).show();
        //processsearch(datt.getText().toString());
        //f.setText(datt.getText().toString());
        //Toast.makeText(Dashboard.this, datt.getText().toString(), Toast.LENGTH_SHORT).show();
    }


    private void processsearch(String s)
    {

        DatabaseReference mDatabaseRef =FirebaseDatabase.getInstance().getReference("Users").child(id);

        Query query=mDatabaseRef.orderByChild("date").equalTo(s);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){

                    String key = data.getKey();
                    String name = data.child("item").getValue(String.class);
                    Toast.makeText(Dashboard.this, name, Toast.LENGTH_SHORT).show();
                    //f.setText(name);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


}