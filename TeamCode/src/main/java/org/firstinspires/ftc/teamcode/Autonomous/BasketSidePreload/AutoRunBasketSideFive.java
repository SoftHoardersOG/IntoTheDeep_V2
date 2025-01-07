package org.firstinspires.ftc.teamcode.Autonomous.BasketSidePreload;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;

public class AutoRunBasketSideFive implements Runnable {
    private SampleMecanumDrive drive;

    public AutoRunBasketSideFive(SampleMecanumDrive Drive){
        drive = Drive;
    }

    private enum AutoState {
        START,
        SCORED_PRELOAD,
        SCORED_FIRST,
        SCORED_SECOND,
        SCORED_THIRD,
        SCORED_FOURTH

    }
    private static double firstSampleExtend = 0.69;
    private static double secondSampleExtend = 0.74;
    private static double thirdSampleExtend = 0.29;

    private static AutoState progress;

    @Override
    public void run() {
        progress = AutoState.START;
        drive.setPoseEstimate(new Pose2d(-39.88, -63.7, Math.toRadians(90.00)));
        BasketSidePreloadTrajectories.setDrive(drive);
        runAuto();
    }

    private void runAuto(){
        placePreload();
        ActionDelayer.condition(() -> progress == AutoState.SCORED_PRELOAD, this :: FirstSample);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_FIRST, this :: SecondSample);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_SECOND, this :: ThirdSample);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_THIRD, this :: park);
    }



    private void placePreload(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.distanceFromWall());
        ActionDelayer.time(300, ActionManager :: highBasketPos);
        ActionDelayer.time(900, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSamplePreload()));
        ActionDelayer.time(2000, ActionManager :: releaseSample);
        ActionDelayer.time(2050, () -> progress = AutoState.SCORED_PRELOAD);
    }



    private void FirstSample(){
        ActionDelayer.time(0, this :: goToFirstSample);
        ActionDelayer.time(800, ActionManager :: resetScoring);
    }

    private void goToFirstSample(){
        ActionDelayer.time(500, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToFirstSampleSamplePreload()));
//        ActionDelayer.time(2400, () -> drive.setPoseEstimate(BasketSidePreloadTrajectories.FirstSamplePoseAdjusted()));
        ActionDelayer.time(1800, this :: CollectFirstSample);
    }

    private void CollectFirstSample(){
        FrontSlides.extendPercentage(firstSampleExtend, 0.35);
        Intake.collectWide();
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(firstSampleExtend - 0.01), () -> {
            ActionDelayer.time(100, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(200, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(1000, this :: ScoreFirstSample)));
        });
    }

    private void ScoreFirstSample(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSamplePreload());
        ActionDelayer.time(700, ActionManager :: releaseSample);
        ActionDelayer.time(800, () -> progress = AutoState.SCORED_FIRST);
    }






    private void SecondSample()
    {
        ActionDelayer.time(0, this :: goToSecondSample);
        ActionDelayer.time(400, ActionManager :: resetScoring);
    }

    private void goToSecondSample()
    {
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToSecondSampleSamplePreload());
        ActionDelayer.time(1000, this :: CollectSecondSample);
    }

    private void CollectSecondSample() {
        FrontSlides.extendPercentage(secondSampleExtend, 0.37);
        Intake.collectWide();
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(secondSampleExtend - 0.07), () -> {
            ActionDelayer.time(100, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(200, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(1200, this :: ScoreSecondSample)));
        });
    }

    private void ScoreSecondSample(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSamplePreload());
        ActionDelayer.time(1000, ActionManager :: releaseSample);
        ActionDelayer.time(1200, () -> progress = AutoState.SCORED_SECOND);
    }





    private void ThirdSample() {
        ActionDelayer.time(100, this :: goToThirdSample);
        ActionDelayer.time(400, ActionManager :: resetScoring);
    }

    private void goToThirdSample() {
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToThirdSampleSamplePreload());
        ActionDelayer.time(1700, Intake :: collectWide);
        ActionDelayer.time(2000, this :: CollectThirdSample);
    }

    private void CollectThirdSample() {
        FrontSlides.extendPercentage(thirdSampleExtend, 0.4);
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(thirdSampleExtend - 0.02), () -> {
            ActionDelayer.time(300, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.Turn()));
            ActionDelayer.time(700, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(800, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(500, this :: ScoreThirdSample)));
        });
    }

    private void ScoreThirdSample(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSamplePreload());
        ActionDelayer.time(1500, ActionManager :: releaseSample);
        ActionDelayer.time(1700, () -> progress = AutoState.SCORED_THIRD);
    }





    private void park(){
        ActionDelayer.time(100, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.park()));
        ActionDelayer.time(600, ActionManager :: resetScoring);
        ActionDelayer.time(2800, Claw :: armsPark);
    }

}
