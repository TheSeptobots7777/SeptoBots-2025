// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import com.revrobotics.spark.SparkFlex;
//import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.net.PortForwarder;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  
  //public Joystick driver2 = new Joystick(1);

  //private SparkFlex ClawMotor = new SparkFlex(12, MotorType.kBrushless);
  
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;

  public Robot() {
    PortForwarder.add(5800, "photonvision.local", 5800);
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run(); 
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
/* 
  if (driver2.getRawButton(2)){  //Stuck - Cross
    ClawMotor.set(0.25);
  } else if(driver2.getRawButton(4)){ //Unstuck - Triangle
    ClawMotor.set(-0.25);
  }
  else{
    ClawMotor.set(0);
  }
  */
  }
  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationPeriodic() {}
}
