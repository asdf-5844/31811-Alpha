package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Auto")
public class BlueAuto extends LinearOpMode {

    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor FrontRight;

    private void goForward(int ticks) {
        BackLeft.setPower(0.3);
        BackRight.setPower(0.3);
        FrontLeft.setPower(0.3);
        FrontRight.setPower(0.3);
        BackRight.setTargetPosition(ticks);
        FrontRight.setTargetPosition(ticks);
        FrontLeft.setTargetPosition(ticks);
        BackLeft.setTargetPosition(ticks);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (isBusy() && opModeIsActive()) {
        }
    }

    private void Turn(int ticks) {
        // 1 inch = 43 ticks
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setTargetPosition(-1 * ticks);
        BackLeft.setTargetPosition(ticks);
        FrontLeft.setTargetPosition(ticks);
        FrontRight.setTargetPosition(-1 * ticks);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setPower(-0.5);
        BackRight.setPower(0.5);
        FrontLeft.setPower(-0.5);
        FrontRight.setPower(0.5);
        while (isBusy() && opModeIsActive()) {
        }
    }

    private void Strafing_left(int ticks) {
        // 1 inch = 50 - 60 ticks
        BackLeft.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setTargetPosition(-1 * ticks);
        FrontLeft.setTargetPosition(ticks);
        BackLeft.setTargetPosition(-1 * ticks);
        BackRight.setTargetPosition(ticks);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setPower(-0.5);
        BackRight.setPower(0.5);
        FrontLeft.setPower(0.5);
        FrontRight.setPower(-0.5);
        while (isBusy() && opModeIsActive()) {

        }
    }

    private void Resetting_4_encoders() {
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private boolean isBusy() {
        if (BackLeft.isBusy()) {
            return true;
        }
        if (BackRight.isBusy()) {
            return true;
        }
        if (FrontLeft.isBusy()) {
            return true;
        }
        if (FrontRight.isBusy()) {
            return true;
        }
        return false;
    }

    /**
     * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue Comment
     * Blocks show where to place Initialization code (runs once, after touching the DS INIT
     * button, and before touching the DS Start arrow), Run code (runs once, after touching
     * Start), and Loop code (runs repeatedly while the OpMode is active, namely not Stopped)
     */
    @Override
    public void runOpMode() {
        BackLeft = hardwareMap.get(DcMotor.class, "BL");
        BackRight = hardwareMap.get(DcMotor.class, "BR");
        FrontLeft = hardwareMap.get(DcMotor.class, "FL");
        FrontRight = hardwareMap.get(DcMotor.class, "FR");

        // 1 inch = 43 ticks
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        while (opModeIsActive()) {
            // Lock in
        }
    }

}