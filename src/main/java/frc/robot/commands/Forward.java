package frc.robot.commands;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
/**
 * Move Forward to Cargo Ship
 */
public class Forward extends Command{
   private double distance;
   private double distancePerRotation = 4 * Math.PI;
   private boolean isDone = false;

    public Forward(double d) {
        this.distance = d;
    }

    @Override
    protected void initialize() {
        Robot.drivetrain.drive(0, 0); // Don't move on init
        Robot.drivetrain.motorEncoderL1.setPosition(0);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if(distancePerRotation * Robot.drivetrain.motorEncoderL1.getPosition() < -distance){
            Robot.drivetrain.drive(-0.5, -0.5); // TODO should this work w/o negative pwr?
        } else {
            isDone = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.drivetrain.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
