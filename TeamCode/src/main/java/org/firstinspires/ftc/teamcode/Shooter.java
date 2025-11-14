package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter {
    DcMotor shooter;
    DcMotor intake;
    Servo servo;
    String state = "nothing";
    Telemetry telemetry;
    double position;
    double shooterSpeed;
    public Shooter(DcMotor intake, DcMotor shooter, Servo servo, Telemetry telemetry){
        this.intake = intake;
        this.shooter = shooter;
        this.servo = servo;
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
        shooter.setPower(shooterSpeed);
        intake.setPower(shooterSpeed);
        telemetry.addData("org.firstinspires.ftc.teamcode.Shooter Speed", shooterSpeed);
        telemetry.addData("servoPos", position);

    }
    public void spinClose(){ state = "spinup"; shooterSpeed = .58;}
    public void spinFar(){state="spinup"; shooterSpeed = 0.5;}
    public void drop(){state = "shoot";}
    public void stop(){state="stop";}
}
