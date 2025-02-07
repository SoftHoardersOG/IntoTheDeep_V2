package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Utils.GameMap;
import org.firstinspires.ftc.teamcode.Utils.Potentiometers;
import org.opencv.core.Mat;

public class Claw {

    public static boolean reachedVerticalBasketAdjust = false;
    public static boolean fixedPositionActive = false;
    public static boolean basket = false;
    public static boolean chamber = false;
    public static boolean preciseAdjusting = false;

    public static double armsCurrentVerticalPosition;
    public static double armsTargetVerticalPosition;

    public static double armsCurrentHorizontalPosition;
    public static double armsTargetHorizontalPosition;

    private static int rotationDirection;
    
    private static double armsNextVerticalPosition = 0.00;
    
    private static double rotateClawMaxLeft = -0.25;
    private static double rotateClawMaxRight = 0.25;
    private static double armsHighBasketMaxAdjust = 0.13;
    
    private static double rotationSafeMargin = 0.02;

    private static double armsMiddleOffset = -0.00;
    private static double armsMiddleTolerance = 0.05;

    private static double armsInit = 0.06;
    private static double armsTransfer = 0.06;
    private static double armsHighBasket = 0.51;
    private static double armsLowBasket = 0.51;
    private static double armsLowChamber = 0.32;
    private static double armsHighChamber = 0.51;
    private static double armsPark = 0.62;


    private static double clawClosedSample = 0.77;
    private static double clawClosed = 0.77;
    private static double clawOpen = 0.63;
    private static double clawOpenScore = 0.47;

    private static void queueNextPositionVertical(double position){
        armsNextVerticalPosition = position;
        fixedPositionActive = true;
    }

    private static void rotateClawToCurrentMiddle(){
        double middle = (Potentiometers.armLeftPosition() + Potentiometers.armRightPosition()) / 2;
        Hardware.armLeft.setPosition(middle - armsMiddleOffset);
        Hardware.armRight.setPosition(middle + armsMiddleOffset);
    }

    private static boolean reachedVerticalTarget(){
        return Math.abs(armsCurrentVerticalPosition - armsTargetVerticalPosition) < 0.1;
    }

    public static boolean reachedMiddle(){
//        double middle = (Potentiometers.armLeftPosition() + Potentiometers.armRightPosition()) / 2;
        return Math.abs(armsCurrentHorizontalPosition) < armsMiddleTolerance;
    }

    private static void armsAdjustHeight(double p){
        if (p > 1) p = 1;
        if (p < 0) p = 0;
        double maxAmount = armsHighBasketMaxAdjust;
        double processedAmount = p * maxAmount;
        armsTargetVerticalPosition = armsHighBasket + processedAmount;
    }

    public static void update(int slidesCurrent, int slidesTarget){
        updateData();
        if (fixedPositionActive){
            if (reachedMiddle()){
                armsTargetVerticalPosition = armsNextVerticalPosition;
                Hardware.armLeft.setPosition(armsNextVerticalPosition - 2 * armsMiddleOffset);
                Hardware.armRight.setPosition(armsNextVerticalPosition);
            }
            if (reachedVerticalTarget() && reachedMiddle()){
                armsTargetVerticalPosition = armsNextVerticalPosition;
                Hardware.armLeft.setPosition(armsNextVerticalPosition - 2 * armsMiddleOffset);
                Hardware.armRight.setPosition(armsNextVerticalPosition);
                fixedPositionActive = false;
            }
        }
        else{
            if (basket){
//                if (!reachedVerticalBasketAdjust) updateClawAngleVertical(slidesCurrent, slidesTarget);
//                updateData();
//                if (reachedVerticalBasketAdjust) updateClawAngleHorizontal();
                updateClawAngleVertical(slidesCurrent, slidesTarget);
//                updateData();
                updateClawAngleHorizontal();
            }
            else if (chamber){
                updateClawAngleHorizontal();
            }
        }
    }

