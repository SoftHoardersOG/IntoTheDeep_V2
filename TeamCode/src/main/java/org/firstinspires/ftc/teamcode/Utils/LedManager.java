package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.TeleOp.Movement;

public class LedManager {
    private static Gamepad gamepad1;
    private static Gamepad gamepad2;

    public static void init(Gamepad _gamepad1, Gamepad _gamepad2){
        gamepad1 = _gamepad1;
        gamepad2 = _gamepad2;
    }

    public static void update(){
        if (Movement.peAnglia){
            gamepad1.setLedColor(0.03, 0.88, 0.58, 100000000);
        }
        else{
            gamepad1.setLedColor(0.64, 0.03, 0.88, 100000000);
        }
        gamepad2.setLedColor(0.94, 0.99, 0.26, 100000000);
    }
}
