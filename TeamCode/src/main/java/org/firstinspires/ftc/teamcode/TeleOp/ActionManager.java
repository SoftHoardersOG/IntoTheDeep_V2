package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
//import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.Climb;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Utils.OneTap;
import org.firstinspires.ftc.teamcode.Utils.Potentiometers;

import java.util.Objects;


public class ActionManager {
    private enum ClimbLevel {
        NOT_SELECTED,
        FIRST_LEVEL,
        SECOND_LEVEL
    }

    private static ClimbLevel selectedClimbLevel;

    public static float dt;
    public static long previousTime;

    private static Gamepad gamepad1;
    private static Gamepad gamepad2;

    private static OneTap cross1 = new OneTap();
    private static OneTap square1 = new OneTap();
    private static OneTap triangle1 = new OneTap();
    private static OneTap circle1 = new OneTap();
    private static OneTap dpad1down = new OneTap();
    private static OneTap dpad1left = new OneTap();

    private static OneTap square2 = new OneTap();
    private static OneTap options2 = new OneTap();
    private static OneTap touchpad2 = new OneTap();
    private static OneTap cross2 = new OneTap();
    private static OneTap circle2 = new OneTap();
    private static OneTap left_bumper2 = new OneTap();
    private static OneTap right_bumper2 = new OneTap();

    private static OneTap frontSlidesKeepPos = new OneTap();

    private static OneTap climbConfirmation = new OneTap();
    private static OneTap climbCancelConfirmation = new OneTap();

    private static int colorCycleTime;
    public static boolean scored;
    private static boolean lowChamberCase;
    public static boolean transferring;
    public static String transferFinishScoreCase;
    public static String climbProgress;

    private static double transferTimeScale = 2.3;
    private static double transferSpecimenTimeScale = 1.7;
    private static double unHookTimeScale = 2; // 0.75

    private static long firstTimeTransfer;

    public static void init(){
        selectedClimbLevel = ClimbLevel.NOT_SELECTED;
        climbProgress = "begin";
        colorCycleTime = 1000;
        //manageLeds();
        previousTime = System.currentTimeMillis();
        ActionDelayer.setTimeScale(transferTimeScale);
        scored = false;
        lowChamberCase = false;
        transferring = false;
    }

    public static void control(Gamepad _gamepad1, Gamepad _gamepad2){
        gamepad1 = _gamepad1;
        gamepad2 = _gamepad2;

        calculateDelta();

        IntakeCheck();
        IntakeUpDown();
        calibrateFrontSlides();
        manageFrontSlides();
        lowBasket();
        highBasket();
        lowChamber();
        highChamber();
        rotateClawScoring();
        score();
        Transfer();
        TransferSpecimen();
        ClimbCancel();
//        rotateRobot();
        selectClimbLevel();
        Climbing();
        Inspection();

        Intake.updateCollectPosition();
        FrontSlides.update();
    }

    public static void controlAuto(){
        calculateDelta();

        lowBasketAuto();
        highBasketAuto();
        lowChamberAuto();
        highChamberAuto();

        Intake.updateCollectPosition();
    }

//    private static void manageLeds(){
//        setLedGreen();
//        ActionDelayer.time(colorCycleTime, ActionManager::setLedWhite);
//        ActionDelayer.time(2 * colorCycleTime, ActionManager::setLedBlack);
//        ActionDelayer.time(3 * colorCycleTime, ActionManager::manageLeds);
//    }
//
//    private static void setLedGreen(){
//        gamepad1.setLedColor(0.01, 0.93, 0.05, 1000000000);
//        gamepad2.setLedColor(0.01, 0.93, 0.05, 1000000000);
//    }
//
//    private static void setLedWhite(){
//        gamepad1.setLedColor(0, 0, 0, 1000000000);
//        gamepad2.setLedColor(0, 0, 0, 1000000000);
//    }
//
//    private static void setLedBlack(){
//        gamepad1.setLedColor(0, 0.93, 0.05, 1000000000);
//        gamepad2.setLedColor(0.01, 0.93, 0.05, 1000000000);
//    }

    private static void calculateDelta(){
        dt = System.currentTimeMillis() - previousTime;
        previousTime = System.currentTimeMillis();
        dt /= 1000;
    }
    private static void IntakeCheck(){
        if (transferring) return;
        if(cross1.onPress(gamepad1.cross)){
            if (Intake.collecting){
                Intake.neutral();
                ActionDelayer.time(200, Intake::stopCollect);
            }
            else {
                Intake.collect();
            }
        }
        if(square1.onPress(gamepad1.square)){
            if (Intake.spittingGround){
                Intake.neutral();
                ActionDelayer.time(200, Intake::stopCollect);
            }
            else {
                Intake.spitoutGround();
            }
        }
    }

