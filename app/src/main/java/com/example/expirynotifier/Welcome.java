package com.example.expirynotifier;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @SuppressLint("BatteryLife")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        Window window = Welcome.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//
//// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        }

// finally change the color
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.setStatusBarColor(ContextCompat.getColor(Welcome.this,R.color.back));
//        }


        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);





//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            String packageName = getApplicationContext().getPackageName();
//            PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
//            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
//                Intent intent = new Intent();
//                intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                intent.setData(Uri.parse("package:" + packageName));
//                getApplicationContext().startActivity(intent);
//            }
//        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(this)) {
//                if ("xiaomi".equals(Build.MANUFACTURER.toLowerCase(Locale.ROOT))) {
//                    final Intent intent =new Intent("miui.intent.action.APP_PERM_EDITOR");
//                    intent.setClassName("com.miui.securitycenter",
//                            "com.miui.permcenter.permissions.PermissionsEditorActivity");
//                    intent.putExtra("extra_pkgname", getPackageName());
//                    new AlertDialog.Builder(this)
//                            .setTitle("Please Enable the additional permissions")
//                            .setMessage("You will not receive notifications while the app is in background if you disable these permissions")
//                            .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    startActivity(intent);
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_info)
//                            .setCancelable(false)
//                            .show();
//                }
//            }
//        }

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