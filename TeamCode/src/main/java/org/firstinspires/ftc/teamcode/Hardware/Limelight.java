package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.teamcode.Hardware.ColorSensor;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;

import java.util.List;

public class Limelight {

     private static double pixelsPerInchHorizontalLeft = 30.0;
     private static double pixelsPerInchHorizontalRight = 34.0;
     private static double middleHorizontal = 300.0;
     private static double maxDistance = 60.0;
     private static double maxView = 20.0;
     private static double minView = 230.0;
     private static double minMultiplier = 0.5;
     private static double maxMultiplier = 3;
     private static double unaryMultiplierDistance = 110.0;
     private static double horizontalMaxOffset = 60.0;

     private static double lerp(double min, double max, double weight){
          return (min * (1.0 - weight)) + (max * weight);
     }

     public static double horizontalOffset(double x, double y){
          double actualMiddle = actualMiddle(y);
          if (x < actualMiddle) return (x - actualMiddle) / pixelsPerInchHorizontalLeft * distanceMultiplier(y);
          else return (x - actualMiddle) / pixelsPerInchHorizontalRight * distanceMultiplier(y);
     }

     public static boolean centeredOnSample(){
          List<LLResultTypes.DetectorResult> result = getDetectorResults();
          if (result == null) return false;
          for (int k = result.size() - 1; k >= 0; k--){
               LLResultTypes.DetectorResult i = result.get(k);
               if (Limelight.isYellowSample(i.getClassId()) || Limelight.isAllianceSample(i.getClassId())){
                    if (i.getTargetYPixels() < maxDistance) continue;
                    if (inFrontOfIntake(i.getTargetXPixels(), i.getTargetYPixels())) return true;
               }
          }
          return false;
     }

     private static boolean inFrontOfIntake(double x, double y){
          double actualMiddle = actualMiddle(y);
          return Math.abs(x - actualMiddle) < 30;
     }

     private static double actualMiddle(double y){
          return middleHorizontal + distancePercentage(y) * horizontalMaxOffset;
     }

     private static double distanceMultiplier(double y){
          if (y < unaryMultiplierDistance){
               double p = (y - maxDistance) / (unaryMultiplierDistance - maxDistance);
               return lerp(1, maxMultiplier, (1.0 - p) * (1.0 - p));
          }
          else{
               return lerp(minMultiplier, 1, 1.0 - (y - unaryMultiplierDistance) / (minView - unaryMultiplierDistance));
          }
     }

     public static LLResultTypes.DetectorResult findTarget(){
          List<LLResultTypes.DetectorResult> result = getDetectorResults();
          LLResultTypes.DetectorResult target = null;
          for (int k = result.size() - 1; k >= 0; k--){
               LLResultTypes.DetectorResult i = result.get(k);
               if (Limelight.isYellowSample(i.getClassId()) || Limelight.isAllianceSample(i.getClassId())){
                    if (i.getTargetYPixels() < maxDistance) continue;
                    if (i.getTargetYPixels() > minView) continue;
                    if (target == null) {
                         target = i;
                         continue;
                    }
                    if (Math.abs(i.getTargetYPixels() - unaryMultiplierDistance) < Math.abs(target.getTargetYPixels() - unaryMultiplierDistance)){
                         target = i;
                    }
               }
          }
          return target;
     }

     private static double distancePercentage(double y){
          if (y > minView) return 1;
          else if (y < maxView) return 0;
          else {
               return (y - maxView) / (minView - maxView);
          }
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
