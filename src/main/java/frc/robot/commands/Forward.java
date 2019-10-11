package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

/**
 * Move Forward to Cargo Ship
 */
public class Forward extends Command{
   Timer timer = new Timer();

    @Override
    protected void initialize() {
        Robot.drivetrain.drive(0, 0); // Don't move on init
        timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
      Robot.drivetrain.drive(0.5, 0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return timer.get() < 2.0;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
      Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
