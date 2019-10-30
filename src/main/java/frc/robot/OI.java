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
 */
public class OI {
  //// CREATING BUTTONS
  private Joystick leftJoy, rightJoy;
  private Button armUp, armDown, hookUp, hookDown, limelightButton;

  public OI() {
    this.leftJoy = new Joystick(RobotMap.joy1);
    this.rightJoy = new Joystick(RobotMap.joy2);
    
    this.limelightButton = new JoystickButton(rightJoy, 5);
    this.armUp = new JoystickButton(rightJoy, RobotMap.arm1);
    this.armDown = new JoystickButton(rightJoy, RobotMap.arm2);
    this.hookUp = new JoystickButton(leftJoy, RobotMap.arm1);
    this.hookDown = new JoystickButton(leftJoy, RobotMap.arm2);
  }

  public Joystick getLeftJoy() {
    return leftJoy;
  };

  public Joystick getRightJoy() {
    return rightJoy;
  };

  public Button getHookUpBtn() {
    return hookUp;
  }

  public Button getHookDownBtn() {
    return hookDown;
  }

  public boolean isHookUpPressed() {
    return hookUp.get();
  }

  public boolean isHookDownPressed () {
    return hookDown.get();
  }
  public Button getArmUpBtn() {
    return armUp;
  }

  public Button getArmDownBtn() {
    return armDown;
  }

  public boolean isArmUpPressed() {
    return armUp.get();
  }

  public boolean isArmDownPressed () {
    return armDown.get();
  }
  
  public Button limelightButton(){
    return limelightButton; 
  }
  //// TRIGGERING COMMANDS WITH BUTTONS
  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
