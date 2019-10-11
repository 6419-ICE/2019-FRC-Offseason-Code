package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

/**
 * Turn Left to align with Cargo Ship
 */
public class TurnLeft extends Command{
    boolean done;
    private final Timer timer = new Timer();
    @Override
    protected void initialize() {
        Robot.drivetrain.drive(0, 0); // Don't move on init
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if(timer.get() < 1.0){
            Robot.drivetrain.drive(-.5, .5);
        }else{
            done = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return done;
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