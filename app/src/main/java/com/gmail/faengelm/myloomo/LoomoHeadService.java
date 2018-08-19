package com.gmail.faengelm.myloomo;


import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.segway.robot.sdk.base.bind.ServiceBinder;
import com.segway.robot.sdk.locomotion.head.Head;

/**
 * Created by abr on 22.12.17.
 */

public class LoomoHeadService {

    private static final String TAG = "LoomoHeadService";

    private Head head = null;
    private Context context;
    private Handler timehandler;

    public static LoomoHeadService instance;

    public static LoomoHeadService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("LoomoHeadService instance not initialized yet");
        }
        return instance;
    }

    public LoomoHeadService(Context context) {
        timehandler = new Handler();
        this.context = context;
        initHead();
        this.instance = this;
    }

    public void restartService() {
        initHead();
    }

    public void move(int mode, float yawValue, float pitchValue) {
        Log.d(TAG, "actual world head position: " + this.head.getWorldYaw().getAngle() + ";" + this.head.getWorldPitch().getAngle());
        this.head.setMode(mode);
        if (mode == Head.MODE_SMOOTH_TACKING) {
            Log.d(TAG, "Moving world position to: " + yawValue + " / " + pitchValue);
            this.head.setWorldYaw(yawValue);
            this.head.setWorldPitch(pitchValue);
        } else if (mode == Head.MODE_ORIENTATION_LOCK) {
            Log.d(TAG, "Accelerating Head with: " + yawValue + " / " + pitchValue);
            this.head.setYawAngularVelocity(yawValue);
            this.head.setPitchAngularVelocity(pitchValue);
        } else {
            Log.e(TAG, "Skipping command, since received unknown mode: " + mode);
        }
    }


    private void initHead() {
        head = Head.getInstance();
        head.bindService(context, new ServiceBinder.BindStateListener() {
            @Override
            public void onBind() {
                Log.d(TAG, "Head bind successful");
            }

            @Override
            public void onUnbind(String reason) {
                Log.d(TAG, "Head bind failed");
            }
        });
    }

    public void disconnect() {
        this.head.unbindService();
    }
}
