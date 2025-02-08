package org.firstinspires.ftc.teamcode.Autonomous.SpecimenSidePreload;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;
import org.firstinspires.ftc.teamcode.Utils.ColorSensor;
import org.firstinspires.ftc.teamcode.Utils.GameMap;


public class AutoRunSpecimenSideFive implements Runnable {
    private SampleMecanumDrive drive;

    private enum AutoState {
        START,
        SCORED_PRELOAD,
        DELIVERED_FIRST,
        DELIVERED_SECOND,
        DELIVERED_THIRD,
        SCORED_FIRST,
        SCORED_SECOND,
        SCORED_THIRD,
        SCORED_FOURTH,
        SCORED_LAST
    }

    private static double firstSampleExtend = 0.5;
    private static double secondSampleExtend = 0.6;
    private static double thirdSampleExtend = 0.58;
    private static double firstSpecimenExtend = 0.25;
    private static double secondSpecimenExtend = 0.55;
    private static double deliverSampleExtend = 0.5;
    private static double deliverThirdSampleExtend = 0.3;
    private static double parkExtend = 1;

    private static boolean placingSample = false;

    private static AutoState progress;

    public AutoRunSpecimenSideFive(SampleMecanumDrive Drive){
        drive = Drive;
    }

    public void init(){
        progress = AutoState.START;
        drive.setPoseEstimate(new Pose2d(17.00, -64.14, Math.toRadians(-90.00)));
        SpecimenSidePreloadTrajectories.setDrive(drive);
        GameMap.init(drive);
    }

    @Override
    public void run() {
        runAuto();
    }

