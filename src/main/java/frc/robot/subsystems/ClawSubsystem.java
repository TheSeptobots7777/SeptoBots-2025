package frc.robot.subsystems;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase {
    private final SparkFlex clawMotor;
    private final SparkClosedLoopController pidController;
    private final RelativeEncoder encoder;

    // Closed-loop target velocities (adjust as needed)
    private final double STUCK_SPEED = 500; // RPM for pushing
    private final double UNSTUCK_SPEED = -500; // RPM for pulling

    public ClawSubsystem(int motorID) {
        clawMotor = new SparkFlex(20, MotorType.kBrushless);
        pidController = clawMotor.getClosedLoopController();
        encoder = clawMotor.getEncoder();

        pidController.setP(0.1); //error (Glitch?)
        pidController.setI(0.001); //error (Glitch?)
        pidController.setD(0); //error (Glitch?)
        pidController.setFF(0.000156); // Feedforward for velocity control //error (Glitch?)
    }

    public void setClawVelocity(double velocity) {
        pidController.setReference(velocity, SparkFlex.ControlType.kVelocity);
    }

    public void stopClaw() {
        clawMotor.set(0);
    }
}
