package org.firstinspires.ftc.teamcode.Hardware;

import android.graphics.Color;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class ColorSensor {
    
    public enum AllianceColors{
        RED,
        BLUE,
        NOT_SELECTED
    }

    public enum Colors{
        RED,
        BLUE,
        YELLOW,
        NONE
    }
    
    public static AllianceColors matchColor;
    
    private static float matchSensitivity = 8;

    private static final float yellowHue = 81;
    private static final float blueHue = 218;
    private static final float redHue = 26;

    private static final float collectedDistanceMax = 30;  // millimeters
    private static final float collectedDistanceMin = 15;  // millimeters

    private static float[] hsv = new float[3];

    private static float hue;

    private static double safeTime = 200;
    private static double safeTimeRemaining;

    private static Colors previousColor;

    public static void init(AllianceColors color){
        matchColor = color;
        safeTimeRemaining = safeTime;
    }

    public static void update(int deltaMs){
        safeTimeRemaining = Math.max(safeTimeRemaining - deltaMs, 0);
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

    private static Colors getColor(){
         if (Math.abs(hue - yellowHue) < matchSensitivity) return Colors.YELLOW;
         else if (Math.abs(hue - redHue) < matchSensitivity) return Colors.RED;
         else if (Math.abs(hue - blueHue) < matchSensitivity) return Colors.BLUE;
         else return Colors.NONE;
    }

    private static boolean inRange(){
        return collectedDistanceMin < Hardware.colorSensor.getDistance(DistanceUnit.MM) && Hardware.colorSensor.getDistance(DistanceUnit.MM) < collectedDistanceMax;
    }
}
