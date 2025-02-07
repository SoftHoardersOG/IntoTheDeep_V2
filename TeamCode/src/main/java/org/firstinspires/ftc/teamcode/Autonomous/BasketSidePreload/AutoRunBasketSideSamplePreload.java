package org.firstinspires.ftc.teamcode.Autonomous.BasketSidePreload;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms.Limelight;
import org.firstinspires.ftc.teamcode.Mechanisms.Sweeper;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Utils.ColorSensor;
import org.firstinspires.ftc.teamcode.Utils.ConditionChecker;
import org.firstinspires.ftc.teamcode.Utils.GameMap;
import org.firstinspires.ftc.teamcode.Utils.Lambda;
import org.firstinspires.ftc.teamcode.Utils.Sleep;

import java.util.List;


public class AutoRunBasketSideSamplePreload implements Runnable {
    private SampleMecanumDrive drive;

    public AutoRunBasketSideSamplePreload(SampleMecanumDrive Drive){
        drive = Drive;
    }

    private enum AutoState {
        START,
        SCORED_PRELOAD,
        SCORED_FIRST,
        SCORED_SECOND,
        SCORED_THIRD,
        SCORED_FOURTH,
        SCORED_FIFTH,
        SCORED_SIXTH

    }
    private static double firstSampleExtend = 0.55;
    private static double secondSampleExtend = 0.45;
    private static double thirdSamplePrepareExtend = 0.3;
    private static double thirdSampleExtend = 0.7;

    private static List<LLResultTypes.DetectorResult> result;
    private static LLResultTypes.DetectorResult target;

    private static long lastTime;

    private static AutoState progress;

    public void init(){
        progress = AutoState.START;
        drive.setPoseEstimate(new Pose2d(-40.99, -64.43, Math.toRadians(0.00)));
        BasketSidePreloadTrajectories.setDrive(drive);
        GameMap.init(drive);
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
            printSegmentTime("Fourth Sample: ");
            FifthSample();
        });
        ActionDelayer.condition(() -> progress == AutoState.SCORED_FIFTH, () -> {
            printSegmentTime("Fifth Sample: ");
            SixthSample();
        });
