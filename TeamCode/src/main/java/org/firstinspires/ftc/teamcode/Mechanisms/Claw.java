package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;

public class Claw {

    public enum States{
        INIT,
        TRANSFER,
        BASKET,
        CHAMBER,
        SCORE
    }

    public static States currentState;

    private static double armLeftInit = 0.8;
    private static double armLeftTransfer = 0.85;
    private static double armLeftBasket = 0.48;
    private static double armLeftChamber = 0.48;

    private static double armRightInit = 0.08;
    private static double armRightTransfer = 0.03;
    private static double armRightBasket = 0.42;
    private static double armRightChamber = 0.42;

    private static double rotateClawInit = 0.48;
    private static double rotateClawTransfer = 0.46;
    private static double rotateClawBasket = 0.3;
    private static double rotateClawChamber = 0.4;

    private static double clawClosed = 0;
    private static double clawOpen = 0.14;

    private static void closeClaw(){
        Hardware.claw.setPosition(clawClosed);
    }

    private static void openClaw(){
        Hardware.claw.setPosition(clawOpen);
    }

    private static void process(){
        switch (currentState){
            case INIT:
                Hardware.armLeft.setPosition(armLeftInit);
                Hardware.armRight.setPosition(armRightInit);
                Hardware.rotateClaw.setPosition(rotateClawInit);
                closeClaw();
                BackSlides.initPosition();
            case TRANSFER:
                Hardware.armLeft.setPosition(armLeftTransfer);
                Hardware.armRight.setPosition(armRightTransfer);
                Hardware.rotateClaw.setPosition(rotateClawTransfer);
                BackSlides.transferPosition();
                openClaw();
                ActionDelayer.time(800, Claw :: closeClaw);
            case BASKET:
                Hardware.armLeft.setPosition(armLeftBasket);
                Hardware.armRight.setPosition(armRightBasket);
                Hardware.rotateClaw.setPosition(rotateClawBasket);
                BackSlides.setCurrentMode(BackSlides.ScoringModes.BASKET);
                BackSlides.setLevel(0);
            case CHAMBER:
                Hardware.armLeft.setPosition(armLeftChamber);
                Hardware.armRight.setPosition(armRightChamber);
                Hardware.rotateClaw.setPosition(rotateClawChamber);
                BackSlides.setCurrentMode(BackSlides.ScoringModes.CHAMBER);
                BackSlides.setLevel(0);
            case SCORE:
                openClaw();
        }
    }

    public static void setCurrentState(States newState){
        currentState = newState;
        process();
    }

    public static void raiseLevel(){
        if (currentState != States.BASKET && currentState != States.CHAMBER) return;
        BackSlides.raiseLevel();
    }

    public static void lowerLevel(){
        if (currentState != States.BASKET && currentState != States.CHAMBER) return;
        BackSlides.lowerLevel();
    }
}
