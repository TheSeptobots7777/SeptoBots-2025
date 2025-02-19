package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClawSubsystem;

public class MoveClawStuck extends Command {
    private final ClawSubsystem claw;

    public MoveClawStuck(ClawSubsystem claw) {
        this.claw = claw;
        addRequirements(claw);
    }

    @Override
    public void initialize() {
        claw.setClawVelocity(500); // Set velocity to push
    }

    @Override
    public void end(boolean interrupted) {
        claw.stopClaw();
    }
}
