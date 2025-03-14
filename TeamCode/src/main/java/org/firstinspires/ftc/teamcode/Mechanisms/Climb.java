package org.firstinspires.ftc.teamcode.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Utils.Potentiometers;

@Config
public class Climb {

    public static double configAngle;
    public static int configExtend;

    private static double climbInit = 0.3;
    private static double climbInspection = 0.62;
    private static double climbPrepare = 0.66;
    private static double climbFirstLevel = 0.52;
    private static double climbRaiseFirstLevel = 0.36;
    private static double climbHangFirstLevel = 0.56;
    private static double climbTransfer = 0.52;
    private static double climbSecondLevel = 0.43;
    private static double climbUnHook = 0.45;
    private static double climbPassHook = 0.33;
    private static double climbPassHook2 = 0.45;
    private static double climbSecondLevelPull = 0.7;
    private static double climbSecondLevelHang = 0.6;
    private static double climbPassFirstBar = 0.15;

    private static double armsError = 0.07;

    private static int climbRightSlidesOffset = 0;

    private static int climbSlidesInit = 0;
    private static int climbSlidesInterview = 1200;
    private static int climbSlidesInitRaised = 200;
    private static int climbSlidesPrepare = 1700; // 985
    private static int climbSlidesInspection = 1100;
    private static int climbSlidesPull = -1000;
    private static int climbSlidesHangFirstLevel = 690;
    private static int climbSlidesTransfer = 1090;
    private static int climbSlidesSecondLevel = 3250;
    private static int climbSlidesUnHook = 2200;
    private static int climbSlidesPassHook = 2900;
    private static int climbSlidesPassHook2 = 2500;


    private static double adjustManuallySpeed = 0.15;

    public static void init(){
        configAngle = climbInit;
        configExtend = climbSlidesInit;
        angleInit();
        extendInit();
    }

    public static void updateConfig(){
        Hardware.climbBackLeft.setPosition(configAngle);
        Hardware.climbBackRight.setPosition(configAngle);
        Hardware.climbFrontLeft.setPosition(configAngle);
        Hardware.climbFrontRight.setPosition(configAngle);
        Hardware.climbLeftSlides.setTargetPosition(-1 * configExtend);
        Hardware.climbRightSlides.setTargetPosition(configExtend + climbRightSlidesOffset);
    }

    public static void raise(){
        Hardware.climbLeftSlides.setTargetPosition(-3800);
        Hardware.climbRightSlides.setTargetPosition(3800);
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
    public static void angleInspection(){
        Hardware.climbBackLeft.setPosition(climbInspection);
        Hardware.climbBackRight.setPosition(climbInspection);
        Hardware.climbFrontLeft.setPosition(climbInspection);
        Hardware.climbFrontRight.setPosition(climbInspection);
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

    public static void anglePassFirstBar(){
        Hardware.climbBackLeft.setPosition(climbPassFirstBar);
        Hardware.climbBackRight.setPosition(climbPassFirstBar);
        Hardware.climbFrontLeft.setPosition(climbPassFirstBar);
        Hardware.climbFrontRight.setPosition(climbPassFirstBar);
    }



    public static void extendRightSecondLevel(){
        Hardware.climbRightSlides.setTargetPosition(climbSlidesSecondLevel + climbRightSlidesOffset);
    }


    public static void extendInit(){
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesInit);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesInit + climbRightSlidesOffset);
    }
    public static void extendInterview(){
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesInterview);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesInterview + climbRightSlidesOffset);
    }
    public static void extendInitRaised(){
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesInitRaised);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesInitRaised + climbRightSlidesOffset);
    }
    public static void extendPrepare(){
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesPrepare);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesPrepare + climbRightSlidesOffset);
    }
    public static void extendInspection(){
        enableMotors();
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesInspection);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesInspection + climbRightSlidesOffset);
    }
    public static void pullFirst(){
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesPull);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesPull + climbRightSlidesOffset);
    }
    public static void pullSecond(){
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesPull);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesPull + climbRightSlidesOffset);
    }
    
    public static void SlidesUnHook(){
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesUnHook);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesUnHook + climbRightSlidesOffset);
    }

    public static void SlidesPassHook(){
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesPassHook);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesPassHook + climbRightSlidesOffset);
    }

    public static void SlidesPassHook2(){
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesPassHook2);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesPassHook2 + climbRightSlidesOffset);
    }

    public static void extendTransfer(){
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesTransfer);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesTransfer + climbRightSlidesOffset);
    }
    public static void extendSecondLevel(){
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesSecondLevel);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesSecondLevel + climbRightSlidesOffset);
    }
    public static void extendSlidesHangFirstLevel(){
        Hardware.climbLeftSlides.setTargetPosition(-1 * climbSlidesHangFirstLevel);
        Hardware.climbRightSlides.setTargetPosition(climbSlidesHangFirstLevel + climbRightSlidesOffset);
    }


    private static boolean armsReached(double position){
        return Potentiometers.inRange(Potentiometers.climbLeftPosition(), position - armsError, position + armsError) && Potentiometers.inRange(Potentiometers.climbRightPosition(), position - armsError, position + armsError);
    }

    private static boolean  armsReached(double left, double right){
        return Potentiometers.inRange(Potentiometers.climbLeftPosition(), left - armsError, left + armsError) && Potentiometers.inRange(Potentiometers.climbRightPosition(), right - armsError, right + armsError);
    }


    public static boolean armsReachedPrepare(){
        return armsReached(climbPrepare);
    }

    public static boolean armsReachedFirstLevel(){
        return armsReached(climbFirstLevel + 0.05);
    }

    public static boolean armsReachedHangFirstLevel(){
        return armsReached(climbHangFirstLevel + -0.01, climbHangFirstLevel + 0.04);
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
        return position >= target - 50 && position <= target + 50;
    }

    private static boolean closeTo(int position, int target, int range){
        return position >= target - range && position <= target + range;
    }

    public static boolean motorsReachedSecondRetracted(){
        return Hardware.climbRightSlides.getCurrentPosition() < (climbSlidesSecondLevel - 3250);
    }

    public static boolean motorsReachedHalfwaySecond(){
        return Hardware.climbRightSlides.getCurrentPosition() < (climbSlidesSecondLevel - 2500);
    }
    public static boolean motorsReachedAlmostHalfway(){
        return Hardware.climbRightSlides.getCurrentPosition() < (climbSlidesSecondLevel - 1500);
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
