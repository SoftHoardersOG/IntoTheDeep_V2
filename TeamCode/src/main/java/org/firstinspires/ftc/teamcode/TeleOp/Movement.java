package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.util.MathUtil;
import com.acmerobotics.roadrunner.util.MathUtilKt;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.apache.commons.math3.util.MathUtils;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;

public class Movement {
    private static SampleMecanumDrive drive;
    private static double maxLimitSpeed = 3;
    private static double limitSpeed;
    private static double sensitivity = 0.8;
    private static double speed;
    private static boolean enabled = true;

    private static double lerp(double a, double b, double f)
    {
        return (a * (1.0 - f)) + (b * f);
    }

    public static void init(SampleMecanumDrive _drive) {
        drive = _drive;
    }

    public static void run(Gamepad gamepad1) {
        setLimitSpeed(gamepad1);

        Pose2d drivePower = new Pose2d(
                -gamepad1.left_stick_y / limitSpeed * sensitivity,
                -gamepad1.left_stick_x / limitSpeed * sensitivity,
                gamepad1.right_stick_x / limitSpeed * sensitivity
        );

        /*
        Pose2d drivePower = new Pose2d(
                -gamepad1.left_stick_y / limitSpeed,
                -gamepad1.left_stick_x / limitSpeed,
                -gamepad1.right_stick_x / limitSpeed
        );
        */

        drive.setWeightedDrivePower(drivePower);
        speed = new Vector2d(Math.abs(gamepad1.right_stick_y), Math.abs(gamepad1.right_stick_x)).distTo(new Vector2d(0, 0));
        drive.update();
    }

    private static void setLimitSpeed(Gamepad gamepad) {
        limitSpeed = lerp(1, maxLimitSpeed, gamepad.right_trigger);
    }

    public static double getSpeed(){
        return speed;
    }

    public static void disable(){
        enabled = false;
    }

    public static void enable(){
        enabled = true;
    }
}