package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Timer;
import com.analog.adis16448.frc.ADIS16448_IMU;  
/**
 * Move Forward to Cargo Ship
 */
public class Forward extends Command{
    private final Timer timer = new Timer();
    private  final ADIS16448_IMU imu = new ADIS16448_IMU();
    private AnalogGyro m_gyro = new AnalogGyro(RobotMap.GYRO_PORT);

    @Override
    protected void initialize() {
        Robot.drivetrain.drive(0, 0); // Don't move on init
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {    
        Robot.drivetrain.drive(0.5, 0.5);
    }
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return timer.get() > 2.0;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}