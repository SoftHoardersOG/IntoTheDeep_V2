package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Utils.ConditionChecker;
import org.firstinspires.ftc.teamcode.Utils.Initializations;
import org.firstinspires.ftc.teamcode.Utils.LedManager;

@TeleOp(group = "telemetry")
public class MainTeleOpTelemetry extends LinearOpMode {
   @Override
    public void runOpMode() {
        Initializations.initTeleOp(gamepad1, gamepad2);
        //Rumble.start();
        while (opModeInInit() && !isStopRequested()){
            LedManager.update();
            if (gamepad1.share){
                Movement.peAnglia = false;
            }
            else if (gamepad1.options) {
                Movement.peAnglia = true;
            }
            TelemetryManager.manage(gamepad2);
            ActionManager.updateInit();
        }
       BackSlides.transferPosition();
        while (opModeIsActive() && !isStopRequested()) {
            LedManager.update();
            Movement.updateLimitSpeedMultiplier();
            Movement.run(gamepad1);
            ActionManager.control(gamepad1, gamepad2);
            TelemetryManager.manage(gamepad2);
            Hardware.climbRightSlides.setPower(0);
            Hardware.climbLeftSlides.setPower(0);
        }
        ConditionChecker.opModeStopped = true;
    }
}
