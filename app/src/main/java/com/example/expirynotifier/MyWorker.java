package com.example.expirynotifier;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyWorker extends Worker {

    public MyWorker(@NotNull Context context,@NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private String UserID ="";

    @NonNull
    @Override
    public Result doWork() {


        //displayNotification("Expiry Notifier", "Item Added");
        managerFunc();

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        displayNotification("To be Expired", "Hey I finished my work");

        return Result.success();
    }

    @SuppressLint("SimpleDateFormat")
    private void managerFunc() {
        Date todayDat = new Date();
        String todayDate;
        todayDate = new SimpleDateFormat("MM/dd/yy").format(todayDat);
        processsearch(todayDate);
    }


    private void displayNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
    }

    private void processsearch(String s)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        UserID =user.getUid();

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(UserID);

        Query query=mDatabaseRef.orderByChild("date").equalTo(s);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){

                    String key = data.getKey();
                    String name = data.child("item").getValue(String.class);
                    displayNotification("Expiry Notifier", "Item Added");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
    

}