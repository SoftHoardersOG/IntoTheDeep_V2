package org.firstinspires.ftc.teamcode.Autonomous.BasketSidePreload;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;

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
                .lineToLinearHeading(new Pose2d(-59.23, -57.5, Math.toRadians(30.00)),
                        SampleMecanumDrive.getVelocityConstraint(40.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

    }

    public static TrajectorySequence goToBasketFaster(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-60.23, -59.5, Math.toRadians(60.00)),
                        SampleMecanumDrive.getVelocityConstraint(45.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

    }

    public static TrajectorySequence distanceFromBasket(){
        return  drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-51.62, -53.22, Math.toRadians(45.00)),
                        SampleMecanumDrive.getVelocityConstraint(40.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

    }

    public static TrajectorySequence distanceFromWall(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(15,
                        SampleMecanumDrive.getVelocityConstraint(40.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence Forward(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(2,
                        SampleMecanumDrive.getVelocityConstraint(20.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToFirstSample(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-49, -49.5, Math.toRadians(90.00)),
                        SampleMecanumDrive.getVelocityConstraint(65.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }

    public static TrajectorySequence goToFirstSampleSamplePreload(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-49, -49.5, Math.toRadians(89.00)),
                        SampleMecanumDrive.getVelocityConstraint(50.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToSecondSample()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-59.5, -49.5, Math.toRadians(80.00)),
                        SampleMecanumDrive.getVelocityConstraint(65.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

    }
    public static TrajectorySequence goToSecondSampleSamplePreload()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-59.5, -49.5, Math.toRadians(86.00)),
                        SampleMecanumDrive.getVelocityConstraint(50.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

    }
    public static TrajectorySequence goToThirdSample()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-49.00, -33.00, Math.toRadians(162.00)),
                        SampleMecanumDrive.getVelocityConstraint(65.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToThirdSampleSamplePreload()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-54.00, -32.50, Math.toRadians(150.00)),
                        SampleMecanumDrive.getVelocityConstraint(50.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }

    public static TrajectorySequence park()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .splineToLinearHeading(new Pose2d(-18, -0.81, Math.toRadians(180.00)), Math.toRadians(0.00))
                .build();
    }

    public static TrajectorySequence Turn(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .turn(Math.toRadians(-20.00))
                .build();
    }
}
