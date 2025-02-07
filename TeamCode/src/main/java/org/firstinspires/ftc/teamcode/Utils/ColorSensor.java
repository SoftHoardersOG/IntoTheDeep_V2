package org.firstinspires.ftc.teamcode.Utils;

import android.graphics.Color;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class ColorSensor {
    
    public enum AllianceColors{
        RED,
        BLUE,
        NOT_SELECTED
    }
    
    public static AllianceColors matchColor;
    
    private static float matchSensitivity = 5;

    private static final float yellowHue = 75;
    private static final float blueHue = 224;
    private static final float redHue = 20;

    private static final float collectedDistanceMax = 20;  // millimeters
    private static final float collectedDistanceMin = 0;  // millimeters

    private static float[] hsv = new float[3];

    private static float hue;

    public static void init(AllianceColors color){
        matchColor = color;
    }
    
    private static void updateReadings(){
        Color.RGBToHSV(Hardware.colorSensor.red(), Hardware.colorSensor.green(), Hardware.colorSensor.blue(), hsv);
        hue = hsv[0];
    }

    public static double hue(){
        updateReadings();
        return hue;
    }

    public static boolean nothingCollected(){
        return !collectedAllianceSpecificSample() && !collectedWrongSample() && !collectedYellowSample();
    }

    public static boolean collectedAllianceSpecificSample(){
        if (!inRange()) return false;
        if (matchColor == AllianceColors.BLUE) return collectedBlue();
        else if (matchColor == AllianceColors.RED) return  collectedRed();
        else return false;
    }

    public static boolean collectedWrongSample(){
        if (!inRange()) return false;
        if (matchColor == AllianceColors.BLUE) return collectedRed();
        else if (matchColor == AllianceColors.RED) return collectedBlue();
        else return false;
    }
    
    public static boolean collectedYellowSample(){
        if (!inRange()) return false;
        updateReadings();
        return Math.abs(hue - yellowHue) < matchSensitivity;
    }

    private static boolean collectedBlue(){
        updateReadings();
        return Math.abs(hue - blueHue) < matchSensitivity;
    }

    private static boolean collectedRed(){
        updateReadings();
        return Math.abs(hue - redHue) < matchSensitivity;
    }

    private static boolean inRange(){
        return collectedDistanceMin < Hardware.colorSensor.getDistance(DistanceUnit.MM) && Hardware.colorSensor.getDistance(DistanceUnit.MM) < collectedDistanceMax;
    }
}
