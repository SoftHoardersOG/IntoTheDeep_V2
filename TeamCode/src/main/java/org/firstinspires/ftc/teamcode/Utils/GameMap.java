package org.firstinspires.ftc.teamcode.Utils;

import static org.firstinspires.ftc.teamcode.Hardware.Hardware.odometry;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;

public class GameMap {

    private static Pose2d basket = new Pose2d(-70, -70, Math.toRadians(45.00));
    public static Vector2d basketPosition = new Vector2d(-66.2, -66.2);
    public static double basketAngle = Math.toRadians(45.00);
    public static double chamberAngle = Math.toRadians(270.00);
    private static double initialHeading;
    public static double basketImuRotationOffset;
    public static double chamberImuRotationOffset;
    private static final double clawOffset = -5;

    private static SampleMecanumDrive drive;

    public static double normalizedAngleDegrees(double angle){
        double distance = angle / 360;
        if (distance < 0) {
            angle -= Math.floor(distance) * 360;
        }
        else if (distance > 1){
            angle -= Math.floor(distance) * 360;
        }
        return angle;
    }

    public static double rotatedOrigin(double mark, double angle){
        mark = normalizedAngleDegrees(mark);
        if (angle > mark) angle -= 360;
        return angle;
    }

    public static double normalizedAngle (double angle){
        angle = Math.toDegrees(angle);
        return Math.toRadians(normalizedAngleDegrees(angle));
    }

    public static void init(SampleMecanumDrive _drive){
        drive = _drive;
//        initialHeading = Math.toDegrees(drive.getPoseEstimate().getHeading());
//        basketImuRotationOffset = normalizedAngleDegrees(initialHeading);
//        basketAngle = basketImuRotationOffset - 360.00 + 225.00;
//        chamberImuRotationOffset = normalizedAngleDegrees(normalizedAngleDegrees(initialHeading) - 90.00);
//        chamberAngle = chamberImuRotationOffset - 360.00 + 180.00;
    }

    public static Pose2d clawPose(){
        return new Pose2d(drive.getPoseEstimate().vec().plus(drive.getPoseEstimate().headingVec().times(clawOffset)), drive.getPoseEstimate().getHeading() - Math.PI);
    }

    public static double angleToBasket(){
        return clawPose().vec().unaryMinus().plus(basket.vec()).angle();
    }

//    public static double clawAngleToBasket(){
//        return angleToBasket() - clawPose().getHeading();
//    }

    public static double clawAngleToBasketPrecise(){
        double angle = angleToBasket();
        double heading = clawPose().getHeading();
        while (angle - heading > Math.PI){
            angle -= 2 * Math.PI;
        }
        while (angle - heading < -Math.PI){
            angle += 2 * Math.PI;
        }
        return -1 * (angle - heading);
    }

    public static double clawAngleToBasket(){
//        Pose2D pos = odometry.getPosition();
//        return rotatedOrigin(basketImuRotationOffset, normalizedAngleDegrees(pos.getHeading(AngleUnit.DEGREES))) - basketAngle;
        double heading = drive.getPoseEstimate().getHeading();
        while (basketAngle - heading > Math.PI){
            basketAngle -= 2 * Math.PI;
        }
        while (basketAngle - heading < -Math.PI){
            basketAngle += 2 * Math.PI;
        }
        return heading - basketAngle;
    }

//    public static double clawAngleToChamber(){
//        return chamberAngle - clawPose().getHeading();
//    }

    public static double clawAngleToChamber(){
//        Pose2D pos = odometry.getPosition();
//        return rotatedOrigin(chamberImuRotationOffset, normalizedAngleDegrees(pos.getHeading(AngleUnit.DEGREES))) - chamberAngle;
        double heading = drive.getPoseEstimate().getHeading();
        while (chamberAngle - heading > Math.PI){
            chamberAngle -= 2 * Math.PI;
        }
        while (chamberAngle - heading < -Math.PI){
            chamberAngle += 2 * Math.PI;
        }
        return heading - chamberAngle;
    }

    public static double clawAngleToBasketPrecisePercentage(){
        return 1.00 * ((int)(clawAngleToBasketPrecise() / (2 * Math.PI) * 100)) / 100.00;
    }

    public static double clawAngleToBasketPercentage(){
        return 1.00 * ((int)(clawAngleToBasket() / (2 * Math.PI) * 100)) / 100.00;
    }

    public static double clawAngleToChamberPercentage(){
        return 1.00 * ((int)(clawAngleToChamber() / (2 * Math.PI) * 100)) / 100.00;
    }

}
