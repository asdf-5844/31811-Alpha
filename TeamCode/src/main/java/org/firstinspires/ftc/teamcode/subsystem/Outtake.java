package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {

    private DcMotor outtakeMotor;

    public Outtake(HardwareMap hardwareMap) {
        outtakeMotor = hardwareMap.get(DcMotor.class, "m2");
        outtakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void shoot() {
        outtakeMotor.setPower(0.5); // half speed
    }

    public void stop() {
        outtakeMotor.setPower(0);
    }
}