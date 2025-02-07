package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Utils.ActionDelayer;

public class BackSlides {

    public static boolean scoringPosition;

    private static int slidesInit = -3;
    private static int slidesInitLower = 35;
    public static int slidesTransfer = 0;
    private static int basketLevels[] = {-425, -1150};
    private static int chamberLevels[] = {-154, -265};
    private static int climbTilt = -700;

    public static void initPosition(){
//        Hardware.backSlides.setPower(1);
//        lowerSlidesGradual();
        Hardware.backSlides.setTargetPosition(slidesInit);
        scoringPosition = false;
    }

    public static void climbTiltPercentage(double p){
        if (p < 0) p = 0;
        else if (p > 1) p = 1;
        Hardware.backSlides.setPower(1);
        Hardware.backSlides.setTargetPosition((int)(climbTilt * p));
        scoringPosition = false;
    }

    public static void climbTilt(){
        Hardware.backSlides.setPower(1);
        Hardware.backSlides.setTargetPosition(climbTilt);
        scoringPosition = false;
    }

    public static void transferPosition(){
        Hardware.backSlides.setPower(1);
        Hardware.backSlides.setTargetPosition(slidesTransfer);
        scoringPosition = false;
    }

    public static void lowBasket(){
        Hardware.backSlides.setPower(1);
        Hardware.backSlides.setTargetPosition(basketLevels[0]);
        scoringPosition = true;
    }
    public static void highBasket(){
        Hardware.backSlides.setPower(1);
        Hardware.backSlides.setTargetPosition(basketLevels[1]);
        scoringPosition = true;
    }
    public static void lowChamber(){
        Hardware.backSlides.setPower(1);
        Hardware.backSlides.setTargetPosition(chamberLevels[0]);
        scoringPosition = true;
    }
    public static void highChamber(){
        Hardware.backSlides.setPower(1);
        Hardware.backSlides.setTargetPosition(chamberLevels[1]);
        scoringPosition = true;
    }

    public static void lowChamberFinish(){
        Hardware.backSlides.setTargetPosition(slidesInit);
        scoringPosition = true;
    }

    public static boolean reachedHighBasket(){
        int current = -Hardware.backSlides.getCurrentPosition();
        int target = -basketLevels[1];
        return current - 150 <= target && target <= current + 150;
    }

    private static void lowerSlidesGradual() {
        Hardware.backSlides.setTargetPosition(slidesInit);
        ActionDelayer.time(350, () -> {
            ActionDelayer.condition(() -> Math.abs(Hardware.backSlides.getCurrentPosition()) < 50, () -> {
                Hardware.backSlides.setTargetPosition(slidesInitLower);
                ActionDelayer.condition(() -> Math.abs(Hardware.backSlides.getCurrentPosition()) < 3, () -> {
                    Hardware.backSlides.setTargetPosition(slidesInit);
                    Hardware.backSlides.setPower(0);
                });
            });
        });
    }
}