    private static void updateData(){
        double armLeftPos = Potentiometers.armLeftPosition();
        double armRightPos = Potentiometers.armRightPosition();
        double armLeftTargetPos = Hardware.armLeft.getPosition();
        double armRightTargetPos = Hardware.armRight.getPosition();
        double middle = (armLeftPos + armRightPos) / 2;
        double targetMiddle = (armLeftTargetPos + armRightTargetPos) / 2;
        rotationDirection = middle <= armRightPos ? 1 : -1;
        armsCurrentVerticalPosition = middle;
        armsCurrentHorizontalPosition = armRightPos - middle;
//        rotateClawMaxLeft = -armsTargetVerticalPosition - rotationSafeMargin;
//        rotateClawMaxRight = 1 - armsTargetVerticalPosition + rotationSafeMargin;
        armsTargetVerticalPosition = targetMiddle;
        armsTargetHorizontalPosition = armRightTargetPos - targetMiddle;
    }
    
    public static double updateClawAngleVertical(int current, int target){
        if (basket){
            int maxAmount = 900;
            int minAdjust = target + maxAmount;
            int over = current - minAdjust;
            if (over > 0){
                reachedVerticalBasketAdjust = false;
                armsAdjustHeight(1);
            }
            else if (over < -maxAmount){
                reachedVerticalBasketAdjust = true;
                armsAdjustHeight(0);
            }
            else{
                double p = -1.0 * over / maxAmount;
                double height = 1 - p * p * p;
                if (height < 0.20) height = 0;
                if (!reachedVerticalBasketAdjust) armsAdjustHeight(height);
                else armsAdjustHeight(0);
                if (height == 0){
                    reachedVerticalBasketAdjust = true;
                }
            }
//            return over;
        }
        return -1;
    }

    public static void updateClawAngleHorizontal(){
        double position = 0;
        if (basket && preciseAdjusting){
            position += GameMap.clawAngleToBasketPrecisePercentage();
        }
        else if (basket && !preciseAdjusting){
            position += GameMap.clawAngleToBasketPercentage();
        }
        else if (chamber){
            position += GameMap.clawAngleToChamberPercentage();
        }
        if (position > rotateClawMaxRight) position = rotateClawMaxRight;
        else if (position < rotateClawMaxLeft) position = rotateClawMaxLeft;
//        if (basket &&
//                Math.abs((armsTargetVerticalPosition + position) - Hardware.armLeft.getPosition()) < 0.00 &&
//                Math.abs((armsTargetVerticalPosition - position) - Hardware.armRight.getPosition()) < 0.00) return;
        Hardware.armLeft.setPosition(armsTargetVerticalPosition + position);
        Hardware.armRight.setPosition(armsTargetVerticalPosition - position);
    }

    public static void clawPositionInit(){
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsInit);
        reachedVerticalBasketAdjust = false;
        basket = false;
        chamber = false;
    }
    public static void clawPositionTransfer(){
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsTransfer);
        reachedVerticalBasketAdjust = false;
        basket = false;
        chamber = false;
    }
    public static void clawPositionHighChamber(){
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsHighChamber);
        reachedVerticalBasketAdjust = false;
        basket = false;
        chamber = true;
    }
    public static void clawPositionLowChamber(){
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsLowChamber);
        reachedVerticalBasketAdjust = false;
        basket = false;
        chamber = false;
    }

    public static void clawPositionLowBasket(){
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsLowBasket);
        reachedVerticalBasketAdjust = false;
        basket = true;
        chamber = false;
    }

    public static void clawPositionHighBasket(){
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsHighBasket);
        reachedVerticalBasketAdjust = false;
        basket = true;
        chamber = false;
    }

    public static void clawPositionPark(){
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsPark);
        reachedVerticalBasketAdjust = false;
        basket = false;
        chamber = false;
    }

    public static void closeClawSample(){Hardware.claw.setPosition(clawClosedSample);}
    public static void closeClaw(){Hardware.claw.setPosition(clawClosed);}
    public static void openClaw(){Hardware.claw.setPosition(clawOpen);}
    public static void openClawScore(){Hardware.claw.setPosition(clawOpenScore);}

    public static void clawInit(){
        closeClaw();
        clawPositionInit();
        preciseAdjusting = false;
    }

    public static void clawInitAuto(){
        closeClawSample();
        clawPositionInit();
        preciseAdjusting = true;
    }

}
