package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Utils.Potentiometers;

public class Climb {

    private static double climbInit = 0.5;
    private static double climbPrepare = 0.66;
    private static double climbFirstLevel = 0.6;
    private static double climbRaiseFirstLevel = 0.42;
    private static double climbHangFirstLevel = 0.27;
    private static double climbTransfer = 0.37;
    private static double climbSecondLevel = 0.21;
    private static double climbSecondLevelPull = 0.45;
    private static double climbUnHook = 0.36;
    private static double climbPassHook = 0.2;
    private static double climbPassHook2 = 0.4;
    private static double climbSecondLevelHang = 0.6;

    private static double armsError = 0.04;

    private static int climbRightSlidesOffset = 40;

    private static int climbSlidesInit = 0;
    private static int climbSlidesPrepare = 2400; // 985
    private static int climbSlidesPull = -1000;
    private static int climbSlidesHangFirstLevel = 690;
    private static int climbSlidesTransfer = 1090;
    private static int climbSlidesSecondLevel = 2625;
    private static int climbSlidesUnHook = 1975;
    private static int climbSlidesPassHook = 2625;
    private static int climbSlidesPassHook2 = 2360;


    private static double adjustManuallySpeed = 0.15;

    public static void init(){
        angleInit();
        extendInit();
    }





    public static void angleInit(){
        Hardware.climbBackLeft.setPosition(climbInit);
        Hardware.climbBackRight.setPosition(climbInit);
        Hardware.climbFrontLeft.setPosition(climbInit);
        Hardware.climbFrontRight.setPosition(climbInit);
    }
    public static void anglePrepare(){
        Hardware.climbBackLeft.setPosition(climbPrepare);
        Hardware.climbBackRight.setPosition(climbPrepare);
        Hardware.climbFrontLeft.setPosition(climbPrepare);
        Hardware.climbFrontRight.setPosition(climbPrepare);
    }
    public static void angleFirstLevel(){
        Hardware.climbBackLeft.setPosition(climbFirstLevel);
        Hardware.climbBackRight.setPosition(climbFirstLevel);
        Hardware.climbFrontLeft.setPosition(climbFirstLevel);
        Hardware.climbFrontRight.setPosition(climbFirstLevel);
    }
    public static void angleRaiseFirstLevel(){
        Hardware.climbBackLeft.setPosition(climbRaiseFirstLevel);
        Hardware.climbBackRight.setPosition(climbRaiseFirstLevel);
        Hardware.climbFrontLeft.setPosition(climbRaiseFirstLevel);
        Hardware.climbFrontRight.setPosition(climbRaiseFirstLevel);
    }
    public static void angleHangFirstLevel(){
        Hardware.climbBackLeft.setPosition(climbHangFirstLevel);
        Hardware.climbBackRight.setPosition(climbHangFirstLevel);
        Hardware.climbFrontLeft.setPosition(climbHangFirstLevel);
        Hardware.climbFrontRight.setPosition(climbHangFirstLevel);
    }
    public static void angleTransfer(){
        Hardware.climbBackLeft.setPosition(climbTransfer);
        Hardware.climbBackRight.setPosition(climbTransfer);
        Hardware.climbFrontLeft.setPosition(climbTransfer);
        Hardware.climbFrontRight.setPosition(climbTransfer);
    }
    public static void angleSecondLevel(){
        Hardware.climbBackLeft.setPosition(climbSecondLevel);
        Hardware.climbBackRight.setPosition(climbSecondLevel);
        Hardware.climbFrontLeft.setPosition(climbSecondLevel);
        Hardware.climbFrontRight.setPosition(climbSecondLevel);
    }

    public static void angleSecondLevelPull(){
        Hardware.climbBackLeft.setPosition(climbSecondLevelPull);
        Hardware.climbBackRight.setPosition(climbSecondLevelPull);
        Hardware.climbFrontLeft.setPosition(climbSecondLevelPull);
        Hardware.climbFrontRight.setPosition(climbSecondLevelPull);
    }
    
