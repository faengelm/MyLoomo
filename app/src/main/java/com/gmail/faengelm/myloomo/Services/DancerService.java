package com.gmail.faengelm.myloomo.Services;

import android.media.MediaPlayer;
import android.widget.ViewSwitcher;

import com.gmail.faengelm.myloomo.LoomoBaseService;
import com.gmail.faengelm.myloomo.MainActivity;
import com.segway.robot.sdk.locomotion.head.Head;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class DancerService {
    private static String TAG ="Dancer";

    public static DancerService instance;
    private MainActivity mainActivity;
    private MainActivity  activity;
    private SoundsService soundsService;
    private ViewSwitcher viewSwitcher;
    private boolean enableEmoji = true;

    MediaPlayer player = new MediaPlayer();

    public DancerService(MainActivity activity) {
        this.mainActivity = activity;
        this.activity = activity;
        instance = this;
    }

    public static DancerService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Dancer instance not initialized yet");
        }
        return instance;
    }


    public void setForward(String speed) {
        switch (speed) {
            case ("SLOW"): {
                LoomoBaseService.getInstance().move(1.0f, 0.0f);
                break;
            }
            case ("FAST"): {
                try {
                    LoomoBaseService.getInstance().move(2.0f, 0.0f);
                    MILLISECONDS.sleep(1000);
                    LoomoBaseService.getInstance().move(2.0f, 0.0f);;
                    MILLISECONDS.sleep(1000);
                }
                catch (Exception e) {
                }
                break;
            }

            default: {
                break;
            }
        }
    }

    public void setBackward() {
        LoomoBaseService.getInstance().move(-1.0f, 0.0f);
        // MILLISECONDS.sleep(200);
    }
    public void setLeft() {
        LoomoBaseService.getInstance().move(0.0f, 1.1f);
        // MILLISECONDS.sleep(200);
    }

    public void setRight() {
        LoomoBaseService.getInstance().move(0.0f, -1.1f);
        // MILLISECONDS.sleep(200);
    }

    public void setAround() {
        try{
            LoomoBaseService.getInstance().move(0.0f, -3.0f);
            MILLISECONDS.sleep(400);
        }
        catch (Exception e){
        }
    }

    public void setCircles() {
        soundsService.playVoice(4);
        try{
            setAround();
            MILLISECONDS.sleep(400);

            setAround();
            MILLISECONDS.sleep(400);

            setAround();
            MILLISECONDS.sleep(400);

            setAround();
            MILLISECONDS.sleep(400);

            setAround();
            MILLISECONDS.sleep(400);

            setAround();
            MILLISECONDS.sleep(400);
        }
        catch (Exception e){
        }
        player.stop();
    }



    public void execute()   {

        try {
            float newSpeed = 0.0F;
            float newAngle = 0.0F;
            float yawValue = 0.0f;
            float pitchValue =0.0f;
            int mode = Head.MODE_SMOOTH_TACKING;

            SECONDS.sleep(1); // wait for speaking to finish
            //    EmojiVoiceSampleActivity emojiVoiceSampleActivity = new EmojiVoiceSampleActivity();
            //    emojiVoiceSampleActivity.sayIt("in dancer method");
            //   MILLISECONDS.sleep(1000);

           soundsService.playVoice(1);


            setAround(); // turn around

            for (int i=0; i<4; i++) {
                loomoDance(0.05f, 1.5f, 1.0f, 1.0f, mode );
                MILLISECONDS.sleep(1000);

                loomoDance(-0.05f, -1.5f, -1.0f, -1.0f, mode );
                MILLISECONDS.sleep(1000);

                loomoDance(-0.05f, -1.5f, -1.0f, -1.0f, mode );
                MILLISECONDS.sleep(1000);

                loomoDance(0.05f, 1.5f, 1.0f, 1.0f, mode );
                MILLISECONDS.sleep(1000);

                setAround(); // turn around
                MILLISECONDS.sleep(1000);
            }

            setAround(); // turn around
            MILLISECONDS.sleep(1000);
            player.stop();

        } catch (Exception e) {;
        }

    } // end of execute()

    private void loomoDance(float speed, float angle,float yawValue, float pitchValue, int mode){
        try {


            LoomoBaseService.getInstance().move(speed, angle);
            MILLISECONDS.sleep(200);

            //      LoomoHeadService.getInstance().move(mode, yawValue, pitchValue);
            MILLISECONDS.sleep(200);
        }
        catch (Exception e) {
        }
    }

}



