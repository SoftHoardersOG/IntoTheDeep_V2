package org.firstinspires.ftc.teamcode.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class ActionDelayer {
    public static void time(int msDelay, Lambda lambda){
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
