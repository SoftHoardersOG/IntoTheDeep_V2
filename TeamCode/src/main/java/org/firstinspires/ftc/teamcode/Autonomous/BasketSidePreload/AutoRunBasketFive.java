package org.firstinspires.ftc.teamcode.Autonomous.BasketSidePreload;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Hardware.Limelight;
import org.firstinspires.ftc.teamcode.Mechanisms.Sweeper;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Hardware.ColorSensor;
import org.firstinspires.ftc.teamcode.Utils.ConditionChecker;
import org.firstinspires.ftc.teamcode.Utils.GameMap;
import org.firstinspires.ftc.teamcode.Utils.Initializations;

import java.util.List;


public class AutoRunBasketFive implements Runnable {
    private SampleMecanumDrive drive;

    public AutoRunBasketFive(SampleMecanumDrive Drive){
        drive = Drive;
    }

    private enum AutoState {
        START,
        SCORED_PRELOAD,
        SCORED_FIRST,
        SCORED_SECOND,
        SCORED_THIRD,
        SCORED_FOURTH,
        SCORED_FIFTH
    }
    private static double firstSampleExtend = 0.55;
    private static double secondSampleExtend = 0.45;
    private static double thirdSamplePrepareExtend = 0.25;
    private static double thirdSampleExtend = 0.7;

    private static boolean collectedFirst = false;
    private static boolean collectedSecond = false;
    private static boolean collectedThird = false;

    private static List<LLResultTypes.DetectorResult> result;
    private static LLResultTypes.DetectorResult target;

    private static long startTime;

    private static long lastTime;

    private static AutoState progress;

    private static boolean collectedExtra;

    public void init(){
        progress = AutoState.START;
        drive.setPoseEstimate(new Pose2d(-40.99, -64.43, Math.toRadians(0.00)));
        BasketSidePreloadTrajectories.setDrive(drive);
        GameMap.init(drive);
        collectedExtra = false;
        collectedFirst = false;
        collectedSecond = false;
        collectedThird = false;
    }

    @Override
    public void run() {
        runAuto();
    }

    private void printSegmentTime(String text){
        Hardware.telemetry.log().add(text + Double.toString(1.0 * (System.currentTimeMillis() - lastTime) / 1000));
        lastTime = System.currentTimeMillis();
    }

    private void runAuto(){
        lastTime = System.currentTimeMillis();
        startTime = lastTime;
        FrontSlides.release();
        placePreload();
        ActionDelayer.condition(() -> progress == AutoState.SCORED_PRELOAD, () -> {
            printSegmentTime("Preload Sample: ");
            FirstSample();
        });
        ActionDelayer.condition(() -> progress == AutoState.SCORED_FIRST, () -> {
            printSegmentTime("First Sample: ");
            SecondSample();
        });
        ActionDelayer.condition(() -> progress == AutoState.SCORED_SECOND, () -> {
            printSegmentTime("Second Sample: ");
            ThirdSample();
        });
        ActionDelayer.condition(() -> progress == AutoState.SCORED_THIRD, () -> {
            printSegmentTime("Third Sample: ");
            FourthSample();
        });
        ActionDelayer.condition(() -> progress == AutoState.SCORED_FOURTH, () -> {
            long timeLeft = 30000 - (System.currentTimeMillis() - startTime);
            if (timeLeft < 2500){
                quickPark();
            }
            else{
                park();
            }
        });
//        ActionDelayer.condition(() -> progress == AutoState.SCORED_FOURTH, () -> {
//            long timeLeft = 30000 - (System.currentTimeMillis() - startTime);
//            printSegmentTime("Fourth Sample: ");
//            if (timeLeft < 2000){
//                quickPark();
//            }
//            else if (timeLeft < 7000){
//                park();
//            }
//            else{
//                FifthSample();
//            }
//        });
//        ActionDelayer.condition(() -> progress == AutoState.SCORED_THIRD, this :: park);
    }



