/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer; 
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Limelight.LightMode;
import edu.wpi.first.networktables.*; 

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * <<<<<<< HEAD creating this project, you must also update the manifest file in
 * the resource directory.
 */
public class Robot extends TimedRobot {
  public static DriveTrain drivetrain;
  public static Arm arm;
  public static OI m_oi;
  public static Compressor c;
  public static boolean m_LimelightHasValidTarget;
  public static double m_LimelightDriveCommand;
  public static boolean endDrive;
  public static final Limelight m_Limelight = new Limelight();

  private final Timer m_timer = new Timer();
  
  /* PD
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  */

  public enum autoSelections {
    AUTO_1,
    AUTO_2,
    AUTO_3;
  }
  
  Command m_autonomousCommand;
  SendableChooser<autoSelections> m_chooser;  

  private void smartDashboardCommand(){
    m_chooser = new SendableChooser<>();
    m_chooser.setName("Auto Selector");
    m_chooser.setDefaultOption("Default Auto", null);
    m_chooser.addOption("Autonomous 1", autoSelections.AUTO_1);
    m_chooser.addOption("Autonomous 2", autoSelections.AUTO_2);
    m_chooser.addOption("Autonomous 3", autoSelections.AUTO_3);
    SmartDashboard.putData("Auto Selector", m_chooser);
  }

  @Override
  public void robotInit() {

    m_oi = new OI();
    drivetrain = new DriveTrain();
    arm = new Arm();
    c = new Compressor(0);
    c.setClosedLoopControl(true);
    smartDashboardCommand();
    
    /* PD
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
   PD */
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {    
    final autoSelections autoSelected;
    //autoSelected = SmartDashboard.getData("Auto Selector", "Default"); - This should work too, use if other method stops working
    autoSelected = m_chooser.getSelected();
    // Select an Auto
    switch(autoSelected) { 
      case AUTO_1: 
        m_autonomousCommand = new AutoGroup(autoSelected.toString());
      case AUTO_2:
        m_autonomousCommand = new TurnToHeading(90);
      case AUTO_3:
        m_autonomousCommand = new Forward(5);
      break; 
     // case "Default Auto": default: Command m_autonomousCommand = new ExampleCommand(); 
    }

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    boolean auto = this.m_oi.limelightButton().get();
    /*if(auto){ 
      endDrive = true;
      new LimelightAssist();
    }*/
  }
  


  @Override
  public void testPeriodic() {
    Scheduler.getInstance().run();
  }
  
  
}
