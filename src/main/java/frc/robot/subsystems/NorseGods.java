package frc.robot.subsystems;

// FINAL CODE
// LOKI'S #7777 IMPORTS
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// LOKI's SubSystem
public class NorseGods extends SubsystemBase {
SparkFlex lokiSparkFlex = new SparkFlex(15, MotorType.kBrushless); // Motor Maker with REV Software
SparkFlex thorSparkFlex = new SparkFlex(16, MotorType.kBrushless); // Motor Maker with REV Software

SparkClosedLoopController lokiController = lokiSparkFlex.getClosedLoopController(); // Enabling Closed Loop Control (PID CONTROL SYSTEM)
SparkClosedLoopController thorController = thorSparkFlex.getClosedLoopController(); // Enabling Closed Loop Control (PID CONTROL SYSTEM)

SparkFlexConfig lokiConfig = new SparkFlexConfig(); // Setting Config Method - Motor 15
SparkFlexConfig thorConfig = new SparkFlexConfig(); // Setting Config Method - Motor 16

// SET POINT SCHEDULER
public void setTargetPosition(double position) { // SET POINT
    lokiController.setReference(position, ControlType.kPosition); // Position Set Point
    thorController.setReference(position, ControlType.kPosition); // Position Set Point
    
    }

// CONFIGS For NorseGods
public NorseGods() {
    lokiConfig.closedLoop // Closed Loop Method
    .p(0.01)
    .i(0)
    .d(0)
    .outputRange(-0.5, 0.5)
    .velocityFF(1/0.02); // Feed Forward
    
    thorConfig.closedLoop // Closed Loop Method
    .p(0.01)
    .i(0)
    .d(0)
    .outputRange(-0.5, 0.5)
    .velocityFF(1/0.02); // Feed Forward

    lokiSparkFlex.configure(lokiConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters); // For POWER CYCLE
    thorSparkFlex.configure(thorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters); // For POWER CYCLE

    lokiConfig 
    .idleMode(IdleMode.kBrake) // Brake Mode - Motor is constantly stopped until robot is powered off
    .smartCurrentLimit(50); // AMPs limit on motor

    thorConfig 
    .idleMode(IdleMode.kBrake) // Brake Mode - Motor is constantly stopped until robot is powered off
    .smartCurrentLimit(50); // AMPs limit on motor
    }

// Method to Reset Position to Home (0.0)
public double getCurrentPosition() {
    double position = lokiSparkFlex.getEncoder().getPosition(); // Reads Position of Encoder
        return position;
}

public void resetToHome() {
    double homePosition = 0.0; // Define your home position (e.g., starting position)
    setTargetPosition(homePosition); // Moves motor to home
}
}
