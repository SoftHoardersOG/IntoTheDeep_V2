package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.Hardware.Hardware.telemetry;



import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
public class TelemetryManager {

   // private static FtcDashboard dashboard;
  //  private static TelemetryPacket packet;

    public static void init(){

      // dashboard = FtcDashboard.getInstance();
      //  dashboard.setTelemetryTransmissionInterval(25);
    }


    public static void manage(){

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
       // packet.put(caption, value);
    }

    private static void addTelemetry(String caption, boolean value){
        telemetry.addData(caption, value);
      //  packet.put(caption, value);
    }
}

