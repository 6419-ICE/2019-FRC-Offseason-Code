package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.RobotMap;

/**
 * Handle the Drivetrain
 */
public class AutoGroup extends CommandGroup{
    private String name;

    public AutoGroup(String n) {
        name = n;

        /* Add Auto Commands to Sequence based on delivery location */
        addSequential(new Forward(RobotMap.d1));
        addSequential(new LimelightAssist());
        addSequential(new DeliverHatchPanel());

    }

    public String getAutoName() {
        return name;
    }
}