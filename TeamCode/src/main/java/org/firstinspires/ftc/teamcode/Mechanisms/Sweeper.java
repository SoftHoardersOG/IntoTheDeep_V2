package org.firstinspires.ftc.teamcode.Mechanisms;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;

@Config
public class Sweeper {
    public static double configPosition;

    private static double sweeperOpenMax = 0.52;
    private static double sweeperOpen = 0.41;
    private static double sweeperClosed = 0.215;
    private static double sweeperClosedFull = 0.215;


    public static void init(){
        Hardware.sweeper.setPosition(sweeperClosed);
        configPosition = sweeperClosed;
    }

    public static void updateConfig(){
        Hardware.sweeper.setPosition(configPosition);
    }

    public static void sweepFast(){
        open();
        ActionDelayer.time(100, Sweeper :: close);
    }

    public static void sweep(){
        open();
        ActionDelayer.time(300, Sweeper :: close);
    }

    public static void sweepLarge(){
        openMax();
        ActionDelayer.time(350, Sweeper:: close);
    }

    public static void open(){
        Hardware.sweeper.setPosition(sweeperOpen);
    }

    public static void openMax(){
        Hardware.sweeper.setPosition(sweeperOpenMax);
    }

    public static void close(){
        Hardware.sweeper.setPosition(sweeperClosed);
    }

    public static void closeFull(){
        Hardware.sweeper.setPosition(sweeperClosedFull);
    }

}
