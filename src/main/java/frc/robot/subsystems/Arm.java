package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMVictorSPX;

// import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.HandleArm;

public class Arm extends Subsystem {
    private PWMVictorSPX armMotor;  
    private DoubleSolenoid hookSolenoid;


    public Arm() {
       armMotor = new PWMVictorSPX(0);
       hookSolenoid = new DoubleSolenoid(0, 1);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new HandleArm());
    }
    

    public void armMotor(double m){
        armMotor.set(m);
    }

    public void hookSolenoid(DoubleSolenoid.Value m){
        hookSolenoid.set(m);
    }
    
    // Add methods
}
