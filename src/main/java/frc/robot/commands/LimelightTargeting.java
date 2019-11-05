/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LimelightTargeting extends Command implements PIDOutput {
  private PIDController pid;
  private volatile double pidOutput;

  private final static double NOMINAL_SPEED = 0.1;

  public LimelightTargeting() {
    this(0);
  }

  public LimelightTargeting(double targetOffset) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_Limelight);
    requires(Robot.drivetrain);
    pid = new PIDController(0.04, 0, 0, Robot.m_Limelight, this);
    pid.disable();
    pid.setSetpoint(targetOffset);
    pid.setAbsoluteTolerance(3);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    pid.setEnabled(true);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.drivetrain.drive(pidOutput, -pidOutput);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return pid.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    pid.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

  @Override
  public void pidWrite(double output) {
    pidOutput = output;
    if (Math.abs(pidOutput) < NOMINAL_SPEED) {
      pidOutput = Math.copySign(NOMINAL_SPEED, pidOutput);
    }
  }
}
