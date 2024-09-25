package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Utils.OneTap;


public class ActionManager {
    public static long dt;
    public static long previousTime;

    private static Gamepad gamepad1;
    private static Gamepad gamepad2;

    private static OneTap cross1 = new OneTap();
    private static OneTap square1 = new OneTap();
    private static OneTap triangle1 = new OneTap();
    private static OneTap dpad1down = new OneTap();
    private static OneTap dpad1left = new OneTap();

    private static OneTap cross2 = new OneTap();
    private static OneTap circle2 = new OneTap();
    private static OneTap left_bumper2 = new OneTap();
    private static OneTap right_bumper2 = new OneTap();

    public static void init(){
        previousTime = System.currentTimeMillis();
    }

    public static void control(Gamepad _gamepad1, Gamepad _gamepad2){
        gamepad1 = _gamepad1;
        gamepad2 = _gamepad2;

        calculateDelta();

        // Intake Controls

        collect();
        spitout();
        intakeInitPosition();
        extendFrontSlides();
        retractFrontSlides();

        // Claw Controls

        clawInitPosition();
        clawBasketMode();
        clawChamberMode();
        clawLowerLevel();
        clawRaiseLevel();

        // Transfer

        transfer();
    }

    private static void calculateDelta(){
        dt = System.currentTimeMillis() - previousTime;
        previousTime = System.currentTimeMillis();
    }

    // Intake Controls

    private static void collect(){
        if (cross1.onPress(gamepad1.cross)) Intake.setCurrentState(Intake.States.COLLECT);
    }

    private static void spitout(){
        if (square1.onPress(gamepad1.square)) Intake.setCurrentState(Intake.States.SPITOUT);
    }

    private static void intakeInitPosition(){
        if (dpad1down.onPress(gamepad1.dpad_down)) Intake.setCurrentState(Intake.States.INIT);
    }

    private static void extendFrontSlides(){
        if (gamepad1.right_bumper){
            Intake.extendSlides(dt);
        }
    }

    private static void retractFrontSlides(){
        if (gamepad1.left_bumper){
            Intake.retractSlides(dt);
        }
    }

    // Claw Controls

    private static void clawInitPosition(){
        if (dpad1left.onPress(gamepad1.dpad_left)) Claw.setCurrentState(Claw.States.INIT);
    }

    private static void clawBasketMode(){
        if (cross2.onPress(gamepad2.cross)) Claw.setCurrentState(Claw.States.BASKET);
    }

    private static void clawChamberMode(){
        if (circle2.onPress(gamepad2.circle)) Claw.setCurrentState(Claw.States.CHAMBER);
    }

    private static void clawRaiseLevel(){
        if (right_bumper2.onPress(gamepad2.right_bumper)) Claw.raiseLevel();
    }

    private static void clawLowerLevel(){
        if (left_bumper2.onPress(gamepad2.left_bumper)) Claw.lowerLevel();
    }

    // Transfer

    private static void transfer(){
        if (triangle1.onPress(gamepad1.triangle)){
            Intake.setCurrentState(Intake.States.TRANSFER);
            Claw.setCurrentState(Claw.States.TRANSFER);
        }
    }
}
