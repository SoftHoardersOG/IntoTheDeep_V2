package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.Climb;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms.Sweeper;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Hardware.ColorSensor;
import org.firstinspires.ftc.teamcode.Utils.GameMap;
import org.firstinspires.ftc.teamcode.Utils.Initializations;
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

    private static final OneTap left_trigger1 = new OneTap();
    private static final OneTap share1 = new OneTap();
    private static final OneTap cross1 = new OneTap();
    private static final OneTap square1 = new OneTap();
    private static final OneTap triangle1 = new OneTap();
    private static final OneTap circle1 = new OneTap();
    private static final OneTap dpad2up = new OneTap();
    private static final OneTap square2 = new OneTap();
    private static final OneTap touchpad2 = new OneTap();
    private static final OneTap frontSlidesKeepPos = new OneTap();
    private static final OneTap autoTransferOneTap = new OneTap();
    private static final OneTap triangle2 = new OneTap();
    private static final OneTap circle2 = new OneTap();
    private static final OneTap cross2 = new OneTap();
    private static final OneTap dpad2down = new OneTap();


    public static boolean colorSensorAuto;

    public static boolean scored;
    private static boolean lowChamberCase;
    private static boolean deliverSampleCase;
    public static boolean retractedSpecimen;
    public static boolean transferring;
    public static boolean sampleOutTransfer;
    public static boolean backSlidesTilt;
    public static boolean slidesLocked;
    public static boolean extendedAll;
    public static String transferFinishScoreCase;
    public static String climbProgress;

    private static final double transferTimeScale = 2.5; // 2.7

    public static void init() {
        sampleOutTransfer = true;
        retractedSpecimen = true;
        selectedClimbLevel = ClimbLevel.NOT_SELECTED;
        transferStyle = TransferStyles.AUTO_TRANSFER_BASKET;
        climbProgress = "begin";
        previousTime = System.currentTimeMillis();
        ActionDelayer.setTimeScale(transferTimeScale);
        scored = false;
        lowChamberCase = false;
        transferring = false;
        backSlidesTilt = false;
        slidesLocked = false;
        colorSensorAuto = false;
        extendedAll = false;
        deliverSampleCase = true;
    }

    public static void updateInit() {
        Claw.update(Hardware.backSlides.getCurrentPosition(), Hardware.backSlides.getTargetPosition());
    }

    public static void control(Gamepad _gamepad1, Gamepad _gamepad2) {
        gamepad1 = _gamepad1;
        gamepad2 = _gamepad2;

        calculateDelta();

        autoTransferOneTap.update((long) (dt * 1000));

        SelectTransferStyle();

//        if (transferring && Hardware.intakeUpDown.getPosition() != Intake.intakeUpDownTransfer) {
//            Intake.transfer();
//        }

        Sweep();
        retractSpecimen();
        Transfer();
        IntakeCheck();
        SpitWrongSample();
        SpitYellowSample();
        IntakeUpDown();
        manageFrontSlides();
        deliverSample();
        lowBasket();
        highBasket();
        lowChamber();
        highChamber();
        score();
        selectClimbLevel();
        Climbing();
        Inspection();
        FrontSlidesSecondDriver();

        Claw.update(Hardware.backSlides.getCurrentPosition(), Hardware.backSlides.getTargetPosition());

        Intake.updateCollectPosition();
        FrontSlides.update();
    }

    public static void controlAuto() {
        calculateDelta();

        autoTransferOneTap.update((long) (dt * 1000));

        Hardware.odometry.update();

        if (colorSensorAuto) SpitWrongSample();

        lowBasketAuto();
        highBasketAuto();
        lowChamberAuto();
        highChamberAuto();

        Claw.update(Hardware.backSlides.getCurrentPosition(), Hardware.backSlides.getTargetPosition());

        Intake.updateCollectPosition();
    }
    public static void controlInterview(Gamepad _gamepad1, Gamepad _gamepad2){
        gamepad1 = _gamepad1;
        gamepad2 = _gamepad2;

        calculateDelta();

        autoTransferOneTap.update((long) (dt * 1000));

        Transfer("high_chamber");
        extendAll();
        IntakeCheck();
        highChamber();
        score();
        rotateClawLeftRight();
        ClimbFirstLevelOnly();

        Claw.update(Hardware.backSlides.getCurrentPosition(), Hardware.backSlides.getTargetPosition());
    }

    private static void calculateDelta() {
        dt = System.currentTimeMillis() - previousTime;
        previousTime = System.currentTimeMillis();
        dt /= 1000;
    }

    private static void extendAll(){
        if (transferring) return;
        boolean input = triangle1.onPress(gamepad1.triangle);
        if (input && !extendedAll){
            FrontSlides.extendPercentage(0.6);
            Intake.ground();
            highChamberPos();
            Climb.extendInterview();
            extendedAll = true;
        }
        else if (input && extendedAll){
            resetScoring();
            Climb.extendInit();
            extendedAll = false;
        }
    }

    private static void rotateClawLeftRight(){
        if (share1.onPress(gamepad1.share)){
            if (Claw.clawOutOfRobot()){
                Claw.rotateHorizontallyManually(-0.2);
                ActionDelayer.time(1000, () -> Claw.rotateHorizontallyManually(0.2));
                ActionDelayer.time(2000, () -> Claw.rotateHorizontallyManually(0));
            }
        }
    }

    private static void Sweep() {
        if (left_trigger1.onPress(gamepad1.left_trigger > 0.5)) {
            sweepAction();
        }
    }

    public static void sweepAction() {
        Sweeper.sweepLarge();
    }

    private static void SelectTransferStyle() {
        if (gamepad2.left_stick_button) {
            transferStyle = TransferStyles.AUTO_TRANSFER_BASKET;
            Claw.horizontalAdjusting = true;
        } else if (gamepad2.right_stick_button) {
            transferStyle = TransferStyles.AUTO_TRANSFER_SPECIMEN;
        }
    }

    private static void IntakeCheck() {
        if (transferring) return;
        if (cross1.onPress(gamepad1.cross || gamepad1.dpad_down)) {
            if (Intake.collecting) {
                Intake.neutral();
                ActionDelayer.time(200, Intake::stopCollect);
            } else {
                Intake.collect();
            }
        }
        if (square1.onPress(gamepad1.square)) {
            if (Intake.spittingGround) {
                Intake.neutral();
                ActionDelayer.time(200, Intake::stopCollect);
            } else {
                Intake.spitoutGround();
            }
        }
    }

    private static void SpitWrongSample() {
        if (ColorSensor.collectedWrongSample()) {
            Intake.spitoutGround();
            ActionDelayer.time(300, Intake::collect);
        }
    }

    private static void SpitYellowSample() {
        if (ColorSensor.collectedYellowSample() && transferStyle == TransferStyles.AUTO_TRANSFER_SPECIMEN) {
            Intake.spitoutGround();
            ActionDelayer.time(300, Intake::collect);
        }
    }

    private static void IntakeUpDown() {
        if (transferring) return;
        if (triangle1.onPress(gamepad1.triangle)) {
            if (Intake.inCollectPosition) {
                Intake.neutral();
                ActionDelayer.time(200, Intake::stopCollect);
            } else {
                Intake.ground();
            }
        }
    }

    private static void manageFrontSlides() {
        if (slidesLocked || transferring || !retractedSpecimen) {
            return;
        }
        if (gamepad1.right_bumper) {
            FrontSlides.extend(dt);
            frontSlidesKeepPos.onPress(false);
        } else if (gamepad1.left_bumper) {
            FrontSlides.retract(dt);
            frontSlidesKeepPos.onPress(false);
        } else if (frontSlidesKeepPos.onPress(true)) {
            FrontSlides.keepPosition();
            ActionDelayer.time(300, FrontSlides::keepPosition);
        }
    }

    public static void deliverSamplePos() {
        ActionDelayer.time(200, Claw::clawPositionDeliverSample1);
        BackSlides.deliverSample();
        Claw.closeClawSample();
        deliverSampleCase = true;
        scored = false;
        lowChamberCase = false;
    }

    private static void deliverSample(){
        if (!transferring && Objects.equals(transferFinishScoreCase, "deliver_sample")) {
            deliverSamplePos();
            transferFinishScoreCase = "";
        }
    }

    public static void lowBasketPos() {
        ActionDelayer.time(100, Claw::clawPositionLowBasket);
        BackSlides.lowBasket();
        Claw.closeClawSample();
        deliverSampleCase = false;
        scored = false;
        lowChamberCase = false;
    }

    private static void lowBasket() {
        boolean input = cross2.onPress(gamepad2.cross);
        if (transferring && input) {
            transferFinishScoreCase = "low_basket";
            return;
        } else if (!transferring && Objects.equals(transferFinishScoreCase, "low_basket")) {
            lowBasketPos();
            transferFinishScoreCase = "";
        } else if (transferring) return;
        if (input) {
            lowBasketPos();
        }
    }

    private static void lowBasketAuto() {
        if (!transferring && Objects.equals(transferFinishScoreCase, "low_basket")) {
            lowBasketPos();
            transferFinishScoreCase = "";
        }
    }

    public static void highBasketPos() {
        ActionDelayer.time(100, () -> {
            Claw.clawPositionHighBasket();
            ActionDelayer.condition(Claw :: clawOutOfRobot, () -> {
                Claw.fixedPositionActive = false;
                Claw.update(Hardware.backSlides.getCurrentPosition(), Hardware.backSlides.getTargetPosition());
            });
        });
        BackSlides.highBasket();
        Claw.closeClawSample();
        deliverSampleCase = false;
        scored = false;
        lowChamberCase = false;
    }

    private static void highBasket() {
        boolean input;
        if (transferStyle == TransferStyles.AUTO_TRANSFER_BASKET) input = triangle2.onPress(gamepad2.triangle || gamepad1.dpad_up);
        else input = triangle2.onPress(gamepad2.triangle);
        if (transferring && input) {
            transferFinishScoreCase = "high_basket";
            return;
        } else if (!transferring && Objects.equals(transferFinishScoreCase, "high_basket")) {
            highBasketPos();
            transferFinishScoreCase = "";
        } else if (transferring) return;
        if (input) {
            highBasketPos();
        }
    }

    private static void highBasketAuto() {
        if (!transferring && Objects.equals(transferFinishScoreCase, "high_basket")) {
            highBasketPos();
            transferFinishScoreCase = "";
        }
    }

    public static void lowChamberPos() {
        ActionDelayer.time(100, Claw::clawPositionLowChamber);
        BackSlides.lowChamber();
        Claw.closeClawSample();
        deliverSampleCase = false;
        scored = false;
        lowChamberCase = true;
    }

    private static void lowChamber() {
        boolean input = dpad2down.onPress(gamepad2.dpad_down);
        if (transferring && input) {
            transferFinishScoreCase = "low_chamber";
            return;
        } else if (!transferring && Objects.equals(transferFinishScoreCase, "low_chamber")) {
            lowChamberPos();
            transferFinishScoreCase = "";
        } else if (transferring) return;
        if (input) {
            lowChamberPos();
        }
    }

    private static void lowChamberAuto() {
        if (!transferring && Objects.equals(transferFinishScoreCase, "low_chamber")) {
            lowChamberPos();
            transferFinishScoreCase = "";
        }
    }

    public static void highChamberPos() {
        ActionDelayer.time(100, () -> {
            Claw.clawPositionHighChamber();
            ActionDelayer.condition(Claw :: clawOutOfRobot, () -> {
                Claw.fixedPositionActive = false;
                Claw.update(Hardware.backSlides.getCurrentPosition(), Hardware.backSlides.getTargetPosition());
            });
        });
        BackSlides.highChamber();
        Claw.closeClawSample();
        deliverSampleCase = false;
        scored = false;
        lowChamberCase = false;
    }

    private static void highChamber() {
        boolean input;
        if (transferStyle == TransferStyles.AUTO_TRANSFER_SPECIMEN) input = circle2.onPress(gamepad2.circle || gamepad1.dpad_up);
        else input = circle2.onPress(gamepad2.circle);
        if (transferring && input) {
            transferFinishScoreCase = "high_chamber";
            return;
        } else if (!transferring && Objects.equals(transferFinishScoreCase, "high_chamber")) {
            highChamberPos();
            transferFinishScoreCase = "";
        } else if (transferring) return;
        if (input) {
            highChamberPos();
        }
    }

    private static void highChamberAuto() {
        if (!transferring && Objects.equals(transferFinishScoreCase, "high_chamber")) {
            highChamberPos();
            transferFinishScoreCase = "";
        }
    }

    public static void releaseSample() {
        scored = true;
        if (deliverSampleCase){
            ActionDelayer.time(50, Claw :: openClaw);
            ActionDelayer.time(150, ActionManager :: resetScoring);
        }
        else if (lowChamberCase) {
            BackSlides.lowChamberFinish();
            ActionDelayer.time(400, Claw::openClawScore);
            ActionDelayer.time(400, () -> lowChamberCase = false);
        } else {
            Claw.openClawScore();
        }
    }

    public static void resetScoring() {
        Claw.closeClaw();
        Claw.clawPositionInit();
        ActionDelayer.time(300, BackSlides::transferPosition);
        scored = false;
    }

    private static void score() {
        if (transferring) return;
        if (circle1.onPress(gamepad1.circle || gamepad1.dpad_left) && BackSlides.scoringPosition && !scored) {
            releaseSample();
        } else if (touchpad2.onPress(gamepad2.touchpad || gamepad1.right_stick_button) && (BackSlides.scoringPosition || Initializations.unPark)) {
            resetScoring();
            Initializations.unPark = false;
        }
    }

    private static void retractSpecimen() {
        if (transferStyle == TransferStyles.AUTO_TRANSFER_SPECIMEN) {
            if (autoTransferOneTap.onPressSmooth(ColorSensor.collectedAllianceSpecificSample(), 300)) {
                deliverSampleCase = true;
            }
        }
    }

    ///// TRANSFER /////

    private static void exitTransferPos(){
        deliverSample();
        lowBasketAuto();
        highBasketAuto();
        lowChamberAuto();
        highChamberAuto();
    }

    private static void exitTransfer() {
        ActionDelayer.time(100, Intake::spitoutSlow, transferTimeScale);
        ActionDelayer.time(100, () -> {
            transferring = false;
            exitTransferPos();
        }, transferTimeScale);
        ActionDelayer.time(600, Intake::stopCollect, transferTimeScale);
        ActionDelayer.time(600, Intake::neutral, transferTimeScale);
        ActionDelayer.time(600, FrontSlides::release, transferTimeScale);
        ActionDelayer.time(3000, () -> sampleOutTransfer = true, transferTimeScale);
        ActionDelayer.time(3000, () -> slidesLocked = false, transferTimeScale);
        scored = false;
        lowChamberCase = true;
    }

    private static void prepareClawTransfer() {
        Claw.closeClaw();
        Claw.clawPositionTransfer();
        ActionDelayer.condition(Claw :: reachedVerticalTarget, Claw::openClaw);
        BackSlides.transferPosition();
    }

    private static void prepareTransfer() {
        Intake.close();
        BackSlides.transferPosition();
        if (GameMap.inLowerArea()) ActionDelayer.time(100, Intake :: neutral);
        else ActionDelayer.time(100, Intake :: transferPrepare);
        Intake.startCollectSlow();
    }

    private static void executeTransfer() {
        Intake.transfer();
        ActionDelayer.condition(Potentiometers :: intakeUpDownInTransfer, () ->
                ActionDelayer.time(250, () -> {
                    Claw.closeClawSample();
                    ActionManager.exitTransfer();
                }, transferTimeScale)
        );
    }

    private static void transferAfterReset() {
        ActionDelayer.condition(() -> FrontSlides.reachedInit() &&
                Math.abs(Hardware.backSlides.getCurrentPosition() - BackSlides.slidesTransfer) <= 8  &&
                Claw.reachedVerticalTarget(), () -> ActionDelayer.time(0, ActionManager::executeTransfer, transferTimeScale));
    }

    public static void transferAuto(String scoreCase) {
        transferProcess(scoreCase);
    }


    private static void transferProcess() {
        ActionDelayer.setTimeScale(transferTimeScale);
        transferFinishScoreCase = "low_chamber";
        transferring = true;
        sampleOutTransfer = false;
        prepareClawTransfer();
        prepareTransfer();
        if (GameMap.inLowerArea()){
            ActionDelayer.time(200, () -> {
                FrontSlides.findNewInitPosition();
                slidesLocked = true;
                ActionManager.transferAfterReset();
            });
        }
        else {
            ActionDelayer.time(200, () -> {
                FrontSlides.findNewInitPosition();
                slidesLocked = true;
                ActionManager.transferAfterReset();
            });
        }
    }

    private static void transferProcess(String transferCase) {
        ActionDelayer.setTimeScale(transferTimeScale);
        transferFinishScoreCase = transferCase;
        transferring = true;
        sampleOutTransfer = false;
        prepareClawTransfer();
        prepareTransfer();
        if (GameMap.inLowerArea()){
            Intake.neutral();
            Intake.startCollectSlow();
            ActionDelayer.time(200, () -> {
                FrontSlides.findNewInitPosition();
                slidesLocked = true;
                ActionManager.transferAfterReset();
            });
        }
        else {
            ActionDelayer.time(200, () -> {
                FrontSlides.findNewInitPosition();
                slidesLocked = true;
                ActionManager.transferAfterReset();
            });
        }
    }

    private static void Transfer() {
        if (square2.onPress(gamepad2.square)) {
            transferProcess();
        } else if (transferStyle == TransferStyles.AUTO_TRANSFER_SPECIMEN) {
//            if (ColorSensor.collectedYellowSample() && sampleOutTransfer) transferProcess();
            if (ColorSensor.collectedAllianceSpecificSample() && sampleOutTransfer) {
                transferProcess("deliver_sample");
                deliverSampleCase = true;
            }
        } else if (transferStyle == TransferStyles.AUTO_TRANSFER_BASKET) {
            if ((ColorSensor.collectedYellowSample() || ColorSensor.collectedAllianceSpecificSample()) && sampleOutTransfer)
                transferProcess();
        }
    }

    private static void Transfer(String scoreCase) {
        if (square2.onPress(gamepad2.square)) {
            transferProcess(scoreCase);
        } else if (transferStyle == TransferStyles.AUTO_TRANSFER_SPECIMEN) {
//            if (ColorSensor.collectedYellowSample() && sampleOutTransfer) transferProcess();
            if (ColorSensor.collectedAllianceSpecificSample() && sampleOutTransfer) {
                transferProcess("deliver_sample");
                deliverSampleCase = true;
            }
        } else if (transferStyle == TransferStyles.AUTO_TRANSFER_BASKET) {
            if ((ColorSensor.collectedYellowSample() || ColorSensor.collectedAllianceSpecificSample()) && sampleOutTransfer)
                transferProcess(scoreCase);
        }
    }

    private static void selectClimbLevel() {
        if (gamepad2.left_bumper) {
            selectedClimbLevel = ClimbLevel.FIRST_LEVEL;
        } else if (gamepad2.right_bumper) {
            selectedClimbLevel = ClimbLevel.SECOND_LEVEL;
        }
    }

    private static void ClimbFirstLevelOnly(){
        if (Objects.equals(climbProgress, "begin") && (gamepad1.left_bumper || gamepad1.right_bumper)) {
            climbProgress = "busy";
            ClimbFirstPhase();
        } else if (Objects.equals(climbProgress, "adjustFirst")) {
            climbProgress = "busy";
            climbFirstLevel();
        } else if (Objects.equals(climbProgress, "reachedFirst")) {
            climbProgress = "busy";
            climbHangFirstLevel();
        }
    }

    private static void Climbing() {
        // TODO: pula mea candidatule cauta ce e ala enum -stamatescu
        if (Objects.equals(climbProgress, "begin") && (selectedClimbLevel == ClimbLevel.FIRST_LEVEL || selectedClimbLevel == ClimbLevel.SECOND_LEVEL)) {
            climbProgress = "busy";
            ClimbFirstPhase();
        } else if (Objects.equals(climbProgress, "adjustFirst")) {
            climbProgress = "busy";
            climbFirstLevel();
        } else if (Objects.equals(climbProgress, "reachedFirst")) {
            climbProgress = "busy";
            climbHangFirstLevel();
        } else if (Objects.equals(climbProgress, "hungFirst") && selectedClimbLevel == ClimbLevel.SECOND_LEVEL) {
            climbStartSecondLevelAscent();
        } else if (Objects.equals(climbProgress, "awaitingConfirmation") && gamepad2.right_bumper) {
            climbSecond();
        } else if (Objects.equals(climbProgress, "raisedSecond") && selectedClimbLevel == ClimbLevel.SECOND_LEVEL && gamepad2.right_bumper) {
            hangSecond();
        }
    }

    private static void ClimbFirstPhase() {
        climbProgress = "busy";
        Climb.enableMotors();
        Sweeper.closeFull();
        Climb.anglePrepare();
        ActionDelayer.time(300, Climb::extendPrepare);
        ActionDelayer.condition(Climb::motorsReachedPrepare, Climb::angleFirstLevel);
        ActionDelayer.condition(() -> Climb.motorsReachedPrepare() && Climb.armsReachedFirstLevel(), () -> ActionDelayer.time(300, () -> climbProgress = "adjustFirst"));
    }

    private static void climbFirstLevel() {
        climbProgress = "busy";
        Climb.pullFirst();
        FrontSlides.findNewInitPosition();
        Intake.transfer();
        BackSlides.climbPosition();
        Claw.clawPositionClimb();
        ActionDelayer.time(500, Climb::angleRaiseFirstLevel);
        ActionDelayer.condition(Climb::motorsPulledFully, () -> climbProgress = "reachedFirst");
    }

    private static void climbHangFirstLevel() {
        climbProgress = "busy";
        Climb.angleHangFirstLevel();
        ActionDelayer.time(150, Climb::extendSlidesHangFirstLevel);
        ActionDelayer.condition(Climb::motorsHungFirstLevel, () -> climbProgress = "hungFirst");
    }

    private static void climbStartSecondLevelAscent() {
        climbProgress = "busy";
        Climb.extendSecondLevel();
        Climb.angleTransfer();
        ActionDelayer.condition(Climb::motorsReachedSecondLevel, () -> {
            Climb.angleSecondLevel();
            climbProgress = "awaitingConfirmation";
        });
    }

    private static void climbSecond() {
        Hardware.climbRightSlides.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Hardware.climbLeftSlides.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Hardware.climbRightSlides.setPower(-1);
        Hardware.climbLeftSlides.setPower(1);
        FrontSlides.findNewInitPositionSlower();
        ActionDelayer.time(300, Climb::angleSecondLevelPull);
        ActionDelayer.condition(Climb::motorsReachedHalfwaySecond, Climb::anglePassFirstBar);
//        ActionDelayer.condition(Climb::motorsReachedAlmostHalfway, Claw::clawPositionClimb2);
        ActionDelayer.condition(Climb::motorsPulledFully, () -> climbProgress = "raisedSecond");
    }

    private static void hangSecond() {
        climbProgress = "busy";
        Climb.angleHangSecondLevel();
        climbProgress = "releaseConfirmation";
    }

    private static void Inspection() {
        if (gamepad1.touchpad) {
            Climb.angleInspection();
            Climb.extendInspection();
        }
        if (gamepad2.share) {
            FrontSlides.extendPercentage(0.9);
            Intake.Inspection();
        } else if (gamepad2.left_trigger > 0.5) {
            FrontSlides.findNewInitPosition();
        }
    }

    private static void FrontSlidesSecondDriver(){
        if (gamepad2.right_trigger > 0.5) {
            FrontSlides.extendPercentage(0.8);
        }
    }
}
