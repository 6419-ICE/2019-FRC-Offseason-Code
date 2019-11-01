package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
// import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
// import frc.robot.RobotMap;
import frc.robot.RobotMap;

/**
 * Deliver Hatch Panel to Cargo Ship - Autonomous 3
 */
public class DeliverHatchPanel extends Command{

   private boolean status;
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
      status = Robot.arm.getMagnetDigitalInput();

       if (status) {
         Robot.arm.armMotor(-RobotMap.armPower);
       } else {
         Robot.arm.hookSolenoid(Value.kReverse);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
