package org.firstinspires.ftc.teamcode.Utils;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;

public class GameMap {

    private static Pose2d basket = new Pose2d(-70, -70, Math.toRadians(45.00));
    public static double basketAngle = Math.toRadians(45.00);
    public static double chamberAngle = Math.toRadians(270.00);
    private static final double clawOffset = -4;

    private static SampleMecanumDrive drive;

    public static void init(SampleMecanumDrive _drive){
        drive = _drive;
    }

    public static boolean inLowerArea(){
        return drive.getPoseEstimate().getY() > -56 && drive.getPoseEstimate().getY() < -25
                && drive.getPoseEstimate().getX() > -19 && drive.getPoseEstimate().getX() < 19
                && drive.getHeadingNormalized() > Math.toRadians(30) && drive.getHeadingNormalized() < Math.toRadians(150);
    }

    public static Pose2d clawPose(){
        return new Pose2d(drive.getPoseEstimate().vec().plus(drive.getPoseEstimate().headingVec().times(clawOffset)), drive.getPoseEstimate().getHeading() - Math.PI);
    }

    public static double angleToBasket(){
        return clawPose().vec().unaryMinus().plus(basket.vec()).angle();
    }

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
        double heading = drive.getPoseEstimate().getHeading();
        while (basketAngle - heading > Math.PI){
            basketAngle -= 2 * Math.PI;
        }
        while (basketAngle - heading < -Math.PI){
            basketAngle += 2 * Math.PI;
        }
        return heading - basketAngle;
    }

    public static double clawAngleToChamber(){
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
