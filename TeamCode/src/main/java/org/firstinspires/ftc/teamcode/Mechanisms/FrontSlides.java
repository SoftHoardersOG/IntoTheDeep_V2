package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class FrontSlides {

    private static int manualExtendRate = 0;

    private static int slidesInit = 0;
    private static int slidesTransfer = 0;
    private static int slidesExtended = 0;

    public static void initPosition(){Hardware.frontSlides.setTargetPosition(slidesInit);}

    public static void transferPosition(){Hardware.frontSlides.setTargetPosition(slidesTransfer);}

    public static void extend(float dt){
        int position = Hardware.frontSlides.getTargetPosition() + (int)(manualExtendRate * dt);
        position = Math.min(position, slidesExtended);
        Hardware.frontSlides.setTargetPosition(position);
    }

    public static void retract(float dt){
        int position = Hardware.frontSlides.getTargetPosition() - Math.round(manualExtendRate * dt);
        position = Math.max(position, slidesInit);
        Hardware.frontSlides.setTargetPosition(position);
    }
}
