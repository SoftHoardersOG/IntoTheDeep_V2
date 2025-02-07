package org.firstinspires.ftc.teamcode.Utils;

public class OneTap {
    private boolean firstPress = true;
    private long actualTime = 0;

    public void update(long dt){
        actualTime -= dt;
    }

    public boolean onPress(boolean button) {
        boolean result;
        if (button && firstPress) {
            result = true;
            firstPress = false;
        } else {
            result = false;
        }
        if (!button) {
            firstPress = true;
        }
        return result;
    }

    public boolean onPressSmooth(boolean button, long releaseIgnoreTime) {
        boolean result;
        if (button) {
            actualTime = releaseIgnoreTime;
        }
        if (button && firstPress) {
            result = true;
            firstPress = false;
        } else {
            result = false;
        }
        if (!button && actualTime <= 0) {
            firstPress = true;
        }
        return result;
    }
}