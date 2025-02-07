package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;

public class Movement {
    public static boolean peAnglia;

    private static SampleMecanumDrive drive;
    private static double transferSlowDown = 0;
    private static double maxLimitSpeed = 2.5;
    private static double limitSpeed = 1;
    private static double sensitivity = 1;
    private static double speed;

    private static double lerp(double a, double b, double f)
    {
        return (a * (1.0 - f)) + (b * f);
    }

    public static void init(SampleMecanumDrive _drive) {
        drive = _drive;
        peAnglia = true;
    }

    public static void run(Gamepad gamepad1) {
        setLimitSpeed(gamepad1);
        fullSpeed(gamepad1);

        Pose2d drivePower;

        if (peAnglia){
            drivePower = new Pose2d(
                    -gamepad1.right_stick_y / limitSpeed * sensitivity,
                    -gamepad1.right_stick_x / limitSpeed * sensitivity,
                    -gamepad1.left_stick_x / limitSpeed * sensitivity
            );
        }
        else{
            drivePower = new Pose2d(
                    -gamepad1.left_stick_y / limitSpeed * sensitivity,
                    -gamepad1.left_stick_x / limitSpeed * sensitivity,
                    -gamepad1.right_stick_x / limitSpeed * sensitivity
            );
        }

        drive.setWeightedDrivePower(drivePower);
        drive.update();
    }

    public static void setDrivePower(Pose2d power){
        drive.setWeightedDrivePower(power);
        drive.update();
    }

    private static void fullSpeed(Gamepad gamepad){
        if (gamepad.left_trigger >= 0.05 && !ActionManager.transferring){
            sensitivity = 1;
            limitSpeed = 1;
        }
        else{
            sensitivity = 0.8;
        }
    }

    private static void setLimitSpeed(Gamepad gamepad) {
        if (ActionManager.transferring){
            limitSpeed = lerp(1, maxLimitSpeed, transferSlowDown);
        }
        else{
            limitSpeed = lerp(1, maxLimitSpeed, gamepad.right_trigger);
        }
    }

    public static double getSpeed(){
        return speed;
    }
}