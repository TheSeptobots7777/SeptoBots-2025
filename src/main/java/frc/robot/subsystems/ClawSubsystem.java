package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase {
  private final SparkFlex clawMotor = new SparkFlex(20, MotorType.kBrushless);  // Spark Flex Port 20
  SparkFlexConfig config_ = new SparkFlexConfig();


  public ClawSubsystem() {
    config_.idleMode(SparkBaseConfig.IdleMode.kBrake);
  }

  public void moveClaw(double speed) {
    clawMotor.set(speed);
  }

  public void stopClaw() {
    clawMotor.set(0);
  }
}
