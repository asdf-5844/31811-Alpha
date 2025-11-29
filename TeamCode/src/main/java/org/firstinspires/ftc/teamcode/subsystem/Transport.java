package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Transport {

    // Motors or CRServos used to push balls up
    private CRServo s0, s1, s2, s3;
    private CRServo topServo; // optional top shooter/transport wheel

    public Transport(HardwareMap hardwareMap) {
        s0 = hardwareMap.get(CRServo.class, "s0");
        s1 = hardwareMap.get(CRServo.class, "s1");
        s2 = hardwareMap.get(CRServo.class, "s2");
        s3 = hardwareMap.get(CRServo.class, "s3");
        topServo = hardwareMap.get(CRServo.class, "TopServo");
    }

    /**
     * Powers the transport servos to move balls up.
     * Positive power moves balls forward (upwards), negative reverses.
     */
    public void move(double power) {
        s0.setPower(power);
        s1.setPower(power);
        s2.setPower(-power);  // invert to match orientation
        s3.setPower(-power);  // invert to match orientation
        topServo.setPower(power);
    }

    /**
     * Stops the transport.
     */
    public void stop() {
        s0.setPower(0);
        s1.setPower(0);
        s2.setPower(0);
        s3.setPower(0);
        topServo.setPower(0);
    }
}