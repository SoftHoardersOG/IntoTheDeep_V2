package org.firstinspires.ftc.teamcode.Utils;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class Potentiometer {
    public static boolean isFrontArmUp(){
        return Hardware.potentiometer.getVoltage()<1.2;
    }
}
