package com.gmail.faengelm.myloomo;


import android.util.Log;

import static java.util.concurrent.TimeUnit.SECONDS;


/**
 * Created by abr on 22.12.17.
 * faengelm 06/11/2018  slowed slow base
 * float newSpeed = 0.3f * Float.valueOf(message[1]);
 */

public class RawMoveCommand extends MessageCommand {

    private static final String TAG = "RawMoveCommand";

    public RawMoveCommand(String[] message) {
        super(message);
    }

    @Override
    public void execute() {
        Log.i(TAG, "move speed: " + message[1]);
        Log.i(TAG, "move angle: " + message[2]);



        try {
            float newSpeed = 0.3f * Float.valueOf(message[1]); // was 1.0f
            float newAngle = Float.valueOf(message[2]);


            newSpeed = 0.4F;
            newAngle = 0.4F;
            //    LoomoBaseService.getInstance().move(newSpeed, newAngle);
            SECONDS.sleep(1);

            newSpeed = 0.0F;
            newAngle = 0.0F;
            //    LoomoBaseService.getInstance().move(newSpeed, newAngle);
            SECONDS.sleep(1);

            newSpeed = -0.4F;
            newAngle = -0.4F;
            //   LoomoBaseService.getInstance().move(newSpeed, newAngle);
            SECONDS.sleep(1);




        } catch (Exception e) {
            Log.e(TAG, "Got Exception during HEAD command", e);
        }
    }
}
