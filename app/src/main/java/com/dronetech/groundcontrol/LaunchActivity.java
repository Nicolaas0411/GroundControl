package com.dronetech.groundcontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dji.sdk.base.DJIBaseProduct;
import util.DJIApiUtil;

/**
 * Created by Nicolaas on 7/2/16.
 */
public class LaunchActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();

    TextView statusText;
    Button enterFpvButton;
    DJIApiUtil djiApiUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);
        djiApiUtil = new DJIApiUtil();
        initUI();

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

    private void initUI() {
        statusText = (TextView) findViewById(R.id.statusTextView);
        enterFpvButton = (Button) findViewById(R.id.enterFpvButton);
    }

}
