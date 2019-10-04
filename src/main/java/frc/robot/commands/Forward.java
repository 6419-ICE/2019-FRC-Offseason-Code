package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;

/**
 * Move Forward to Cargo Ship
 */
public class Forward extends Command{
    private final Timer timer = new Timer();
    @Override
    protected void initialize() {
        Robot.drivetrain.drive(0, 0); // Don't move on init
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if(timer.get() < 2.0){     
            Robot.drivetrain.drive(0.5, 0.5);
        }
 
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}