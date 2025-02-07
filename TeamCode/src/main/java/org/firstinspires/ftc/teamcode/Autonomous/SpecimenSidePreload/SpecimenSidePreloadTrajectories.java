package org.firstinspires.ftc.teamcode.Autonomous.SpecimenSidePreload;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

public class SpecimenSidePreloadTrajectories {

    private static SampleMecanumDrive drive;

    public static void setDrive(SampleMecanumDrive Drive) {
        drive = Drive;
    }

    public static TrajectorySequence placePreload(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(3, -30, Math.toRadians(-60.00)))
                .build();
    }

    public static TrajectorySequence goToChamber(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToConstantHeading(new Vector2d(3, -31))
                .build();
    }

    public static TrajectorySequence goToPark(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToSplineHeading(new Pose2d(29.70, -53.48, Math.toRadians(-15.00)))
                .build();
    }

    public static TrajectorySequence TurnToObservationZoneOne()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .turn(Math.toRadians(-115.00))
                .build();
    }

    public static TrajectorySequence Forward(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(2,
                        SampleMecanumDrive.getVelocityConstraint(20.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence Back(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .back(3.5,
                        SampleMecanumDrive.getVelocityConstraint(25.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
//    public static TrajectorySequence distanceFromChamber()
//    {
//        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
//                .lineToHeading(new Vector2d(9.68, -46.17))
//                .build();
//    }
    public static TrajectorySequence goToFirstSample()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(37, -45, Math.toRadians(43.00)))
                .build();

    }
    public static TrajectorySequence goToSecondSample()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(42, -48.51, Math.toRadians(42.00)),
                        SampleMecanumDrive.getVelocityConstraint(45.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .forward(6,
                        SampleMecanumDrive.getVelocityConstraint(9.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToThirdSample()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(45.73, -41.32, Math.toRadians(22.00)),
                        SampleMecanumDrive.getVelocityConstraint(60.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .forward(6,
                        SampleMecanumDrive.getVelocityConstraint(20.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .back(4)
                .build();

    }
    public static TrajectorySequence goToFirstSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(13,
                        SampleMecanumDrive.getVelocityConstraint(15.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToSecondSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToSplineHeading(new Pose2d(22.20, -48.08, Math.toRadians(-34.00)),
                        SampleMecanumDrive.getVelocityConstraint(65.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToThirdSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToSplineHeading(new Pose2d(22.20, -48.08, Math.toRadians(-34.00)),
                        SampleMecanumDrive.getVelocityConstraint(65.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToFourthSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToSplineHeading(new Pose2d(22.20, -48.08, Math.toRadians(-34.00)),
                        SampleMecanumDrive.getVelocityConstraint(65.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence collectSecondSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(5,
                        SampleMecanumDrive.getVelocityConstraint(15.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence collectThirdSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(5,
                        SampleMecanumDrive.getVelocityConstraint(15.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence collectFourthSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(5,
                        SampleMecanumDrive.getVelocityConstraint(15.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToScoreFirstSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(8.00, -44.92, Math.toRadians(-90.00)))
                .build();
    }
    public static TrajectorySequence scoreFirstSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(7.00, -30.00, Math.toRadians(-60.00)))
                .build();
    }
    public static TrajectorySequence scoreSecondSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(8.00, -30.00, Math.toRadians(-60.00)))
                .build();
    }
    public static TrajectorySequence scoreThirdSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(11.00, -30.00, Math.toRadians(-60.00)))
                .build();
    }
    public static TrajectorySequence scoreFourthSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(10.00, -30.00, Math.toRadians(-60.00)))
                .build();
    }
}
