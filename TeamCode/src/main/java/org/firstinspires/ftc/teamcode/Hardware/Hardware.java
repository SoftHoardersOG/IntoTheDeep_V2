package org.firstinspires.ftc.teamcode.Hardware;

import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getAnalogInput;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getCRServo;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getColorSensor;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getDcEx;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getDigitalChannel;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.getServo;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.Drivers.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Climb;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
//import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Utils.GameMap;


public class Hardware {
    public static Telemetry telemetry;

    public static DcMotorEx frontRight;
    public static DcMotorEx frontLeft;
    public static DcMotorEx backRight;
    public static DcMotorEx backLeft;

    public static SampleMecanumDrive drive;

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
    public static Servo claw;
    public static Servo climbBackLeft;
    public static Servo climbBackRight;
    public static Servo climbFrontLeft;
    public static Servo climbFrontRight;
    public static Servo sweeper;

    public static AnalogInput armLeftPotentiometer;
    public static AnalogInput armRightPotentiometer;

    public static AnalogInput potentiometer;
//    public static AnalogInput openerPotentiometer;

    public static AnalogInput climbLeftPotentiometer;
    public static AnalogInput climbRightPotentiometer;

    public static DigitalChannel sensor;

    public static RevColorSensorV3 colorSensor;

    public static IMU RobotIMU;

    public static Limelight3A limelight;

    public static GoBildaPinpointDriver odometry;


    public static void init(HardwareMap hardwareMap, Telemetry _telemetry) {
        HardwareUtils.hardwareMap = hardwareMap;
        telemetry = _telemetry;

        odometry = hardwareMap.get(GoBildaPinpointDriver.class, "odometry");

        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(3);

        limelight.start();

        RobotIMU = hardwareMap.get(IMU.class, "imu");
        RobotIMU.resetYaw();

        frontRight = getDcEx("frontRight");
        frontLeft = getDcEx("frontLeft");
        backRight = getDcEx("backRight");
        backLeft = getDcEx("backLeft");

        drive = new SampleMecanumDrive(hardwareMap);

        GameMap.init(drive);

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
        claw = getServo("claw");


        sweeper = getServo("sweeper");

        armLeftPotentiometer = getAnalogInput("armLeftPotentiometer");
        armRightPotentiometer = getAnalogInput("armRightPotentiometer");

        climbBackLeft = getServo("climbBackLeft");
        climbBackRight = getServo("climbBackRight");
        climbFrontLeft = getServo("climbFrontLeft");
        climbFrontRight = getServo("climbFrontRight");

        sweeper = getServo("sweeper");

        potentiometer = getAnalogInput("potentiometer");
//        openerPotentiometer = getAnalogInput("openerPotentiometer");

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

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

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

//        backSlides.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(15, 0, 0,0));
//        backSlides.setTargetPositionTolerance(80);

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

//        odometry.resetPosAndIMU();
//        odometry.recalibrateIMU();

        FrontSlides.initPosition();
        BackSlides.initPosition();
        Climb.extendInit();

        frontSlides.setPower(0);
        backSlides.setPower(0);
        climbLeftSlides.setPower(0);
        climbRightSlides.setPower(0);

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

        climbLeftSlides.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(10, 0, 0, 0));
        climbRightSlides.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(10, 0, 0, 0));

//        backSlides.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(15, 0, 0,0));
//        backSlides.setTargetPositionTolerance(80);


        frontSlides.setPower(1);
        backSlides.setPower(1);

        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Intake.init();

    }

    public static void startAuto(){
        backSlides.setPower(1);
        frontSlides.setPower(1);
        climbLeftSlides.setPower(1);
        climbRightSlides.setPower(1);
    }

    private static void initPositions(){

    }

    private static void initPositionsAuto(){

    }

}