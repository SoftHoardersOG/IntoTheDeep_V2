package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;

public class Movement {
    public static boolean peAnglia;

    private static SampleMecanumDrive drive;
    private static final double maxLimitSpeed = 2.5;
    public static double limitSpeedMultiplier = 1;
    private static double limitSpeed = 1;
    private static double sensitivity = 1;

    private static double gamepadThreshold = 0.07;

    private static double lerp(double f) {
        return ((1.0 - f)) + (Movement.maxLimitSpeed * f);
    }

    public static void init(SampleMecanumDrive _drive) {
        drive = _drive;
        peAnglia = true;
    }

    public static void run(Gamepad gamepad1) {
        setLimitSpeed(gamepad1);
        fullSpeed(gamepad1);

        Pose2d drivePower;

        if (peAnglia) {
            Vector2d inputTranslational = new Vector2d(-gamepad1.right_stick_y, -gamepad1.right_stick_x);
            drivePower = new Pose2d(
                    -gamepad1.right_stick_y / limitSpeed * sensitivity,
                    -gamepad1.right_stick_x / limitSpeed * sensitivity,
                    -gamepad1.left_stick_x / limitSpeed * sensitivity
            );
            if (inputTranslational.norm() < gamepadThreshold) {
                drivePower = new Pose2d(0, 0, drivePower.getHeading());
            }
            if (Math.abs(gamepad1.left_stick_x) < gamepadThreshold){
                drivePower = new Pose2d(drivePower.vec(), 0);
            }
        } else {
            Vector2d inputTranslational = new Vector2d(-gamepad1.left_stick_y, -gamepad1.left_stick_x);
            drivePower = new Pose2d(
                    -gamepad1.left_stick_y / limitSpeed * sensitivity,
                    -gamepad1.left_stick_x / limitSpeed * sensitivity,
                    -gamepad1.right_stick_x / limitSpeed * sensitivity
            );
            if (inputTranslational.norm() < gamepadThreshold) {
                drivePower = new Pose2d(0, 0, drivePower.getHeading());
            }
            if (Math.abs(gamepad1.right_stick_x) < gamepadThreshold){
                drivePower = new Pose2d(drivePower.vec(), 0);
            }
        }

        drive.setWeightedDrivePower(drivePower);
        drive.update();
    }

    private static void fullSpeed(Gamepad gamepad) {
        if (gamepad.left_trigger >= 0.05 && !ActionManager.transferring) {
            sensitivity = 1;
            limitSpeed = 1;
        } else {
            sensitivity = 0.8;
        }
    }

    private static void setLimitSpeed(Gamepad gamepad) {
        if (ActionManager.transferring) {
            limitSpeed = lerp(0);
        } else {
            limitSpeed = lerp(gamepad.right_trigger);
        }
        limitSpeed *= limitSpeedMultiplier;
    }

    public static void updateLimitSpeedMultiplier(){
//        if (FrontSlides.getCurrentPercentage() > 0.3) limitSpeedMultiplier = 1.7;
//        else limitSpeedMultiplier = 1;
        limitSpeedMultiplier = 1;
    }

}