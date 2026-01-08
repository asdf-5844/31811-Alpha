package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Outtake3 {

    private DcMotorEx flywheel;
    private Servo gate;
    private Transport transport;
    private Intake intake;

    // Timer
    private final ElapsedTime timer = new ElapsedTime();

    // Tunables
    private static final double GATE_OPEN = 1.0;
    private static final double GATE_CLOSED = 0.7;

    private double TARGET_VELOCITY = 1000;     // tune
    private double SPEED_TOLERANCE = 50;
    private double SPINUP_TIMEOUT_S  = 1.0;       // 1 second spinup

    private double TRANSPORT_POWER  = 1.0; // tune
    private double INTAKE_POWER  = 0.8;    // tune

    // How long it takes to push ONE ball through when feeding (tune!)
    private static final double SECONDS_PER_BALL = 0.6;


    private enum ShootState {
        IDLE,
        SPINNING_UP,
        OPENING_GATE,
        FEEDING,
        FINISHING
    }
    private ShootState state = ShootState.IDLE;

    private int ballsToShoot = 0;
    private double feedDuration;

    public void init(HardwareMap hardwareMap) {
        gate = hardwareMap.get(Servo.class, "GateServo");
        flywheel = hardwareMap.get(DcMotorEx.class, "m2");

        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        flywheel.setVelocityPIDFCoefficients(
                33.77, 0.0, 0.0, 12.81
        );

        // Subsystems so we can feed balls
        transport = new Transport(hardwareMap);
        intake = new Intake(hardwareMap);

        stopAll();
        gate.setPosition(GATE_CLOSED);
        state = ShootState.IDLE;
    }

    public void update() {
        switch (state) {
            case IDLE:
                // do nothing
                break;

            case SPINNING_UP:
                // Start feeding when at speed or timeout expires
                if (atSpeed() || timer.seconds() > SPINUP_TIMEOUT_S) {
                    gate.setPosition(GATE_OPEN);
                    timer.reset();
                    state = ShootState.OPENING_GATE;
                }
                break;

            case OPENING_GATE:
                // Start feeding
                transport.move(TRANSPORT_POWER);
                intake.intake(INTAKE_POWER);

                timer.reset();
                state = ShootState.FEEDING;
                break;

            case FEEDING:
                if (timer.seconds() >= feedDuration) {
                    // Stop feeding, close gate, stop flywheel
                    transport.stop();
                    intake.stop();
                    gate.setPosition(GATE_CLOSED);
                    flywheel.setPower(0);

                    timer.reset();
                    state = ShootState.FINISHING;
                }
                break;

            case FINISHING:
                // One loop delay is fine; then we’re done
                ballsToShoot = 0;
                state = ShootState.IDLE;
                break;
        }
    }


    /** Start a launch of N balls. Only works if currently idle. */
    public void startLaunch(int balls) {
        if (state != ShootState.IDLE) return;
        if (balls <= 0) return;

        ballsToShoot = balls;

        // Compute feed time
        feedDuration = ballsToShoot * SECONDS_PER_BALL;

        // Prep + spin up
        gate.setPosition(GATE_CLOSED);
        transport.stop();
        intake.stop();

        flywheel.setVelocity(TARGET_VELOCITY);

        timer.reset();
        state = ShootState.SPINNING_UP;
    }

    public boolean isBusy() {
        return state != ShootState.IDLE;
    }

    private boolean atSpeed() {
        return Math.abs(flywheel.getVelocity() - TARGET_VELOCITY) <= SPEED_TOLERANCE;
    }

    public void stopAll() {
        if (transport != null) transport.stop();
        if (intake != null) intake.stop();
        if (flywheel != null) flywheel.setPower(0);
        if (gate != null) gate.setPosition(GATE_CLOSED);

        ballsToShoot = 0;
        state = ShootState.IDLE;
    }
}
