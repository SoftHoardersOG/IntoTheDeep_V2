package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.Hardware.Hardware.telemetry;

import android.drm.DrmStore;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
//import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.Climb;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms.Sweeper;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Utils.ColorSensor;
import org.firstinspires.ftc.teamcode.Utils.OneTap;
import org.firstinspires.ftc.teamcode.Utils.Potentiometers;

import java.util.Objects;


public class ActionManager {

    private enum ClimbLevel {
        NOT_SELECTED,
        FIRST_LEVEL,
        SECOND_LEVEL
    }

    public enum TransferStyles {
        UNASSISTED,
        AUTO_TRANSFER_BASKET,
        AUTO_TRANSFER_SPECIMEN
    }

    private static ClimbLevel selectedClimbLevel;
    public static TransferStyles transferStyle;

    public static float dt;
    public static long previousTime;

    private static Gamepad gamepad1;
    private static Gamepad gamepad2;

    private static OneTap cross1 = new OneTap();
    private static OneTap square1 = new OneTap();
    private static OneTap triangle1 = new OneTap();
    private static OneTap circle1 = new OneTap();
    private static OneTap dpad2up = new OneTap();
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
    private static OneTap autoTransferOneTap = new OneTap();

    private static int colorCycleTime;
    public static boolean scored;
    private static boolean lowChamberCase;
    public static boolean retractedSpecimen;
    public static boolean transferring;
    public static boolean sampleOutTransfer;
    public static boolean backSlidesTilt;
    public static boolean slidesLocked;
    public static String transferFinishScoreCase;
    public static String climbProgress;

    private static double transferTimeScale = 2.3;
    private static double transferSpecimenTimeScale = 1.7;
    private static double unHookTimeScale = 0.8; // 0.75

    private static long firstTimeTransfer;

    public static void init(){
        sampleOutTransfer = true;
        retractedSpecimen = true;
        selectedClimbLevel = ClimbLevel.NOT_SELECTED;
        transferStyle = TransferStyles.AUTO_TRANSFER_BASKET;
        climbProgress = "begin";
        colorCycleTime = 1000;
        //manageLeds();
        previousTime = System.currentTimeMillis();
        ActionDelayer.setTimeScale(transferTimeScale);
        scored = false;
        lowChamberCase = false;
        transferring = false;
        backSlidesTilt = false;
        slidesLocked = false;
    }

    public static void updateInit(){
        Claw.update(Hardware.backSlides.getCurrentPosition(), Hardware.backSlides.getTargetPosition());
    }

    public static void control(Gamepad _gamepad1, Gamepad _gamepad2){
        gamepad1 = _gamepad1;
        gamepad2 = _gamepad2;

        calculateDelta();

        autoTransferOneTap.update((long)(dt * 1000));

        SelectTransferStyle();

        if (transferring && Hardware.intakeUpDown.getPosition() != Intake.intakeUpDownTransfer){
            Intake.transfer();
        }

        Hardware.odometry.update();

        susjos();
        Sweep();
        Transfer();
        IntakeCheck();
        SpitWrongSample();
        IntakeUpDown();
        manageFrontSlides();
        lowBasket();
        highBasket();
        lowChamber();
        highChamber();
        rotateClawScoring();
        score();
        retractSpecimen();
        ClimbCancel();
        selectClimbLevel();
        Climbing();
        Inspection();

        Claw.update(Hardware.backSlides.getCurrentPosition(), Hardware.backSlides.getTargetPosition());

        Intake.updateCollectPosition();
        FrontSlides.update();
    }

