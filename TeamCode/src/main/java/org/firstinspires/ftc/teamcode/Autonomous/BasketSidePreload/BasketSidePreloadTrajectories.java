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
                .lineToLinearHeading(new Pose2d(-59.48, -55, Math.toRadians(66.00)))
//                        SampleMecanumDrive.getVelocityConstraint(60.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

    }

    public static TrajectorySequence goToBasketFirstSample(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-61.39, -54, Math.toRadians(86.00)))
                .build();

    }

    public static TrajectorySequence goToBasketSecondSample(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-57.26, -54, Math.toRadians(90.00)))
                .build();

    }

    public static TrajectorySequence goToBasketThirdSample(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-63.26, -56.28, Math.toRadians(90.00)))
//                        SampleMecanumDrive.getVelocityConstraint(60.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

    }

    public static TrajectorySequence distanceFromSubmersible(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-37, -13.5, Math.toRadians(0.00)))
                .build();

    }

    public static TrajectorySequence goToBasketFromSubmersible(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-54.26, -58, Math.toRadians(90.00)))
                .build();

    }

    public static TrajectorySequence goToBasketSamplePreloadSlower(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-58.23, -61, Math.toRadians(40.00)),
                        SampleMecanumDrive.getVelocityConstraint(30.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
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
                .lineToConstantHeading(new Vector2d(-53, -48),
                        SampleMecanumDrive.getVelocityConstraint(40.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence Forward(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(8,
                        SampleMecanumDrive.getVelocityConstraint(40.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }

    public static TrajectorySequence goToSecondSamplePreload(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate()).lineToLinearHeading(new Pose2d(17.64, -54.5, Math.toRadians(-20.00)))
                .build();

    }

    public static TrajectorySequence goToFirstSample(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-55.5, -46, Math.toRadians(66.00)),
                        SampleMecanumDrive.getVelocityConstraint(20.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToSecondSample()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(10,
                        SampleMecanumDrive.getVelocityConstraint(40.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

    }
    public static TrajectorySequence goToThirdSample()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-47.6, -36.00, Math.toRadians(139.00)))
                .forward(2)
                .build();
    }

    public static TrajectorySequence goToSubmersible(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .splineToLinearHeading(new Pose2d(-21.91, -10.12, Math.toRadians(0.00)), Math.toRadians(-0.00))
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

    public static TrajectorySequence Strafe(double amount){
        if (amount < 0){
            return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .strafeLeft(Math.abs(amount))
                    .build();
        }
        else{
            return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .strafeRight(amount)
                    .build();
        }
    }
}
