package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

@Disabled
@TeleOp(name = "SoloDrive")
public class SoloDrive extends LinearOpMode {

    private DcMotor BackRight;
    private DcMotor FrontRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;
    private DcMotor intakeMotor, outtakeMotor;
    private CRServo s0, s1, s2, s3, TopServo;
    private Servo GateServo;
    private Servo rgb;

    @Override
    public void runOpMode() {
        // Code to run ONCE when the driver hits INIT

        // Drive Motors
        BackRight = hardwareMap.get(DcMotor.class, "BR");
        FrontRight = hardwareMap.get(DcMotor.class, "FR");
        FrontLeft = hardwareMap.get(DcMotor.class, "FL");
        BackLeft = hardwareMap.get(DcMotor.class, "BL");
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        boolean slowMode = false;
        boolean xWasPressed = false;

        double flyWheelPower = 0.5;
        boolean aWasPressed = false;

        double GateOpen = 1.0;
        double GateClose = 0.7;

        // RGB indicator light
        rgb = hardwareMap.get(Servo.class, "rgb");

        // Define and initialize ALL installed servos.
        s0 = hardwareMap.get(CRServo.class, "s0");
        s1 = hardwareMap.get(CRServo.class, "s1");
        s2 = hardwareMap.get(CRServo.class, "s2");
        s3 = hardwareMap.get(CRServo.class, "s3");
        TopServo = hardwareMap.get(CRServo.class, "TopServo");
        GateServo = hardwareMap.get(Servo.class, "GateServo");

        intakeMotor = hardwareMap.get(DcMotor.class, "m1");
        outtakeMotor = hardwareMap.get(DcMotor.class, "m2");

        telemetry.addData(">", "Hardware Initialized");
        telemetry.update();


        waitForStart();
        while (opModeIsActive()) {
            // Variables that continuously change
            double forward, right, rotate;


            forward = -gamepad1.left_stick_y;
            right = gamepad1.left_stick_x;
            rotate  =  gamepad1.right_stick_x;

            // Toggle slow mode when X is pressed
            if (gamepad1.x && !xWasPressed) {
                slowMode = !slowMode; // flip the mode
                xWasPressed = true;   // mark that X was pressed
            } else if (!gamepad1.x) {
                xWasPressed = false;  // reset when button released
            }

            if (gamepad1.a && !aWasPressed) {
                flyWheelPower = 0.7;
            } else if (!gamepad1.a) {
                flyWheelPower = 0.5;
            }

            if (gamepad1.b) {
                flyWheelPower = 0.8;
            }
            // Set MaxSpeed based on mode
            double MaxSpeed;
            if (slowMode) {
                MaxSpeed = 0.3;
            } else {
                MaxSpeed = 1.0;
            }

            MecanumDrive(forward, right, rotate, MaxSpeed);

            // --- OUTTAKE + TRANSPORT LOGIC ---
            // PRIORITY 1 — REVERSE EVERYTHING
            if (gamepad1.left_bumper) {
                outtakePower(-0.6);
                transportPower(-1.0);
                intakePower(0.7);
            }
            else if (gamepad1.left_trigger > 0.1 && gamepad1.right_trigger > 0.1) {
                // BOTH TRIGGERS → shoot AND intake
                rgb.setPosition(0.5); // GREEN

                // Flywheel ON
                outtakePower(0.5);
                // Open Gate to let balls go through
                GateServo.setPosition(GateOpen);
                // Feed balls in
                transportPower(0.3);
                // Intake running to bring next ball up
                intakePower(gamepad1.left_trigger);
            }
            else if (gamepad1.right_trigger > 0.1) {
                rgb.setPosition(0.3); // ORANGE

                // ONLY FLYWHEEL SPIN-UP
                outtakePower(flyWheelPower);

                // NO FEEDING unless A or intake pressed
                transportPower(0);
                intakePower(0);
            }
            else if (gamepad1.left_trigger > 0.1) {
                rgb.setPosition(0.611); // BLUE

                //INTAKE
                intakePower(gamepad1.left_trigger);

                // Transport moves ball up
                transportPower(0.3);

                // Close gate
                GateServo.setPosition(GateClose);
            }
            else if (gamepad1.a) {
                // MANUAL FEED
                transportPower(1.0);
                // If flywheel is spinning, keep power
                if (outtakeMotor.getPower() > 0.1) {
                    outtakePower(outtakeMotor.getPower());
                }
            }
            else {
                // NOTHING PRESSED
                intakePower(0);
                transportPower(0);
                outtakePower(0);
            }

            if (gamepad1.b) {
                GateServo.setPosition(GateOpen);
            }
            if (gamepad1.y) {
                GateServo.setPosition(GateClose);
            }

            // Send telemetry messages to explain controls and show robot status
            telemetry.addData("Drive Power", "%.2f", forward);
            telemetry.addData("Strafe Power", "%.2f", right);
            telemetry.addData("Turn Power",  "%.2f", rotate);
            telemetry.addLine();
            telemetry.addData("Intake Power",  "%.2f", intakeMotor.getPower());
            telemetry.addData("Outtake Motor Power", "%.2f", outtakeMotor.getPower());
            telemetry.addLine();
            telemetry.addData("LY", gamepad1.left_stick_y);
            telemetry.addData("LX", gamepad1.left_stick_x);
            telemetry.addData("RX", gamepad1.right_stick_x);
            telemetry.update();
        }
    }

    public void MecanumDrive(double forward, double strafe, double rotate, double MaxSpeed) {

        // the denominator is the largest motor power (absolute value) or 1
        double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(rotate), 1);

        double frontLeftPower = (forward + strafe + rotate) / denominator;
        double frontRightPower = (forward - strafe - rotate) / denominator;
        double backLeftPower = (forward - strafe + rotate) / denominator;
        double backRightPower = (forward + strafe - rotate) / denominator;

        // Apply MaxSpeed scaling
        FrontLeft.setPower(frontLeftPower * MaxSpeed);
        FrontRight.setPower(frontRightPower * MaxSpeed);
        BackLeft.setPower(backLeftPower * MaxSpeed);
        BackRight.setPower(backRightPower * MaxSpeed);
    }


    public void intakePower(double mPower) {
        // you previously inverted this; keep that if intake motor is reversed on robot
        intakeMotor.setPower(-mPower);
    }

    public void transportPower(double mPower) {
        // s2 & s3 are inverted to match physical orientation
        s0.setPower(mPower);
        s1.setPower(mPower);
        s2.setPower(-mPower);
        s3.setPower(-mPower);
        TopServo.setPower(mPower);
    }

    public void outtakePower(double mPower) {
        outtakeMotor.setPower(mPower);
    }
}