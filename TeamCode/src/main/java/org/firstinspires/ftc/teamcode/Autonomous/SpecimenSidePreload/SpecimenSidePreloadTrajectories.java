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
                .lineToLinearHeading(new Pose2d(0, -28, Math.toRadians(-70.00)))
                .build();
    }

    public static TrajectorySequence goToChamber(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToConstantHeading(new Vector2d(3, -31))
                .build();
    }

    public static TrajectorySequence goToPark(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToSplineHeading(new Pose2d(29.70, -64.48, Math.toRadians(-15.00)))
                .build();
    }

    public static TrajectorySequence goToPark2(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToSplineHeading(new Pose2d(29.70, -60.48, Math.toRadians(0.00)))
                .build();
    }


    public static TrajectorySequence TurnToObservationZoneOne()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .turn(Math.toRadians(-125.00))
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
                .lineToLinearHeading(new Pose2d(43, -48.6, Math.toRadians(57.50)),
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .forward(3,
                        SampleMecanumDrive.getVelocityConstraint(8.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

    }
    public static TrajectorySequence goToSecondSample()
    {
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(42, -48.51, Math.toRadians(38.00)),
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
                .lineToLinearHeading(new Pose2d(47, -41.6, Math.toRadians(21.00)),
                        SampleMecanumDrive.getVelocityConstraint(60.00, Math.toRadians(100),DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .forward(5,
                        SampleMecanumDrive.getVelocityConstraint(7.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .back(4)
                .build();

    }
    public static TrajectorySequence goToFirstSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(7,
                        SampleMecanumDrive.getVelocityConstraint(11.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToSecondSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(19.70, -49.08, Math.toRadians(-34.00)),
                        SampleMecanumDrive.getVelocityConstraint(30.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToThirdSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(19.70, -49.08, Math.toRadians(-34.00)),
                        SampleMecanumDrive.getVelocityConstraint(30.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToFourthSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(19.70, -49.08, Math.toRadians(-34.00)),
                        SampleMecanumDrive.getVelocityConstraint(30.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence collectSecondSpecimen(){
        return drive.trajectorySequenceBuilder(new Pose2d(19.70, -49.08, Math.toRadians(-34.00)))
                .forward(10,
                        SampleMecanumDrive.getVelocityConstraint(15.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence collectThirdSpecimen(){
        return drive.trajectorySequenceBuilder(new Pose2d(19.70, -49.08, Math.toRadians(-34.00)))
                .forward(10,
                        SampleMecanumDrive.getVelocityConstraint(15.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence collectFourthSpecimen(){
        return drive.trajectorySequenceBuilder(new Pose2d(19.70, -49.08, Math.toRadians(-34.00)))
                .forward(10,
                        SampleMecanumDrive.getVelocityConstraint(15.00, DriveConstants.MAX_ANG_VEL,DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
    }
    public static TrajectorySequence goToScoreFirstSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(12.00, -49.92, Math.toRadians(-80.00)))
                .build();
    }
    public static TrajectorySequence scoreFirstSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(10.00, -27.00, Math.toRadians(-70.00)))
                .build();
    }
    public static TrajectorySequence scoreSecondSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(13.00, -27.00, Math.toRadians(-70.00)))
                .build();
    }
    public static TrajectorySequence scoreThirdSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(10.00, -27.00, Math.toRadians(-70.00)))
                .build();
    }
    public static TrajectorySequence scoreFourthSpecimen(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(16.00, -27.00, Math.toRadians(-70.00)))
                .build();
    }
    public static TrajectorySequence scoreLastSample(){
        return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-51, -62.00, Math.toRadians(15.00)))
                .build();
    }
}
