package org.firstinspires.ftc.teamcode.Mechanisms;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Utils.GameMap;
import org.firstinspires.ftc.teamcode.Utils.Potentiometers;
import org.opencv.core.Mat;

@Config
public class Claw {

    public static double configVertical;
    public static double configHorizontal;
    public static double configClaw;

    public static boolean reachedVerticalBasketAdjust = false;
    public static boolean fixedPositionActive = false;
    public static boolean basket = false;
    public static boolean chamber = false;
    public static boolean preciseAdjusting = false;

    private static double armsCurrentVerticalPosition;
    private static double armsTargetVerticalPosition;

    private static double armsCurrentHorizontalPosition;
    private static double armsTargetHorizontalPosition;

    private static int rotationDirection;
    
    private static double armsNextVerticalPosition = 0.00;
    
    private static double rotateClawMaxLeft = -0.16;
    private static double rotateClawMaxRight = 0.16;
    private static double armsHighBasketMaxAdjust = 0.17;
    
    private static double rotationSafeMargin = 0.02;

    private static double armsMiddleOffset = -0.00;
    private static double armsMiddleTolerance = 0.04;

    private static double armsInit = 0.04;
    private static double armsTransfer = 0.04;
    private static double armsClimb = 0.02;
    private static double armsHighBasket = 0.51;
    private static double armsLowBasket = 0.51;
    private static double armsLowChamber = 0.32;
    private static double armsHighChamber = 0.5;
    private static double armsPark = 0.63;
    private static double armsUnPark = 0.66;
    private static double deliverSample1 = 0.28;
    private static double deliverSample2 = 0.32;


    private static double clawClosedSample = 0.84;
    private static double clawClosed = 0.82;
    private static double clawOpen = 0.69;
    private static double clawOpenScore = 0.67;

    public static boolean horizontalAdjusting = false;

    private static void queueNextPositionVertical(double position){
        armsNextVerticalPosition = position;
        fixedPositionActive = true;
    }

    private static void rotateClawToCurrentMiddle(){
        double middle = (Potentiometers.armLeftPosition() + Potentiometers.armRightPosition()) / 2;
        Hardware.armLeft.setPosition(middle - armsMiddleOffset);
        Hardware.armRight.setPosition(middle + armsMiddleOffset);
    }

    public static void rotateHorizontallyManually(double position){
        if (position > rotateClawMaxRight) position = rotateClawMaxRight;
        else if (position < rotateClawMaxLeft) position = rotateClawMaxLeft;
        horizontalAdjusting = false;
        Hardware.armLeft.setPosition(armsTargetVerticalPosition + position);
        Hardware.armRight.setPosition(armsTargetVerticalPosition - position);
    }

    public static boolean reachedVerticalTarget(){
        updateData();
        return Math.abs(armsCurrentVerticalPosition - armsNextVerticalPosition) < 0.1;
    }

    public static boolean clawOutOfRobot(){
        updateData();
        return armsCurrentVerticalPosition > 0.25;
    }

    public static boolean reachedMiddle(){
        updateData();
        return Math.abs(armsCurrentHorizontalPosition) < armsMiddleTolerance;
    }

    private static void armsAdjustHeight(double p){
        if (p > 1) p = 1;
        if (p < 0) p = 0;
        double maxAmount = armsHighBasketMaxAdjust;
        double processedAmount = p * maxAmount;
        armsTargetVerticalPosition = armsHighBasket + processedAmount;
    }

    public static void updateConfig(){
        updateData();
        armsTargetVerticalPosition = configVertical;
        Hardware.armLeft.setPosition(armsTargetVerticalPosition + configHorizontal);
        Hardware.armRight.setPosition(armsTargetVerticalPosition - configHorizontal);
        Hardware.claw.setPosition(configClaw);
    }

    public static void update(int slidesCurrent, int slidesTarget){
        if (fixedPositionActive){
            if (reachedMiddle()){
                armsTargetVerticalPosition = armsNextVerticalPosition;
                Hardware.armLeft.setPosition(armsNextVerticalPosition - 2 * armsMiddleOffset);
                Hardware.armRight.setPosition(armsNextVerticalPosition);
            }
            if ((clawOutOfRobot() && reachedMiddle() && (basket || chamber)) || (reachedVerticalTarget() && reachedMiddle())){
                armsTargetVerticalPosition = armsNextVerticalPosition;
                Hardware.armLeft.setPosition(armsNextVerticalPosition - 2 * armsMiddleOffset);
                Hardware.armRight.setPosition(armsNextVerticalPosition);
                fixedPositionActive = false;
            }
        }
        else{
            if (basket){
                armsTargetVerticalPosition = armsHighBasket;
                updateClawAngleVertical(slidesCurrent, slidesTarget);
                updateClawAngleHorizontal();
            }
            else if (chamber){
                armsTargetVerticalPosition = armsHighChamber;
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
                double height = 1 - p * p * p * p;
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
        if (!horizontalAdjusting) return;
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
        Hardware.armLeft.setPosition(armsTargetVerticalPosition + position);
        Hardware.armRight.setPosition(armsTargetVerticalPosition - position);
    }

    public static void clawPositionInit(){
        basket = false;
        chamber = false;
        reachedVerticalBasketAdjust = false;
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsInit);
    }
    public static void clawPositionTransfer(){
        basket = false;
        chamber = false;
        reachedVerticalBasketAdjust = false;
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsTransfer);
    }
    public static void clawPositionClimb(){
        basket = false;
        chamber = false;
        reachedVerticalBasketAdjust = false;
        queueNextPositionVertical(armsClimb);
    }
    public static void clawPositionHighChamber(){
        basket = false;
        chamber = true;
        reachedVerticalBasketAdjust = false;
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsHighChamber);
    }
    public static void clawPositionLowChamber(){
        basket = false;
        chamber = false;
        reachedVerticalBasketAdjust = false;
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsLowChamber);
    }
    public static void clawPositionDeliverSample1(){
        basket = false;
        chamber = false;
        reachedVerticalBasketAdjust = false;
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(deliverSample1);
    }
    public static void clawPositionLowBasket(){
        basket = true;
        chamber = false;
        reachedVerticalBasketAdjust = false;
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsLowBasket);
    }

    public static void clawPositionHighBasket(){
        basket = true;
        chamber = false;
        reachedVerticalBasketAdjust = false;
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsHighBasket + armsHighBasketMaxAdjust);
    }

    public static void clawPositionPark(){
        basket = false;
        chamber = false;
        reachedVerticalBasketAdjust = false;
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsPark);
    }

    public static void clawPositionUnPark(){
        basket = false;
        chamber = false;
        reachedVerticalBasketAdjust = false;
        rotateClawToCurrentMiddle();
        queueNextPositionVertical(armsUnPark);
    }

    public static void closeClawSample(){Hardware.claw.setPosition(clawClosedSample);}
    public static void closeClaw(){Hardware.claw.setPosition(clawClosed);}
    public static void openClaw(){Hardware.claw.setPosition(clawOpen);}
    public static void openClawScore(){Hardware.claw.setPosition(clawOpenScore);}

    public static void clawInit(){
        configClaw = clawClosed;
        configVertical = armsInit;
        configHorizontal = 0;
        armsHighBasketMaxAdjust = 0.14;
        closeClaw();
        clawPositionInit();
        horizontalAdjusting = true;
        preciseAdjusting = false;
    }

    public static void clawInitAuto(){
        closeClawSample();
        clawPositionInit();
        armsHighBasketMaxAdjust = 0.17;
        horizontalAdjusting = true;
        preciseAdjusting = true;
    }

}
