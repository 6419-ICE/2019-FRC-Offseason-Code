/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class HandleArm extends Command {
  private boolean isDone = false;
  
  public HandleArm() {
    requires(Robot.arm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.arm.armMotor(0);
    Robot.arm.hookSolenoid(Value.kOff);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.m_oi.isArmDownPressed()){
        Robot.arm.armMotor(-RobotMap.armPower);
    }else if(Robot.m_oi.isArmUpPressed()){
        Robot.arm.armMotor(RobotMap.armPower);
    }else if(Robot.m_oi.isHookUpPressed()){
        Robot.arm.hookSolenoid(Value.kForward);
    }else if(Robot.m_oi.isHookDownPressed()){
      Robot.arm.hookSolenoid(Value.kReverse);
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.arm.armMotor(0.0);
  }
}
