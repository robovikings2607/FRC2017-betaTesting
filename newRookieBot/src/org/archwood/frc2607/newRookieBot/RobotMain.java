package org.archwood.frc2607.newRookieBot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class RobotMain extends IterativeRobot {
	Talon Left1 ;
	Talon Left2 ;
	Talon Right1 ;
	Talon Right2 ;
	RobovikingStick DriveStick ;
	RobotDrive DriveTrain ;
	
@Override
	public void robotInit() {
		// TODO Auto-generated method stub
	Left1 = new Talon(Constants.leftMotor1) ;
	Left2 = new Talon(Constants.leftMotor2) ;
	Right1 = new Talon(Constants.rightMotor1) ;
	Right2 = new Talon(Constants.rightMotor2) ;
	DriveStick = new RobovikingStick(Constants.driveController) ;
	DriveTrain = new RobotDrive(Left1, Left2, Right1, Right2) ;
	}
	@Override
	public void teleopPeriodic() {
		// TODO Auto-generated method stub
		DriveTrain.arcadeDrive(DriveStick.getRawAxisWithDeadzone(RobovikingStick.xBoxLeftStickY), DriveStick.getRawAxisWithDeadzone(RobovikingStick.xBoxRightStickX));
	}


}
