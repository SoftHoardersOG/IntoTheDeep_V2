package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.Hardware.Hardware.backLeft;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.backRight;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.backSlides;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.climbLeftSlides;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.climbRightSlides;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.colorSensor;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.frontLeft;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.frontRight;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.frontSlides;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.openerPotentiometer;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.potentiometer;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.sensor;
import static org.firstinspires.ftc.teamcode.Hardware.Hardware.telemetry;


import android.graphics.Color;

import com.acmerobotics.dashboard.FtcDashboard;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;
import org.firstinspires.ftc.teamcode.Autonomous.BasketSidePreload.AutoRunBasketSidePreload;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;
import org.firstinspires.ftc.teamcode.Mechanisms.Climb;
import org.firstinspires.ftc.teamcode.Mechanisms.FrontSlides;
import org.firstinspires.ftc.teamcode.Utils.Potentiometers;
//import org.firstinspires.ftc.teamcode.Mechanisms.BackSlides;

public class TelemetryManager {

    private static FtcDashboard dashboard;
  //private static TelemetryPacket packet;

    public static void init(){

      dashboard = FtcDashboard.getInstance();
      dashboard.setTelemetryTransmissionInterval(25);
    }

    public static void manage(){

//        addTelemetry("stanga fata", frontLeft.getPortNumber());
//        addTelemetry("dreapta fata", frontRight.getPortNumber());
//        addTelemetry("dreapta spate", backRight.getPortNumber());
//        addTelemetry("stanga spate", backLeft.getPortNumber());
//
//        addTelemetry("stanga fata", frontLeft.getCurrentPosition());
//        addTelemetry("dreapta fata", frontRight.getCurrentPosition());
//        addTelemetry("dreapta spate", backRight.getCurrentPosition());
//        addTelemetry("stanga spate", backLeft.getCurrentPosition()

//          addTelemetry("back slides current position", backSlides.getCurrentPosition());
//          addTelemetry("back slides target", backSlides.getTargetPosition());

            addTelemetry("Pe Anglia?", Movement.peAnglia);

          addTelemetry("front slides position", frontSlides.getCurrentPosition());
          addTelemetry("front slides target", frontSlides.getTargetPosition());

//          addTelemetry("front slides transfer", FrontSlides.slidesTransfer);


//          addTelemetry("opener voltage", openerPotentiometer.getVoltage());
//          addTelemetry("up down voltage", potentiometer.getVoltage());

//          addTelemetry("climb left", Hardware.climbLeftSlides.getCurrentPosition());
//          addTelemetry("climb right", Hardware.climbRightSlides.getCurrentPosition());
//          addTelemetry("cilmb progress", ActionManager.climbProgress);


//          addTelemetry("stanga fata", frontLeft.getCurrentPosition());
//          addTelemetry("stanga spate", backLeft.getCurrentPosition());
//          addTelemetry("dreapta fata", frontRight.getCurrentPosition());
//          addTelemetry("dreapta spate", backRight.getCurrentPosition());

//          addTelemetry("glisiere fata voltage", frontSlides.getCurrent(CurrentUnit.AMPS));
//          addTelemetry("glisiere spate voltage", backSlides.getCurrent(CurrentUnit.AMPS));
//          addTelemetry("catarare stanga voltage", climbLeftSlides.getCurrent(CurrentUnit.AMPS));
//          addTelemetry("catarare dreapta voltage", climbRightSlides.getCurrent(CurrentUnit.AMPS));
////
//        addTelemetry("stanga fata", frontLeft.getCurrent(CurrentUnit.AMPS));
//        addTelemetry("stanga spate", backLeft.getCurrent(CurrentUnit.AMPS));
//        addTelemetry("dreapta fata", frontRight.getCurrent(CurrentUnit.AMPS));
//        addTelemetry("dreapta spate", backRight.getCurrent(CurrentUnit.AMPS));

        addTelemetry("climb left slides pos", climbLeftSlides.getCurrentPosition());
        addTelemetry("climb left slides target", climbLeftSlides.getTargetPosition());

        addTelemetry("climb right slides pos", climbRightSlides.getCurrentPosition());
        addTelemetry("climb right slides target", climbRightSlides.getTargetPosition());


        addTelemetry("climb left potentiometer", Potentiometers.climbLeftPosition());

        addTelemetry("climb right potentiometer", Potentiometers.climbRightPosition());

        addTelemetry("front slides power", frontSlides.getPower());

        addTelemetry("climb progress", ActionManager.climbProgress);

        addTelemetry("motors pulled fully", Climb.motorsPulledFully());

        addTelemetry("climb servos position", Hardware.climbBackRight.getPosition());

        float[] hsv = new float[3];
        Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsv);

        float hue = hsv[0];
        float sat = hsv[1];
        float val = hsv[2];

        addTelemetry("H", hue);
        addTelemetry("S", sat);
        addTelemetry("V", val);


        telemetry.update();
    }

    public static void manageAuto(){
        addTelemetry("front slides power", frontSlides.getPower());
        addTelemetry("transfer finish score case", ActionManager.transferFinishScoreCase);
        addTelemetry("front slides target", frontSlides.getTargetPosition());
        telemetry.update();
    }

    private static void addTelemetry(String caption, int value){
        telemetry.addData(caption, value);
       // packet.put(caption, value);
    }

    private static void addTelemetry(String caption, double value){
        telemetry.addData(caption, value);
      //  packet.put(caption, value);
    }

    private static void addTelemetry(String caption, String value){
        telemetry.addData(caption, value);
        //packet.put(caption, value);
    }

    private static void addTelemetry(String caption, boolean value){
        telemetry.addData(caption, value);
      //  packet.put(caption, value);
    }
}

