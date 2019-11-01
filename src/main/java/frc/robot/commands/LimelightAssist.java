/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class LimelightAssist extends Command {
  public static boolean m_LimelightHasValidTarget;
  public static double m_LimelightDriveCommand;

  public LimelightAssist() {
    requires(Robot.m_Limelight);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Update_Limelight_Tracking();
 
      if (m_LimelightHasValidTarget)
      {
            Robot.drivetrain.drive(m_LimelightDriveCommand, m_LimelightDriveCommand);
      }
  }
  

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (m_LimelightDriveCommand == 0){
      return true;
    } else {
      return false;
    }
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

  private void Update_Limelight_Tracking() { // PD
    // Limelight control
    // These numbers must be tuned for your Robot!  Be careful!
    final double DRIVE_K = 0.3;                    // how hard to drive fwd toward the target
    final double DESIRED_TARGET_AREA = 13.0;        // Area of the target when the robot reaches the wall
    final double MAX_DRIVE = 0.7;                   // Simple speed limit so we don't drive too fast
    boolean tv = Robot.m_Limelight.canSeeTarget();
    double tx = Robot.m_Limelight.getHorizontalAngle();
    double ty = Robot.m_Limelight.getVerticalAngle();
    double ta = Robot.m_Limelight.getTargetArea();
    Command m_turnCommand;
    
    if (tv){
      m_LimelightHasValidTarget = false;
      m_LimelightDriveCommand = 0.0;
      return;
    }
    m_LimelightHasValidTarget = true;
    // Start with proportional steering
    if(tx < 2 || tx > -2){
      m_turnCommand = new TurnToHeading(tx);
      m_turnCommand.start();
    }
    // try to drive forward until the target area reaches our desired area
    double drive_cmd = (DESIRED_TARGET_AREA - ta) * DRIVE_K;
    // don't let the robot drive too fast into the goal
    if (drive_cmd > MAX_DRIVE)
    {
      drive_cmd = MAX_DRIVE;
    }
    m_LimelightDriveCommand = drive_cmd;
  }
  
}
