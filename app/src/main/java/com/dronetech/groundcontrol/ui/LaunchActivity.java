package com.dronetech.groundcontrol.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dronetech.groundcontrol.R;
import com.dronetech.groundcontrol.util.DJIApiUtil;
import com.dronetech.groundcontrol.util.GifView;

import butterknife.Bind;
import butterknife.ButterKnife;
import dji.sdk.base.DJIBaseProduct;

/**
 * Created by Nicolaas on 7/2/16.
 */
public class LaunchActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();

    @Bind(R.id.statusTextView)
    TextView statusText;
    @Bind(R.id.enterFpvButton)
    Button enterFpvButton;
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

        enterFpvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LaunchActivity.this, MainActivity.class);
                LaunchActivity.this.startActivity(myIntent);
            }
        });

    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
        updateStatus();
    }

    private void updateStatus() {
        if (statusText == null) return;
        DJIBaseProduct product = GroundControlApplication.getProductInstance();
        statusText.setText(djiApiUtil.getAircraftConnectionStatus(product));
    }


}