    private static void IntakeUpDown(){
        if (transferring) return;
        if (triangle1.onPress(gamepad1.triangle)){
            if (Intake.inCollectPosition){
                Intake.neutral();
                ActionDelayer.time(200, Intake::stopCollect);
            }
            else {
                Intake.ground();
            }
        }
    }

    private static void calibrateFrontSlides(){
        if (dpad1down.onPress(gamepad1.dpad_down) && !FrontSlides.calibrating){
            FrontSlides.findNewInitPosition();
        }
    }

    private static void manageFrontSlides(){
        if (transferring){
            return;
        }
        if (gamepad1.right_bumper){
            FrontSlides.extend(dt);
            frontSlidesKeepPos.onPress(false);
        }
        else if(gamepad1.left_bumper){
            FrontSlides.retract(dt);
            frontSlidesKeepPos.onPress(false);
        }
        else if(!transferring && frontSlidesKeepPos.onPress(true)){
            FrontSlides.keepPosition();
            ActionDelayer.time(300, FrontSlides::keepPosition);
        }
        else{
            FrontSlides.init = false;
        }
    }

    public static void lowBasketPos(){
        Claw.armsLowBasket();
        ActionDelayer.time(250, Claw::clawAngleMiddle);
        BackSlides.lowBasket();
        Claw.closeClawSample();
        scored = false;
        lowChamberCase = false;
    }

    private static void lowBasket(){
        if (transferring && gamepad2.cross){
            transferFinishScoreCase = "low_basket";
            return;
        }
        else if (!transferring && transferFinishScoreCase == "low_basket"){
            lowBasketPos();
            transferFinishScoreCase = "";
        }
        else if (transferring) return;
        if (gamepad2.cross) {
            lowBasketPos();
        }
    }

    private static void lowBasketAuto(){
        if (!transferring && transferFinishScoreCase == "low_basket"){
            lowBasketPos();
            transferFinishScoreCase = "";
        }
    }

    public static void highBasketPos(){
        Claw.armsHighBasket();
        ActionDelayer.time(250, Claw::clawAngleMiddle);
        BackSlides.highBasket();
        Claw.closeClawSample();
        scored = false;
        lowChamberCase = false;
    }

    private static void highBasket(){
        if (transferring && gamepad2.triangle) {
            transferFinishScoreCase = "high_basket";
            return;
        }
        else if(!transferring && transferFinishScoreCase == "high_basket"){
            highBasketPos();
            transferFinishScoreCase = "";
        }
        else if (transferring) return;
        if (gamepad2.triangle) {
            highBasketPos();
        }
    }

    private static void highBasketAuto(){
        if(!transferring && transferFinishScoreCase == "high_basket"){
            highBasketPos();
            transferFinishScoreCase = "";
        }
    }

    public static void lowChamberPos(){
        Claw.armsLowChamber();
        ActionDelayer.time(250, Claw::clawAngleChamber);
        BackSlides.lowChamber();
        Claw.closeClawSample();
        scored = false;
        lowChamberCase = true;
    }

    private static void lowChamber(){
        if (transferring && gamepad2.dpad_down) {
            transferFinishScoreCase = "low_chamber";
            return;
        }
        else if (!transferring && transferFinishScoreCase == "low_chamber"){
            lowChamberPos();
            transferFinishScoreCase = "";
        }
        else if (transferring) return;
        if (gamepad2.dpad_down) {
            lowChamberPos();
        }
    }

    private static void lowChamberAuto(){
        if (!transferring && transferFinishScoreCase == "low_chamber"){
            lowChamberPos();
            transferFinishScoreCase = "";
        }
    }

    public static void highChamberPos(){
        Claw.armsHighChamber();
        ActionDelayer.time(250, Claw::clawAngleChamber);
        BackSlides.highChamber();
        Claw.closeClawSample();
        scored = false;
        lowChamberCase = false;
    }

    private static void highChamber(){
        if (transferring && gamepad2.circle) {
            transferFinishScoreCase = "high_chamber";
            return;
        }
        else if(!transferring && transferFinishScoreCase == "high_chamber"){
            highChamberPos();
            transferFinishScoreCase = "";
        }
        else if (transferring) return;
        if (gamepad2.circle) {
            highChamberPos();
        }
    }

