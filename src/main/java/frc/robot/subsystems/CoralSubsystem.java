package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLimitSwitch;

public class CoralSubsystem extends SubsystemBase {

    private SparkLimitSwitch coralSensor;
    private SparkFlex pivotMotor;
    private SparkAbsoluteEncoder throughBoreEncoder;

    private SparkClosedLoopController pivotMotorPID;
    private SparkFlexConfig pivotMotorConfig;

    public enum CoralPivotPositions {
        // increases moving towards the front
        L1(0.637),
        L2(0.8116),
        L3(0.8116),
        L4(0.83),
        Stow(0.58),
        CoralStation(.33),
        MinimumAngle(0),
        MaximumAngle(0);

        private final double value;

        CoralPivotPositions(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

    }

    public CoralSubsystem() {
        pivotMotor = new SparkFlex(20, MotorType.kBrushless);
        pivotMotorConfig = new SparkFlexConfig();
        pivotMotorPID = pivotMotor.getClosedLoopController();
        throughBoreEncoder = pivotMotor.getAbsoluteEncoder();

        pivotMotorConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
                .p(7)
                .i(0)
                .d(3)
                .maxOutput(0.75)
                .minOutput(-0.75);

        
        pivotMotorConfig
                .inverted(true)
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kBrake);
        
        pivotMotor.configure(pivotMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setPivotMotorSpeed(double speed) {
        pivotMotor.set(speed);
    }

    public void stopPivotMotor() {
        pivotMotor.stopMotor();
    }

    public double getPivotPosition() {
        return throughBoreEncoder.getPosition();
    }

    public double getPivotVelocity() {
        return throughBoreEncoder.getVelocity();
    }

    public boolean getCoralSensor(){
        return coralSensor.isPressed();
    }

    public void setPIDPosition(CoralPivotPositions position) {
        pivotMotorPID.setReference(position.getValue(), ControlType.kPosition);
    }

    public boolean pidWithinBounds(CoralPivotPositions position, double positionTolerance, double velocityTolerance) {
        double upperbound = position.getValue() + positionTolerance;
        double lowerbound = position.getValue() - positionTolerance;
        boolean withinPosition = getPivotPosition() <= upperbound && getPivotPosition() >= lowerbound;
        boolean withinVelocity = getPivotVelocity() <= Math.abs(velocityTolerance) && getPivotVelocity() >= -Math.abs(velocityTolerance);
        return (withinPosition && withinVelocity);
    }

    public void resetPIDController(){
        pivotMotor.set(0);
    }
}