//        ActionDelayer.condition(() -> progress == AutoState.SCORED_THIRD, this :: park);
    }



    private void placePreload(){
        ActionManager.highBasketPos();
        ActionDelayer.time(0, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSamplePreload()));
        ActionDelayer.time(1500, () -> FrontSlides.extendPercentage(firstSampleExtend, 1));
        ActionDelayer.time(1500, Intake :: collectWide);
        ActionDelayer.time(2300, ActionManager :: releaseSample);
        ActionDelayer.time(2300, () -> progress = AutoState.SCORED_PRELOAD);
    }



    private void FirstSample(){
        ActionDelayer.time(0, this :: goToFirstSample);
    }

    private void goToFirstSample(){
        ActionDelayer.time(0, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToFirstSample()));
        ActionDelayer.time(0, this :: CollectFirstSample);
        ActionDelayer.time(400, ActionManager :: resetScoring);
    }

    private void CollectFirstSample(){
        Intake.collectWide();
        ActionDelayer.condition(() -> ColorSensor.collectedYellowSample(), () -> {
            ActionDelayer.time(300, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(400, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(500, this :: ScoreFirstSample)));
        });
    }

    private void ScoreFirstSample(){
        FrontSlides.release();
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketFirstSample());
        ActionDelayer.time(800, () -> FrontSlides.extendPercentage(secondSampleExtend, 1));
        ActionDelayer.time(800, Intake :: collectWide);
        ActionDelayer.time(800, ActionManager :: releaseSample);
        ActionDelayer.time(1200, () -> progress = AutoState.SCORED_FIRST);
    }






    private void SecondSample()
    {
        ActionDelayer.time(0, this :: goToSecondSample);
        ActionDelayer.time(300, ActionManager :: resetScoring);
    }

    private void goToSecondSample()
    {
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToSecondSample());
        ActionDelayer.time(0, this :: CollectSecondSample);
    }

    private void CollectSecondSample() {
        Intake.collectWide();
        ActionDelayer.condition(() -> ColorSensor.collectedYellowSample(), () -> {
            ActionDelayer.time(200, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(300, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(500, this :: ScoreSecondSample)));
        });
    }

    private void ScoreSecondSample(){
        FrontSlides.release();
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSecondSample());
        ActionDelayer.time(400, () -> FrontSlides.extendPercentage(thirdSamplePrepareExtend, 1));
        ActionDelayer.time(600, ActionManager :: releaseSample);
        ActionDelayer.time(800, () -> progress = AutoState.SCORED_SECOND);
    }





    private void ThirdSample() {
        ActionDelayer.time(0, this :: goToThirdSample);
        ActionDelayer.time(400, ActionManager :: resetScoring);
    }

    private void goToThirdSample() {
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToThirdSample());
        ActionDelayer.time(700, Intake :: collectWide);
        ActionDelayer.time(1000, () -> FrontSlides.extendPercentage(thirdSamplePrepareExtend, 1));
        ActionDelayer.time(1500, this :: CollectThirdSample);
    }

    private void CollectThirdSample() {
        FrontSlides.extendPercentage(thirdSampleExtend, 1);
        ActionDelayer.condition(() -> ColorSensor.collectedYellowSample(), () -> {
            ActionDelayer.time(0, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.Turn()));
            ActionDelayer.time(200, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(300, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(600, this :: ScoreThirdSample)));
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
        goToFourthSample();
    }

    private void goToFourthSample(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToSubmersible());
        ActionDelayer.condition(() -> !drive.isBusy(), () -> {
            Sweeper.open();
            ActionDelayer.time(300, Sweeper :: close);
            ActionDelayer.time(500, () -> {
                TryCollectFromSubmersible(AutoState.SCORED_FOURTH);
            });
        });
    }

    private void FifthSample(){
        Intake.clearLimelightView();
//        ActionDelayer.time(500, ActionManager :: resetScoring);
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

    private void SixthSample(){
        Intake.clearLimelightView();
//        ActionDelayer.time(500, ActionManager :: resetScoring);
        goToSixthSample();
    }

    private void goToSixthSample(){
        drive.followTrajectorySequence(BasketSidePreloadTrajectories.goToSubmersible());
        Sweeper.open();
        ActionDelayer.time(300, Sweeper :: close);
        ActionDelayer.time(500, () -> {
            TryCollectFromSubmersible(AutoState.SCORED_SIXTH);
        });
    }

    private double getTargetOffset(LLResultTypes.DetectorResult trackedTarget){
        return Limelight.horizontalOffset(trackedTarget.getTargetXPixels());
    }

    private void TryCollectFromSubmersible(AutoState endState){
        if (ConditionChecker.opModeStopped) return;

        result = Limelight.getDetectorResults();
        if (result == null){
            retryTrajectory();
            TryCollectFromSubmersible(endState);
            return;
        }

        target = null;
        for (LLResultTypes.DetectorResult i : result){
            if (i.getClassId() == 2){ // e gen yellow
                target = i;
                break;
            }
        }

        if (target == null){
            retryTrajectory();
            TryCollectFromSubmersible(endState);
            return;
        }

        drive.followTrajectorySequence(BasketSidePreloadTrajectories.Strafe(getTargetOffset(target)));
        CollectFromSubmersible(endState);
    }

    private void retryTrajectory() {
        drive.followTrajectorySequence(BasketSidePreloadTrajectories.Strafe(-3));
    }

    private void CollectFromSubmersible(AutoState endState){
        Intake.collect();
        ActionDelayer.time(200, () -> FrontSlides.extendPercentage(1, 0.4));
        ActionDelayer.condition(() -> ColorSensor.collectedYellowSample(), () -> scoreFromSubmersible(endState));
    }

    private void scoreFromSubmersible(AutoState endState){
        ActionManager.transferAuto("high_basket");
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketFromSubmersible());
        ActionDelayer.time(3000, ActionManager :: releaseSample);
        ActionDelayer.time(3000, () -> progress = endState);
    }

    private void park(){
        ActionDelayer.time(100, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.park()));
        ActionDelayer.time(600, ActionManager :: resetScoring);
        ActionDelayer.time(2800, Claw :: clawPositionPark);
    }

}
