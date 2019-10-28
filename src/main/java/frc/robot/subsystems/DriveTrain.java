package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.HandleDriveTrain;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class DriveTrain extends PIDSubsystem {

    private ADIS16448_IMU imu;

    private CANSparkMax left1, left2, left3, right1, right2, right3;
    public CANEncoder   motorEncoderL1,
                        motorEncoderL2,
                        motorEncoderL3,
                        motorEncoderR1,
                        motorEncoderR2,
                        motorEncoderR3;

    public DriveTrain() {
        super(1, 0, 0, 0); //Calls PIDSubsystem constructor. Makes a PID Loop with the values given. Constructor: PIDSubsytem(double p, double i, double d, double f);. 
        getPIDController().setAbsoluteTolerance(0.05);//Sets margin of error for PID Loop
        getPIDController().setOutputRange(-1, 1);//Sets min and max values to write
        imu = new ADIS16448_IMU();
        imu.calibrate();

        left1 = new CANSparkMax(RobotMap.FRONT_ONE_PIN, MotorType.kBrushless);
        left2 = new CANSparkMax(RobotMap.FRONT_TWO_PIN, MotorType.kBrushless);
        left3 = new CANSparkMax(RobotMap.FRONT_THREE_PIN, MotorType.kBrushless);
        right1 = new CANSparkMax(RobotMap.BACK_ONE_PIN, MotorType.kBrushless);
        right2 = new CANSparkMax(RobotMap.BACK_TWO_PIN, MotorType.kBrushless);
        right3 = new CANSparkMax(RobotMap.BACK_THREE_PIN, MotorType.kBrushless);

        motorEncoderL1 = left1.getEncoder();
        motorEncoderL2 = left2.getEncoder();
        motorEncoderL3 = left3.getEncoder();
        motorEncoderR1 = right1.getEncoder();
        motorEncoderR2 = right2.getEncoder();
        motorEncoderR3 = right3.getEncoder();
        
        left2.follow(left1);
        left3.follow(left1);

        right2.follow(right1);
        right3.follow(right1);

        setClosedLoopEnabled(false);//Makes sure loop doesn't start when a new instance is made
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new HandleDriveTrain());//Calls HandleDriveTrain() on initialization
    }
    
    public void drive(double l, double r) {
        left1.set(-l * 0.3);
        right1.set(r * 0.3);
    }

    public void stop() {
        left1.set(0.0);
        right1.set(0.0);
    }

    public double getHeading() {
        return imu.getAngleZ();
    }

    public void setClosedLoopEnabled(boolean enabled) {
        getPIDController().setEnabled(enabled); //When called, you can make this true to activate the PID Loop
    }

    public boolean isClosedLoopEnabled() {
        return getPIDController().isEnabled();
    }

    public void setHeadingTarget(double target) {
        getPIDController().setSetpoint(target); // Sets the end goal for the PID Loop
    }

    public double getHeadingTarget() {
        return getPIDController().getSetpoint();
    }

    public void resetHeading() {
        imu.reset();
    }

    public boolean atHeadingTarget() {
        return getPIDController().onTarget(); // onTarget() returns true if the error is within the percentage of the total input range, determined by setTolerance.
    }

    @Override
    protected double returnPIDInput() {
        return getHeading(); // Feeds the loop the angle the Input for calculations. The angle that the robot is at when called
    }

    @Override
    protected void usePIDOutput(double output) {
        drive(output, -output); // Takes the output and applies it to the robot
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Heading", this::getHeading, null);
        super.initSendable(builder);
    }
}
