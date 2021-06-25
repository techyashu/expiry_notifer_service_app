package com.example.expirynotifier;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity
{
    TextView emailhome,uidhome,f;
    private EditText datt;
    String id = "";
    private Button bt;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        emailhome=(TextView)findViewById(R.id.email_home);
        uidhome=(TextView)findViewById(R.id.uidhome);

//        f=(TextView)findViewById(R.id.res);
//        datt =(EditText)findViewById(R.id.dat);

        bt=(Button)findViewById(R.id.add);

        id = getIntent().getStringExtra("uid");

        emailhome.setText(getIntent().getStringExtra("email"));
        uidhome.setText("UID :"+ getIntent().getStringExtra("uid"));

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,addItem.class);
                //intent.putExtra("uid",getIntent().getStringExtra("uid"));
                startActivity(intent);
            }
        });


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

    public void searchHere(View view) {
        processsearch(datt.getText().toString());
        //f.setText(datt.getText().toString());
        Toast.makeText(Dashboard.this, datt.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private void processsearch(String s)
    {

        DatabaseReference mDatabaseRef =FirebaseDatabase.getInstance().getReference("Users").child(id);

        Query query=mDatabaseRef.orderByChild("date").equalTo(s);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){


                    itemInfo models=data.getValue(itemInfo.class);
                    String itemm = models.getItemm();
                    String datee = models.getDatee();
                    //f.setText(itemm);
                    Toast.makeText(Dashboard.this, itemm, Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



//        FirebaseRecyclerOptions<model> options =
//                new FirebaseRecyclerOptions.Builder<model>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(id).orderByChild("date").equalTo(s), model.class)
//                        .build();

        //myadapter adapter = new myadapter(options);
        //myadapter.getDate()

    }


}