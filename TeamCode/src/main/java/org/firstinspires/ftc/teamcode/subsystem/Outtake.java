package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Outtake {

    private DcMotorEx outtakeMotor;
    private Servo gateServo;
    private Transport transport;
    private Intake intake;
    private static final double GATE_OPEN = 0.7;
    private static final double GATE_CLOSED = 1.0;
    private static final double FEED_TIME = 0.30; // seconds
    private final ElapsedTime fireTimer = new ElapsedTime();
    private boolean firing = false;

    public static final double SHOOT_VELOCITY = 1000;
    public Outtake(HardwareMap hardwareMap) {
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "m2");
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        outtakeMotor.setVelocityPIDFCoefficients(
                33.77,
                0.0,
                0.0,
                12.81
        );
    }

    public void shoot() {
        outtakeMotor.setVelocity(SHOOT_VELOCITY);
    }

    public void setShootVelocity(double velocity) {
        outtakeMotor.setVelocity(velocity);
    }

    public boolean atSpeed() {
        return Math.abs(
                outtakeMotor.getVelocity() - SHOOT_VELOCITY
        ) < 50; // tolerance
    }

    public void stop() {
        outtakeMotor.setPower(0);
    }

    public void fireSequence() {
        gateServo.setPosition(GATE_OPEN);
        transport.move(1.0);
        intake.intake(0.7);

        fireTimer.reset();
        firing = true;
    }

}
