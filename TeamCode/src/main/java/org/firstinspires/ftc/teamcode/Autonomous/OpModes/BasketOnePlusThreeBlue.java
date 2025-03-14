package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.BasketSidePreload.AutoRunBasketOnePlusThree;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.TeleOp.TelemetryManager;
import org.firstinspires.ftc.teamcode.Hardware.ColorSensor;
import org.firstinspires.ftc.teamcode.Utils.ConditionChecker;
import org.firstinspires.ftc.teamcode.Utils.Initializations;

//@Autonomous(name = "BasketOnePlusThreeBlue", group = "blue", preselectTeleOp = "MainTeleOp")
public class BasketOnePlusThreeBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Initializations.initAuto(hardwareMap, telemetry, ColorSensor.AllianceColors.BLUE);
        AutoRunBasketOnePlusThree autoCase = new AutoRunBasketOnePlusThree(Hardware.drive);
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
