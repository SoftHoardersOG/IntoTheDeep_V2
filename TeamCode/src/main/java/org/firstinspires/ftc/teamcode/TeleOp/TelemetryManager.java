package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.Hardware.Hardware.RobotIMU;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.backSlides;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.climbLeftSlides;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.climbRightSlides;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.colorSensor;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.drive;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.frontSlides;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.odometry;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.telemetry;


import android.graphics.Color;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.Climb;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Limelight;
import org.firstinspires.ftc.teamcode.Utils.ColorSensor;
import org.firstinspires.ftc.teamcode.Utils.GameMap;
import org.firstinspires.ftc.teamcode.Utils.Potentiometers;

import java.util.List;
import java.util.Locale;
//import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;

public class TelemetryManager {

    private static FtcDashboard dashboard;
  //private static TelemetryPacket packet;

    public static void init(){

      dashboard = FtcDashboard.getInstance();
      dashboard.setTelemetryTransmissionInterval(100);
    }

    public static void manageOptimizedTeleOp(){
        addTelemetry("delta", ActionManager.dt);

//        Pose2d pos = drive.getPoseEstimate();
//        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(), pos.getY(), Math.toDegrees(pos.getHeading()));
//        telemetry.addData("Position", data);
//
//        Pose2d vel = drive.getPoseVelocity();
//        String velocity = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", vel.getX(), vel.getY(), Math.toDegrees(vel.getHeading()));
//        telemetry.addData("Velocity", velocity);

//        telemetry.addData("climb progress", ActionManager.climbProgress);

        telemetry.update();
    }

