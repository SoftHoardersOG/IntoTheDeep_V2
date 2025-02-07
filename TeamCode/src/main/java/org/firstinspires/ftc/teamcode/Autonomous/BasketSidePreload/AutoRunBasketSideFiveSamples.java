package org.firstinspires.ftc.teamcode.Autonomous.BasketSidePreload;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Utils.ColorSensor;
import org.firstinspires.ftc.teamcode.Utils.GameMap;

public class AutoRunBasketSideFiveSamples implements Runnable{
    private SampleMecanumDrive drive;

    public AutoRunBasketSideFiveSamples(SampleMecanumDrive Drive){
        drive = Drive;
    }

    private enum AutoState {
        START,
        SCORED_PRELOAD,
        SCORED_SECOND_PRELOAD,
        SCORED_FIRST,
        SCORED_SECOND,
        SCORED_THIRD,
        SCORED_FOURTH

    }

    private static double secondPreloadExtend = 0.86;
    private static double firstSampleExtend = 0.69;
    private static double secondSampleExtend = 0.74;
    private static double thirdSampleExtend = 0.32;

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

    private void runAuto(){
        placePreload();
        ActionDelayer.condition(() -> progress == AutoState.SCORED_PRELOAD, this :: SecondPreload);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_SECOND_PRELOAD, this :: FirstSample);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_FIRST, this :: SecondSample);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_SECOND, this :: ThirdSample);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_THIRD, this :: park);
    }



    private void placePreload(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.distanceFromWall());
        ActionDelayer.time(1100, ActionManager:: highBasketPos);
        ActionDelayer.time(1400, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSamplePreloadSlower()));
        ActionDelayer.time(2600, ActionManager :: releaseSample);
        ActionDelayer.time(2600, () -> progress = AutoState.SCORED_PRELOAD);
    }

    private void SecondPreload(){
        ActionDelayer.time(0, this :: goToSecondPreload);
        ActionDelayer.time(400, ActionManager :: resetScoring);
    }

    private void goToSecondPreload(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToSecondSamplePreload());
        ActionDelayer.time(2500, this::CollectSecondPreload);
    }

    private void CollectSecondPreload(){
        FrontSlides.extendPercentage(secondPreloadExtend, 0.25);
        Intake.collectWide();
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(secondPreloadExtend - 0.10) || ColorSensor.collectedYellowSample(), () -> {
            ActionDelayer.time(100, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(700, this :: ScoreSecondPreload);
        });
    }

    private void ScoreSecondPreload(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSamplePreload());
        ActionDelayer.time(3600, ActionManager :: releaseSample);
        ActionDelayer.time(3600, () -> progress = AutoState.SCORED_SECOND_PRELOAD);
    }

    private void FirstSample(){
        ActionDelayer.time(0, this :: goToFirstSample);
        ActionDelayer.time(800, ActionManager :: resetScoring);
    }

    private void goToFirstSample(){
        ActionDelayer.time(500, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToFirstSample()));
        ActionDelayer.time(1300, this :: CollectFirstSample);
    }

    private void CollectFirstSample(){
        FrontSlides.extendPercentage(firstSampleExtend, 0.35);
        Intake.collectWide();
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(firstSampleExtend - 0.01) || ColorSensor.collectedYellowSample(), () -> {
            ActionDelayer.time(100, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(200, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(500, this :: ScoreFirstSample)));
        });
    }

    private void ScoreFirstSample(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSamplePreload());
        ActionDelayer.time(800, ActionManager :: releaseSample);
        ActionDelayer.time(800, () -> progress = AutoState.SCORED_FIRST);
    }






    private void SecondSample()
    {
        ActionDelayer.time(0, this :: goToSecondSample);
        ActionDelayer.time(400, ActionManager :: resetScoring);
    }

    private void goToSecondSample()
    {
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToSecondSample());
        ActionDelayer.time(900, () -> FrontSlides.extendPercentage(secondSampleExtend, 0.37));
        ActionDelayer.time(900, Intake :: collectWide);
        ActionDelayer.time(1000, this :: CollectSecondSample);
    }

    private void CollectSecondSample() {
        FrontSlides.extendPercentage(secondSampleExtend, 0.37);
        Intake.collectWide();
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(secondSampleExtend - 0.07) || ColorSensor.collectedYellowSample(), () -> {
            ActionDelayer.time(100, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(200, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(800, this :: ScoreSecondSample)));
        });
    }

    private void ScoreSecondSample(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSamplePreload());
        ActionDelayer.time(900, ActionManager :: releaseSample);
        ActionDelayer.time(900, () -> progress = AutoState.SCORED_SECOND);
    }





    private void ThirdSample() {
        ActionDelayer.time(100, this :: goToThirdSample);
        ActionDelayer.time(400, ActionManager :: resetScoring);
    }

    private void goToThirdSample() {
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToThirdSample());
        ActionDelayer.time(1000, () -> FrontSlides.extendPercentage(thirdSampleExtend, 0.4));
        ActionDelayer.time(1000, Intake :: collectWide);
        ActionDelayer.time(2000, this :: CollectThirdSample);
    }

    private void CollectThirdSample() {
        FrontSlides.extendPercentage(thirdSampleExtend, 0.28);
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(thirdSampleExtend - 0.02) || ColorSensor.collectedYellowSample(), () -> {
            ActionDelayer.time(300, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.Turn()));
            ActionDelayer.time(700, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(800, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(50, this :: ScoreThirdSample)));
        });
    }

    private void ScoreThirdSample(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSamplePreload());
        ActionDelayer.time(1600, ActionManager :: releaseSample);
        ActionDelayer.time(1600, () -> progress = AutoState.SCORED_THIRD);
    }





    private void park(){
        ActionDelayer.time(100, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.park()));
        ActionDelayer.time(600, ActionManager :: resetScoring);
        ActionDelayer.time(2800, Claw:: clawPositionPark);
    }

}
