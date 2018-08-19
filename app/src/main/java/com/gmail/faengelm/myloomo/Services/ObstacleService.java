package com.gmail.faengelm.myloomo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import com.gmail.faengelm.myloomo.LoomoSpeakService;
import com.gmail.faengelm.myloomo.MainActivity;
import com.gmail.faengelm.myloomo.RobotMode;
import com.segway.robot.sdk.locomotion.sbv.Base;
import com.segway.robot.sdk.locomotion.sbv.UltrasonicData;

public class ObstacleService extends Service {
    public static ObstacleService instance;
    private SoundsService soundsService;
    private LoomoSpeakService loomoSpeakService;
    private UltrasonicData ultrasonicData;
    private Base mBase;
    private float distance;
    private MainActivity mainActivity;
    public static RobotMode robotMode = new RobotMode();


    public static ObstacleService getInstance() {
        return instance;
    }

    public ObstacleService(MainActivity activity) {
        this.mainActivity = activity;
        instance = this;
    }


    public ObstacleService() {
       soundsService = SoundsService.getInstance();

      while (true) {
           try {
               SystemClock.sleep(2000);
              senseDistance();
            } catch (Exception e) {
            }
       } // End of While


    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Invoke background service onCreate method.", Toast.LENGTH_LONG).show();
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Invoke background service onStartCommand method.", Toast.LENGTH_LONG).show();
        soundsService = SoundsService.getInstance();
        soundsService.sayIt("I'm starting up");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Invoke background service onDestroy method.", Toast.LENGTH_LONG).show();
    }


    public void senseDistance() {
        soundsService = SoundsService.getInstance();
        mBase = Base.getInstance();
        ultrasonicData = mBase.getUltrasonicDistance();
        distance = (ultrasonicData.getDistance() / 100) * 3.2f; // /1000 * 3.2 feet/meter
        distance = (Math.round(distance)/10);
        //soundsService.sayIt("distance equals " + distance + "feet");

        if (distance < 4.0f) {
            soundsService.playVoice(5);
        }
    }
}
