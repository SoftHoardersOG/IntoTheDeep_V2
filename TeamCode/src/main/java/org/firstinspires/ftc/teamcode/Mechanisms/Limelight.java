package org.firstinspires.ftc.teamcode.Mechanisms;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ColorSensor;

import java.util.List;

public class Limelight {

     private static double pixelsPerInchHorizontal = 40.0;
     private static double middleHorizontal = 300.00;

     public static double horizontalOffset(double pixels){
          return (pixels - middleHorizontal) / pixelsPerInchHorizontal;
     }

     public static LLResult getResult() {
          LLResult result = Hardware.limelight.getLatestResult();
          if (result != null) {
               if (result.isValid()) {
                    return result;
               }
          }
          return null;
     }

     public static List<LLResultTypes.DetectorResult> getDetectorResults(){
          LLResult result = getResult();
          if (result != null){
               if (result.isValid()){
                    return result.getDetectorResults();
               }
          }
          return null;
     }

     public static boolean isAllianceSample(int id){
          if (id == 0){
               if (ColorSensor.matchColor == ColorSensor.AllianceColors.BLUE) return true;
               if (ColorSensor.matchColor == ColorSensor.AllianceColors.RED) return false;
          }
          if (id == 1){
               if (ColorSensor.matchColor == ColorSensor.AllianceColors.BLUE) return false;
               if (ColorSensor.matchColor == ColorSensor.AllianceColors.RED) return true;
          }
          return false;
     }

     public static boolean isYellowSample(int id){
          return id == 2;
     }
}
