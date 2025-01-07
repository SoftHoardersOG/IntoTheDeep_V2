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
    public static TrajectorySequence distanceFromChamber()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToConstantHeading(new Vector2d(9.68, -46.17))
                .build();
    }
    public static TrajectorySequence goToFirstSample()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(36.59, -46.00, Math.toRadians(54.00)),
                        SampleMecanumDrive.getVelocityConstraint(65.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
//                .forward(3,
//                        SampleMecanumDrive.getVelocityConstraint(20.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToSecondSample()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(38.69, -48.51, Math.toRadians(44.50)),
                        SampleMecanumDrive.getVelocityConstraint(60.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .forward(6,
                        SampleMecanumDrive.getVelocityConstraint(9.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToThirdSample()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(45.73, -41.32, Math.toRadians(30.00)),
                        SampleMecanumDrive.getVelocityConstraint(65.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .forward(8,
                        SampleMecanumDrive.getVelocityConstraint(15.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
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
                .lineToConstantHeading(new Vector2d(3.33, -32.00))
                .build();
    }
    public static TrajectorySequence scoreSecondSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(2.3, -31.00, Math.toRadians(-92.00)))
                .build();
    }
    public static TrajectorySequence scoreThirdSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(6.1, -30.00, Math.toRadians(-92.00)))
                .build();
    }
    public static TrajectorySequence scoreFourthSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(8.00, -30.00, Math.toRadians(-92.00)))
                .build();
    }
}
