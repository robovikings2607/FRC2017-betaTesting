package org.archwood.frc2607.newRookieBot;

import org.archwood.frc2607.newRookieBot.auto.AutonomousEngine;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class RobotMain extends IterativeRobot {

	Talon Left1 ;
	Talon Left2 ;
	Talon Right1 ;
	Talon Right2 ;
	Talon intakeRollers ;
	public Solenoid shifter;
	public Solenoid intakePiston;
	Compressor compressor ;
	RobovikingStick DriveStick , OpStick;
	public RobotDrive DriveTrain ;
	CANTalon testMotor;
	
	AutonomousEngine autoEngine;
	Thread autoThread = null;
	boolean autonModeRan;
	
	DriverStation ds = DriverStation.getInstance();
	
	@Override
	public void robotInit() {
		Left1 = new Talon(Constants.leftMotor1) ;
		Left2 = new Talon(Constants.leftMotor2) ;
		Right1 = new Talon(Constants.rightMotor1) ;
		Right2 = new Talon(Constants.rightMotor2) ;
		intakeRollers = new Talon(Constants.rollersMotor);
		shifter = new Solenoid(Constants.shifter) ;
		intakePiston = new Solenoid(Constants.clawSolenoid);
		compressor = new Compressor();
		DriveStick = new RobovikingStick(Constants.driveController) ;
		OpStick = new RobovikingStick(Constants.operatorController) ;
		DriveTrain = new RobotDrive(Left1, Left2, Right1, Right2) ;
		DriveTrain.setSafetyEnabled(false);
		compressor.start();
		
		autoEngine = new AutonomousEngine(this);
		autoEngine.loadSavedMode();
		autonModeRan = false;
		autoSwitch = true;
		
		testMotor = new CANTalon(7);
	}
	int rpCounter;

	@Override
	public void robotPeriodic() {
		
		if(rpCounter++ > 25){
			for(int i = 1 ; i <= ds.getStickButtonCount(0) ; i++){
				System.out.print( i + ":");
				System.out.print(DriveStick.getRawButton(i) ? "T" : "F");
				System.out.print(" ");
			}
			System.out.println(" HAL buttons: " + ds.getStickButtons(0));
			System.out.println("");
	
			rpCounter = 0;
		}
	}

	@Override
	public void teleopPeriodic() {
		shifter.set(DriveStick.getToggleButton(RobovikingStick.xBoxButtonA)) ;
		intakePiston.set(OpStick.getToggleButton(RobovikingStick.xBoxButtonY)) ;
		
		if(OpStick.getRawButton(RobovikingStick.xBoxLeftBumper)) {
			intakeRollers.set(0.9);
		} else if(OpStick.getRawButton(RobovikingStick.xBoxRightBumper)) {
			intakeRollers.set(-0.9);
		} else {
			intakeRollers.set(0.0);
		}
		
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
	boolean autoSwitch;
	
	@Override
	public void disabledInit() {
		//DriveTrain.setSafetyEnabled(true);
	}

	@Override
	public void disabledPeriodic() {
		
		intakePiston.set(false);
		if(DriveStick.getButtonPressedOneShot(RobovikingStick.xBoxButtonStart)) {
			autoEngine.selectMode();
			autoSwitch = true;
		}
		if(DriveStick.getButtonPressedOneShot(RobovikingStick.xBoxButtonBack)) {
			autoSwitch = false;
		}
		if (autonModeRan) {

    		autonModeRan = false;

    		if (autoThread != null) {

    			if (autoThread.isAlive()) { 
    				System.out.println("autoThread alive, interrupting");
    				autoThread.interrupt();
    			} else {System.out.println("autoThread not alive");}
    		}
    	}
	}
	
	@Override
	public void autonomousInit() {
		//DriveTrain.setSafetyEnabled(false);
		autoThread = new Thread(autoEngine);
		autoThread.start();
		
		autonModeRan = true;
	}

	@Override
	public void autonomousPeriodic() {

	}
	
	/*
	@Override
	public void autonomousInit() {
		autoCounter = 0;
		autoTime = 0;
	}
	
	@Override
	public void autonomousPeriodic() {
		if(autoSwitch){
			
			if(autoTime < 6.5) {
				shifter.set(true);
				DriveTrain.arcadeDrive(-0.95 , 0.0);
			} else {
				DriveTrain.arcadeDrive(0.0, 0.0);
			}
		}
		autoTime = autoCounter++ / 50.0;
	}
	*/
}
