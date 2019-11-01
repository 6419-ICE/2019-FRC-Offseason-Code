package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;

import frc.robot.RobotMap;
import frc.robot.commands.HandleArm;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {
    private PWMVictorSPX armMotor;  
    private DoubleSolenoid hookSolenoid;
    private DigitalInput heffect;


    public Arm() {
       armMotor = new PWMVictorSPX(0); // Make not 0
       hookSolenoid = new DoubleSolenoid(0, 1);
       heffect = new DigitalInput(RobotMap.sensorPrt);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new HandleArm());
    }
    

    public void armMotor(double m){
        armMotor.set(m);
    }

    public void hookSolenoid(Value m){
        hookSolenoid.set(m);
    }

    public boolean getMagnetDigitalInput() {
        return heffect.get();
    }
    
    // Add methods
}
