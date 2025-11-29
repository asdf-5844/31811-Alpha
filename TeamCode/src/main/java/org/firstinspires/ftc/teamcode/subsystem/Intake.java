package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Intake {

    private DcMotor intakeMotor;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "m1");
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void intake(double power) {
        intakeMotor.setPower(power);
    }

    public void outtake_for_intake() {
        intakeMotor.setPower(-1.0);   // full speed reverse
    }

    public void stop() {
        intakeMotor.setPower(0);
    }
}
