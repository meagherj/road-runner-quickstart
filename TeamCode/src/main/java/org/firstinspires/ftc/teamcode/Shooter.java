package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter {
    DcMotorEx shooter;
    Servo servo;
    String state = "nothing";
    Telemetry telemetry;
    double position;
    double shooterSpeed;

    double range;
    public Shooter(HardwareMap hw, Telemetry telemetry){
        this.shooter = hw.get(DcMotorEx.class, "shooter");
        this.servo = hw.get(Servo.class, "left_hand");
        this.telemetry = telemetry;
    }
    public void update(){
        switch (state){
            case "stop":
                position = 0.3;
                shooterSpeed = 0.0;
                break;
            case "spinup":
                position = 0.3;
                break;
            case "shoot":
                position = 1.0;
                break;
            default:

                break;
        }
        servo.setPosition(position);
        shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter.setVelocity(shooterSpeed);
        telemetry.addData("Shooter Speed", shooterSpeed);
        telemetry.addData("servoPos", position);
    }
    public void spinClose(){ state = "spinup"; shooterSpeed = .58;}
    public void spinFar(){state="spinup"; shooterSpeed = 0.5;}
    public void drop(){state = "shoot";}
    public void stop(){state="stop";}

    public void setRange(double range){
        // from range get shooter speed as Ticks Per Second
        double gears = 3; // 1/3 gears to speed it up
        double ticks_rotation = 145.1; // shaft tics per rev on 1150 motor
        double radius = .0043; // 96mm/2=43mm then convert to meters
        double twoPiR = 2*Math.PI*radius;
        double gravity = 9.8; // in meters/s squared
        double top = Math.sqrt(range * gravity);
        double spins = top/twoPiR;
        double ticksPerSec = spins/gears*ticks_rotation;
        telemetry.addData("TicksPerSecond", ticksPerSec);
        shooterSpeed = ticksPerSec;
    }
}
