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
  public handleButtons buttonHandler;

  private final Timer m_timer = new Timer();
  private final Limelight m_Limelight = new Limelight();
  
  /* PD
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  */

  public enum autoSelections {
    AUTO_1,
    Auto_2;
  }
  
  Command m_autonomousCommand;
  SendableChooser<autoSelections> m_chooser;  

  private void smartDashboardCommand(){
    m_chooser = new SendableChooser<>();
    m_chooser.setName("Auto Selector");
    m_chooser.setDefaultOption("Default Auto", null);
    m_chooser.addOption("Autonomous 1", autoSelections.AUTO_1);
    m_chooser.addOption("Autonomous 2", autoSelections.AUTO_2);
    m_chooser.putData("Auto Selector", m_chooser);
  }

  @Override
  public void robotInit() {

    m_oi = new OI();
    drivetrain = new DriveTrain();
    arm = new Arm();
    c = new Compressor(0);
    buttonHandler = new handleButtons();
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
    private autoSelected;
    //autoSelected = SmartDashboard.getData("Auto Selector", "Default"); - This should work too, use if other method stops working
    autoSelected = m_chooser.getSelected();
    // Select an Auto
    switch(autoSelected) { 
      case AUTO_1: 
        m_autonomousCommand = new AutoGroup(autoSelected);
      case AUTO_2:
        m_autonomousCommand = null;
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
    buttonHandler.checkButtons();
    Scheduler.getInstance().run();
    Update_Limelight_Tracking();
        boolean auto = this.m_oi.limelightButton().get();

        if (auto)
        {
          if (m_LimelightHasValidTarget)
          {
                if (HandleDriveTrain().isRunning){
                  HandleDriveTrain().cancel(); 
                }
                this.drivetrain.drive(m_LimelightDriveCommand, m_LimelightDriveCommand);
          }
          else
          {
                this.drivetrain.drive(0, 0);
          }
        }
        else
        {
          HandleDriveTrain().start();
        }
  }
  

  }

  @Override
  public void testPeriodic() {
    Scheduler.getInstance().run();
  }
  
  public static void Update_Limelight_Tracking() { // PD
    // Limelight control
    // These numbers must be tuned for your Robot!  Be careful!
    final double DRIVE_K = 0.3;                    // how hard to drive fwd toward the target
    final double DESIRED_TARGET_AREA = 13.0;        // Area of the target when the robot reaches the wall
    final double MAX_DRIVE = 0.7;                   // Simple speed limit so we don't drive too fast
    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    boolean m_LimelightHasValidTarget;
    double m_LimelightDriveCommand;
    Command m_turnCommand;
    
    if (tv < 1.0)
    {
      m_LimelightHasValidTarget = false;
      m_LimelightDriveCommand = 0.0;
      return;
    }
    m_LimelightHasValidTarget = true;
    // Start with proportional steering
    if(tx < 2 || tx > 2){
      m_turnCommand = new TurnToHeading(tx)
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
