package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;

// import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.HandleArm;

public class Arm extends Subsystem {
    private PWMVictorSPX armMotor = new PWMVictorSPX(0);  


    public Arm() {
        
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new HandleArm(0.0));
    }
    

    public void armMotor(double m){
        armMotor.set(m);
    }
    
    // Add methods
}