package org.firstinspires.ftc.teamcode.Utils;

public class Sleep {
    public static void ms(long milis){
        long currenttime = System.currentTimeMillis();
        while (System.currentTimeMillis()-currenttime<milis);
    }
}
