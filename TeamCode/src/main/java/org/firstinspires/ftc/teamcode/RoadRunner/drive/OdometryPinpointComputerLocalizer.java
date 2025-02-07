package org.firstinspires.ftc.teamcode.RoadRunner.drive;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.localization.Localizer;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Drivers.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class OdometryPinpointComputerLocalizer implements Localizer {

    public static double X_MULTIPLIER = 1;
    public static double Y_MULTIPLIER = 1;

    private Pose2d poseEstimate = new Pose2d();
    private Pose2d poseVelocity = new Pose2d();

    private Pose2d initialPose;
    private Pose2d travelOffset;

    private double previousRawAngle;

    private GoBildaPinpointDriver odometry;

    public OdometryPinpointComputerLocalizer(HardwareMap hardwareMap){
        odometry = hardwareMap.get(GoBildaPinpointDriver.class, "odometry");
        odometry.setOffsets(-23.5, -75.2);
        odometry.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odometry.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        initialPose = new Pose2d(0, 0, 0);
        travelOffset = new Pose2d(0, 0, 0);
        previousRawAngle = 0;
        odometry.resetPosAndIMU();
        odometry.update();
    }

    public void updateTravelledOffset(){
        Pose2D odometryPose = odometry.getPosition();

        double rawAngle = odometryPose.getHeading(AngleUnit.RADIANS);
        double delta = rawAngle - previousRawAngle;

        if (delta > Math.PI) {
            delta -= 2 * Math.PI;
        } else if (delta < -Math.PI) {
            delta += 2 * Math.PI;
        }

        travelOffset = new Pose2d(odometryPose.getY(DistanceUnit.INCH) * X_MULTIPLIER, -odometryPose.getX(DistanceUnit.INCH) * Y_MULTIPLIER, travelOffset.getHeading() + delta);

        previousRawAngle = rawAngle;
    }

    @Override
    public void update() {
        odometry.update();

        // Fetch external localization data
        updateTravelledOffset();

        Pose2D vel = odometry.getVelocity();
        Pose2d velRR = new Pose2d(new Vector2d(vel.getY(DistanceUnit.INCH), vel.getX(DistanceUnit.INCH)).rotated(initialPose.getHeading()), vel.getHeading(AngleUnit.RADIANS));

        // Update velocity (use computed external velocity or estimate it)
        poseVelocity = velRR;

        // Update pose estimate
        poseEstimate = initialPose.plus(new Pose2d(travelOffset.vec().rotated(initialPose.getHeading()), travelOffset.getHeading()));
    }

    @Override
    public Pose2d getPoseEstimate() {
        return poseEstimate;
    }

    @Override
    public void setPoseEstimate(Pose2d pose) {
        initialPose = pose;
        travelOffset = new Pose2d(0, 0, 0);
        previousRawAngle = 0;
        odometry.resetPosAndIMU();
        odometry.update();
        poseEstimate = initialPose;
        poseVelocity = new Pose2d(0, 0, 0);
    }

    @Override
    public Pose2d getPoseVelocity() {
        return poseVelocity;
    }
}
