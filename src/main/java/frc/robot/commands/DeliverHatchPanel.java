package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Deliver Hatch Panel to Cargo Ship
 */
public class DeliverHatchPanel extends Command{
   
   private Timer timer = new Timer();
   private boolean isDone = false;
   
    @Override
    protected void initialize() {
        Robot.drivetrain.drive(0, 0); // Don't move on init
        Robot.arm.armMotor(0);
        Robot.arm.hookSolenoid(Value.kOff);
        requires(Robot.arm);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
       if(timer.get() < 1){
          Robot.arm.armMotor(1);
       }else if(timer.get() > 1){
          Robot.arm.hookSolenoid(Value.kReverse);
       } else if(timer.get() > 1.3){
          isDone = false;
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
