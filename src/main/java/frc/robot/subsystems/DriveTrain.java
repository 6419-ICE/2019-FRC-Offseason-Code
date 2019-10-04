package frc.robot.subsystems;

import frc.robot.Config;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

    private CANSparkMax 

    public DriveTrain() {
        frontLeft = new Spark(Config.FRONT_LEFT_PIN);
        frontRight = new Spark(Config.FRONT_RIGHT_PIN);
        backLeft = new Spark(Config.BACK_LEFT_PIN);
        backRight = new Spark(Config.BACK_RIGHT_PIN);

    }

    @Override
    protected void initDefaultCommand() {
        //
    }

    public void drive(double left, double right) {
        frontLeft.set(left);
        backLeft.set(left);

        frontRight.set(right);
        backRight.set(right);
    }

    public void stop() {
        frontLeft.set(0.0);
        backLeft.set(0.0);

        frontRight.set(0.0);
        backRight.set(0.0);
    }
}