    public static void controlAuto(){
        calculateDelta();

        autoTransferOneTap.update((long)(dt * 1000));

        Hardware.odometry.update();

        lowBasketAuto();
        highBasketAuto();
        lowChamberAuto();
        highChamberAuto();

        Claw.update(Hardware.backSlides.getCurrentPosition(), Hardware.backSlides.getTargetPosition());

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

    private static void Sweep(){
        if (dpad2up.onPress(gamepad2.dpad_up)){
            sweepAction();
        }
    }

    public static void sweepAction(){
        Sweeper.sweep();
    }

    private static void SelectTransferStyle(){
        if (gamepad2.share){
            transferStyle = TransferStyles.UNASSISTED;
        }
        else if (gamepad2.left_stick_button){
            transferStyle = TransferStyles.AUTO_TRANSFER_BASKET;
        }
        else if (gamepad2.right_stick_button){
            transferStyle = TransferStyles.AUTO_TRANSFER_SPECIMEN;
        }
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

    private static void SpitWrongSample(){
        if (ColorSensor.collectedWrongSample()){
            Intake.spitoutGround();
            ActionDelayer.time(200, Intake :: collect);
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

//    private static void calibrateFrontSlides(){
//        if (dpad1down.onPress(gamepad1.dpad_down) && !FrontSlides.calibrating){
//            FrontSlides.findNewInitPosition();
//        }
//    }

    private static void manageFrontSlides(){
        if (slidesLocked || transferring || !retractedSpecimen){
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
        ActionDelayer.time(100, Claw::clawPositionLowBasket);
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
        ActionDelayer.time(100, Claw::clawPositionHighBasket);
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
        ActionDelayer.time(100, Claw::clawPositionLowChamber);
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
        ActionDelayer.time(100, Claw::clawPositionHighChamber);
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
//        if (!BackSlides.scoringPosition || lowChamberCase) return;
//        if(gamepad2.dpad_left){
//            Claw.clawAngleRight();
//        }
//        else if(gamepad2.dpad_up){
//            Claw.clawAngleMiddle();
//        }
//        else if(gamepad2.dpad_right){
//            Claw.clawAngleLeft();
//        }
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
        Claw.closeClaw();
        Claw.clawPositionInit();
        ActionDelayer.time(250, BackSlides::initPosition);
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

    private static void retractSpecimen(){
        if (transferStyle == TransferStyles.AUTO_TRANSFER_SPECIMEN){
            if (autoTransferOneTap.onPressSmooth(ColorSensor.collectedAllianceSpecificSample(), 300)) {
                retractedSpecimen = false;
                Intake.neutral();
                ActionDelayer.time(200, Intake::stopCollect);
                ActionDelayer.time(200, FrontSlides :: retractFullyReleaseAfter);
                ActionDelayer.time(1100, () -> retractedSpecimen = true);
            }
        }
    }

    ///// TRANSFER /////

    private static void closeClawDelay(){
//        Claw.clawPositionTransfer();
        ActionDelayer.time(300, Claw::closeClawSample, transferTimeScale);
    }

    private static void exitTransfer(){
        Intake.stopCollect();
        ActionDelayer.time(100, Intake::spitoutSlow, transferTimeScale);
        ActionDelayer.time(200, BackSlides::lowChamber, transferTimeScale);
//        ActionDelayer.time(300, Claw::clawPositionLowChamber, transferTimeScale);
        ActionDelayer.time(400, FrontSlides :: initPosition, transferTimeScale);
        ActionDelayer.time(400, () -> transferring = false, transferTimeScale);
        ActionDelayer.time(500, Intake::stopCollect, transferTimeScale);
        ActionDelayer.time(550, Claw::clawPositionLowChamber, transferTimeScale);
        ActionDelayer.time(1200, Intake::neutral, transferTimeScale);
        ActionDelayer.time(1200, FrontSlides::release, transferTimeScale);
        ActionDelayer.time(3000, () -> sampleOutTransfer = true, transferTimeScale);
        ActionDelayer.time(3000, () -> slidesLocked = false, transferTimeScale);
        scored = false;
        lowChamberCase = true;
    }

    private static void prepareClawTransfer(){
        Claw.closeClaw();
        Claw.clawPositionTransfer();
        ActionDelayer.time(600, Claw :: openClaw, transferTimeScale);
        BackSlides.initPosition();
    }

    private static void prepareClawTransferSpecimen(){
        Claw.closeClaw();
        Claw.clawPositionTransfer();
        BackSlides.initPosition();
    }

    private static void prepareTransfer(){
        Intake.startCollect();
        BackSlides.transferPosition();
//        Claw.clawPositionTransfer();
//        ActionDelayer.time(100, Claw::openClaw, transferTimeScale);
        Intake.transfer();
    }

    private static void executeTransfer(){
        closeClawDelay();
        ActionDelayer.time(200, ActionManager::exitTransfer, transferTimeScale);
    }

    private static void executeTransferSpecimen(){
        Claw.clawPositionTransfer();
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
        sampleOutTransfer = false;
        prepareClawTransfer();
        prepareTransfer();
        ActionDelayer.time(200, ()-> {
            FrontSlides.findNewInitPosition();
            ActionManager.transferAfterReset();
        });
    }

    private static void retryFrontSlidesPull(){
        ActionDelayer.time(300, () -> ActionDelayer.condition(() -> !FrontSlides.pullingFully(), FrontSlides :: findNewInitPosition));
        ActionDelayer.time(300, () -> ActionDelayer.condition(() -> !FrontSlides.pullingFully(), ActionManager :: retryFrontSlidesPull));
    }

    private static void transferProcess(){
        ActionDelayer.setTimeScale(transferTimeScale);
        transferFinishScoreCase = "low_chamber";
        transferring = true;
        sampleOutTransfer = false;
        prepareClawTransfer();
        prepareTransfer();
        firstTimeTransfer = System.currentTimeMillis();
        ActionDelayer.time(200, ()-> {
            FrontSlides.findNewInitPosition();
            slidesLocked = true;
            ActionManager.transferAfterReset();
//            retryFrontSlidesPull();
//                ActionDelayer.condition(() ->Potentiometers.intakeUpDownCloseToTransfer(), ActionManager::transferAfterReset);
        });
    }

    private static void Transfer(){
        if (square2.onPress(gamepad2.square)){
            transferProcess();
        }
        else if (transferStyle == TransferStyles.AUTO_TRANSFER_SPECIMEN){
            if (ColorSensor.collectedYellowSample() && sampleOutTransfer) transferProcess();
        }
        else if (transferStyle == TransferStyles.AUTO_TRANSFER_BASKET){
            if ((ColorSensor.collectedYellowSample() || ColorSensor.collectedAllianceSpecificSample()) && sampleOutTransfer) transferProcess();
        }
    }


//    private static void TransferSpecimen(){
//        if (FrontSlides.calibrating || transferring) return;
//        if(options2.onPress(gamepad2.options)){
//            ActionDelayer.setTimeScale(transferSpecimenTimeScale);
//            transferFinishScoreCase = "low_chamber";
//            transferring = true;
//            prepareClawTransferSpecimen();
//            prepareTransfer();
//            ActionDelayer.condition(Potentiometers :: openerClosed, FrontSlides :: findNewInitPosition);
//            ActionDelayer.condition(() -> !FrontSlides.calibrating && Potentiometers.openerClosed(), ActionManager::transferAfterResetSpecimen);
//        }
//    }

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

//    private static void ClimbTiltBackSlides(){
//        if (backSlidesTilt){
//            double inclination = Math.abs(Hardware.RobotIMU.getRobotOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).firstAngle);
//            double start = 30;
//            double end = 120;
//            if (inclination < start){
//                BackSlides.climbTiltPercentage(1);
//            }
//            else if (inclination > end){
//                BackSlides.climbTiltPercentage(0);
//            }
//            else{
//                double amount = end - start;
//                double current = inclination - start;
//                BackSlides.climbTiltPercentage(current / amount);
//            }
//        }
//    }

    private static void Climbing() {
        // TODO: pula mea candidatule cauta ce e ala enum -stamatescu
        if (Objects.equals(climbProgress, "begin") && (selectedClimbLevel == ClimbLevel.FIRST_LEVEL || selectedClimbLevel == ClimbLevel.SECOND_LEVEL)) {
            climbProgress = "busy";
//            Climb.extendInitRaised();
            ClimbFirstPhase();
        }
        else if (Objects.equals(climbProgress, "adjustFirst")) {
            climbProgress = "busy";
            climbFirstLevel();
        }
        else if (Objects.equals(climbProgress, "reachedFirst")) {
            climbProgress = "busy";
            climbHangFirstLevel();
        }
        else if (Objects.equals(climbProgress, "hungFirst") && selectedClimbLevel == ClimbLevel.SECOND_LEVEL) { // && selectedClimbLevel == ClimbLevel.SECOND_LEVEL
            climbStartSecondLevelAscent();
        }
        else if (Objects.equals(climbProgress, "awaitingConfirmation") && gamepad2.right_bumper){
            climbSecond();
        }
        else if (Objects.equals(climbProgress, "raisedSecond") && selectedClimbLevel == ClimbLevel.SECOND_LEVEL) {
            hangSecond();
        }
        else if (Objects.equals(climbProgress, "releaseConfirmation") && gamepad2.right_bumper) {
            hangSecure();
        }
    }

    private static void ClimbFirstPhase(){
        climbProgress = "busy";
        Climb.anglePrepare();
        ActionDelayer.time(300, Climb::extendPrepare);
        ActionDelayer.condition(Climb :: motorsReachedPrepare, Climb::angleFirstLevel);
        ActionDelayer.condition(() -> Climb.motorsReachedPrepare() && Climb.armsReachedFirstLevel(), () -> climbProgress = "adjustFirst");
    }

    private static void climbFirstLevel(){
        climbProgress = "busy";
        Climb.pullFirst();
        FrontSlides.retractFullyReleaseAfter();
        Intake.transfer();
//        ActionDelayer.time(700, BackSlides :: climbTilt);
//        ActionDelayer.time(700, () -> backSlidesTilt = true);
        ActionDelayer.time(100, Climb :: angleRaiseFirstLevel);
//        ActionDelayer.condition(Climb :: motorsPulledFully, Climb :: angleHangFirstLevel);
        ActionDelayer.condition(() -> Climb.motorsPulledFully(), () -> climbProgress = "reachedFirst");
    }

    private static void climbHangFirstLevel(){
        climbProgress = "busy";
        Climb.angleHangFirstLevel();
        ActionDelayer.time(150, Climb :: extendSlidesHangFirstLevel);
        ActionDelayer.condition(Climb :: motorsHungFirstLevel, () -> climbProgress = "hungFirst");
    }

    private static void climbStartSecondLevelAscent(){
        climbProgress = "busy";
        Climb.extendSecondLevel();
        ActionDelayer.time(300, Climb :: angleTransfer);
        ActionDelayer.condition(Climb :: motorsReachedSecondLevel, () -> {
            Climb.angleSecondLevel();
            climbProgress = "awaitingConfirmation";
        });
    }

    private static void climbSecond(){
        Climb.pullSecond();
        FrontSlides.findNewInitPositionSlower();
        ActionDelayer.time(600, Climb :: angleSecondLevelPull);
        ActionDelayer.condition(Climb :: motorsReachedHalfwaySecond, Climb :: anglePassFirstBar);
//            ActionDelayer.condition(Climb :: motorsReachedSecondRetracted, Climb :: angleHangSecondLevel);
        ActionDelayer.condition(Climb :: motorsPulledFully, () -> climbProgress = "raisedSecond");
    }

    private static void hangSecond(){
        climbProgress = "busy";
        Climb.angleHangSecondLevel();
        climbProgress = "releaseConfirmation";
    }

    private static void hangSecure(){
        climbProgress = "busy";
        Climb.extendInitRaised();
    }

    private static void UnHookFirstLevel(){
        climbProgress = "busy";
        Climb.SlidesUnHook();
        ActionDelayer.condition(Climb :: motorsUnHooked, Climb :: angleUnHook);
        ActionDelayer.condition(() -> Climb.motorsUnHooked(), () -> ActionDelayer.time(900, ActionManager :: PassHook));
    }

    private static void PassHook(){
        Climb.SlidesPassHook();
        ActionDelayer.condition(Climb :: motorsReachedPassHook, () -> ActionDelayer.time(200, Climb :: anglePassHook, unHookTimeScale));
//        ActionDelayer.condition(Climb :: motorsReachedPassHook, () -> ActionDelayer.time(200, BackSlides :: climbTilt, unHookTimeScale));
//        ActionDelayer.condition(Climb :: motorsReachedPassHook, () -> ActionDelayer.time(200, Claw :: clawPositionPark, unHookTimeScale));
        ActionDelayer.condition(() -> Climb.motorsReachedPassHook() && Climb.armsReachedPassHook(), () -> ActionDelayer.time(1200, Climb :: SlidesPassHook2, unHookTimeScale));
        ActionDelayer.condition(() -> Climb.motorsReachedPassHook() && Climb.armsReachedPassHook(), () -> ActionDelayer.time(2000, ActionManager :: PassHook2, unHookTimeScale));
    }

    private static void PassHook2(){
//        ActionDelayer.condition(Climb :: motorsReachedPassHook2,() -> ActionDelayer.time(800, Climb :: anglePassHook2, unHookTimeScale));
//        ActionDelayer.condition(() -> Climb.motorsReachedPassHook2(), () -> ActionDelayer.time(200, () -> climbProgress = "unHooked", unHookTimeScale));
        ActionDelayer.time(700, Climb :: anglePassHook2, unHookTimeScale);
//        ActionDelayer.time(700, BackSlides :: initPosition, unHookTimeScale);
//        ActionDelayer.time(700, Claw :: clawPositionInit, unHookTimeScale);
        ActionDelayer.time(200, () -> climbProgress = "unHooked", unHookTimeScale);
    }

    private static void ClimbSecondLevel()
    {
        climbProgress = "busy";
        ActionDelayer.time(0, Climb::pullSecond);
        ActionDelayer.time(1000, Climb::angleSecondLevelPull);
        ActionDelayer.condition(Climb :: motorsPulledFully, () -> ActionDelayer.time(200, Climb :: angleHangSecondLevel));
//        ActionDelayer.condition(Climb :: motorsPulledFully, () -> ActionDelayer.time(200, () -> FrontSlides.extendPercentage(0.28)));
        ActionDelayer.condition(Climb :: motorsPulledFully, () -> climbProgress = "done");
    }

    private static void susjos(){
        if(gamepad2.dpad_left) {
            ActionDelayer.time(0, Climb::raise);
        }
        else if(gamepad2.dpad_right){
            ActionDelayer.time(0, Climb::pullFirst);
        }
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
