package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.TeleOp.MainTeleOp;

public class Rumble {
    private static ElapsedTime timer = new ElapsedTime();
    private static OneTap rumble1 = new OneTap();
    private static OneTap rumble2 = new OneTap();
    public static void rumble(Gamepad gamepad1, Gamepad gamepad2){
        if (rumble1.onPress(MatchTime.ascentMark())){
            gamepad1.rumble(500);
            gamepad2.rumble(500);
        }
//
    }

    public static void init(){
        timer.reset();
    }

    public static void start(){
        timer.startTime();
    }

}