package org.firstinspires.ftc.teamcode.Utils;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class Potentiometers {
    public static double armLeftPosition(){
        return Hardware.armLeftPotentiometer.getVoltage() / Hardware.armLeftPotentiometer.getMaxVoltage();
    }

    public static double armRightPosition(){
        return (Hardware.armRightPotentiometer.getMaxVoltage() - Hardware.armRightPotentiometer.getVoltage()) / Hardware.armRightPotentiometer.getMaxVoltage();
    }

    public static double climbLeftPosition(){
        return (Hardware.climbLeftPotentiometer.getMaxVoltage() - Hardware.climbLeftPotentiometer.getVoltage()) / Hardware.climbLeftPotentiometer.getMaxVoltage();
    }

    public static double climbRightPosition(){
        return Hardware.climbRightPotentiometer.getVoltage() / Hardware.climbRightPotentiometer.getMaxVoltage();
    }

    public static boolean inRange(double position, double left, double right){
        return position >= left && position <= right;
    }

    public static boolean intakeUpDownCloseToTransfer(){
        return Hardware.potentiometer.getVoltage() > 1.4;
    }
    public static boolean intakeUpDownInTransfer(){
        return Hardware.potentiometer.getVoltage() > 2.1;
    }
    public static boolean openerClosed(){
//        return Hardware.openerPotentiometer.getVoltage() > 2.35
        return true;
    }
}
