package org.firstinspires.ftc.teamcode.Autonomous.BasketSidePreload;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Hardware.ColorSensor;
import org.firstinspires.ftc.teamcode.Utils.GameMap;


public class AutoRunBasketFour implements Runnable {
    private SampleMecanumDrive drive;

    public AutoRunBasketFour(SampleMecanumDrive Drive) {
        drive = Drive;
    }

    private enum AutoState {
        START,
        SCORED_PRELOAD,
        SCORED_FIRST,
        SCORED_SECOND,
        SCORED_THIRD
    }

    private static double firstSampleExtend = 0.55;
    private static double secondSampleExtend = 0.45;
    private static double thirdSamplePrepareExtend = 0.3;
    private static double thirdSampleExtend = 0.7;

    private static long lastTime;

    private static AutoState progress;

    private static boolean collectedExtra;

    public void init() {
        progress = AutoState.START;
        drive.setPoseEstimate(new Pose2d(-40.99, -64.43, Math.toRadians(0.00)));
        BasketSidePreloadTrajectories.setDrive(drive);
        GameMap.init(drive);
        collectedExtra = false;
    }

    @Override
    public void run() {
        runAuto();
    }

    private void printSegmentTime(String text) {
        Hardware.telemetry.log().add(text + Double.toString(1.0 * (System.currentTimeMillis() - lastTime) / 1000));
        lastTime = System.currentTimeMillis();
    }

    private void runAuto() {
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
            park();
        });
    }


    private void placePreload(){
        ActionManager.highBasketPos();
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSamplePreload());
        ActionDelayer.time(1500, () -> FrontSlides.extendPercentage(firstSampleExtend, 1));
        ActionDelayer.time(1500, Intake :: collectWide);
        ActionDelayer.time(1700, ActionManager :: releaseSample);
        ActionDelayer.time(2200, () -> progress = AutoState.SCORED_PRELOAD);
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
        ActionDelayer.condition(() -> ColorSensor.collectedYellowSample(), () -> {
            ActionDelayer.time(900, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(1000, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(1000, this :: ScoreFirstSample)));
        });
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
        ActionDelayer.time(600, ActionManager :: resetScoring);
    }

    private void goToSecondSample()
    {
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToSecondSample());
        ActionDelayer.time(0, this :: CollectSecondSample);
    }

    private void CollectSecondSample() {
        Intake.collectWide();
        ActionDelayer.condition(() -> ColorSensor.collectedYellowSample(), () -> {
            ActionDelayer.time(700, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(800, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(1800, this :: ScoreSecondSample)));
        });
    }

    private void ScoreSecondSample(){
        FrontSlides.release();
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketSecondSample());
        ActionDelayer.time(400, () -> FrontSlides.extendPercentage(thirdSamplePrepareExtend, 1));
        ActionDelayer.time(1700, ActionManager :: releaseSample);
        ActionDelayer.time(1800, () -> progress = AutoState.SCORED_SECOND);
    }





    private void ThirdSample() {
        ActionDelayer.time(0, this :: goToThirdSample);
        ActionDelayer.time(100, Claw :: closeClaw);
        ActionDelayer.time(600, ActionManager :: resetScoring);
    }

    private void goToThirdSample() {
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToThirdSample());
        ActionDelayer.time(700, Intake :: collectWide);
        ActionDelayer.time(1400, () -> FrontSlides.extendPercentage(thirdSamplePrepareExtend, 1));
        ActionDelayer.time(1900, this :: CollectThirdSample);
    }

    private void CollectThirdSample() {
        FrontSlides.extendPercentage(thirdSampleExtend, 0.3);
        ActionDelayer.condition(() -> ColorSensor.collectedYellowSample(), () -> {
            ActionDelayer.time(0, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.Turn()));
            ActionDelayer.time(100, () -> ActionManager.transferAuto("high_basket"));
            ActionDelayer.time(200, () -> ActionDelayer.condition(() -> !ActionManager.transferring, () -> ActionDelayer.time(1500, this :: ScoreThirdSample)));
        });
    }

    private void ScoreThirdSample(){
        FrontSlides.release();
        drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.goToBasketThirdSample());
        ActionDelayer.time(1300, ActionManager :: releaseSample);
        ActionDelayer.time(1400, () -> progress = AutoState.SCORED_THIRD);
    }

    private void park(){
        ActionDelayer.time(0, () -> drive.followTrajectorySequenceAsync(BasketSidePreloadTrajectories.park()));
        ActionDelayer.time(600, ActionManager :: resetScoring);
        ActionDelayer.time(3000, Claw :: clawPositionPark);
    }

}