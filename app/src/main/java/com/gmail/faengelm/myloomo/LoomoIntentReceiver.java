package com.gmail.faengelm.myloomo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gmail.faengelm.myloomo.Services.DancerService;
import com.gmail.faengelm.myloomo.Services.SensorsService;
import com.gmail.faengelm.myloomo.Services.SoundsService;
import com.segway.robot.sdk.locomotion.sbv.Base;
import com.segway.robot.sdk.locomotion.sbv.UltrasonicData;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by mss on 02.02.18.
 */

public class LoomoIntentReceiver extends BroadcastReceiver {
    private static final String TAG = "LoomoIntentReceiver";
    private static final int battery_Low_Level= 20;

    private static final String TO_SBV = "com.segway.robot.action.TO_SBV";
    private static final String TO_ROBOT = "com.segway.robot.action.TO_ROBOT";
    private static final String STEP_ON= "com.segway.robot.action.STEP_ON";
    private static final String STEP_OFF= "com.segway.robot.action.STEP_OFF";
    private static final String POWER_BUTTON_PRESSED = "com.segway.robot.action.POWER_BUTTON_PRESSED";
    private static final String SLEEP= "com.segway.robot.action.SLEEP";
    private static final String LIFT_UP= "com.segway.robot.action.LIFT_UP";
    private static final String BATTERY_CHANGED= "com.segway.robot.action.BATTERY_CHANGED";
    private static final String POWER_DOWN= "com.segway.robot.action.POWER_DOWN";
    private static final String PUSHING= "com.segway.robot.action.PUSHING";
    private static final String POWER_BUTTON_RELEASED= "com.segway.robot.action.POWER_BUTTON_RELEASED";

    public static RobotMode robotMode = new RobotMode();
    private Base mBase;
    private SoundsService soundsService;
    private SensorsService sensorsService;
    private DancerService dancerService;

    private float ultrasonicObstacleAvoidanceDistance;
    private UltrasonicData ultrasonicData;
    private float distance;
    private float aFloat;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received intent:" + intent.getAction());
        startServices();
        Boolean robotState = robotMode.getMode();
        mBase = Base.getInstance();


        switch (intent.getAction()) {
            case TO_SBV:
                // don't stop services so I can test ultrasonic sensor--- was  stopServices();
                robotMode.setMode(false); //  NOT in Robot mode
                LoomoSpeakService.getInstance().speak("battery level is " + sensorsService.getRobotPower() + "percent");
                break;

            case TO_ROBOT:
                robotMode.setMode(true); //  YES in Robot mode
                soundsService.sayIt("battery level is " + sensorsService.getRobotPower() + "percent");
                break;

            case STEP_ON:
                sensorsService.setLightOn();
                if (robotState) { // in Robot mode... do not allow step on
                   soundsService.sayIt("You must first trans form to rider mode! ");
               }
                else {
                    LoomoSpeakService.getInstance().speak("battery level is " + sensorsService.getRobotPower() + "percent");//   LoomoSpeakService.getInstance().speak("Ultrasonic distance is " + distance/10+ "feet");
                }
                break;

            case STEP_OFF:
                sensorsService.setLightOff();
                if (robotState) { // robot mode
                    if (sensorsService.getRobotPower() < battery_Low_Level ) {
                        soundsService.sayIt("battery level is VERY LOW,  turn Loomo off");
                        soundsService.setCry();
                        soundsService.sayIt("battery level is " + sensorsService.getRobotPower() + "percent");
                    }
                    else { // battery is OK
                        LoomoSpeakService.getInstance().speak("battery level is " + sensorsService.getRobotPower() + "percent");
                    }
                }
                else { // rider mode
                    if (sensorsService.getRobotPower() < battery_Low_Level ) {
                        LoomoSpeakService.getInstance().speak("battery level is very low,  turn Loomo off");
                        soundsService.setCry();
                        LoomoSpeakService.getInstance().speak("battery level is " + sensorsService.getRobotPower() + "percent");
                    }

                    else {
                        LoomoSpeakService.getInstance().speak("battery level is " + sensorsService.getRobotPower() + "percent");
                    }
            }
                break;


            case SLEEP:
                soundsService.sayIt("I am going to sleep");
                break;

            case LIFT_UP:
               soundsService.sayIt("Hey! Put me down");
                break;

            case POWER_DOWN:
                // soundsService.sayIt("Loomo is powering off, lay Loomo down!");
                LoomoSpeakService.getInstance().speak("Loomo is powering off, lay Loomo down!");
                try {
                    MILLISECONDS.sleep(3000);
                }
                catch (Exception e){
                }
                break;

            case PUSHING:
                 //  soundsService.sayIt("Keep your hand here to push me around");
                   LoomoSpeakService.getInstance().speak("Keep your hand here to push me around");
                break;

            case BATTERY_CHANGED:
                if (sensorsService.getRobotPower() < battery_Low_Level){  /// battery_Low_Level
                    soundsService.sayIt("battery level is VERY LOW,  turn Loomo off");
                    soundsService.setCry();
                    soundsService.sayIt("battery level is " + sensorsService.getRobotPower() + "percent");
                }
                else {
                    soundsService.sayIt("battery level is " + sensorsService.getRobotPower() + "percent");
                }
                break;
        }
    }

    private void startServices() {
        Log.i(TAG, "Restarting Loomo Services");
 //       LoomoSpeakService.getInstance().restartService();

        sensorsService = SensorsService.getInstance();
        soundsService = SoundsService.getInstance();
        dancerService= DancerService.getInstance();
    }

} //Broadcast Receiver
