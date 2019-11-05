package frc.robot.commands;

import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
/**
 * Move Forward specified distance - Autonomous 1
 */
public class Forward extends Command {
   private double rotations;
   private final double inchesPerRotation = 4 * Math.PI / ((50.0 / 12.0) * (40.0 / 34.0));
   private final double rotationsPerInch = 1.0 / inchesPerRotation;

    public Forward(double d) {
        rotations = d * rotationsPerInch;
        System.out.println(String.format("Driving %f rotations", rotations));
    }

    @Override
    protected void initialize() {
        Robot.drivetrain.drive(0, 0); // Don't move on init
        Robot.drivetrain.motorEncoderL1.setPosition(0);
        Robot.drivetrain.motorEncoderR1.setPosition(0);
        Robot.drivetrain.leftController.setOutputRange(-0.5, 0.5);
        Robot.drivetrain.rightController.setOutputRange(-0.5, 0.5);

        Robot.drivetrain.leftController.setReference(rotations, ControlType.kPosition);
        Robot.drivetrain.rightController.setReference(rotations, ControlType.kPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.drivetrain.motorEncoderL1.getPosition() - rotations) < 0.25 ||
               Math.abs(Robot.drivetrain.motorEncoderR1.getPosition() - rotations) < 0.25;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.drivetrain.leftController.setReference(0, ControlType.kDutyCycle);
        Robot.drivetrain.rightController.setReference(0, ControlType.kDutyCycle);
        Robot.drivetrain.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
