package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Utils.Initializations;
import org.firstinspires.ftc.teamcode.Utils.Rumble;

@TeleOp
public class MainTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Initializations.initTeleOp(drive, hardwareMap, telemetry);
        waitForStart();
        Rumble.start();
        while (opModeIsActive() && !isStopRequested()) {
            ActionManager.control(gamepad1, gamepad2);
            TelemetryManager.manage();
        }
    }
}
