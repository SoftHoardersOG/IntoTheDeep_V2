package org.firstinspires.ftc.teamcode.Mechanisms;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Utils.Potentiometers;

@Config
public class Intake {

    public static double configUpDown;
    public static double configOpener;
    public static double configCollectPower;

    public static boolean inCollectPosition;
    public static boolean collecting;
    public static boolean spittingGround;

    private static double openerCollect = 0.19;
    private static double openerCollectWide = 0.24;
    private static double openerClosed = 0.165;

    private static double collectPowerSlow = 0.2;
    private static double collectPower = 1;
    private static double spitOutPower = -1;

    private static double intakeRightDirection = -1;
    private static double intakeLeftDirection = 1;

    private static double intakeUpDownLimelightScan = 0.435;
    private static double intakeUpDownInit = 0.545;
    private static double intakeUpDownNeutral = 0.275;
    private static double intakeUpDownInspection = 0.215;
    private static double intakeUpDownCollect = 0.14;
    private static double intakeUpDownTransferPrepare = 0.61;
    private static double intakeUpDownTransfer = 0.655;
    private static double intakeUpDownParkSpecimenSide = 0.465;

    private static double intakeUpDownCollectMaxOffset = 0.02;

    public static void updateCollectPosition(){
        if (inCollectPosition && !ActionManager.transferring){
            double position = intakeUpDownCollect + FrontSlides.getCurrentPercentage() * intakeUpDownCollectMaxOffset;
            Hardware.intakeUpDown.setPosition(position);
        }
    }

    public static void updateConfig(){
        Hardware.intakeUpDown.setPosition(configUpDown);
        Hardware.opener.setPosition(configOpener);
        Hardware.intakeLeft.setPower(configCollectPower * intakeLeftDirection);
        Hardware.intakeRight.setPower(configCollectPower * intakeRightDirection);
    }

    public static void Inspection(){
        Hardware.intakeUpDown.setPosition(intakeUpDownInspection);
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
        configUpDown = intakeUpDownInit;
        configOpener = openerClosed;
        configCollectPower = 0;
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
        Hardware.intakeLeft.setPower(collectPower * intakeLeftDirection);
        Hardware.intakeRight.setPower(collectPower * intakeRightDirection);
        Hardware.intakeUpDown.setPosition(intakeUpDownCollect);
        Hardware.opener.setPosition(openerCollect);
    }
    public static void collectWide(){
        inCollectPosition = true;
        collecting = true;
        spittingGround = false;
        Hardware.intakeLeft.setPower(collectPower * intakeLeftDirection);
        Hardware.intakeRight.setPower(collectPower * intakeRightDirection);
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
        Hardware.intakeLeft.setPower(spitOutPower * intakeLeftDirection / 4);
        Hardware.intakeRight.setPower(spitOutPower * intakeRightDirection / 4);
        Hardware.opener.setPosition(openerCollect);
    }
    public static void spitoutGround(){
        inCollectPosition = true;
        collecting = false;
        spittingGround = true;
        Hardware.intakeLeft.setPower(spitOutPower * intakeLeftDirection);
        Hardware.intakeRight.setPower(spitOutPower * intakeRightDirection);
        Hardware.intakeUpDown.setPosition(intakeUpDownCollect);
        Hardware.opener.setPosition(openerClosed);
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
    public static void transferPrepare(){
        inCollectPosition = false;
        collecting = false;
        spittingGround = false;
        Hardware.opener.setPosition(openerClosed);
        Hardware.intakeUpDown.setPosition(intakeUpDownTransferPrepare);
    }

    public static void startCollect(){
        Hardware.intakeLeft.setPower(collectPower * intakeLeftDirection);
        Hardware.intakeRight.setPower(collectPower * intakeRightDirection);
    }
    public static void startCollectSlow(){
        Hardware.intakeLeft.setPower(collectPowerSlow * intakeLeftDirection);
        Hardware.intakeRight.setPower(collectPowerSlow * intakeRightDirection);
    }

    public static void parkSpecimenSide(){
        inCollectPosition = false;
        collecting = false;
        spittingGround = false;
        Hardware.intakeUpDown.setPosition(intakeUpDownParkSpecimenSide);
    }

    public static void close(){
        Hardware.opener.setPosition(openerClosed);
    }


}
