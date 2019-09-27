package frc.robot.subsystems;

import frc.robot.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

    private CANSparkMax left1, left2, left3, right1, right2, right3;
    private SpeedControllerGroup left;
    private SpeedControllerGroup right;

    public DriveTrain() {
        left1 = new CANSparkMax(RobotMap.FRONT_ONE_PIN, MotorType.kBrushless);
        left2 = new CANSparkMax(RobotMap.FRONT_TWO_PIN, MotorType.kBrushless);
        left3 = new CANSparkMax(RobotMap.FRONT_THREE_PIN, MotorType.kBrushless);
        right1 = new CANSparkMax(RobotMap.BACK_ONE_PIN, MotorType.kBrushless);
        right2 = new CANSparkMax(RobotMap.BACK_TWO_PIN, MotorType.kBrushless);
        right3 = new CANSparkMax(RobotMap.BACK_THREE_PIN, MotorType.kBrushless);
        
        left = new SpeedControllerGroup(left1, left2, left3);
        right = new SpeedControllerGroup(right1, right2, right3);

    }

    @Override
    protected void initDefaultCommand() {
        // Test if this is ok 
        // this.stop();
    }

    public void drive(double l, double r) {
        left.set(l);
        right.set(r);
    }

    public void stop() {
        left.set(0.0);
        right.set(0.0);
    }
}