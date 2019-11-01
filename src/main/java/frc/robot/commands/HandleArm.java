/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.RobotMap;

public class HandleArm extends Command {
  private boolean isDone = false;
  private double armPower;
  private Value hookPosition;

  private double coarseAdj;
  private double fineAdj;
  private boolean isMovingUp = false;
  private boolean isMovingDown = false;

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
    boolean state = Robot.arm.getMagnetDigitalInput();
    SmartDashboard.putBoolean("DB/LED 0", state);

    coarseAdj = Robot.m_oi.getArmJoy().getRawAxis(1); // Coarse Adjustment
    fineAdj = Robot.m_oi.getArmJoy().getRawAxis(2);
    

    if (Robot.m_oi.isArmDownPressed() && state && !isMovingDown) {
      isMovingDown = true;
    } else if (Robot.m_oi.isArmUpPressed() && state && !isMovingUp) {
      isMovingUp = true;
    }

    if (Robot.m_oi.isHookUpPressed()) {
      Robot.arm.hookSolenoid(Value.kForward);
    } else if (Robot.m_oi.isHookDownPressed()) {
      Robot.arm.hookSolenoid(Value.kReverse);
    } else {
      Robot.arm.hookSolenoid(Value.kOff);
    }
    if (Robot.m_oi.isArmCancelPressed()) {
      Robot.arm.armMotor(0);
      isMovingUp = false;
      isMovingDown = false;
    } else if (isMovingDown && state) {
      Robot.arm.armMotor(RobotMap.armPower * 0.5);
    } else if (isMovingUp && state) {
      Robot.arm.armMotor(-RobotMap.armPower * 0.5);
    }
     else {
      isMovingDown = false;
      Robot.arm.armMotor((coarseAdj * 0.5) - 0.1);
      // TODO make fine adjustment
      //Robot.arm.armMotor(fineAdj * 0.5); 
      //Robot.arm.armMotor(0.2);
      
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
