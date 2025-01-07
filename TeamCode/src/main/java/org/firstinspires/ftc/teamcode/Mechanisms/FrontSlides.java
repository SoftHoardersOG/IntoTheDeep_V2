package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Utils.OneTap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

public class FrontSlides {

    public static boolean init = true;
    private static boolean transfer = false;
    public static boolean calibrating = false;

    private static int slidesInit = 0; // 5
    public static int slidesTransfer = -1;
    private static int slidesExtended = 570;

    private static double power = 1;

    private static OneTap sensorDetect = new OneTap();

    public static void update(){
//        updatePositions();

//          start();
        if (Hardware.frontSlides.getPower() != power){
            Hardware.frontSlides.setPower(power);
        }

        if (Math.abs(Hardware.frontSlides.getCurrentPosition() - Hardware.frontSlides.getTargetPosition()) >= 10){
            start();
        }
        else {
            stop();
        }
//        if(Hardware.frontSlides.getCurrentPosition() - slidesInit <= 1 && init){
//            Hardware.frontSlides.setTargetPosition(slidesInit);
//        }
//        else if(Hardware.frontSlides.getCurrentPosition() - slidesTransfer <= 1 && transfer){
//            Hardware.frontSlides.setTargetPosition(slidesTransfer);
//        }
    }

    public static void init(){
        init = true;
        slidesInit = 0;
        slidesTransfer = -1;
        slidesExtended = 570;
        power = 1;
        Hardware.frontSlides.setTargetPosition(slidesInit - 700);
        ActionDelayer.condition(FrontSlides :: reachedInit, FrontSlides :: updatePositions);
    }

    public static void retractFullyReleaseAfter(){
        power = 1;
        start();
        Hardware.frontSlides.setTargetPosition(slidesInit - 700);
        init = false;
        transfer = false;
        calibrating = true;
        ActionDelayer.condition(FrontSlides::reachedInit, FrontSlides::updatePositions);
    }

    public static void findNewInitPosition(){
        power = 1;
        start();
        Hardware.frontSlides.setTargetPosition(slidesInit - 700);
        init = false;
        transfer = false;
        calibrating = true;
    }

    public static void initPosition(){
        power = 1;
        start();
        Hardware.frontSlides.setTargetPosition(slidesInit);
        init = true;
        transfer = false;
        calibrating = false;
    }

    public static void start(){
        Hardware.frontSlides.setPower(power);
    }

    public static void stop(){
        Hardware.frontSlides.setPower(0);
        init = false;
        transfer = false;
        calibrating = false;
    }

    private static void updatePositions(){
        int distanceToExtended = slidesExtended - slidesInit;
        slidesInit = Hardware.frontSlides.getCurrentPosition();
        slidesExtended = slidesInit + distanceToExtended;
        slidesTransfer = slidesInit - 1;
        calibrating = false;
        Hardware.frontSlides.setTargetPosition(slidesInit);
    }

//    public static boolean isStuck(){
//        if (Math.abs(lastPositionAverage - Hardware.frontSlides.getCurrentPosition()) <= 2 && Math.abs(Hardware.frontSlides.getTargetPosition() - Hardware.frontSlides.getCurrentPosition()) > 3){
//            return true;
//        }
//        return false;
//    }

//    private static void updateAverageLastPosition(){
//        int sum = 0;
//        for(int i : lastPositions){
//            sum += i;
//        }
//        int nums = lastPositions.size();
//        if (nums == 0) nums++;
//        lastPositionAverage = sum / nums;
//        lastPositions.clear();
//        ActionDelayer.time(averageTime, FrontSlides::updateAverageLastPosition);
//    }

    public static void keepPosition(){
        Hardware.frontSlides.setTargetPosition(Hardware.frontSlides.getCurrentPosition());
        init = false;
        transfer = false;
        calibrating = false;
    }

    public static void extendPercentage(double p){
        if (p < 0) p = 0;
        if (p > 1) p = 1;
        power = 1;
        start();
        Hardware.frontSlides.setTargetPosition((int)((slidesExtended - slidesInit) * p) + slidesInit);
        init = false;
        transfer = false;
        calibrating = false;
    }

    public static void extendPercentage(double p, double _power){
        if (p < 0) p = 0;
        if (p > 1) p = 1;
        power = _power;
        start();
        Hardware.frontSlides.setTargetPosition((int)((slidesExtended - slidesInit) * p) + slidesInit);
        init = false;
        transfer = false;
        calibrating = false;
    }

    public static double getCurrentPercentage(){
        int distance = slidesExtended - slidesInit;
        int current = Hardware.frontSlides.getCurrentPosition() - slidesInit;
        return 1.0 * current / distance;
    }

    public static void release(){
        extendPercentage(0.03);
    }

    public static void extend(float dt){
        Hardware.frontSlides.setTargetPosition(slidesExtended); // position
        power = 1;
        init = false;
        transfer = false;
        calibrating = false;
    }

    public static void retract(float dt){
        Hardware.frontSlides.setTargetPosition(slidesInit - 1000); // position
        power = 1;
        init = false;
        transfer = false;
        calibrating = false;
    }

    public static boolean reachedInit(){
        return !Hardware.sensor.getState();
    }
    public static boolean reachedTargetPosition(){
        int current = Hardware.frontSlides.getCurrentPosition();
        int target = Hardware.frontSlides.getTargetPosition();
        return current - 10 <= target && target <= current + 10;
    }

    public static boolean reachedPercentage(double p){
        int current = Hardware.frontSlides.getCurrentPosition();
//        Hardware.frontSlides.setTargetPosition((int)((slidesExtended - slidesInit) * p) + slidesInit);
        int target = Hardware.frontSlides.getTargetPosition();
        return current - 40 <= target && target <= current + 40;
    }
}
