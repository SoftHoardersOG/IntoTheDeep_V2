package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.Climb;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms.Sweeper;
import org.firstinspires.ftc.teamcode.Utils.ConditionChecker;
import org.firstinspires.ftc.teamcode.Utils.Initializations;
import org.firstinspires.ftc.teamcode.Utils.LedManager;
import org.firstinspires.ftc.teamcode.Utils.MatchTime;
import org.firstinspires.ftc.teamcode.Utils.Rumble;

@TeleOp
public class ConfigPositions extends LinearOpMode {
    @Override
    public void runOpMode() {
        Initializations.initTeleOp(gamepad1, gamepad2);
        while (opModeInInit() && !isStopRequested()) {
            LedManager.update();
            if (gamepad1.share) {
                Movement.peAnglia = false;
            } else if (gamepad1.options) {
                Movement.peAnglia = true;
            }
            TelemetryManager.manageOptimizedTeleOp();
        }
        BackSlides.transferPosition();
        while (opModeIsActive() && !isStopRequested()) {
            Movement.run(gamepad1);
            TelemetryManager.manageOptimizedTeleOp();
            BackSlides.updateConfig();
            Claw.updateConfig();
            Climb.updateConfig();
            FrontSlides.updateConfig();
            Intake.updateConfig();
            Sweeper.updateConfig();
            Hardware.drive.update();
        }
    }
}
