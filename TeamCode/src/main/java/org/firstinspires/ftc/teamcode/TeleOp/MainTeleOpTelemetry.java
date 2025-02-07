package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ConditionChecker;
import org.firstinspires.ftc.teamcode.Utils.Initializations;
import org.firstinspires.ftc.teamcode.Utils.LedManager;

@TeleOp(group = "telemetry")
public class MainTeleOpTelemetry extends LinearOpMode {
   @Override
    public void runOpMode() {
//        drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate()).lineToLinearHeading(drive.getPoseEstimate()).build());
        Initializations.initTeleOp(gamepad1, gamepad2);
        //Rumble.start();
        while (opModeInInit() && !isStopRequested()){
            LedManager.update();
//            FrontSlides.update();
//            if (FrontSlides.isStuck()){
//                FrontSlides.initPosition();
//            }
            if (gamepad1.share){
                Movement.peAnglia = false;
            }
            else if (gamepad1.options) {
                Movement.peAnglia = true;
            }
            TelemetryManager.manage();
            ActionManager.updateInit();
        }
//        FrontSlides.release();
//        Intake.neutral();
        while (opModeIsActive() && !isStopRequested()) {
            LedManager.update();
            Movement.run(gamepad1);
            ActionManager.control(gamepad1, gamepad2);
            TelemetryManager.manage();
            Hardware.climbRightSlides.setPower(0);
            Hardware.climbLeftSlides.setPower(0);
        }
        ConditionChecker.opModeStopped = true;
    }
}
