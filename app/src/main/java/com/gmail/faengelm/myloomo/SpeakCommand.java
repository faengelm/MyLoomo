package com.gmail.faengelm.myloomo;



/**
 * Created by abr on 22.12.17.
 */

public  class SpeakCommand extends MessageCommand {

    private static final String TAG = "SpeakCommand";

    public SpeakCommand(String[] message) {
        super(message);
    }




    @Override
    public void execute() {
        LoomoSpeakService.getInstance().speak(message[1]);
    }
}

