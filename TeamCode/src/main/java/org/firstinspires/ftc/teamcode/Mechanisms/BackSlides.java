package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class BackSlides {

    public enum ScoringModes{
        BASKET,
        CHAMBER
    }

    private static ScoringModes currentMode;

    private static int slidesInit = 0;
    private static int slidesTransfer = 0;
    private static int basketLevels[] = {0, 0};
    private static int chamberLevels[] = {0, 0};

    private static int level = 0;

    public static void initPosition(){
        Hardware.backSlides.setTargetPosition(slidesInit);
    }

    public static void transferPosition(){
        Hardware.backSlides.setTargetPosition(slidesTransfer);
    }

    public static void setCurrentMode(ScoringModes mode){
        currentMode = mode;
    }

    public static void setLevel(int _level){
        level = _level;
        updateLevel();
    }

    public static void raiseLevel(){
        level++;
        level = Math.min(level, 1);
        updateLevel();
    }

    public static void lowerLevel(){
        level--;
        level = Math.max(level, 0);
        updateLevel();
    }

    private static void updateLevel(){
        if (currentMode == ScoringModes.BASKET)
            Hardware.backSlides.setTargetPosition(basketLevels[level]);
        else if (currentMode == ScoringModes.CHAMBER)
                Hardware.backSlides.setTargetPosition(chamberLevels[level]);
    }
}
