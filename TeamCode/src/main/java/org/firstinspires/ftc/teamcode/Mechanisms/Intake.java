package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class Intake {

    public enum States {
        INIT,
        NEUTRAL,
        COLLECT,
        SPITOUT,
        TRANSFER
    }

    public static States currentState;

    private static double intakeRightCollect = 1;
    private static double intakeRightSpitout = -1;

    private static double intakeLeftCollect = -1;
    private static double intakeLeftSpitout = 1;

    private static double intakeUpDownInit = 0.87;
    private static double intakeUpDownNeutral = 0.87;
    private static double intakeUpDownCollect = 0.87;
    private static double intakeUpDownTransfer = 0.0;

    public static void retractSlides(float dt){
        if (currentState == States.INIT) return;
        if (currentState == States.TRANSFER) return;
        FrontSlides.retract(dt);
    }

    public static void extendSlides(float dt){
        if (currentState == States.INIT) return;
        if (currentState == States.TRANSFER) return;
        FrontSlides.extend(dt);
    }

    private static void process(){
        switch (currentState){
            case INIT:
                Hardware.intakeUpDown.setPosition(intakeUpDownInit);
                Hardware.intakeLeft.setPower(0);
                Hardware.intakeRight.setPower(0);
                FrontSlides.initPosition();
                break;
            case NEUTRAL:
                Hardware.intakeUpDown.setPosition(intakeUpDownNeutral);
                Hardware.intakeLeft.setPower(0);
                Hardware.intakeRight.setPower(0);
                break;
            case COLLECT:
                Hardware.intakeUpDown.setPosition(intakeUpDownCollect);
                Hardware.intakeLeft.setPower(intakeLeftCollect);
                Hardware.intakeRight.setPower(intakeRightCollect);
                break;
            case SPITOUT:
                Hardware.intakeUpDown.setPosition(intakeUpDownCollect);
                Hardware.intakeLeft.setPower(intakeLeftSpitout);
                Hardware.intakeRight.setPower(intakeRightSpitout);
                break;
            case TRANSFER:
                Hardware.intakeUpDown.setPosition(intakeUpDownTransfer);
                Hardware.intakeLeft.setPower(0);
                Hardware.intakeRight.setPower(0);
                FrontSlides.transferPosition();
                break;
        }
    }

    public static void setCurrentState(States newState){
        newState = CollectToNeutralBehaviour(newState);
        currentState = newState;
        process();
    }

    private static States CollectToNeutralBehaviour(States state){
        if(currentState == States.COLLECT && state == States.COLLECT) return States.NEUTRAL;
        if(currentState == States.SPITOUT && state == States.SPITOUT) return States.NEUTRAL;
        return state;
    }
}
