package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.SpecimenSidePreload.AutoRunSpecimenSideFive;
import org.firstinspires.ftc.teamcode.Autonomous.SpecimenSidePreload.AutoRunSpecimenSideFour;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.ActionManager;
import org.firstinspires.ftc.teamcode.TeleOp.TelemetryManager;
import org.firstinspires.ftc.teamcode.Utils.Initializations;

@Autonomous(name = "SpecimenSideFour")
public class SpecimenFour extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Initializations.initAuto(hardwareMap, telemetry);
        while (opModeInInit() && !isStopRequested()) {
//            FrontSlides.update();
//            if (FrontSlides.isStuck()) {
//                FrontSlides.initPosition();
//            }
            TelemetryManager.manageAuto();
        }
        if(!isStopRequested()){
            AutoRunSpecimenSideFour autoCase = new AutoRunSpecimenSideFour(drive);
            autoCase.run();
            while (!isStopRequested() && opModeIsActive()) {
                ActionManager.controlAuto();
                TelemetryManager.manageAuto();
                drive.update();
            }
        }
    }
}
