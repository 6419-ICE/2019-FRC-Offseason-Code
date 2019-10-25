package frc.robot.commands;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

/**
 * Turn Left to align with Cargo Ship
 */
public class TurnToHeading extends Command {

    private double heading;

    public TurnToHeading(double heading) {
        this.heading = heading;
    }

    @Override
    protected void initialize() {
        Robot.drivetrain.drive(0, 0); // Don't move on init
        Robot.drivetrain.resetHeading();
        Robot.drivetrain.setHeadingTarget(heading);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.drivetrain.setClosedLoopEnabled(true);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.drivetrain.atHeadingTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.drivetrain.setClosedLoopEnabled(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}