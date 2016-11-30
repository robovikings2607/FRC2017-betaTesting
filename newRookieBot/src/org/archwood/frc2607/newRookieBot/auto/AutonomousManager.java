package org.archwood.frc2607.newRookieBot.auto;

import java.util.ArrayList;

import org.archwood.frc2607.newRookieBot.RobotMain;

/**
 * @author Cerora
 *
 */
public class AutonomousManager {
	
	RobotMain robot;
	public ArrayList<AutonomousMode> modes = new ArrayList<AutonomousMode>();

	AutonomousManager(RobotMain robot){
		this.robot = robot;
		modes.add(new DoNothingFailsafe());
		modes.add(new CrossDefense(robot));
		modes.add(new CrossLowBar(robot));
		modes.add(new DoNothing());
	}
	
	public AutonomousMode getModeByName (String name){
		for (AutonomousMode m : modes){
			if (m.getName().equals(name))
				return m;
		}
		
		try {
			throw new Exception();
		} catch (Exception e) {
			System.err.println("Mode not found");
			e.printStackTrace();
			return new DoNothingFailsafe();
		}
	}
	
	public AutonomousMode getModeByIndex (int index){
		try {
			return modes.get(index);
		} catch (IndexOutOfBoundsException e){
			System.err.println("Mode out of array bounds");
			e.printStackTrace();
			return new DoNothingFailsafe();
		}
	}
	
	/*
	 * BEGIN AUTON MODE DECLARATIONS
	 * 
	 * You must add the mode to the array once you define its class
	 */
	
	public class CrossDefense extends AutonomousMode {

		CrossDefense(RobotMain r) {
			super(r);
		}
		@Override
		public void run() {
			robot.shifter.set(false);
			try { Thread.sleep(1000); } catch (Exception e) { robot.DriveTrain.arcadeDrive(0,0); return; }
			robot.DriveTrain.arcadeDrive(-0.8, 0.0);
			try { Thread.sleep(4000); } catch (Exception e) { robot.DriveTrain.arcadeDrive(0,0); return; }
			robot.DriveTrain.arcadeDrive(0.0, 0.0);
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "CrossDefense";
		}
		
	}
	
	public class CrossLowBar extends AutonomousMode {

		CrossLowBar(RobotMain r) {
			super(r);
		}
		@Override
		public void run() {
			robot.intakePiston.set(true);
			robot.shifter.set(true);
			try { Thread.sleep(1000); } catch (Exception e) { robot.DriveTrain.arcadeDrive(0,0); return; }
			robot.DriveTrain.arcadeDrive(0.95, 0.0);
			try { Thread.sleep(6500); } catch (Exception e) { robot.DriveTrain.arcadeDrive(0,0); return; }
			robot.DriveTrain.arcadeDrive(0.0, 0.0);
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "CrossLowBar";
		}
		
	}

	public class DoNothing extends AutonomousMode {
		
		DoNothing(){
			
		}

		@Override
		public void run() {
			System.out.println("Explicitly told not to move");
		}

		@Override
		public String getName() {
			return "DoNothing";
		}
		
	}
	
	public class DoNothingFailsafe extends AutonomousMode {
		
		DoNothingFailsafe(){
			
		}

		@Override
		public void run() {
			System.out.println("This shouldn't be running - Mode 0 selected for some reason");
		}

		@Override
		public String getName() {
			return "DoNothingFailsafe";
		}
		
	}
}