    public static void angleUnHook(){
        Hardware.climbBackLeft.setPosition(climbUnHook);
        Hardware.climbBackRight.setPosition(climbUnHook);
        Hardware.climbFrontLeft.setPosition(climbUnHook);
        Hardware.climbFrontRight.setPosition(climbUnHook);
    }

    public static void anglePassHook(){
        Hardware.climbBackLeft.setPosition(climbPassHook);
        Hardware.climbBackRight.setPosition(climbPassHook);
        Hardware.climbFrontLeft.setPosition(climbPassHook);
        Hardware.climbFrontRight.setPosition(climbPassHook);
    }

    public static void anglePassHook2(){
        Hardware.climbBackLeft.setPosition(climbPassHook2);
        Hardware.climbBackRight.setPosition(climbPassHook2);
        Hardware.climbFrontLeft.setPosition(climbPassHook2);
        Hardware.climbFrontRight.setPosition(climbPassHook2);
    }

    public static void angleHangSecondLevel(){
        Hardware.climbBackLeft.setPosition(climbSecondLevelHang);
        Hardware.climbBackRight.setPosition(climbSecondLevelHang);
        Hardware.climbFrontLeft.setPosition(climbSecondLevelHang);
        Hardware.climbFrontRight.setPosition(climbSecondLevelHang);
    }






