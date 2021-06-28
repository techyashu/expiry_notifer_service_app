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
import java.util.Random;
import java.util.concurrent.CountDownLatch;


public class MyWorker extends Worker {

    public MyWorker(@NotNull Context context,@NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    CountDownLatch  latch;
    private String UserID ="";

    @NonNull
    @Override
    public Result doWork() {
        latch = new CountDownLatch(1);


        //displayNotification("Expiry Notifier", "Item Added");
        managerFunc();

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        displayNotification("To be Expired", "Hey I finished my work");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            NotificationChannel channel = new NotificationChannel("expiry", "expiry", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "expiry")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.drawable.ice_cream);

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(m, notification.build());
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
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists())
                    latch.countDown();

                for (DataSnapshot data:dataSnapshot.getChildren()){

                    String key = data.getKey();
                    String name = data.child("item").getValue(String.class);
                    displayNotification(name, "Getting Expired Tomorrow");
                    latch.countDown();


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                latch.countDown();
            }
        });




    }
    

}