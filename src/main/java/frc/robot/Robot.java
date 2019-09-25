/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.networktables.*; //PD



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * <<<<<<< HEAD creating this project, you must also update the manifest file in
 * the resource directory.
 */
public class Robot extends TimedRobot {
  
  /* PD
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

PD */ 
  
  private final Spark frontLeft = new Spark(RobotMap.frontLeft); // This is how you label port
  private final PWMVictorSPX frontRight = new PWMVictorSPX(RobotMap.frontRight);
  private final PWMVictorSPX backLeft = new PWMVictorSPX(RobotMap.backLeft);
  private final PWMVictorSPX backRight = new PWMVictorSPX(RobotMap.backRight);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(frontLeft, frontRight); // Drive object
  private final DifferentialDrive m_robotDrive2 = new DifferentialDrive(backLeft, backRight); // Drive object

  private final Timer m_timer = new Timer();

  // public static ExampleSubsystem m_subsystem = new ExampleSubsystem(); -- Remvoed cuz it not needed
  
  public static OI m_oi;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    m_oi = new OI();
    
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
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      m_robotDrive.arcadeDrive(0.5, 0.0);
      System.out.println("Autonomus Started for 2 seconds"); // drive forwards half speed
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    m_robotDrive.arcadeDrive(m_oi.getJoy().getY(), m_oi.getJoy().getX()); // Move using drive object
    m_robotDrive2.arcadeDrive(m_oi.getJoy().getY(), m_oi.getJoy().getX());

    
    
    /* PD
    Update_Limelight_Tracking();

        double steer = m_Controller.getX(Hand.kRight);
        double drive = -m_Controller.getY(Hand.kLeft);
        boolean auto = m_Controller.getAButton();

        steer *= 0.70;
        drive *= 0.70;

        if (auto)
        {
          if (m_LimelightHasValidTarget)
          {
                m_Drive.arcadeDrive(m_LimelightDriveCommand,m_LimelightSteerCommand);
          }
          else
          {
                m_Drive.arcadeDrive(0.0,0.0);
          }
        }
        else
        {
          m_Drive.arcadeDrive(drive,steer);
        }
  }

    PD*/
  }

  @Override
  public void testPeriodic() {
  }
  
  public static void Update_Limelight_Tracking() { 
    // Limelight control
    // These numbers must be tuned for your Robot!  Be careful!
    final double STEER_K = 0.03;                    // how hard to turn toward the target
    final double DRIVE_K = 0.26;                    // how hard to drive fwd toward the target
    final double DESIRED_TARGET_AREA = 13.0;        // Area of the target when the robot reaches the wall
    final double MAX_DRIVE = 0.7;                   // Simple speed limit so we don't drive too fast

    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

    boolean m_LimelightHasValidTarget;
    double m_LimelightDriveCommand;
    double m_LimelightSteerCommand;

    if (tv < 1.0)
    {
      m_LimelightHasValidTarget = false;
      m_LimelightDriveCommand = 0.0;
      m_LimelightSteerCommand = 0.0;
      return;
    }

    m_LimelightHasValidTarget = true;

    // Start with proportional steering
    double steer_cmd = tx * STEER_K;
    m_LimelightSteerCommand = steer_cmd;

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
