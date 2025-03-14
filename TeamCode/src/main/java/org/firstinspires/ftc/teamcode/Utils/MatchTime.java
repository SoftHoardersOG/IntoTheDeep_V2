package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class MatchTime {
    private static ElapsedTime timer = new ElapsedTime();

    public static void init(){
        timer.reset();
    }

    public static void start(){
        timer.startTime();
    }

    public static boolean endgame(){
        return timer.time(TimeUnit.SECONDS) > 90;
    }

    public static boolean ascentMark(){
        return timer.time(TimeUnit.SECONDS) > 100;
    }
}
