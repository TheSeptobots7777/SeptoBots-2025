package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralSubsystem;
import frc.robot.subsystems.CoralSubsystem.CoralPivotPositions;

public class ScoringPosition extends Command {

    CoralSubsystem coralSubsystem;
    CoralPivotPositions coralPivotPositions;

    public ScoringPosition(CoralSubsystem coralSubsystem, CoralPivotPositions coralPivotPositions) {
        this.coralSubsystem = coralSubsystem;
        this.coralPivotPositions = coralPivotPositions;
        addRequirements(coralSubsystem);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
    coralSubsystem.setPIDPosition(coralPivotPositions);
    }
}