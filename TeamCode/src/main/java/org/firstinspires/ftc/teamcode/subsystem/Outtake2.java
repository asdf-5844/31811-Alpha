package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Outtake2 {

    private DcMotorEx outtakeMotor;
    private Servo gateServo;
    private Transport transport;
    private Intake intake;
    private final ElapsedTime stateTimer = new ElapsedTime();
    private boolean firing = false;

    private enum FlywheelState {
        IDLE,
        SPIN_UP,
        LAUNCH,
        RESET_GATE
    }
    private FlywheelState flywheelState;

    // Gate Logic
    private static final double GATE_OPEN = 0.7;
    private static final double GATE_CLOSED = 1.0;
    private double GATE_OPEN_TIME = 0.25;
    private double GATE_CLOSE_TIME = 0.25;

    private static final double FEED_TIME = 0.30; // seconds

    // Flywheel Constants
    private int shotsRemaining = 0;
    private double flywheelVelocity = 0;
    private double TARGET_SHOOT_VELOCITY = 1000;
    private double FLYWHEEL_MAX_SPINUP_TIME = 1;

    public void init(HardwareMap hardwareMap) {
        gateServo = hardwareMap.get(Servo.class, "GateServo");
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "m2");
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        outtakeMotor.setVelocityPIDFCoefficients(
                33.77,
                0.0,
                0.0,
                12.81
        );

        flywheelState = FlywheelState.IDLE;

        gateServo.setPosition(GATE_CLOSED);
        outtakeMotor.setPower(0);

    }

    public void update() {
        switch (flywheelState){
            case IDLE:
                if (shotsRemaining > 0) {
                    gateServo.setPosition(GATE_CLOSED);
                    outtakeMotor.setVelocity(TARGET_SHOOT_VELOCITY);

                    stateTimer.reset();
                    flywheelState = FlywheelState.SPIN_UP;
                }
                break;
            case SPIN_UP:
                if (atSpeed() || stateTimer.seconds() > FLYWHEEL_MAX_SPINUP_TIME) {
                    gateServo.setPosition(GATE_OPEN);
                    stateTimer.reset();

                    flywheelState = FlywheelState.LAUNCH;
                }
                break;
            case LAUNCH:
                if (stateTimer.seconds() > GATE_OPEN_TIME) {
                    shotsRemaining--; // increment by -1
                    gateServo.setPosition(GATE_CLOSED);
                    stateTimer.reset();

                    flywheelState = FlywheelState.RESET_GATE;
                }

            case RESET_GATE:
                if (stateTimer.seconds() > GATE_CLOSE_TIME) {
                    if (shotsRemaining > 0) {
                        stateTimer.reset();
                        flywheelState = FlywheelState.SPIN_UP;
                    }
                    else {
                        outtakeMotor.setPower(0);
                        flywheelState = FlywheelState.IDLE;
                    }
                }
                break;
        }
    }

    // Helper safety function
    public void fireShots(int numberOfShots) {
        if (flywheelState == FlywheelState.IDLE) {
            shotsRemaining = numberOfShots;
        }
    }

    public boolean isBusy() {
        return flywheelState != FlywheelState.IDLE;
    }

    public boolean atSpeed() {
        return Math.abs(
                outtakeMotor.getVelocity() - TARGET_SHOOT_VELOCITY
        ) < 50; // tolerance
    }

    public void stop() {
        outtakeMotor.setPower(0);
    }
}