    public static void manage(){

        addTelemetry("angle to basket", GameMap.clawAngleToBasketPrecise());

//        addTelemetry("stanga fata", frontLeft.getPortNumber());
//        addTelemetry("dreapta fata", frontRight.getPortNumber());
//        addTelemetry("dreapta spate", backRight.getPortNumber());
//        addTelemetry("stanga spate", backLeft.getPortNumber());
//
//        addTelemetry("stanga fata", frontLeft.getCurrentPosition());
//        addTelemetry("dreapta fata", frontRight.getCurrentPosition());
//        addTelemetry("dreapta spate", backRight.getCurrentPosition());
//        addTelemetry("stanga spate", backLeft.getCurrentPosition()

//          addTelemetry("back slides current position", backSlides.getCurrentPosition());
//          addTelemetry("back slides target", backSlides.getTargetPosition());

        List<LLResultTypes.DetectorResult> detectorResults = Limelight.getDetectorResults();
//        if (detectorResults != null){
//            if (detectorResults.size() == 0){
//                telemetry.addData("no detected samples", "");
//            }
//            else{
//                for (LLResultTypes.DetectorResult cr : detectorResults) {
////                    telemetry.addData(cr.getClassName(), "X: %.2f, Y: %.2f", cr.getTargetXPixels(), cr.getTargetYPixels());
//                    telemetry.addData(cr.getClassName(), cr.getClassId());
//                }
//            }
//        }
//        else {
//            telemetry.addData("no detected samples", "");
//        }
//
//        addTelemetry("back slides position", backSlides.getCurrentPosition());
//
        addTelemetry("Pe Anglia?", Movement.peAnglia);
//
//            addTelemetry("claw adjust", Claw.updateClawAngleVertical(backSlides.getCurrentPosition(), backSlides.getTargetPosition()));

//        addTelemetry("arm left current", Potentiometers.armLeftPosition());
//        addTelemetry("arm right current", Potentiometers.armRightPosition());
//        addTelemetry("arm left target", Hardware.armLeft.getPosition());
//        addTelemetry("arm right target", Hardware.armRight.getPosition());
//
//        addTelemetry("arms vertical current", Claw.armsCurrentVerticalPosition);
//        addTelemetry("arms horizontal current", Claw.armsCurrentHorizontalPosition);
//        addTelemetry("arms vertical target", Claw.armsTargetVerticalPosition);
//        addTelemetry("arms horizontal target", Claw.armsTargetHorizontalPosition);

//        addTelemetry("chamber position", Claw.chamber);

//        addTelemetry("fixed claw position", Claw.fixedPositionActive);
//
//        addTelemetry("reached middle rotation", Claw.reachedMiddle());
//
//        addTelemetry("back slides pos", backSlides.getCurrentPosition());

//        addTelemetry("potentiometer voltage",Hardware.potentiometer.getVoltage());
//        addTelemetry("glisiera stanga",Hardware.climbLeftSlides.getCurrentPosition());
//        addTelemetry("glisiera dreapta",Hardware.climbRightSlides.getCurrentPosition());

          addTelemetry("front slides position", frontSlides.getCurrentPosition());
          addTelemetry("front slides target", frontSlides.getTargetPosition());
//
//          addTelemetry("front slides transfer", FrontSlides.slidesTransfer);
//
//
//          addTelemetry("opener voltage", openerPotentiometer.getVoltage());
//          addTelemetry("up down voltage", potentiometer.getVoltage());
//
//          addTelemetry("climb left", Hardware.climbLeftSlides.getCurrentPosition());
//          addTelemetry("climb right", Hardware.climbRightSlides.getCurrentPosition());
//          addTelemetry("cilmb progress", ActionManager.climbProgress);
//
//
//          addTelemetry("stanga fata", frontLeft.getCurrentPosition());
//          addTelemetry("stanga spate", backLeft.getCurrentPosition());
//          addTelemetry("dreapta fata", frontRight.getCurrentPosition());
//          addTelemetry("dreapta spate", backRight.getCurrentPosition());
//
//          addTelemetry("glisiere fata voltage", frontSlides.getCurrent(CurrentUnit.AMPS));
//          addTelemetry("glisiere spate voltage", backSlides.getCurrent(CurrentUnit.AMPS));
//          addTelemetry("catarare stanga voltage", climbLeftSlides.getCurrent(CurrentUnit.AMPS));
//          addTelemetry("catarare dreapta voltage", climbRightSlides.getCurrent(CurrentUnit.AMPS));
////
//        addTelemetry("stanga fata", frontLeft.getCurrent(CurrentUnit.AMPS));
//        addTelemetry("stanga spate", backLeft.getCurrent(CurrentUnit.AMPS));
//        addTelemetry("dreapta fata", frontRight.getCurrent(CurrentUnit.AMPS));
//        addTelemetry("dreapta spate", backRight.getCurrent(CurrentUnit.AMPS));

//        addTelemetry("climb left slides pos", climbLeftSlides.getCurrentPosition());
//        addTelemetry("climb left slides target", climbLeftSlides.getTargetPosition());
////
//        addTelemetry("climb right slides pos", climbRightSlides.getCurrentPosition());
//        addTelemetry("climb right slides target", climbRightSlides.getTargetPosition());

//        telemetry.addData("Control Hub IMU", Hardware.RobotIMU.getRobotOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).firstAngle);
//        telemetry.addData("Control Hub IMU", Hardware.RobotIMU.getRobotOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle);
//        telemetry.addData("Control Hub IMU", Hardware.RobotIMU.getRobotOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle);

//
//
//        addTelemetry("climb left potentiometer", Potentiometers.climbLeftPosition());
////
//        addTelemetry("climb right potentiometer", Potentiometers.climbRightPosition());
//
//        addTelemetry("front slides power", frontSlides.getPower());
//
//        addTelemetry("climb progress", ActionManager.climbProgress);
//
//        addTelemetry("motors pulled fully", Climb.motorsPulledFully());
//
//        addTelemetry("climb servos position", Hardware.climbBackRight.getPosition());

        addTelemetry("delta", ActionManager.dt);

//        addTelemetry("basket angle offset degrees", GameMap.clawAngleToBasket());
//        addTelemetry("IMU angle normalized origin right", GameMap.rotatedOrigin(GameMap.basketImuRotationOffset, GameMap.normalizedAngleDegrees(pos.getHeading(AngleUnit.DEGREES))));
//        addTelemetry("basket angle to chamber", GameMap.clawAngleToBasket());
//        addTelemetry("basket angle offset percentage", GameMap.clawAngleToBasketPercentage());
//        addTelemetry("IMU angle normalized origin down", GameMap.rotatedOrigin(GameMap.chamberImuRotationOffset, GameMap.normalizedAngleDegrees(pos.getHeading(AngleUnit.DEGREES))));
//        addTelemetry("claw angle to chamber", GameMap.clawAngleToChamber());
//        addTelemetry("chamber angle offset percentage", GameMap.clawAngleToChamberPercentage());

//        addTelemetry("original heading", pos.getHeading(AngleUnit.DEGREES));
//        float[] hsv = new float[3];
//        Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsv);
//
//        float hue = hsv[0];
//        float sat = hsv[1];
//        float val = hsv[2];
//
//        addTelemetry("H", hue);
//        addTelemetry("S", sat);
//        addTelemetry("V", val);
//        addTelemetry("distance mm", colorSensor.getDistance(DistanceUnit.MM));


        telemetry.update();
    }

    public static void manageAuto(){
//        addTelemetry("front slides power", frontSlides.getPower());
//        addTelemetry("transfer finish score case", ActionManager.transferFinishScoreCase);
//        addTelemetry("front slides target", frontSlides.getTargetPosition());
//        addTelemetry("back slides pos", backSlides.getTargetPosition());
        telemetry.update();
    }

    private static void addTelemetry(String caption, int value){
        telemetry.addData(caption, value);
       // packet.put(caption, value);
    }

    private static void addTelemetry(String caption, double value){
        telemetry.addData(caption, value);
      //  packet.put(caption, value);
    }

    private static void addTelemetry(String caption, String value){
        telemetry.addData(caption, value);
        //packet.put(caption, value);
    }

    private static void addTelemetry(String caption, boolean value){
        telemetry.addData(caption, value);
      //  packet.put(caption, value);
    }
}