    private static void highChamberAuto(){
        if(!transferring && transferFinishScoreCase == "high_chamber"){
            highChamberPos();
            transferFinishScoreCase = "";
        }
    }

//    private static void backSlidesInitPosition(){
//        if (transferring) return;
//        if (gamepad2.touchpad){
//            BackSlides.initPosition();
//            ActionDelayer.time(0, Claw::closeClaw);
//            ActionDelayer.time(0, Claw::clawAngleInit);
//            ActionDelayer.time(200, Claw::armsInit);
//            lowChamberCase = false;
//        }
//    }

    private static void rotateClawScoring(){
        if (!BackSlides.scoringPosition || lowChamberCase) return;
        if(gamepad2.dpad_left){
            Claw.clawAngleRight();
        }
        else if(gamepad2.dpad_up){
            Claw.clawAngleMiddle();
        }
        else if(gamepad2.dpad_right){
            Claw.clawAngleLeft();
        }
    }

    public static void releaseSample(){
        scored = true;
        if (lowChamberCase){
            BackSlides.lowChamberFinish();
            ActionDelayer.time(400, Claw::openClawScore);
            ActionDelayer.time(400, () -> lowChamberCase = false);
        }
        else{
            Claw.openClawScore();
        }
    }

    public static void resetScoring(){
        ActionDelayer.time(0, Claw::closeClaw);
        ActionDelayer.time(0, Claw::clawAngleInit);
        ActionDelayer.time(200, Claw::armsInit);
        ActionDelayer.time(150, BackSlides::initPosition);
        scored = false;
    }

    private static void score(){
        if (transferring) return;
        if(circle1.onPress(gamepad1.circle) && BackSlides.scoringPosition && !scored){
            releaseSample();
        }
        else if (touchpad2.onPress(gamepad2.touchpad) && BackSlides.scoringPosition){
            resetScoring();
        }
    }


    ///// TRANSFER /////

    private static void closeClawDelay(){
        Claw.armsTransfer();
        ActionDelayer.time(300, Claw::closeClawSample, transferTimeScale);
    }

    private static void exitTransfer(){
        Intake.stopCollect();
        ActionDelayer.time(100, Intake::spitoutSlow, transferTimeScale);
        ActionDelayer.time(200, BackSlides::lowChamber, transferTimeScale);
        ActionDelayer.time(300, Claw::armsLowChamber, transferTimeScale);
        ActionDelayer.time(400, FrontSlides :: initPosition, transferTimeScale);
        ActionDelayer.time(400, () -> transferring = false, transferTimeScale);
        ActionDelayer.time(500, Intake::stopCollect, transferTimeScale);
        ActionDelayer.time(550, Claw::clawAngleChamber, transferTimeScale);
        ActionDelayer.time(600, FrontSlides::initPosition, transferTimeScale);
        ActionDelayer.time(800, FrontSlides::initPosition, transferTimeScale);
        ActionDelayer.time(900, Intake::neutral, transferTimeScale);
        ActionDelayer.time(1200, FrontSlides::release, transferTimeScale);
        scored = false;
        lowChamberCase = true;
    }

    private static void prepareClawTransfer(){
        Claw.closeClaw();
        Claw.clawAngleTransfer();
        ActionDelayer.time(100, Claw::armsTransfer, transferTimeScale);
        BackSlides.initPosition();
    }

    private static void prepareClawTransferSpecimen(){
        Claw.closeClaw();
        Claw.clawAngleTransfer();
        ActionDelayer.time(100, Claw::armsInit, transferSpecimenTimeScale);
        BackSlides.initPosition();
    }

    private static void prepareTransfer(){
        Intake.startCollect();
        BackSlides.transferPosition();
        Claw.clawAngleTransfer();
        ActionDelayer.time(100, Claw::openClaw, transferTimeScale);
        Intake.transfer();
    }

    private static void executeTransfer(){
        closeClawDelay();
        ActionDelayer.time(200, ActionManager::exitTransfer, transferTimeScale);
    }

    private static void executeTransferSpecimen(){
        Claw.armsTransfer();
        ActionDelayer.time(200, ActionManager::closeClawDelay);
        ActionDelayer.time(200, ActionManager::exitTransfer);
    }

    private static void transferAfterReset(){
//        FrontSlides.transferPosition();
//        FrontSlides.start();
        ActionDelayer.condition(() -> FrontSlides.reachedInit() && Hardware.backSlides.getCurrentPosition() + 8 >= BackSlides.slidesTransfer && Potentiometers.intakeUpDownInTransfer(), () -> ActionDelayer.time(200, ActionManager::executeTransfer, transferTimeScale));
    }

