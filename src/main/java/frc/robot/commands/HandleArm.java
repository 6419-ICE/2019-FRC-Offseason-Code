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
  // private Value hookPosition;

  private double armAdjustment;
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

    armAdjustment = Robot.m_oi.getArmJoy().getRawAxis(1); // Joystick Adjustment value
    boolean armAdjusting = Math.abs(armAdjustment) > 0.1;

    /*if (Robot.m_oi.isArmSlowPressed()) {
      Robot.arm.armMotor(armAdjustment * 0.3);
    } else {
      Robot.arm.armMotor(armAdjustment);
    }*/

    /* Adjust the arm using the following one at a time */
    if (!isMovingDown && !isMovingUp) {
      if (Robot.m_oi.isArmSlowPressed()) {
        Robot.arm.armMotor(armAdjustment * 0.3);
      } else if (Robot.m_oi.isArmDownPressed() && state) {
        System.out.println("Moving Down");
        isMovingDown = true;
      } else if (Robot.m_oi.isArmUpPressed() && state) {
        System.out.println("Moving Up");
        isMovingUp = true;
      } else {
        Robot.arm.armMotor(armAdjustment);
      }
    }

    if(!state){
      isMovingDown = false;
      isMovingUp = false;
    }

    /* Use buttons and magnetic sensor if not joystick */
    if (isMovingDown && state) {
      Robot.arm.armMotor(RobotMap.armPower);
    } else if (isMovingUp && state) {
      Robot.arm.armMotor(-RobotMap.armPower);
    } else if(!armAdjusting && state && !isMovingDown && !isMovingUp){
      Robot.arm.armMotor(-0.1);

    }

    /* Attach/Release Hatch Panels */
    if (Robot.m_oi.isAttachPressed()) {
      Robot.arm.hookSolenoid(Value.kForward);
    } else if (Robot.m_oi.isReleasePressed()) {
      Robot.arm.hookSolenoid(Value.kReverse);
    } else {
      Robot.arm.hookSolenoid(Value.kOff);
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
