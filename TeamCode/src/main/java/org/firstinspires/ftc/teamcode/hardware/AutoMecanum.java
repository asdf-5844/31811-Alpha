package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AutoMecanum {

    private LinearOpMode opMode;

    public DcMotor BackLeft;
    public DcMotor BackRight;
    public DcMotor FrontLeft;
    public DcMotor FrontRight;

    public AutoMecanum(LinearOpMode opMode, HardwareMap hardwareMap) {
        this.opMode = opMode;

        BackLeft = hardwareMap.get(DcMotor.class, "BL");
        BackRight = hardwareMap.get(DcMotor.class, "BR");
        FrontLeft = hardwareMap.get(DcMotor.class, "FL");
        FrontRight = hardwareMap.get(DcMotor.class, "FR");

        // Set motor directions
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);

        // Reset encoders ONCE
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Brake when stopping
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void moveTo(int bl, int br, int fl, int fr, double power, double timeoutSeconds) {

        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        BackLeft.setTargetPosition(bl);
        BackRight.setTargetPosition(br);
        FrontLeft.setTargetPosition(fl);
        FrontRight.setTargetPosition(fr);

        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        BackLeft.setPower(power);
        BackRight.setPower(power);
        FrontLeft.setPower(power);
        FrontRight.setPower(power);

        double start = opMode.getRuntime();

        while (opMode.opModeIsActive() &&
                (opMode.getRuntime() - start < timeoutSeconds) &&
                (BackLeft.isBusy() || BackRight.isBusy() ||
                        FrontLeft.isBusy() || FrontRight.isBusy())) {
            opMode.idle();
        }

        BackLeft.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
    }

    public void goForward(int ticks) {
        moveTo(ticks, ticks, ticks, ticks, 0.8, 1.0);
    }

    public void turnLeft(int ticks) {
        moveTo(-ticks, ticks, -ticks, ticks, 0.8, 1.0);
    }

    public void strafeLeft(int ticks) {
        moveTo(ticks, -ticks, -ticks, ticks, 0.8, 1.2);
    }

}