    private static void transferAfterResetSpecimen(){
//        FrontSlides.transferPosition();
//        FrontSlides.start();
        ActionDelayer.condition(() -> FrontSlides.reachedInit() && Hardware.backSlides.getCurrentPosition() + 5 >= BackSlides.slidesTransfer && Potentiometers.intakeUpDownInTransfer(), () -> ActionDelayer.time(200, ActionManager::executeTransferSpecimen, transferTimeScale));
    }

    public static void transferAuto(String scoreCase){
        ActionDelayer.setTimeScale(transferTimeScale);
        transferFinishScoreCase = scoreCase;
        transferring = true;
        prepareClawTransfer();
        prepareTransfer();
//        ActionDelayer.condition(() -> Potentiometers.intakeUpDownCloseToTransfer(), FrontSlides :: findNewInitPosition);
        ActionDelayer.time(200, FrontSlides :: findNewInitPosition);
//        FrontSlides.findNewInitPosition();
        ActionDelayer.condition(() -> FrontSlides.reachedInit() && Potentiometers.intakeUpDownCloseToTransfer(), ActionManager::transferAfterReset);
    }

    private static void Transfer(){
        if(square2.onPress(gamepad2.square)){
            ActionDelayer.setTimeScale(transferTimeScale);
            transferFinishScoreCase = "low_chamber";
            transferring = true;
            prepareClawTransfer();
            prepareTransfer();
            firstTimeTransfer = System.currentTimeMillis();
            ActionDelayer.time(200, ()-> {
                FrontSlides.findNewInitPosition();
                ActionManager.transferAfterReset();
//                ActionDelayer.condition(() ->Potentiometers.intakeUpDownCloseToTransfer(), ActionManager::transferAfterReset);
            });
        }
    }


    private static void TransferSpecimen(){
        if (FrontSlides.calibrating || transferring) return;
        if(options2.onPress(gamepad2.options)){
            ActionDelayer.setTimeScale(transferSpecimenTimeScale);
            transferFinishScoreCase = "low_chamber";
            transferring = true;
            prepareClawTransferSpecimen();
            prepareTransfer();
            ActionDelayer.condition(Potentiometers :: openerClosed, FrontSlides :: findNewInitPosition);
            ActionDelayer.condition(() -> !FrontSlides.calibrating && Potentiometers.openerClosed(), ActionManager::transferAfterResetSpecimen);
        }
    }

    private static void ClimbCancel(){
//        if (climbCancelConfirmation.onPress(gamepad2.left_bumper)){
//            if (climbProgress == "adjustFirst"){
//                climbProgress = "busy";
//                Climb.anglePrepare();
//                ActionDelayer.time(400, Climb :: extendInit);
//                ActionDelayer.condition(Climb :: motorsReachedInit, Climb :: angleInit);
//                ActionDelayer.condition(Climb :: motorsReachedInit, () -> climbProgress = "begin");
//            }
//        }
    }

    private static void rotateRobot(){
        if (climbProgress == "reachedFirst" || climbProgress == "unHooked"){
            if (gamepad2.right_trigger > 0.5){
                Climb.rotateManually(1, dt);
            }
            else if (gamepad2.left_trigger > 0.5){
                Climb.rotateManually(-1, dt);
            }
        }
    }

    private static void selectClimbLevel(){
        if (gamepad2.left_bumper){
            selectedClimbLevel = ClimbLevel.FIRST_LEVEL;
        }
        else if (gamepad2.right_bumper){
            selectedClimbLevel = ClimbLevel.SECOND_LEVEL;
        }
    }

    private static void Climbing() {
        // TODO: pula mea candidatule cauta ce e ala enum -stamatescu
        if (Objects.equals(climbProgress, "begin") && (selectedClimbLevel == ClimbLevel.FIRST_LEVEL || selectedClimbLevel == ClimbLevel.SECOND_LEVEL)) {
            ClimbFirstPhase();
        } else if (Objects.equals(climbProgress, "adjustFirst")) {
            climbFirstLevel();
        } else if (Objects.equals(climbProgress, "reachedFirst")) {
            climbHangFirstLevel();
        } else if (Objects.equals(climbProgress, "hungFirst") && selectedClimbLevel == ClimbLevel.SECOND_LEVEL) {
            climbStartSecondLevelAscent();
        } else if (Objects.equals(climbProgress, "readyToUnhook")) {
            UnHookFirstLevel();
        } else if (Objects.equals(climbProgress, "unHooked")) {
            ClimbSecondLevel();
        }
    }

