package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter {
    DcMotorEx shooter;
    DcMotorEx intake;
    Servo servo;
    String state = "nothing";
    Telemetry telemetry;
    double position;
    double shooterSpeed;
    double rangeMeters;

    public Shooter(HardwareMap hw, Telemetry telemetry){
        this.shooter = hw.get(DcMotorEx.class, "shooter");
        this.shooter.setDirection(DcMotorSimple.Direction.FORWARD);

        this.intake = hw.get(DcMotorEx.class, "intake");
        this.intake.setDirection(DcMotorSimple.Direction.REVERSE);

        this.servo = hw.get(Servo.class, "left_hand");
        this.telemetry = telemetry;
    }
    public void update(){
        switch (state){
            case "stop":
                position = 0.3;
                shooterSpeed = 0.0;
                telemetry.addData("Spinning", "false");
                break;
            case "spinup":
                position = 0.3;
                telemetry.addData("Spinning", "true");
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

        intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        intake.setVelocity(shooterSpeed);
        telemetry.addData("Shooter Speed", shooterSpeed);
        telemetry.addData("Range", rangeMeters);
        telemetry.addData("servoPos", position);
    }
    public void spinClose(){ state = "spinup";}
    public void spinFar(){state="spinup"; }
    public void drop(){state = "shoot";}
    public void stop(){state="stop";}

    public void setRange(double range){
        rangeMeters = range * 0.0254; // convert inches to meters
        // from range get shooter speed as Ticks Per Second
        double gears = 3; // 1/3 gears to speed it up
        double ticks_rotation = 103.8; // shaft tics per rev on 1620 motor
        double radius = .0043; // 96mm/2=43mm then convert to meters
        double twoPiR = 2*Math.PI*radius;
        double gravity = 9.8; // in meters/s squared
        double top = Math.sqrt(rangeMeters * gravity);
        double spins = top/twoPiR;
        shooterSpeed = spins/gears*ticks_rotation;
    }
}
