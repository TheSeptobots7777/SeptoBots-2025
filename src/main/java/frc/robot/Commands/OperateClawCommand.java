package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClawSubsystem;
import edu.wpi.first.wpilibj.Joystick;

public class OperateClawCommand extends Command {
  private final ClawSubsystem clawSubsystem;
  private final Joystick driver2;

  public OperateClawCommand(ClawSubsystem clawSubsystem, Joystick driver2) {
    this.clawSubsystem = clawSubsystem;
    this.driver2 = driver2;
    addRequirements(clawSubsystem);  // Declare subsystem dependencies
  }

  @Override
  public void execute() {
    if (driver2.getRawButton(2)) {  // Button 2 pressed
      clawSubsystem.moveClaw(0.10);  // Move forward at 10% speed
    } else if (driver2.getRawButton(4)) {  // Button 4 pressed
      clawSubsystem.moveClaw(-0.10);  // Move in reverse at 10% speed
    } else {
      clawSubsystem.stopClaw();  // Stop the motor
    }
  }

  @Override
  public boolean isFinished() {
    return false;  // Run until interrupted
  }
}