    private static void ClimbFirstPhase(){
        climbProgress = "busy";
        Climb.anglePrepare();
        ActionDelayer.time(0, Climb::extendPrepare);
        ActionDelayer.condition(Climb :: motorsReachedPrepare, Climb::angleFirstLevel);
        ActionDelayer.condition(() -> Climb.motorsReachedPrepare() && Climb.armsReachedFirstLevel(), () -> climbProgress = "adjustFirst");
    }

    private static void climbFirstLevel(){
        climbProgress = "busy";
        Climb.pullFirst();
        FrontSlides.retractFullyReleaseAfter();
        Intake.transfer();
        ActionDelayer.time(500, Climb :: angleRaiseFirstLevel);
        ActionDelayer.condition(Climb :: motorsPulledFully, Climb :: angleHangFirstLevel);
        ActionDelayer.condition(() -> Climb.motorsPulledFully() && Climb.armsReachedHangFirstLevel(), () -> climbProgress = "reachedFirst");
    }

    private static void climbHangFirstLevel(){
        climbProgress = "busy";
        Climb.extendSlidesHangFirstLevel();
        ActionDelayer.condition(Climb :: motorsHungFirstLevel, () -> climbProgress = "hungFirst");
    }

    private static void climbStartSecondLevelAscent(){
        climbProgress = "busy";
        Climb.extendTransfer();
        ActionDelayer.time(300, Climb :: angleTransfer);
        ActionDelayer.time(300, Climb :: extendSecondLevel);
        ActionDelayer.condition(Climb :: motorsReachedSecondLevel, Climb :: angleSecondLevel);
        ActionDelayer.condition(() -> Climb.motorsReachedSecondLevel(), () -> ActionDelayer.time(600, () -> climbProgress = "readyToUnhook"));
    }

    private static void PassHook2(){
//        ActionDelayer.condition(Climb :: motorsReachedPassHook2,() -> ActionDelayer.time(800, Climb :: anglePassHook2, unHookTimeScale));
//        ActionDelayer.condition(() -> Climb.motorsReachedPassHook2(), () -> ActionDelayer.time(200, () -> climbProgress = "unHooked", unHookTimeScale));
        ActionDelayer.time(700, Climb :: anglePassHook2, unHookTimeScale);
        ActionDelayer.time(200, () -> climbProgress = "unHooked", unHookTimeScale);
    }

    private static void PassHook(){
        Climb.SlidesPassHook();
        ActionDelayer.condition(Climb :: motorsReachedPassHook, () -> ActionDelayer.time(200, Climb :: anglePassHook, unHookTimeScale));
        ActionDelayer.condition(() -> Climb.motorsReachedPassHook() && Climb.armsReachedPassHook(), () -> ActionDelayer.time(600, Climb :: SlidesPassHook2, unHookTimeScale));
        ActionDelayer.condition(() -> Climb.motorsReachedPassHook() && Climb.armsReachedPassHook(), () -> ActionDelayer.time(1400, ActionManager :: PassHook2, unHookTimeScale));
    }

    private static void UnHookFirstLevel(){
        climbProgress = "busy";
        Climb.SlidesUnHook();
        ActionDelayer.condition(Climb :: motorsUnHooked, Climb :: angleUnHook);
        ActionDelayer.condition(() -> Climb.motorsUnHooked(), () -> ActionDelayer.time(900, ActionManager :: PassHook));
    }

    private static void ClimbSecondLevel()
    {
        climbProgress = "busy";
        ActionDelayer.time(0, Climb::pullSecond);
        ActionDelayer.time(1000, Climb::angleSecondLevelPull);
        ActionDelayer.condition(Climb :: motorsPulledFully, () -> ActionDelayer.time(200, Climb :: angleHangSecondLevel));
        ActionDelayer.condition(Climb :: motorsPulledFully, () -> ActionDelayer.time(200, () -> FrontSlides.extendPercentage(0.28)));
        ActionDelayer.condition(Climb :: motorsPulledFully, () -> climbProgress = "done");
    }

    private static void Inspection(){
        if(gamepad1.touchpad){
            Climb.anglePrepare();
            ActionDelayer.condition(Climb :: armsReachedPrepare, Climb::extendPrepare);
        }
        if (gamepad2.right_trigger > 0.5){
            FrontSlides.extendPercentage(0.65);
//            if (!transferring) Intake.neutral();
        }
        else if (gamepad2.left_trigger > 0.5){
            FrontSlides.findNewInitPosition();
//            if (!transferring) Intake.neutral();
        }
    }
}