    private void runAuto(){
        placeSpecimen();
        ActionDelayer.condition(() -> progress == AutoState.SCORED_PRELOAD, this :: FirstSample);
        ActionDelayer.condition(() -> progress == AutoState.DELIVERED_FIRST, this :: SecondSample);
        ActionDelayer.condition(() -> progress == AutoState.DELIVERED_SECOND, this :: ThirdSample);
        ActionDelayer.condition(() -> progress == AutoState.DELIVERED_THIRD, this :: FirstSpecimen);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_FIRST, this :: SecondSpecimen);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_SECOND, this :: ThirdSpecimen);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_THIRD, this :: FourthSpecimen);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_FOURTH, this :: LastSample);
        ActionDelayer.condition(() -> progress == AutoState.SCORED_LAST, this :: park);
    }

    private void placeSpecimen() {
        ActionManager.highChamberPos();
        drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.placePreload());
        ActionDelayer.time(1000, ActionManager :: releaseSample);
        ActionDelayer.time(1000, () -> progress = AutoState.SCORED_PRELOAD);
    }

    private void FirstSample(){
        ActionDelayer.time(50, this :: goToFirstSample);
        ActionDelayer.time(800, ActionManager :: resetScoring);
    }

    private void goToFirstSample(){
//        drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.distanceFromChamber());
        ActionDelayer.time(0, () -> drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.goToFirstSample()));
        ActionDelayer.time(0, Intake :: collect);
        ActionDelayer.time(1100, this :: extendFirstSample);
        ActionDelayer.time(1500, this :: CollectFirstSample);
    }

    private void CollectFirstSample(){
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(firstSampleExtend - 0.01) || ColorSensor.collectedAllianceSpecificSample(),
                () -> ActionDelayer.time(100, () -> DeliverSample(AutoState.DELIVERED_FIRST)));
    }
    private void SecondSample() {
        ActionDelayer.time(100, this :: goToSecondSample);
    }

    private void goToSecondSample(){
        drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.goToSecondSample());
        ActionDelayer.time(300, Intake :: collectWide);
        ActionDelayer.time(1100, this :: extendSecondSample);
        ActionDelayer.time(1600, this :: CollectSecondSample);
    }
    private void CollectSecondSample(){
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(secondSampleExtend - 0.01) || ColorSensor.collectedAllianceSpecificSample(),
                () -> ActionDelayer.time(100, () -> DeliverSample(AutoState.DELIVERED_SECOND)));
    }

    private void ThirdSample(){
        ActionDelayer.time(100, this :: goToThirdSample);
    }

    private void goToThirdSample(){
        extendThirdSampleCenter();
        drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.goToThirdSample());
        ActionDelayer.time(300, Intake :: collectWide);
        ActionDelayer.time(800, this :: extendThirdSample);
        ActionDelayer.time(1400, this :: CollectThirdSample);
    }

    private void CollectThirdSample(){
        ActionDelayer.condition(() -> FrontSlides.reachedPercentage(thirdSampleExtend) || ColorSensor.collectedAllianceSpecificSample(), () -> {
            ActionDelayer.time(300, () -> DeliverSample(AutoState.DELIVERED_THIRD));
            ActionDelayer.time(200, this :: extendDeliverThirdSample);
        });
    }

    private void DeliverSample(AutoState finishState){
        ///Hardware.opener.setPosition(0);
        drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.TurnToObservationZoneOne());
        ActionDelayer.time(500, Intake :: spitoutGround);
        ActionDelayer.time(600, this :: extendDeliverSample);
        ActionDelayer.time(700, () -> progress = finishState);
        if (finishState == AutoState.DELIVERED_THIRD){
            ActionDelayer.time(900, () -> extendFirstSpecimen());
        }
    }

    private void FirstSpecimen(){ActionDelayer.time(700, this :: goToFirstSpecimen);}

    private void goToFirstSpecimen(){
        drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.goToFirstSpecimen());
        ActionDelayer.time(0, Intake :: collect);
        ActionDelayer.time(200, this :: extendFirstSpecimen);
        ActionDelayer.time(600, this :: TransferAndScoreFirstSpecimen);
    }

    private void TransferAndScoreFirstSpecimen(){
        ActionManager.transferAuto("high_chamber");
        ActionDelayer.time(100, () -> drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.goToScoreFirstSpecimen()));
        ActionDelayer.time(1200, () -> drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.scoreFirstSpecimen()));
        ActionDelayer.time(2000, ActionManager :: releaseSample);
        ActionDelayer.time(2000, () -> progress = AutoState.SCORED_FIRST);
    }

    private void SecondSpecimen(){ActionDelayer.time(0, this :: goToSecondSpecimen);}

    private void goToSecondSpecimen(){
        drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.goToSecondSpecimen());
        ActionDelayer.time(0, Intake :: collect);
        ActionDelayer.time(0, this :: extendSecondSpecimen);
        ActionDelayer.time(300, ActionManager :: resetScoring);
        ActionDelayer.time(1400, () -> drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.collectSecondSpecimen()));
        ActionDelayer.time(1600, this :: TransferAndScoreSecondSpecimen);
    }

    private void TransferAndScoreSecondSpecimen(){
        ActionManager.transferAuto("high_chamber");
        ActionDelayer.time(1100, () -> drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.scoreSecondSpecimen()));
        ActionDelayer.time(2000, ActionManager :: releaseSample);
        ActionDelayer.time(2000, () -> progress = AutoState.SCORED_SECOND);
    }

    private void ThirdSpecimen(){ActionDelayer.time(0, this :: goToThirdSpecimen);}

    private void goToThirdSpecimen(){
        drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.goToThirdSpecimen());
        ActionDelayer.time(200, ActionManager :: resetScoring);
        ActionDelayer.time(0, Intake :: collect);
        ActionDelayer.time(0, this :: extendSecondSpecimen);
        ActionDelayer.time(1400, () -> drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.collectThirdSpecimen()));
        ActionDelayer.time(1600, this :: TransferAndScoreThirdSpecimen);
    }

    private void TransferAndScoreThirdSpecimen(){
        ActionManager.transferAuto("high_chamber");
        ActionDelayer.time(1200, () -> drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.scoreThirdSpecimen()));
        ActionDelayer.time(2000, ActionManager :: releaseSample);
        ActionDelayer.time(2000, () -> progress = AutoState.SCORED_THIRD);
    }

    private void FourthSpecimen(){ActionDelayer.time(0, this :: goToFourthSpecimen);}

    private void goToFourthSpecimen(){
        drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.goToFourthSpecimen());
        ActionDelayer.time(200, ActionManager :: resetScoring);
        ActionDelayer.time(0, Intake :: collect);
        ActionDelayer.time(0, this :: extendSecondSpecimen);
        ActionDelayer.time(1400, () -> drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.collectFourthSpecimen()));
        ActionDelayer.time(1600, this :: TransferAndScoreFourthSpecimen);
    }

    private void TransferAndScoreFourthSpecimen(){
        ActionManager.transferAuto("high_chamber");
        ActionDelayer.time(1200, () -> drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.scoreFourthSpecimen()));
        ActionDelayer.time(2000, Intake :: parkSpecimenSide);
        ActionDelayer.time(2100, ActionManager :: releaseSample);
        ActionDelayer.time(2100, () -> progress = AutoState.SCORED_FOURTH);
    }

    private void LastSample(){ActionDelayer.time(0, this :: goToLastSample);}

    private void goToLastSample(){
        drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.goToFourthSpecimen());
        ActionDelayer.time(200, ActionManager :: resetScoring);
        ActionDelayer.time(0, Intake :: collect);
        ActionDelayer.time(0, this :: extendSecondSpecimen);
        ActionDelayer.time(1400, () -> drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.collectFourthSpecimen()));
        ActionDelayer.time(1600, this :: TransferAndScoreLastSample);
    }

    private void TransferAndScoreLastSample(){
        ActionManager.transferAuto("high_basket");
        ActionDelayer.time(0, () -> drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.scoreLastSample()));
        ActionDelayer.time(2400, ActionManager :: releaseSample);
        ActionDelayer.time(2400, () -> progress = AutoState.SCORED_LAST);
    }
    private void goToPark(){
        drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.goToPark());
    }

    private void park(){
        ActionDelayer.time(0, this :: goToPark);
        ActionDelayer.time(0, this :: extendPark);
        ActionDelayer.time(700, ActionManager :: resetScoring);
        ActionDelayer.time(1100, () -> drive.followTrajectorySequenceAsync(SpecimenSidePreloadTrajectories.goToPark2()));
    }

    private void extendFirstSample(){FrontSlides.extendPercentage(firstSampleExtend, 0.3);}

    private void extendSecondSample(){FrontSlides.extendPercentage(secondSampleExtend, 1);}
    private void extendThirdSample() {FrontSlides.extendPercentage(thirdSampleExtend, 1);}
    private void extendThirdSampleCenter() {FrontSlides.extendPercentage(thirdSampleExtend - 0.09, 1);}
    private void extendFirstSpecimen() {FrontSlides.extendPercentage(firstSpecimenExtend, 0.6);}
    private void extendSecondSpecimen() {FrontSlides.extendPercentage(secondSpecimenExtend, 1);}
    private void extendDeliverThirdSample() {FrontSlides.extendPercentage(deliverThirdSampleExtend, 1);}
    private void extendDeliverSample() {FrontSlides.extendPercentage(deliverSampleExtend, 0.7);}
    private void extendPark() {FrontSlides.extendPercentage(parkExtend, 1);}


}
