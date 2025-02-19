package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.BasketSidePreload.AutoRunBasketSideSamplePreload;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.TeleOp.TelemetryManager;
import org.firstinspires.ftc.teamcode.Utils.ColorSensor;
import org.firstinspires.ftc.teamcode.Utils.ConditionChecker;
import org.firstinspires.ftc.teamcode.Utils.Initializations;

@Autonomous(name = "BasketSamplePreloadBlue", group = "blue", preselectTeleOp = "MainTeleOp")
public class BasketSamplePreloadBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Initializations.initAuto(hardwareMap, telemetry, ColorSensor.AllianceColors.BLUE);
        AutoRunBasketSideSamplePreload autoCase = new AutoRunBasketSideSamplePreload(Hardware.drive);
        autoCase.init();
        while (opModeInInit() && !isStopRequested()) {
//            FrontSlides.update();
//            if (FrontSlides.isStuck()) {
//                FrontSlides.initPosition();
//            }
            TelemetryManager.manageAuto();
            ActionManager.updateInit();

        }
        Initializations.startAuto();
        if(!isStopRequested()){
            autoCase.run();
            while (!isStopRequested() && opModeIsActive()) {
                ActionManager.controlAuto();
                TelemetryManager.manageAuto();
                Hardware.drive.update();
            }

        }
        ConditionChecker.opModeStopped = true;
    }
}
