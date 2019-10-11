package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

/**
 * Handle the Drivetrain
 */
public class AutoGroup extends CommandGroup{
    private String name;

    public AutoGroup(String n) {
        name = n;

        addSequential(new Forward());
        addSequential(new TurnLeft());
        addSequential(new Forward());
        addSequential(new DeliverHatchPanel());
    }
}