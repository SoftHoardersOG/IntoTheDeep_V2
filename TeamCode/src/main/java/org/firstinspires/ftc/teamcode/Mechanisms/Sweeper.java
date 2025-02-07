package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;

public class Sweeper {
    private static double sweeperOpen = 0.81;
    private static double sweeperClosed = 0.3;


    public static void init(){
        Hardware.sweeper.setPosition(sweeperClosed);
    }

    public static void sweep(){
        open();
        ActionDelayer.time(500, Sweeper :: close);
    }

    public static void open(){
        Hardware.sweeper.setPosition(sweeperOpen);
    }

    public static void close(){
        Hardware.sweeper.setPosition(sweeperClosed);
    }
}
