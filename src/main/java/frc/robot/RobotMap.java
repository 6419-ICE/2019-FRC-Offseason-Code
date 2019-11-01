/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  /* Motor Ports */
  public static final int FRONT_ONE_PIN = 1, 
                          FRONT_TWO_PIN = 2,
                          FRONT_THREE_PIN = 3,
                          BACK_ONE_PIN = 4,
                          BACK_TWO_PIN = 5,                   
                          BACK_THREE_PIN = 6;

  /* Joystick Ports */
  public static final int joy1 = 0,
                          joy2 = 1,
                          joy3 = 2;

  /* Joystick Buttons */
  public static final int arm1 = 4,
                          arm2 = 2,
                          arm3 = 7,
                          arm4 = 8;

  /* Default Autonomous Values */
  public static final double autoAngle = 90,
                          d1 = 0,
                          d2 = 0;

  /* Hatch Panel Arm Fixed Power */
  public static final double armPower = 1.0;

  /* Hall Effect Sensor Port */
  public static final int sensorPrt = 0;

}
