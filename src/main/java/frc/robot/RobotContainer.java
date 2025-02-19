// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.ScoringPosition;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralSubsystem;
import frc.robot.subsystems.CoralSubsystem.CoralPivotPositions;

public class RobotContainer {
    private final CoralSubsystem coralSubsystem = new CoralSubsystem();
    private int L1Button = 9;
    private int L2Button = 8;
    private int L3Button = 5;
    private int L4Button = 1;

    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.5 * 0.5).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity
    // .75 to 0.5 * 0.5


    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    SendableChooser chooser = new SendableChooser();
    SendableChooser modeChooser = new SendableChooser();


    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);
    

    // Driver 1 - Starts at 0
    private final CommandXboxController joystick = new CommandXboxController(0);
    
    // Driver 2 - Ends at 1
    private final CommandXboxController driver2 = new CommandXboxController(1); 

    // Tuned Swerve
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    // Path Planner
    public RobotContainer() {
        
        chooser.addOption("SeptoBots Auto1", 0);
        chooser.addOption("SeptoBots Auto2" , 1);
        DriverStation.silenceJoystickConnectionWarning(true);

        NamedCommands.registerCommand("Arm to L4", new ScoringPosition(coralSubsystem, CoralPivotPositions.L4));
        NamedCommands.registerCommand("Arm to L3", new ScoringPosition(coralSubsystem, CoralPivotPositions.L3));
        NamedCommands.registerCommand("Arm to L2", new ScoringPosition(coralSubsystem, CoralPivotPositions.L2));
        NamedCommands.registerCommand("Arm to L1", new ScoringPosition(coralSubsystem, CoralPivotPositions.L1));
        SmartDashboard.putData(chooser);
        
        configureBindings();
    }

    // for swerve
    private void configureBindings() {
        driver2.button(L4Button).onTrue(new ScoringPosition(coralSubsystem, CoralPivotPositions.L4));
        driver2.button(L3Button).onTrue(new ScoringPosition(coralSubsystem, CoralPivotPositions.L3));
        driver2.button(L2Button).onTrue(new ScoringPosition(coralSubsystem, CoralPivotPositions.L2));
        driver2.button(L1Button).onTrue(new ScoringPosition(coralSubsystem, CoralPivotPositions.L1));
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);   

    }
    private Command chooseAuto(){
    switch ((int) chooser.getSelected()) {
      case 0:
        return new SequentialCommandGroup(
              //shooter.setState(PivotState.SPEAKER),
              //shooter.ShootSpeaker()
        );

      default:
        return null;
    }
  }

    public Command getAutonomousCommand() {
        //return Commands.print("No autonomous command configured");
        return chooseAuto();    
    }
        
}