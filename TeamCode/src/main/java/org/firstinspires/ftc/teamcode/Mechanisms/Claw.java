package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;

public class Claw {

    private static double armLeftInit = 0.05;
    private static double armLeftTransfer = 0.01;
    private static double armLeftHighBasket = 0.53;
    private static double armLeftLowBasket = 0.49;
    private static double armLeftLowChamber = 0.20;
    private static double armLeftHighChamber = 0.38;
    private static double armLeftPark = 0.57;

    private static double armRightInit = 0.61;
    private static double armRightTransfer = 0.66;
    private static double armRightHighBasket = 0.13;
    private static double armRightLowBasket = 0.17;
    private static double armRightLowChamber = 0.46;
    private static double armRightHighChamber = 0.28;
    private static double armRightPark = 0.09;

    private static double rotateClawInit = 0.44;
    private static double rotateClawTransfer = 0.44;
    private static double rotateClawChamber = 0.48;
    private static double rotateClawMiddle = 0.48;
    private static double rotateClawRight = 0.3;
    private static double rotateClawLeft = 0.66;

    private static double clawClosedSample = 0.68;
    private static double clawClosed = 0.66;
    private static double clawOpen = 0.6;
    private static double clawOpenScore = 0.43;

    public static void armsInit(){
        Hardware.armLeft.setPosition(armLeftInit);
        Hardware.armRight.setPosition(armRightInit);
    }
    public static void armsTransfer(){
        Hardware.armLeft.setPosition(armLeftTransfer);
        Hardware.armRight.setPosition(armRightTransfer);
    }
    public static void armsHighBasket(){
        Hardware.armLeft.setPosition(armLeftHighBasket);
        Hardware.armRight.setPosition(armRightHighBasket);
    }
    public static void armsLowBasket(){
        Hardware.armLeft.setPosition(armLeftLowBasket);
        Hardware.armRight.setPosition(armRightLowBasket);
    }
    public static void armsHighChamber(){

        Hardware.armLeft.setPosition(armLeftHighChamber);
        Hardware.armRight.setPosition(armRightHighChamber);
    }
    public static void armsLowChamber(){
        Hardware.armLeft.setPosition(armLeftLowChamber);
        Hardware.armRight.setPosition(armRightLowChamber);
    }
    public static void armsPark(){
        Hardware.armLeft.setPosition(armLeftPark);
        Hardware.armRight.setPosition(armRightPark);
    }

    public static void clawAngleInit(){Hardware.rotateClaw.setPosition(rotateClawInit);}
    public static void clawAngleTransfer(){Hardware.rotateClaw.setPosition(rotateClawTransfer);}
    public static void clawAngleChamber(){Hardware.rotateClaw.setPosition(rotateClawChamber);}

    public static void clawAngleMiddle(){Hardware.rotateClaw.setPosition(rotateClawMiddle);}
    public static void clawAngleLeft(){Hardware.rotateClaw.setPosition(rotateClawLeft);}
    public static void clawAngleRight(){Hardware.rotateClaw.setPosition(rotateClawRight);}

    public static void closeClawSample(){Hardware.claw.setPosition(clawClosedSample);}
    public static void closeClaw(){Hardware.claw.setPosition(clawClosed);}
    public static void openClaw(){Hardware.claw.setPosition(clawOpen);}
    public static void openClawScore(){Hardware.claw.setPosition(clawOpenScore);}

    public static void clawInit(){
        closeClaw();
        clawAngleInit();
        armsInit();
    }

    public static void clawInitAuto(){
        closeClawSample();
        clawAngleInit();
        armsInit();
    }

}
