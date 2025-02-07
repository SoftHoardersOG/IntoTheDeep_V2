package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Utils.Potentiometers;

public class Intake {

    public static boolean inCollectPosition;
    public static boolean collecting;
    public static boolean spittingGround;

    private static double openerCollect = 0.92;
    private static double openerCollectWide = 0.92;
    private static double openerClosed = 0.52;

    private static double intakeRightCollect = 1;
    private static double intakeRightSpitout = -1;

    private static double intakeLeftCollect = -1;
    private static double intakeLeftSpitout = 1;

    private static double intakeUpDownLimelightScan = 0.45;
    private static double intakeUpDownInit = 0.56;
    private static double intakeUpDownNeutral = 0.29;
    private static double intakeUpDownCollect = 0.16;
    public static double intakeUpDownTransfer = 0.68;
    private static double intakeUpDownParkSpecimenSide = 0.56;

    private static double intakeUpDownCollectMaxOffset = 0.02;

    public static void updateCollectPosition(){
        if (inCollectPosition){
            double position = intakeUpDownCollect + FrontSlides.getCurrentPercentage() * intakeUpDownCollectMaxOffset;
            Hardware.intakeUpDown.setPosition(position);
        }
    }

    public static void clearLimelightView(){
        inCollectPosition = false;
        collecting = false;
        spittingGround = false;
        Hardware.intakeLeft.setPower(0);
        Hardware.intakeRight.setPower(0);
        Hardware.intakeUpDown.setPosition(intakeUpDownLimelightScan);
        Hardware.opener.setPosition(openerClosed);
    }

    public static void init(){
        inCollectPosition = false;
        collecting = false;
        spittingGround = false;
        Hardware.intakeLeft.setPower(0);
        Hardware.intakeRight.setPower(0);
        Hardware.intakeUpDown.setPosition(intakeUpDownInit);
        Hardware.opener.setPosition(openerClosed);
    }
    public static void neutral(){
        inCollectPosition = false;
        Hardware.opener.setPosition(openerClosed);
        Hardware.intakeUpDown.setPosition(intakeUpDownNeutral);
    }
    public static void ground(){
        inCollectPosition = true;
        Hardware.intakeUpDown.setPosition(intakeUpDownCollect);
        Hardware.opener.setPosition(openerClosed);
        stopCollect();
    }
    public static void collect(){
        inCollectPosition = true;
        collecting = true;
        spittingGround = false;
        Hardware.intakeLeft.setPower(intakeLeftCollect);
        Hardware.intakeRight.setPower(intakeRightCollect);
        Hardware.intakeUpDown.setPosition(intakeUpDownCollect);
        Hardware.opener.setPosition(openerCollect);
    }
    public static void collectWide(){
        inCollectPosition = true;
        collecting = true;
        spittingGround = false;
        Hardware.intakeLeft.setPower(intakeLeftCollect);
        Hardware.intakeRight.setPower(intakeRightCollect);
        Hardware.intakeUpDown.setPosition(intakeUpDownCollect);
        Hardware.opener.setPosition(openerCollectWide);
    }
    public static void stopCollect(){
        collecting = false;
        spittingGround = false;
        Hardware.intakeLeft.setPower(0);
        Hardware.intakeRight.setPower(0);
        Hardware.opener.setPosition(openerClosed);
    }
    public static void spitoutSlow(){
        inCollectPosition = false;
        collecting = false;
        spittingGround = false;
        Hardware.intakeLeft.setPower(intakeLeftSpitout / 4);
        Hardware.intakeRight.setPower(intakeRightSpitout / 4);
        Hardware.opener.setPosition(openerCollect);
    }
    public static void spitoutGround(){
        inCollectPosition = true;
        collecting = false;
        spittingGround = true;
        Hardware.intakeLeft.setPower(intakeLeftSpitout);
        Hardware.intakeRight.setPower(intakeRightSpitout);
        Hardware.intakeUpDown.setPosition(intakeUpDownCollect);
        Hardware.opener.setPosition(openerCollect);
    }
    public static void transfer(){
        inCollectPosition = false;
        collecting = false;
        spittingGround = false;
        Hardware.opener.setPosition(openerClosed);
        Hardware.intakeLeft.setPower(0);
        Hardware.intakeRight.setPower(0);
        Hardware.intakeUpDown.setPosition(intakeUpDownTransfer);
    }

    public static void startCollect(){
        Hardware.intakeLeft.setPower(intakeLeftCollect);
        Hardware.intakeRight.setPower(intakeRightCollect);
    }

    public static void parkSpecimenSide(){
        inCollectPosition = false;
        collecting = false;
        spittingGround = false;
        Hardware.intakeUpDown.setPosition(intakeUpDownParkSpecimenSide);
    }


}
