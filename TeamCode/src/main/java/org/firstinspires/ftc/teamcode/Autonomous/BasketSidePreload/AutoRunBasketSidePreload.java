package org.firstinspires.ftc.teamcode.Autonomous.BasketSidePreload;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Autonomous.SpecimenSidePreload.AutoRunSpecimenSideFive;
import org.firstinspires.ftc.teamcode.Autonomous.SpecimenSidePreload.SpecimenSidePreloadTrajectories;
import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;

public class AutoRunBasketSidePreload implements Runnable{
    private SampleMecanumDrive drive;

    public AutoRunBasketSidePreload(SampleMecanumDrive Drive){
        drive = Drive;
    }

    private enum AutoState {
        START,
        SCORED_PRELOAD,
        SCORED_FIRST,
        SCORED_SECOND,
        SCORED_THIRD
    }

    private static double firstSampleExtend = 0.68;
    private static double secondSampleExtend = 0.78;
    private static double thirdSampleExtend = 0.4;

    private static AutoState progress;

    @Override
    public void run() {
        progress = AutoState.START;
        drive.setPoseEstimate(new Pose2d(-16.10, -63.7, Math.toRadians(-90.00)));
        BasketSidePreloadTrajectories.setDrive(drive);
        runAuto();
    }

    private void runAuto(){
        placeSpecimen();
        ActionDelayer.condition(() -> progress == AutoState.SCORED_PRELOAD, this :: FirstSample);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_FIRST, this :: SecondSample);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_SECOND, this :: ThirdSample);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_THIRD, this :: park);
    }






    private void placeSpecimen(){
        ActionManager.highChamberPos();
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToChamber());
        ActionDelayer.time(1400, ActionManager :: releaseSample);
        ActionDelayer.time(1400, () -> progress = AutoState.SCORED_PRELOAD);
    }






    private void FirstSample(){
        ActionDelayer.time(100, this :: goToFirstSample);
        ActionDelayer.time(500, ActionManager :: resetScoring);
    }

    private void goToFirstSample(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.distanceFromChamber());
        ActionDelayer.time(500, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToFirstSample()));
        ActionDelayer.time(2800, this::CollectFirstSample);
    }

    private void CollectFirstSample(){
        FrontSlides.extendPercentage(firstSampleExtend, 0.3);
        Intake.collectWide();
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(firstSampleExtend - 0.22), () -> {
            ActionDelayer.time(400, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(500, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(1000, this :: ScoreFirstSample)));
        });
    }

    private void ScoreFirstSample(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasket());
        ActionDelayer.time(1000, ActionManager :: releaseSample);
        ActionDelayer.time(1200, () -> progress = AutoState.SCORED_FIRST);
    }






    private void SecondSample()
    {
        ActionDelayer.time(100, this :: goToSecondSample);
        ActionDelayer.time(400, ActionManager :: resetScoring);
    }

    private void goToSecondSample()
    {
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToSecondSample());
        ActionDelayer.time(1000, this :: CollectSecondSample);
    }

    private void CollectSecondSample() {
        FrontSlides.extendPercentage(secondSampleExtend, 0.4);
        Intake.collectWide();
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(secondSampleExtend - 0.22), () -> {
            ActionDelayer.time(400, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(500, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(1200, this :: ScoreSecondSample)));
        });
    }

    private void ScoreSecondSample(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasket());
        ActionDelayer.time(1000, ActionManager :: releaseSample);
        ActionDelayer.time(1200, () -> progress = AutoState.SCORED_SECOND);
    }





    private void ThirdSample() {
        ActionDelayer.time(100, this :: goToThirdSample);
        ActionDelayer.time(400, ActionManager :: resetScoring);
    }

    private void goToThirdSample() {
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToThirdSample());
        ActionDelayer.time(2000, this :: CollectThirdSample);
    }

    private void CollectThirdSample() {
        FrontSlides.extendPercentage(thirdSampleExtend, 0.3);
        Intake.collectWide();
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(thirdSampleExtend - 0.03), () -> {
            ActionDelayer.time(300, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.Turn()));
            ActionDelayer.time(700, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(800, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(1300, this :: ScoreThirdSample)));
        });
    }

    private void ScoreThirdSample(){
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasket());
        ActionDelayer.time(1500, ActionManager :: releaseSample);
        ActionDelayer.time(1700, () -> progress = AutoState.SCORED_THIRD);
    }





    private void park(){
        ActionDelayer.time(100, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.park()));
        ActionDelayer.time(600, ActionManager :: resetScoring);
        ActionDelayer.time(2500, Claw:: armsPark);
    }


}
