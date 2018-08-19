package com.gmail.faengelm.myloomo.Services;

import com.gmail.faengelm.myloomo.LoomoBaseService;
import com.gmail.faengelm.myloomo.MainActivity;
import com.segway.robot.sdk.locomotion.sbv.Base;


    public class SensorsService {
        public static SensorsService instance;

        private LoomoBaseService loomoBaseService;
        private boolean bodyLightOn =  false;
        private int baseLightLevel;
        private int robotPower;
        private float ultrasonicObstacleAvoidanceDistance;

        private MainActivity mMainActivity;

        private Base mBase;

        public SensorsService(MainActivity activity) {
            this.mMainActivity = activity;
            instance = this;
            mBase = Base.getInstance();
        }

        public static com.gmail.faengelm.myloomo.Services.SensorsService getInstance() {
            if (instance == null) {
                throw new IllegalStateException("Sensors instance not initialized yet");
            }
            return instance;
        }

        private static final String TAG = "Sensors";


        public void setLightOn() {
            mBase.enableBodyLight(true);
        }

        public void setLightOff() {
            mBase.enableBodyLight(false);
        }

        public int getRobotPower() {   // battery level in %
            return  mBase.getRobotPower();
        }

        public float getUltrasonicObstacleAvoidanceDistance() {
            return mBase.getUltrasonicObstacleAvoidanceDistance();
        }

        public int getLightLevel () {  // room light
            return mBase.getLightBrightness();
        }

        public boolean getIsUltrasonicObstacleAvoidanceEnabled() {
            return mBase.isUltrasonicObstacleAvoidanceEnabled();
        }

        public void setUltrasonicObstacleAvoidanceDistance(float distance){
            int a;
            a=1;
            mBase.setUltrasonicObstacleAvoidanceDistance(distance);
        }

        public void setUltrasonicObstacleAvoidanceEnabled(boolean state){
            int a;
            a=1;
            mBase.setUltrasonicObstacleAvoidanceEnabled(state);
        }

        public void setObstacleStateChangeListener(){
            //  mBase.setObstacleStateChangeListener();
        }

    } // End of Sensors()

