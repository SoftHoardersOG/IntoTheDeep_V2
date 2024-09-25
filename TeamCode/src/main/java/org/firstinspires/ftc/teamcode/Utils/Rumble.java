package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Rumble {
    private static ElapsedTime timer = new ElapsedTime();
    private static OneTap rumble1 = new OneTap();
    private static OneTap rumble2 = new OneTap();
    public static void rumble(Gamepad gamepad1, Gamepad gamepad2){
        if (rumble1.onPress(timer.seconds()>85)){
            gamepad1.rumble(250);
            gamepad2.rumble(250);
        }
        if (rumble2.onPress(timer.seconds()>115)){
            gamepad1.rumble(500);
            gamepad2.rumble(500);
        }
    }

    public static void init(){
        timer.reset();
    }

    public static void start(){
        timer.startTime();
    }

}