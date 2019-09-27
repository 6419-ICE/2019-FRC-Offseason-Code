package frc.robot.subsystems;

import frc.robot.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

    private CANSparkMax front1, front2, front3, back1, back2, back3;
    private SpeedControllerGroup left;
    private SpeedControllerGroup right;

    public DriveTrain() {
        front1 = new CANSparkMax(RobotMap.FRONT_ONE_PIN, MotorType.kBrushless);
        front2 = new CANSparkMax(RobotMap.FRONT_TWO_PIN, MotorType.kBrushless);
        front3 = new CANSparkMax(RobotMap.FRONT_THREE_PIN, MotorType.kBrushless);
        back1 = new CANSparkMax(RobotMap.BACK_ONE_PIN, MotorType.kBrushless);
        back2 = new CANSparkMax(RobotMap.BACK_TWO_PIN, MotorType.kBrushless);
        back3 = new CANSparkMax(RobotMap.BACK_THREE_PIN, MotorType.kBrushless);
        
        left = new SpeedControllerGroup(front1, front2, front3);
        right = new SpeedControllerGroup(back1, back2, back3);

    }

    @Override
    protected void initDefaultCommand() {
        //
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