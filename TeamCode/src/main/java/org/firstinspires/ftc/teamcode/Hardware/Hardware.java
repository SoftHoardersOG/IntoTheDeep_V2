package org.firstinspires.ftc.teamcode.Hardware;

import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getAnalogInput;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getCRServo;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getColorSensor;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getDc;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getDcEx;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getServo;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getTouchSensor;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.Utils.Sleep;


public class Hardware {
    public static Telemetry telemetry;

    public static DcMotorEx frontRight;
    public static DcMotorEx frontLeft;
    public static DcMotorEx backRight;
    public static DcMotorEx backLeft;

    public static DcMotor frontSlides;
    public static DcMotor backSlides;

    public static CRServo intakeLeft;
    public static CRServo intakeRight;

    public static Servo intakeUpDown;
    public static Servo armLeft;
    public static Servo armRight;
    public static Servo rotateClaw;
    public static Servo claw;



    public static void init(HardwareMap hardwareMap, Telemetry _telemetry) {
        HardwareUtils.hardwareMap = hardwareMap;
        telemetry = _telemetry;

        frontRight = getDcEx("frontRight");
        frontLeft = getDcEx("frontLeft");
        backRight = getDcEx("backRight");
        backLeft = getDcEx("backLeft");

        frontSlides = getDc("frontSlides");
        backSlides = getDc("backSlides");

        intakeLeft = getCRServo("intakeLeft");
        intakeRight = getCRServo("intakeRight");

        intakeUpDown = getServo("intakeUpDown");
        armLeft = getServo("armLeft");
        armRight = getServo("armRight");
        rotateClaw = getServo("rotateClaw");
        claw = getServo("claw");

    }

    public static void configureTeleOp(){
        frontSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontSlides.setPower(1);
        backSlides.setPower(1);

    }

    public static void configureAuto(){

    }

    private static void initPositions(){

    }

    private static void initPositionsAuto(){

    }

}