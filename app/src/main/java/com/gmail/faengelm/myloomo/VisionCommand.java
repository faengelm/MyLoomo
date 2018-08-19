package com.gmail.faengelm.myloomo;



/**
 * Created by mss on 22.12.17.
 */


import android.util.Log;

import com.segway.robot.sdk.baseconnectivity.MessageConnection;


public class VisionCommand extends MessageCommand {

    private MessageConnection messageConnection;

    private static String TAG = "VisionCommand";

    public VisionCommand(String[] message) {
        super(message);
        this.messageConnection = LoomoConnectivityService.getInstance().getMessageConnection();
    }

    @Override
    public void execute() {
        if (message[1].equals("start")) {
            Log.d(TAG, "received vision start");
            LoomoVisionService.getInstance().startTransferringImageStream(messageConnection);
        } else if (message[1].equals("stop")) {
            Log.d(TAG, "received vision stop");
            LoomoVisionService.getInstance().stopTransferringImageStream();
        }

    }
}
