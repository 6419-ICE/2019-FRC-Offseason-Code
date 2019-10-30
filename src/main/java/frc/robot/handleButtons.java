package frc.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

import frc.robot.commands.*;

public class handleButtons {
  private Button armUp, armDown, hookUp, hookDown;
  private boolean isArmUp, isArmDown, isHookUp, isHookDown;
  private double armValue;
  private Value hookValue;
  private Command armCommand;
  
  
  public handleButtons(){
    this.armUp = Robot.m_oi.getArmUpBtn();
    this.armDown = Robot.m_oi.getArmDownBtn();
    this.hookUp = Robot.m_oi.getHookUpBtn();
    this.hookDown = Robot.m_oi.getHookDownBtn();
    
    this.isArmUp = Robot.m_oi.isArmUpPressed();
    this.isArmDown = Robot.m_oi.isArmDownPressed();
    this.isHookUp = Robot.m_oi.isHookUpPressed();
    this.isHookDown = Robot.m_oi.isHookDownPressed();
    
    this.armCommand = new HandleArm(0, Value.kOff);
  }
  
  public void checkButtons(){
    if (isArmUp){
      armValue = RobotMap.armPower;
    } else if (isArmDown){
      armValue = -RobotMap.armPower;
    }
    
    if (isHookUp){
      hookValue = Value.kForward;
    } else if (isHookDown){
      hookValue = Value.kReverse;
    }
    
    this.armCommand = new HandleArm(armValue, hookValue);
  }
  
  

}
