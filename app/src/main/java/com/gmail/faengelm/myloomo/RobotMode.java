package com.gmail.faengelm.myloomo;

public class RobotMode {

    private Boolean mode =true;  // Robot mode (Robot and SBV)
    private int batteryLevelChange; // Battery change level message counter

    public Boolean getMode() {
        return mode;
    }

    public void setMode(Boolean m) {
        mode = m;
    }

    public void setBatteryLevelChange() {
        batteryLevelChange= batteryLevelChange +1;
    }

    public int getBatteryLevelChange()
    {
        return batteryLevelChange;
    }
}







