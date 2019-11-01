/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 **************************************************************
 * CONTROLS FOR ICE 6419 FRC 2019 OFFSEASON - YEETUS MAGEETUS *
 **************************************************************
 *
 *  - Tank Drive Joysticks -> Move both forward, backward, left, or right for robot movement
 *  - Game Controller Left Joystick -> Move Hatch Panel Arm up or down
 *      -> Hold "X" button to slow arm motor
 *  - "Y" button -> Move Arm up to next hatch panel level
 *  - "A" button -> Move Arm down to previous hatch panel level
 *  - "LB" button -> Release hatch panel
 *  - "RB" button -> Attach hatch panel
 */

public class OI {
  // CREATING BUTTONS
  private Joystick leftJoy, rightJoy, armJoy;
  private Button armUp, armDown, armSlow, attachPanel, releasePanel, limelightButton;

  public OI() {
    /* Joysticks */
    this.leftJoy = new Joystick(RobotMap.joy1);
    this.rightJoy = new Joystick(RobotMap.joy2);
    this.armJoy = new Joystick(RobotMap.joy3);

    this.limelightButton = new JoystickButton(rightJoy, 5);

    /* Move Arm with Magnets */
    this.armUp = new JoystickButton(armJoy, RobotMap.arm1); 
    this.armDown = new JoystickButton(armJoy, RobotMap.arm2);
    this.armSlow = new JoystickButton(armJoy, RobotMap.arm5);

    /* Hatch Panel */
    this.releasePanel = new JoystickButton(armJoy, RobotMap.arm3); // Release
    this.attachPanel = new JoystickButton(armJoy, RobotMap.arm4); // Attach Hatch panel
  }

  /* Get states */
  public Joystick getLeftJoy() {
    return leftJoy;
  }

  public Joystick getRightJoy() {
    return rightJoy;
  }

  public Joystick getArmJoy() {
    return armJoy;
  }

  public boolean isReleasePressed() {
    return releasePanel.get();
  }

  public boolean isAttachPressed () {
    return attachPanel.get();
  }

  public boolean isArmUpPressed() {
    return armUp.get();
  }

  public boolean isArmDownPressed() {
    return armDown.get();
  }
  
  public Button limelightButton(){
    return limelightButton; 
  }

  public boolean isArmSlowPressed() {
    return armSlow.get();
  }
}