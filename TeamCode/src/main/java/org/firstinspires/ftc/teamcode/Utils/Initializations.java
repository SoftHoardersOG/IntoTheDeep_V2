package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;

//import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.Climb;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms.Sweeper;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.TeleOp.Movement;
import org.firstinspires.ftc.teamcode.TeleOp.TelemetryManager;

public class Initializations {
    public static void initTeleOp(Gamepad gamepad1, Gamepad gamepad2){
        ConditionChecker.timeToEnd = 10000;
        Movement.init(Hardware.drive);
        Hardware.configureTeleOp();
        ActionManager.init();
        LedManager.init(gamepad1, gamepad2);
        ActionManager.resetScoring();
        Climb.init();
        Claw.clawInit();
        FrontSlides.init();
        Intake.init();
        Sweeper.init();
        TelemetryManager.init();
        Rumble.init();
        ConditionChecker.opModeStopped = false;
    }
    public static void initAuto(HardwareMap hardwareMap, Telemetry telemetry, ColorSensor.AllianceColors color){
        ConditionChecker.timeToEnd = 30000;
        ActionManager.transferFinishScoreCase = "";
        Hardware.init(hardwareMap, telemetry);
        Hardware.configureAuto();
        TelemetryManager.init();
        ActionManager.init();
        Climb.init();
        Claw.clawInitAuto();
        FrontSlides.initPosition();
        Intake.init();
        Sweeper.init();
        ColorSensor.init(color);
        ConditionChecker.opModeStopped = false;
    }
    public static void startAuto(){
        Hardware.startAuto();
    }
}
