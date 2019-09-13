/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final PWMVictorSPX frontLeft = new PWMVictorSPX(4); //This is how you label port
  private final PWMVictorSPX frontRight = new PWMVictorSPX(2);
  private final PWMVictorSPX backLeft = new PWMVictorSPX(3);
  private final PWMVictorSPX backRight = new PWMVictorSPX(1);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(frontLeft, frontRight); //Drive object
  private final DifferentialDrive m_robotDrive2 = new DifferentialDrive(backLeft, backRight); //Drive object
 
  private final Joystick m_stick = new Joystick(0);
  private final Timer m_timer = new Timer();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
        if (m_timer.get() < 2.0) {
      m_robotDrive.arcadeDrive(0.5, 0.0);
      System.out.println("Go!"); // drive forwards half speed
    } else {
      m_robotDrive.stopMotor(); // stop robot
      System.out.println("poopy face");
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      m_robotDrive.arcadeDrive(0.5, 0.0);
      System.out.println("Go!"); // drive forwards half speed
    } else {
      m_robotDrive.stopMotor(); // stop robot
      System.out.println("poopy face");
    }
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
    m_robotDrive.arcadeDrive(m_stick.getY(), m_stick.getX()); // Move using drive object
    m_robotDrive2.arcadeDrive(m_stick.getY(), m_stick.getX());

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