    private void placePreload(){
        Intake.neutral();
        ActionManager.highBasketPos();
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSamplePreload());
        ActionDelayer.time(1500, () -> FrontSlides.extendPercentage(firstSampleExtend, 1));
        ActionDelayer.time(1500, Intake :: collectWide);
        ActionDelayer.time(1500, ActionManager :: releaseSample);
        ActionDelayer.time(1700, () -> progress = AutoState.SCORED_PRELOAD);
    }



    private void FirstSample(){
        ActionDelayer.time(0, this :: goToFirstSample);
    }

    private void goToFirstSample(){
        ActionDelayer.time(0, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToFirstSample()));
        ActionDelayer.time(0, this :: CollectFirstSample);
        ActionDelayer.time(100, Claw :: closeClaw);
        ActionDelayer.time(650, ActionManager :: resetScoring);
    }

    private void CollectFirstSample(){
        Intake.collectWide();
        ActionDelayer.condition(() -> ColorSensor.collectedYellowSample() || collectedFirst, () -> {
            collectedFirst = true;
            ActionDelayer.time(400, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(500, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time( 600, this :: ScoreFirstSample)));
        });
        ActionDelayer.time(1000, () -> collectedFirst = true);
    }

    private void ScoreFirstSample(){
        FrontSlides.release();
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketFirstSample());
        ActionDelayer.time(800, () -> FrontSlides.extendPercentage(secondSampleExtend, 1));
        ActionDelayer.time(800, Intake :: collectWide);
        ActionDelayer.time(1400, ActionManager :: releaseSample);
        ActionDelayer.time(1500, () -> progress = AutoState.SCORED_FIRST);
    }






    private void SecondSample()
    {
        ActionDelayer.time(0, this :: goToSecondSample);
        ActionDelayer.time(100, Claw :: closeClaw);
        ActionDelayer.time(500, ActionManager :: resetScoring);
    }

    private void goToSecondSample()
    {
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToSecondSample());
        ActionDelayer.time(0, this :: CollectSecondSample);
    }

    private void CollectSecondSample() {
        Intake.collectWide();
        ActionDelayer.condition(() -> ColorSensor.collectedYellowSample() || collectedSecond, () -> {
            collectedSecond = true;
            ActionDelayer.time(600, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(700, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(700, this :: ScoreSecondSample)));
        });
        ActionDelayer.time(1000, () -> collectedSecond = true);
    }

    private void ScoreSecondSample(){
        FrontSlides.release();
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSecondSample());
        ActionDelayer.time(400, () -> FrontSlides.extendPercentage(thirdSamplePrepareExtend, 1));
        ActionDelayer.time(900, ActionManager :: releaseSample);
        ActionDelayer.time(900, () -> progress = AutoState.SCORED_SECOND);
    }





    private void ThirdSample() {
        ActionDelayer.time(0, this :: goToThirdSample);
        ActionDelayer.time(100, Claw :: closeClaw);
        ActionDelayer.time(500, ActionManager :: resetScoring);
    }

    private void goToThirdSample() {
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToThirdSample());
        ActionDelayer.time(700, Intake :: collectWide);
        ActionDelayer.time(1400, () -> FrontSlides.extendPercentage(thirdSamplePrepareExtend, 1));
        ActionDelayer.time(1900, this :: CollectThirdSample);
    }

    private void CollectThirdSample() {
        FrontSlides.extendPercentage(thirdSampleExtend, 0.3);
        ActionDelayer.condition(() -> ColorSensor.collectedYellowSample() || collectedThird, () -> {
            collectedThird = true;
            ActionDelayer.time(0, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.Turn()));
            ActionDelayer.time(100, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(200, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(1000, this :: ScoreThirdSample)));
        });
        ActionDelayer.time(2000,
                () -> {
                    if (!collectedThird) drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToThirdSampleRetry());
                    ActionDelayer.time(500, () -> collectedThird = true);
                });
    }

    private void ScoreThirdSample(){
        FrontSlides.release();
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketThirdSample());
        ActionDelayer.time(800, ActionManager :: releaseSample);
        ActionDelayer.time(800, () -> progress = AutoState.SCORED_THIRD);
    }


    private void FourthSample(){
        Intake.clearLimelightView();
        ActionDelayer.time(500, ActionManager :: resetScoring);
        ActionDelayer.time(500, FrontSlides :: findNewInitPosition);
        goToFourthSample();
    }

    private void goToFourthSample(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToSubmersible());
        ActionDelayer.condition(() -> !drive.isBusy(), () -> {
            Sweeper.open();
            ActionDelayer.time(300, Sweeper :: close);
            ActionDelayer.time(600, () -> {
                TryCollectFromSubmersible(AutoState.SCORED_FOURTH);
            });
        });
    }

    private void FifthSample(){
        Intake.clearLimelightView();
        ActionDelayer.time(500, ActionManager :: resetScoring);
        goToFifthSample();
    }

    private void goToFifthSample(){
        drive.followTrajectorySequence(BasketSidePreloadTrajectories.goToSubmersible());
        Sweeper.open();
        ActionDelayer.time(300, Sweeper :: close);
        ActionDelayer.time(500, () -> {
            TryCollectFromSubmersible(AutoState.SCORED_FIFTH);
        });
    }

    private void park(){
        Intake.transfer();
        FrontSlides.findNewInitPosition();
        ActionDelayer.time(0, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.park()));
        ActionDelayer.time(600, ActionManager :: resetScoring);
        ActionDelayer.time(1000, () -> {
            Claw.clawPositionPark();
            Initializations.unPark = true;
        });
    }

    private void quickPark(){
        ActionDelayer.time(0, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.quickPark()));
        ActionDelayer.time(600, ActionManager :: resetScoring);
    }

    private double getTargetOffset(LLResultTypes.DetectorResult trackedTarget){
        return Limelight.horizontalOffset(trackedTarget.getTargetXPixels(), trackedTarget.getTargetYPixels());
    }

    private void TryCollectFromSubmersible(AutoState endState){
        if (ConditionChecker.opModeStopped) return;

        collectedExtra = false;

        result = Limelight.getDetectorResults();
        if (result == null){
            retryTrajectory();
            TryCollectFromSubmersible(endState);
            return;
        }

        target = null;
        for (int k = result.size() - 1; k >= 0; k--){
            LLResultTypes.DetectorResult i = result.get(k);
            if (Limelight.isYellowSample(i.getClassId()) || Limelight.isAllianceSample(i.getClassId())){
                target = i;
                break;
            }
        }

        if (target == null){
            retryTrajectory();
            TryCollectFromSubmersible(endState);
            return;
        }

        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.Strafe(getTargetOffset(target)));
        ActionDelayer.time(300, () -> CollectFromSubmersible(endState));
    }

    private void retryTrajectory() {
        drive.followTrajectorySequence(BasketSidePreloadTrajectories.Strafe(-3));
    }

    private void CollectFromSubmersible(AutoState endState){
        ActionManager.colorSensorAuto = true;
        Intake.collectWide();
        ActionDelayer.time(200, () -> FrontSlides.extendPercentage(1, 0.32));
        ActionDelayer.condition(() -> ColorSensor.collectedYellowSample() || ColorSensor.collectedAllianceSpecificSample(), () -> {
            scoreFromSubmersible(endState);
            collectedExtra = true;
        });
        ActionDelayer.time(2500,
                () ->{
                    if (!collectedExtra){
                        long timeLeft = 30000 - (System.currentTimeMillis() - startTime);
                        if (timeLeft > 4000) retryCollectionCycle(endState);
                        else park();
                    }
                });
    }

    private void retryCollectionCycle(AutoState endState){
        Intake.clearLimelightView();
        FrontSlides.findNewInitPosition();
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.Strafe(-13));
        ActionDelayer.time(800, Sweeper :: open);
        ActionDelayer.time(1000, Sweeper :: close);
        ActionDelayer.time(1000, () -> TryCollectFromSubmersible(endState));
    }

    private void scoreFromSubmersible(AutoState endState){
        ActionManager.transferAuto("high_basket");
        ActionDelayer.time(200, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.distanceFromSubmersible()));
        ActionDelayer.time(1200, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketFromSubmersible()));
        ActionDelayer.time(2800, ActionManager :: releaseSample);
        ActionDelayer.time(2800, () -> progress = endState);
    }
}
