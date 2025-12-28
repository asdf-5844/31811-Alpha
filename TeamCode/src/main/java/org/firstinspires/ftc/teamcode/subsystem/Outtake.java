package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {

    private DcMotorEx outtakeMotor;
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

    public boolean atSpeed() {
        return Math.abs(
                outtakeMotor.getVelocity() - SHOOT_VELOCITY
        ) < 50; // tolerance
    }

    public void stop() {
        outtakeMotor.setPower(0);
    }
}