package org.firstinspires.ftc.teamcode.Hardware;

import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getAnalogInput;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getCRServo;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getColorSensor;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getDSensor;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getDc;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getDcEx;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getDigitalChannel;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getServo;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getTouchSensor;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorREV2mDistance;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Climb;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
//import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.Utils.Sleep;

import java.security.PublicKey;


public class Hardware {
    public static Telemetry telemetry;

    public static DcMotorEx frontRight;
    public static DcMotorEx frontLeft;
    public static DcMotorEx backRight;
    public static DcMotorEx backLeft;

    public static DcMotorEx frontSlides;
    public static DcMotorEx backSlides;

    public static DcMotorEx climbLeftSlides;
    public static DcMotorEx climbRightSlides;

    public static CRServo intakeLeft;
    public static CRServo intakeRight;

    public static Servo intakeUpDown;
    public static Servo opener;
    public static Servo armLeft;
    public static Servo armRight;
    public static Servo rotateClaw;
    public static Servo claw;
    public static Servo climbBackLeft;
    public static Servo climbBackRight;
    public static Servo climbFrontLeft;
    public static Servo climbFrontRight;

    public static AnalogInput potentiometer;
    public static AnalogInput openerPotentiometer;

    public static AnalogInput climbLeftPotentiometer;
    public static AnalogInput climbRightPotentiometer;

    public static DigitalChannel sensor;

    public static RevColorSensorV3 colorSensor;


    public static void init(HardwareMap hardwareMap, Telemetry _telemetry) {
        HardwareUtils.hardwareMap = hardwareMap;
        telemetry = _telemetry;

        frontRight = getDcEx("frontRight");
        frontLeft = getDcEx("frontLeft");
        backRight = getDcEx("backRight");
        backLeft = getDcEx("backLeft");

        frontSlides = getDcEx("frontSlides");
        backSlides = getDcEx("backSlides");

        climbLeftSlides = getDcEx("climbLeftSlides");
        climbRightSlides = getDcEx("climbRightSlides");

        intakeLeft = getCRServo("intakeLeft");
        intakeRight = getCRServo("intakeRight");
        intakeUpDown = getServo("intakeUpDown");
        opener = getServo("opener");

        armLeft = getServo("armLeft");
        armRight = getServo("armRight");
        rotateClaw = getServo("rotateClaw");
        claw = getServo("claw");

        climbBackLeft = getServo("climbBackLeft");
        climbBackRight = getServo("climbBackRight");
        climbFrontLeft = getServo("climbFrontLeft");
        climbFrontRight = getServo("climbFrontRight");

        potentiometer = getAnalogInput("potentiometer");
        openerPotentiometer = getAnalogInput("openerPotentiometer");

        climbLeftPotentiometer = getAnalogInput("climbLeftPotentiometer");
        climbRightPotentiometer = getAnalogInput("climbRightPotentiometer");

        sensor = getDigitalChannel("sensor");
        sensor.setMode(DigitalChannel.Mode.INPUT);

        colorSensor = getColorSensor("colorSensor");

    }

    public static void configureTeleOp(){
//        MotorConfigurationType backSlidesConfig = backSlides.getMotorType().clone();
//        backSlidesConfig.setGearing(5.2);
//        backSlidesConfig.setTicksPerRev(145.1);
//        backSlidesConfig.setMaxRPM(1150);
//
//        backSlides.setMotorType(backSlidesConfig);

        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //frontSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //backSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //climbLeftSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //climbRightSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        climbLeftSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        climbRightSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        climbLeftSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        climbRightSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backSlides.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(16, 0, 0,0));
//        frontSlides.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(20, 0, 0,0));
        backSlides.setTargetPositionTolerance(30);
//        frontSlides.setTargetPositionTolerance(10);

        backSlides.setPower(1);
        frontSlides.setPower(1);
        climbLeftSlides.setPower(1);
        climbRightSlides.setPower(1);

//        FrontSlides.initPosition();
        BackSlides.initPosition();
        Climb.extendInit();

        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Intake.init();

    }

    public static void configureAuto(){
//        MotorConfigurationType backSlidesConfig = backSlides.getMotorType().clone();
//        backSlidesConfig.setGearing(5.2);
//        backSlidesConfig.setTicksPerRev(145.1);
//        backSlidesConfig.setMaxRPM(1150);

        FrontSlides.initPosition();
        BackSlides.initPosition();
        Climb.extendInit();

//        backSlides.setMotorType(backSlidesConfig);
//        frontSlides.setMotorType(backSlidesConfig);

        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        climbLeftSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        climbRightSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        climbLeftSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        climbRightSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        climbLeftSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        climbRightSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backSlides.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(16, 0, 0,0));
//        frontSlides.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(20, 0, 0,0));
        backSlides.setTargetPositionTolerance(30);
//        frontSlides.setTargetPositionTolerance(10);

        backSlides.setPower(1);
        frontSlides.setPower(1);
        climbLeftSlides.setPower(1);
        climbRightSlides.setPower(1);


        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Intake.init();

    }

    private static void initPositions(){

    }

    private static void initPositionsAuto(){

    }

}