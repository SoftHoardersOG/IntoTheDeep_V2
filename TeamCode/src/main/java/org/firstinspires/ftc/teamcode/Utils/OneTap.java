package org.firstinspires.ftc.teamcode.Utils;

public class OneTap {
    private boolean firstPress = true;

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
}