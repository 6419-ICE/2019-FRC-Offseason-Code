package frc.robot.subsystems;

import frc.robot.RobotMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

    private CANSparkMax left1, left2, left3, right1, right2, right3;
    public CANEncoder motorEncoder;

    public DriveTrain() {
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
    }

    @Override
    protected void initDefaultCommand() {
        // Test if this is ok 
        // this.stop();
    }

    public void drive(double l, double r) {
        left1.set(l * 0.7);
        right1.set(r * 0.7);
    }

    public void stop() {
        left1.set(0.0);
        right1.set(0.0);
    }
}