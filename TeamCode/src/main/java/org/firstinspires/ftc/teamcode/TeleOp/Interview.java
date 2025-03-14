package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.ColorSensor;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Utils.ConditionChecker;
import org.firstinspires.ftc.teamcode.Utils.Initializations;
import org.firstinspires.ftc.teamcode.Utils.LedManager;
import org.firstinspires.ftc.teamcode.Utils.MatchTime;
import org.firstinspires.ftc.teamcode.Utils.Rumble;

@TeleOp
public class Interview extends LinearOpMode {
    @Override
    public void runOpMode() {
        Initializations.initAuto(hardwareMap, telemetry, ColorSensor.AllianceColors.RED);
        Initializations.startAuto();
        Initializations.initTeleOp(gamepad1, gamepad2);
        Claw.horizontalAdjusting = false;
        while (opModeInInit() && !isStopRequested()) {
            ActionManager.updateInit();
        }
        while (opModeIsActive() && !isStopRequested()) {
            ActionManager.controlInterview(gamepad1, gamepad2);
        }
        ConditionChecker.opModeStopped = true;
    }
}

