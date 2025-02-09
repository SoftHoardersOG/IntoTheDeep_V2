package org.firstinspires.ftc.teamcode.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class ActionDelayer {
    public static double timeScale = 1;

    private static boolean passed = false;

    public static void setTimeScale(double _timeScale){
        timeScale = _timeScale;
    }

    public static void time(int msDelay, Lambda lambda) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                lambda.run();
            }
        }, msDelay);
    }

    public static void time(int msDelay, Lambda lambda, double customTimeScale){
        msDelay = (int)(msDelay * (1 / customTimeScale));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                lambda.run();
            }
        }, msDelay);
    }

    public static void condition(LambdaBool condition, Lambda action){
        new ConditionChecker(condition, action).start();
    }
}
