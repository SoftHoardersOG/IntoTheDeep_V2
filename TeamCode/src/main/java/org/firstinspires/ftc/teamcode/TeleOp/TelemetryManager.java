package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.Hardware.Hardware.drive;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.frontSlides;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.telemetry;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Hardware.ColorSensor;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Hardware.Limelight;
import org.firstinspires.ftc.teamcode.Utils.GameMap;
import org.firstinspires.ftc.teamcode.Utils.Potentiometers;

import java.util.List;
import java.util.Locale;

public class TelemetryManager {

    private static FtcDashboard dashboard;

    private static List<LLResultTypes.DetectorResult> detectorResults;

    public static void init() {

        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(100);
    }

    public static void manageOptimizedTeleOp() {
        addTelemetry("delta", ActionManager.dt);

        Pose2d pos = drive.getPoseEstimate();
        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(), pos.getY(), pos.getHeading());
        telemetry.addData("Position", data);

//        addTelemetry("hue", ColorSensor.hue());
//        addTelemetry("dist", Hardware.colorSensor.getDistance(DistanceUnit.MM));

//        addTelemetry("back slides power", Hardware.backSlides.getPower());
//        addTelemetry("back slides current", Hardware.backSlides.getCurrent(CurrentUnit.AMPS));

        telemetry.update();
    }

    public static void manage(Gamepad gamepad) {
        if (gamepad.dpad_right){
            detectorResults = Limelight.getDetectorResults();
        }
        addTelemetry("hue", ColorSensor.hue());

        if(detectorResults != null){
            for (LLResultTypes.DetectorResult d:
                 detectorResults) {
                telemetry.addData("limelight x + y", d.getTargetXPixels() + " " + d.getTargetYPixels());
            }
            if (detectorResults.size() >= 1){
                telemetry.addData("offset", Limelight.horizontalOffset(detectorResults.get(0).getTargetXPixels(), detectorResults.get(0).getTargetYPixels()));
            }
        }

        addTelemetry("angle to basket", GameMap.clawAngleToBasketPrecise());

        addTelemetry("Pe Anglia?", Movement.peAnglia);
        addTelemetry("front slides position", frontSlides.getCurrentPosition());
        addTelemetry("front slides target", frontSlides.getTargetPosition());
        addTelemetry("delta", ActionManager.dt);
        addTelemetry("centered on sample", Limelight.centeredOnSample());

        telemetry.update();
    }

    public static void manageAuto() {
        telemetry.update();
    }

    private static void addTelemetry(String caption, int value) {
        telemetry.addData(caption, value);
    }

    private static void addTelemetry(String caption, double value) {
        telemetry.addData(caption, value);
    }

    private static void addTelemetry(String caption, boolean value) {
        telemetry.addData(caption, value);
    }
}

