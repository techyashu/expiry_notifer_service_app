package com.example.expirynotifier;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addItem extends AppCompatActivity {

    EditText item,edittext;
    private String UserID ="";
    private String dateee ="";
    DatabaseReference dbreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        item =(EditText)findViewById(R.id.iname);
        final Calendar myCalendar = Calendar.getInstance();
        edittext= (EditText) findViewById(R.id.datee);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        UserID =user.getUid();


        dbreference = FirebaseDatabase.getInstance().getReference().child("Users");

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateee = sdf.format(myCalendar.getTime());
                edittext.setText(dateee);
            }

        };



        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(addItem.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        if (dbreference == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
            dbreference = database.getReference();
            // ...
        }




    }


    @SuppressLint("SimpleDateFormat")
    public void addItemm(View view) {
        dbreference = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);
        final Map<String,Object> map=new HashMap<>();
        map.put("item",item.getText().toString());
        map.put("date",dateee.toString());
        dbreference.push().setValue(map);
        item.setText("");
        edittext.setText("");
        Date todayDat = new Date();
        String todayDate;
        todayDate = new SimpleDateFormat("MM/dd/yy  hh:mm:ss a").format(todayDat);
        Toast.makeText(getApplicationContext(),todayDate,Toast.LENGTH_LONG).show();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).addTag("ab")
                .build();
        WorkManager.getInstance().enqueue(workRequest);

    }

}