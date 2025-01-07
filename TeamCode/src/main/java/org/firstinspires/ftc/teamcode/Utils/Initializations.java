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
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.TeleOp.Movement;
import org.firstinspires.ftc.teamcode.TeleOp.TelemetryManager;

public class Initializations {
    public static void initTeleOp(SampleMecanumDrive drive, HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2){
        ConditionChecker.timeToEnd = 7000;
        Hardware.init(hardwareMap, telemetry);
        Movement.init(drive);
        Hardware.configureTeleOp();
        ActionManager.init();
        LedManager.init(gamepad1, gamepad2);
        ActionManager.resetScoring();
        Climb.init();
        Claw.clawInit();
        FrontSlides.init();
        Intake.init();
        TelemetryManager.init();
        Rumble.init();
    }
    public static void initAuto(HardwareMap hardwareMap, Telemetry telemetry){
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
    }
}
