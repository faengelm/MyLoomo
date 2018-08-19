package com.gmail.faengelm.myloomo;

/*
faengelm 7/22/2018 vs 10 dances, resumes, minimal emoji
faengelm 8/9/2018 vs 11 added Sensors for headlight
                        used Loomo voice EXCEPT when in Rider mode
                        Changed Sounds to NOT use "extends"
                        Added Services folder, moved Sounds and Sensors
faengelm 8/11/2018 vs 12 play Cry music when battery is LOW, stepping off or on change

faengelm 8/15/2018 vs 13 adding Ultrasonic sensor works!!! tried with Step_ON

faengelm 8/15/2018 vs 14 distance verbally said in background loop

faengelm 8/16/2018 vs 15 Got background loop to work in Utils- CheckDistance using AysncTask saying "STOP"

faengelm 8/18/2018 vs 16 rewrite of background task within MainActivity instead of ObstacleService and CheckDistance

 */

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gmail.faengelm.myloomo.Services.DancerService;
import com.gmail.faengelm.myloomo.Services.ObstacleService;
import com.gmail.faengelm.myloomo.Services.SensorsService;
import com.gmail.faengelm.myloomo.Services.SoundsService;
import com.gmail.faengelm.myloomo.Utils.CheckDistance;
import com.segway.robot.sdk.base.bind.ServiceBinder;
import com.segway.robot.sdk.locomotion.sbv.Base;
import com.segway.robot.sdk.locomotion.sbv.UltrasonicData;
import com.segway.robot.sdk.voice.Speaker;


public class MainActivity extends Activity {
    private Speaker mSpeaker;
    private LoomoSpeakService loomoSpeakService;
    private LoomoBaseService loomoBaseService;
    private LoomoHeadService loomoHeadService;
    private LoomoEmojiService loomoEmojiService;
    private LoomoConnectivityService loomoConnectionService;
    private DancerService dancerService;
    private SensorsService sensorsService;
    private SoundsService soundsService;
    private Base mBase;
    private CheckDistance checkDistance;
    private TextView textView;
    private ObstacleService obstacleService;
    private UltrasonicData ultrasonicData;
    private float distance;

    private final String TAG = "MyLoomo MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpeaker = Speaker.getInstance();
        this.loomoSpeakService = new LoomoSpeakService(getApplicationContext());

        Button danceBtn = (Button)findViewById(R.id.danceBtn);
        danceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dancerService.execute();
            }
        });


        Button helloBtn =(Button)findViewById(R.id.helloBtn);
        helloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoomoSpeakService.getInstance().speak("Hello Cobble Rock family, I am your Loomo Robot");
            }
        });

        this.loomoBaseService = new LoomoBaseService(getApplicationContext());
        this.loomoSpeakService = new LoomoSpeakService(getApplicationContext());
        this.loomoHeadService = new LoomoHeadService(getApplicationContext());
        this.loomoEmojiService = new LoomoEmojiService(getApplicationContext(), this);
        this.loomoConnectionService = new LoomoConnectivityService(this);

        this.dancerService = new DancerService(this);
        this.loomoBaseService = new LoomoBaseService (this);
        this.sensorsService = new SensorsService(this);
        this.soundsService = new SoundsService(this);
        this.obstacleService = new ObstacleService(this);

        initBase();

        DistanceAsyncTask distanceAsyncTask = new DistanceAsyncTask();
        distanceAsyncTask.execute(10);

    }// End of onCreate

        /* this is the background task */
    private class DistanceAsyncTask extends AsyncTask<Integer,Integer,Integer>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... intergers) {
            while(true) {
                try {
                    Thread.sleep(1500);
                  //  obstacleService.senseDistance();
                    soundsService = SoundsService.getInstance();
                    mBase = Base.getInstance();
                    ultrasonicData = mBase.getUltrasonicDistance();
                    distance = (ultrasonicData.getDistance() / 100) * 3.2f; // /1000 * 3.2 feet/meter
                    distance = (Math.round(distance)/10);
                    //soundsService.sayIt("distance equals " + distance + "feet");

                    if (distance < 4.0f) {
                        soundsService.playVoice(5);
                    }
                 } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
       }
    }

    private void initBase() {
        // get Base Instance
        mBase = Base.getInstance();
        // bindService, if not, all Base api will not work.
        mBase.bindService(getApplicationContext(), new ServiceBinder.BindStateListener() {
            @Override
            public void onBind() {
                Log.d(TAG, "Base bind success");
                mBase.setControlMode(Base.CONTROL_MODE_NAVIGATION);
            }

            @Override
            public void onUnbind(String reason) {
                Log.d(TAG, "Base bind failed");
            }
        });
    }


} // MainActivity
