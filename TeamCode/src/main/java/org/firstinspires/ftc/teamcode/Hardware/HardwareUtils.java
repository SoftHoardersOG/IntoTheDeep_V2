package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.Hardware.RevColorSensor.RevColorSensorV3;
import org.firstinspires.ftc.teamcode.Hardware.revex.ExpansionHubEx;


public class HardwareUtils {
    public static HardwareMap hardwareMap;

    public static DcMotor getDc(String name) {
        return hardwareMap.get(DcMotor.class, name);
    }

    public static DcMotorEx getDcEx(String name) {
        return hardwareMap.get(DcMotorEx.class, name);
    }

    public static Servo getServo(String name) {
        return hardwareMap.get(Servo.class, name);
    }

    public static CRServo getCRServo ( String name) {return hardwareMap.get(CRServo.class, name);}

    public static AnalogInput getAnalogInput(String name) {
        return hardwareMap.get(AnalogInput.class, name);
    }

    public static TouchSensor getTouchSensor(String name) {
        return hardwareMap.get(TouchSensor.class, name);
    }

    public static RevColorSensorV3 getColorSensor(String name) {
        return hardwareMap.get(RevColorSensorV3.class, name);
    }

    public static ExpansionHubEx getExpansionHub(String name){
        return hardwareMap.get(ExpansionHubEx.class, name);
    }

    public static ModernRoboticsI2cGyro getGyro(String name){
        return hardwareMap.get(ModernRoboticsI2cGyro.class, name);
    }
}