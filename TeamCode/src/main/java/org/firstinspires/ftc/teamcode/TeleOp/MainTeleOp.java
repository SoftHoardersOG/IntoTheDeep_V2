package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.Utils.Initializations;
import org.firstinspires.ftc.teamcode.Utils.LedManager;
import org.firstinspires.ftc.teamcode.Utils.OneTap;
import org.firstinspires.ftc.teamcode.Utils.Rumble;

@TeleOp
public class MainTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
//        drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate()).lineToLinearHeading(drive.getPoseEstimate()).build());
        Initializations.initTeleOp(drive, hardwareMap, telemetry, gamepad1, gamepad2);
        //Rumble.start();
        while (opModeInInit() && !isStopRequested()){
            LedManager.update();
//            FrontSlides.update();
//            if (FrontSlides.isStuck()){
//                FrontSlides.initPosition();
//            }
            if (gamepad1.share){
                Movement.peAnglia = false;
            }
            else if (gamepad1.options) {
                Movement.peAnglia = true;
            }
            TelemetryManager.manage();
        }
//        FrontSlides.release();
//        Intake.neutral();
        while (opModeIsActive() && !isStopRequested()) {
            LedManager.update();
            Movement.run(gamepad1);
            ActionManager.control(gamepad1, gamepad2);
            TelemetryManager.manage();
        }
    }
}
