package com.gmail.faengelm.myloomo;


import android.util.Log;

import com.segway.robot.sdk.locomotion.head.Head;

import static java.util.concurrent.TimeUnit.SECONDS;
/**
 * Created by abr on 22.12.17.
 * @FrankE on 06.19.2018
 * added Yaw lef, Yaw right and center with delays to test for "Come  here" code
 */

public class HeadCommand extends MessageCommand {

    private static final String TAG = "HeadCommand";

    public HeadCommand(String[] message) {
        super(message);
    }

    @Override
    public void execute() {
        float pitchValue = Float.valueOf(message[2]);
        float yawValue = Float.valueOf(message[3]);

        Log.i(TAG, "yawValue: " + yawValue);
        Log.i(TAG, "pitchValue: " + pitchValue);

        if (pitchValue > (3.15F) || pitchValue < (-(3.14F / 2))) {
            Log.e(TAG, "received pitchValue, which is not supported");
        }
        if (yawValue > (3.15F * 0.8) || yawValue < (-(3.15F * 0.8))) {
            Log.e(TAG, "received yawValue, which is not supported");
        }

        try {
            int mode = Head.MODE_SMOOTH_TACKING;
            if (message[1].equalsIgnoreCase("orientation")) {
                mode = Head.MODE_ORIENTATION_LOCK;
            }
            //    LoomoHeadService.getInstance().move(mode, yawValue, pitchValue);

            yawValue= 2.0F;
            pitchValue=0;
            LoomoHeadService.getInstance().move(mode, yawValue, pitchValue);
            SECONDS.sleep(1);

            yawValue= 0;
            LoomoHeadService.getInstance().move(mode, yawValue, pitchValue);
            SECONDS.sleep(1);

            yawValue= -.0F;
            LoomoHeadService.getInstance().move(mode, yawValue, pitchValue);
            SECONDS.sleep(1);

            yawValue= 0;
            LoomoHeadService.getInstance().move(mode, yawValue, pitchValue);


        } catch (Exception e) {
            Log.e(TAG, "Got Exception during HEAD command", e);
        }

    }
}

