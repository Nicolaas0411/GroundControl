package com.dronetech.groundcontrol.ui;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.dronetech.groundcontrol.R;
import com.dronetech.groundcontrol.util.DJIApiUtil;
import com.dronetech.groundcontrol.util.ImageAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import dji.sdk.base.DJIBaseProduct;

/**
 * Created by Nicolaas on 7/2/16.
 */
public class LaunchActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();

    @Bind(R.id.enterFpvButton)
    Button enterFpvButton;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    DJIApiUtil djiApiUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);
        ButterKnife.bind(this);
        djiApiUtil = new DJIApiUtil();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
                            Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW,
                            Manifest.permission.READ_PHONE_STATE,
                    }
                    , 1);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);

        enterFpvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LaunchActivity.this, MainActivity.class);
                LaunchActivity.this.startActivity(myIntent);
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(GroundControlApplication.FLAG_CONNECTION_CHANGE);
        registerReceiver(mReceiver, filter);
    }

    protected BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateStatus();
        }
    };

    @Override
    public void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
        updateStatus();
    }

    private void updateStatus() {
        if (enterFpvButton == null) return;
        DJIBaseProduct product = GroundControlApplication.getProductInstance();
        enterFpvButton.setText(djiApiUtil.getAircraftConnectionStatus(product));
        if (product != null && product.isConnected()){
            enterFpvButton.setBackgroundColor(Color.parseColor("#228B22"));
            enterFpvButton.setEnabled(true);
            enterFpvButton.setAlpha(1.0f);
        } else {
            enterFpvButton.setBackgroundColor(Color.parseColor("#FF0000"));
            enterFpvButton.setEnabled(true);  //FixMe: Set false
            enterFpvButton.setAlpha(0.5f);
        }
    }


}
