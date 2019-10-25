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
    public CANEncoder motorEncoder;

    public DriveTrain() {
        super(1, 0, 0, 0);
        getPIDController().setAbsoluteTolerance(0.05);
        getPIDController().setOutputRange(-1, 1);
        imu = new ADIS16448_IMU();
        imu.calibrate();

        left1 = new CANSparkMax(RobotMap.FRONT_ONE_PIN, MotorType.kBrushless);
        left2 = new CANSparkMax(RobotMap.FRONT_TWO_PIN, MotorType.kBrushless);
        left3 = new CANSparkMax(RobotMap.FRONT_THREE_PIN, MotorType.kBrushless);
        right1 = new CANSparkMax(RobotMap.BACK_ONE_PIN, MotorType.kBrushless);
        right2 = new CANSparkMax(RobotMap.BACK_TWO_PIN, MotorType.kBrushless);
        right3 = new CANSparkMax(RobotMap.BACK_THREE_PIN, MotorType.kBrushless);

        motorEncoder = left1.getEncoder();
        left2.follow(left1);
        left3.follow(left1);

        right2.follow(right1);
        right3.follow(right1);

        setClosedLoopEnabled(false);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new HandleDriveTrain());
    }

    public void drive(double l, double r) {
        left1.set(l);
        right1.set(r);
    }

    public void stop() {
        left1.set(0.0);
        right1.set(0.0);
    }

    public double getHeading() {
        return imu.getAngleZ();
    }

    public void setClosedLoopEnabled(boolean enabled) {
        getPIDController().setEnabled(enabled);
    }

    public boolean isClosedLoopEnabled() {
        return getPIDController().isEnabled();
    }

    public void setHeadingTarget(double target) {
        getPIDController().setSetpoint(target);
    }

    public double getHeadingTarget() {
        return getPIDController().getSetpoint();
    }

    public void resetHeading() {
        imu.reset();
    }

    public boolean atHeadingTarget() {
        return getPIDController().onTarget();
    }

    @Override
    protected double returnPIDInput() {
        return getHeading();
    }

    @Override
    protected void usePIDOutput(double output) {
        drive(output, -output);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Heading", this::getHeading, null);
        super.initSendable(builder);
    }
}