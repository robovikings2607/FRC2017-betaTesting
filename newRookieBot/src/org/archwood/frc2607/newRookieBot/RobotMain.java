package org.archwood.frc2607.newRookieBot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class RobotMain extends IterativeRobot {

	Talon Left1 ;
	Talon Left2 ;
	Talon Right1 ;
	Talon Right2 ;
	Solenoid shifter;
	Compressor compressor ;
	RobovikingStick DriveStick ;
	RobotDrive DriveTrain ;
	
@Override
	public void robotInit() {
		Left1 = new Talon(Constants.leftMotor1) ;
		Left2 = new Talon(Constants.leftMotor2) ;
		Right1 = new Talon(Constants.rightMotor1) ;
		Right2 = new Talon(Constants.rightMotor2) ;
		shifter = new Solenoid(Constants.shifter) ;
		compressor = new Compressor();
		DriveStick = new RobovikingStick(Constants.driveController) ;
		DriveTrain = new RobotDrive(Left1, Left2, Right1, Right2) ;
		shifter.set(false);
		compressor.start();
	}
	@Override
	public void teleopPeriodic() {
		shifter.set(DriveStick.getToggleButton(RobovikingStick.xBoxButtonA)) ;
		DriveTrain.arcadeDrive(DriveStick.getRawAxisWithDeadzone(RobovikingStick.xBoxLeftStickY), DriveStick.getRawAxisWithDeadzone(RobovikingStick.xBoxRightStickX));
/*
		System.out.println("Left Stick: " + DriveStick.getRawButton(RobovikingStick.xBoxButtonLeftStick) 
			+ " , Right Stick: " + DriveStick.getRawButton(RobovikingStick.xBoxButtonRightStick)
			+ " , Right Bumper: " + DriveStick.getRawButton(RobovikingStick.xBoxRightBumper)
			+ " , Left Bumper: " + DriveStick.getRawButton(RobovikingStick.xBoxLeftBumper)
			+ " , Start: " + DriveStick.getRawButton(RobovikingStick.xBoxButtonStart)
			+ " , Back: " + DriveStick.getRawButton(RobovikingStick.xBoxButtonBack)
			+ " , Y Button: " + DriveStick.getRawButton(RobovikingStick.xBoxButtonY)) ;
*/
	}
	
	int autoCounter ;
	double autoTime ;
	
	@Override
	public void autonomousInit() {
		autoCounter = 0;
		autoTime = 0;
	}

	@Override
	public void autonomousPeriodic() {
		
		if(autoTime < 3.3) {
			DriveTrain.arcadeDrive(0.50 , 0.0);
		}
		
		if(autoTime > 3.3 && autoTime < 6.6) {
			DriveTrain.arcadeDrive(-1.0 , 0.0);
		}
		
		if(autoTime > 6.6 && autoTime < 9.9) {
			DriveTrain.arcadeDrive(0.50 , 0.0);
		}
		if(autoTime > 9.9) {
			DriveTrain.arcadeDrive(0.0, 0.0);
		}
		
		System.out.println(autoTime);
		autoTime = autoCounter++ / 50.0 ;
		
	}

}
