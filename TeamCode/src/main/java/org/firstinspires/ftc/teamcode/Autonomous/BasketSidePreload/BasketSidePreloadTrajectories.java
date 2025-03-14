package org.firstinspires.ftc.teamcode.Autonomous.BasketSidePreload;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

public class BasketSidePreloadTrajectories {
    private static SampleMecanumDrive drive;

    public static void setDrive(SampleMecanumDrive Drive) {
        drive = Drive;
    }

    public static TrajectorySequence goToChamber(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToConstantHeading(new Vector2d(-6.23, -30),
                        SampleMecanumDrive.getVelocityConstraint(65.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }

    public static TrajectorySequence distanceFromChamber()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToConstantHeading(new Vector2d(-8.37, -50.90),
                        SampleMecanumDrive.getVelocityConstraint(65.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }

    public static TrajectorySequence goToBasket(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-59.23, -60.5, Math.toRadians(35.00)),
                        SampleMecanumDrive.getVelocityConstraint(40.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

    }

    public static TrajectorySequence goToBasketSamplePreload(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-55.48, -58, Math.toRadians(68.00)))
                .build();

    }

    public static TrajectorySequence goToBasketFirstSample(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-55.39, -58, Math.toRadians(90.00)))
                .build();

    }

    public static TrajectorySequence goToBasketSecondSample(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-57.26, -58, Math.toRadians(87.00)))
                .build();

    }

    public static TrajectorySequence goToBasketThirdSample(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-60.26, -56.00, Math.toRadians(90.00)))
//                        SampleMecanumDrive.getVelocityConstraint(60.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

    }

    public static TrajectorySequence distanceFromSubmersible(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-40, -13.5, Math.toRadians(90.00)))
                .build();

    }

    public static TrajectorySequence goToBasketFromSubmersible(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-57.50, -54, Math.toRadians(90.00)))
                .build();

    }

    public static TrajectorySequence goToFirstSample(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-55.5, -46, Math.toRadians(67.00)),
                        SampleMecanumDrive.getVelocityConstraint(20.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToSecondSample()
    {
        return drive.trajectorySequenceBuilder(new Pose2d(-55.39, -55, Math.toRadians(90.00)))
                .forward(10,
                        SampleMecanumDrive.getVelocityConstraint(13.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

    }
    public static TrajectorySequence goToThirdSample()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-47.6, -38.50, Math.toRadians(138.00)))
                .forward(4,
                        SampleMecanumDrive.getVelocityConstraint(8.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }

    public static TrajectorySequence goToThirdSampleRetry()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-47.6, -34.50, Math.toRadians(136.50)))
                .build();
    }

    public static TrajectorySequence goToSubmersible(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-16.91, -2.12, Math.toRadians(0.00)))
                .build();
    }

    public static TrajectorySequence goToSubmersible2(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-16.91, -12.12, Math.toRadians(0.00)))
                .build();
    }

    public static TrajectorySequence park()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .splineToLinearHeading(new Pose2d(-14, -0.81, Math.toRadians(180.00)), Math.toRadians(0.00))
                .build();
    }

    public static TrajectorySequence quickPark()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-35, -40, Math.toRadians(90.00)))
                .build();
    }

    public static TrajectorySequence Turn(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .turn(Math.toRadians(-20.00))
                .build();
    }

    public static TrajectorySequence Strafe(double amount){
        amount *= -1;
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-19.00, drive.getPoseEstimate().getY() + amount, Math.toRadians(0.00)),
                        SampleMecanumDrive.getVelocityConstraint(35.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
}