    public static void extendInit(){
        disableMotors();
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesInit);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesInit + climbRightSlidesOffset);
//        ActionDelayer.condition(Climb::motorsReachedInit, Climb::disableMotors);
    }
    public static void extendPrepare(){
        enableMotors();
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesPrepare);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesPrepare + climbRightSlidesOffset);
    }
    public static void pullFirst(){
        enableMotors();
        Hardware.climbRightSlides.setPower(1);
        Hardware.climbLeftSlides.setPower(1);
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesPull - 100);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesPull + climbRightSlidesOffset);
    }
    public static void pullSecond(){
        enableMotors();

        Hardware.climbLeftSlides.setPower(1);
        Hardware.climbRightSlides.setPower(1);
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesPull - 100);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesPull + climbRightSlidesOffset);
    }
    
    public static void SlidesUnHook(){
        enableMotors();
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesUnHook);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesUnHook + climbRightSlidesOffset);
    }

    public static void SlidesPassHook(){
        enableMotors();
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesPassHook);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesPassHook + climbRightSlidesOffset);
    }

    public static void SlidesPassHook2(){
        enableMotors();
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesPassHook2);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesPassHook2 + climbRightSlidesOffset);
    }

    public static void extendTransfer(){
        enableMotors();
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesTransfer);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesTransfer + climbRightSlidesOffset);
    }
    public static void extendSecondLevel(){
        enableMotors();
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesSecondLevel);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesSecondLevel + climbRightSlidesOffset);
    }
    public static void extendSlidesHangFirstLevel(){
        enableMotors();
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesHangFirstLevel);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesHangFirstLevel + climbRightSlidesOffset);
    }


    private static boolean armsReached(double position){
        return Potentiometers.inRange(Potentiometers.climbLeftPosition(), position - armsError, position + armsError) && Potentiometers.inRange(Potentiometers.climbRightPosition(), position - armsError, position + armsError);
    }

    private static boolean armsReached(double left, double right){
        return Potentiometers.inRange(Potentiometers.climbLeftPosition(), left - armsError, left + armsError) && Potentiometers.inRange(Potentiometers.climbRightPosition(), right - armsError, right + armsError);
    }


    public static boolean armsReachedPrepare(){
        return armsReached(climbPrepare);
    }

    public static boolean armsReachedFirstLevel(){
        return armsReached(climbFirstLevel);
    }

    public static boolean armsReachedHangFirstLevel(){
        return armsReached(climbHangFirstLevel, climbHangFirstLevel + 0.06);
    }

    public static boolean armsReachedTransfer(){
        return armsReached(climbTransfer);
    }

    public static boolean armsReachedSecondLevel(){
        return armsReached(climbSecondLevel + 0.00, climbSecondLevel + 0.10);
    }

    public static boolean armsUnHooked(){
        return armsReached(climbUnHook);
    }

    public static boolean armsReachedPassHook(){
        return armsReached(climbPassHook + 0.06, climbPassHook + 0.13);
    }

    public static boolean armsReachedPassHook2(){
        return armsReached(climbPassHook2 - 0.07, climbPassHook2 + 0.03);
    }


    private static boolean closeTo(int position, int target){
        return position >= target - 30 && position <= target + 30;
    }

    private static boolean closeTo(int position, int target, int range){
        return position >= target - range && position <= target + range;
    }


    public static boolean motorsReachedPrepare(){
        return closeTo(Hardware.climbRightSlides.getCurrentPosition(), climbSlidesPrepare + climbRightSlidesOffset) && closeTo(-Hardware.climbLeftSlides.getCurrentPosition(), climbSlidesPrepare);
    }

    public static boolean motorsPulledFully(){
        return Hardware.climbRightSlides.getCurrentPosition() - 80 <= 0 && Hardware.climbLeftSlides.getCurrentPosition() + 80 >= 0;
    }

    public static boolean motorsHungFirstLevel(){
        return closeTo(Hardware.climbRightSlides.getCurrentPosition(), climbSlidesHangFirstLevel + climbRightSlidesOffset) && closeTo(-Hardware.climbLeftSlides.getCurrentPosition(), climbSlidesHangFirstLevel);
    }

    public static boolean motorsReachedTransfer(){
        return closeTo(Hardware.climbRightSlides.getCurrentPosition(), climbSlidesTransfer + climbRightSlidesOffset) && closeTo(-Hardware.climbLeftSlides.getCurrentPosition(), climbSlidesTransfer);
    }

    public static boolean motorsReachedSecondLevel(){
        return closeTo(Hardware.climbRightSlides.getCurrentPosition(), climbSlidesSecondLevel + climbRightSlidesOffset) && closeTo(-Hardware.climbLeftSlides.getCurrentPosition(), climbSlidesSecondLevel);
    }

    public static boolean motorsUnHooked(){
        return closeTo(Hardware.climbRightSlides.getCurrentPosition(), climbSlidesUnHook + climbRightSlidesOffset) && closeTo(-Hardware.climbLeftSlides.getCurrentPosition(), climbSlidesUnHook);
    }

    public static boolean motorsReachedPassHook(){
        return closeTo(Hardware.climbRightSlides.getCurrentPosition(), climbSlidesPassHook + climbRightSlidesOffset) && closeTo(-Hardware.climbLeftSlides.getCurrentPosition(), climbSlidesPassHook);
    }

    public static boolean motorsReachedPassHook2(){
        return Hardware.climbRightSlides.getCurrentPosition() > climbSlidesPassHook2 + 110 && -Hardware.climbLeftSlides.getCurrentPosition() > climbSlidesPassHook2 + 110;
    }







    public static void disableMotors(){
        Hardware.climbLeftSlides.setPower(0);
        Hardware.climbRightSlides.setPower(0);
    }

    public static void enableMotors(){
        Hardware.climbLeftSlides.setPower(1);
        Hardware.climbRightSlides.setPower(1);
    }






    public static void rotateManually(int direction, float dt){
        double position = Hardware.climbBackLeft.getPosition();
        position += adjustManuallySpeed * direction * dt;
        Hardware.climbBackLeft.setPosition(position);
        Hardware.climbBackRight.setPosition(position);
        Hardware.climbFrontLeft.setPosition(position);
        Hardware.climbFrontRight.setPosition(position);
    }
}
