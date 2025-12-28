package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

// import motor configuration type for PIDF
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp(name = "Tele")
public class Tele extends LinearOpMode {

    private DcMotor BackRight;
    private DcMotor FrontRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;
    private DcMotor intakeMotor;
    private DcMotorEx outtakeMotor;
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

        double flyWheelVelocity = 1200;

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

        outtakeMotor = hardwareMap.get(DcMotorEx.class, "m2");

        MotorConfigurationType motorConfigurationType = outtakeMotor.getMotorType().clone();
        motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
        outtakeMotor.setMotorType(motorConfigurationType);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        PIDFCoefficients flywheelPIDF = new PIDFCoefficients(
                33.77,
                0.0,
                0.0,
                12.81
        );
        outtakeMotor.setPIDFCoefficients(
                DcMotor.RunMode.RUN_USING_ENCODER,
                flywheelPIDF
        );

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

            if (gamepad1.a) {
                flyWheelVelocity = 1200;    // close shot
            } else if (gamepad1.b) {
                flyWheelVelocity = 1500;   // far shot
            }

            // Set MaxSpeed based on mode
            double MaxSpeed;
            if (slowMode) {
                MaxSpeed = 0.3;
            } else {
                MaxSpeed = 0.8;
            }

            MecanumDrive(forward, right, rotate, MaxSpeed);

            // --- OUTTAKE + TRANSPORT LOGIC ---

            // Emergency Reverse
            if (gamepad2.left_bumper) {
                transportPower(-1.0);
                intakePower(0.7);
            }
            else if (gamepad2.left_trigger > 0.1 && gamepad2.right_trigger > 0.1) {
                // BOTH TRIGGERS → shoot AND intake
                rgb.setPosition(0.5); // GREEN

                // Flywheel Running
                outtakeVelocity(flyWheelVelocity);

                // Open Gate to let balls go through
                GateServo.setPosition(GateOpen);
                // Feed balls in
                transportPower(1.0);
                // Intake running to bring next ball up
                intakePower(gamepad2.left_trigger);
            }
            else if (gamepad2.right_trigger > 0.1) {
                rgb.setPosition(0.3); // ORANGE

                // Spin Up Flywheel
                outtakeVelocity(flyWheelVelocity);

                // NO FEEDING unless A or intake pressed
                transportPower(0);
                intakePower(0);
            }
            else if (gamepad2.left_trigger > 0.1) {
                rgb.setPosition(0.611); // BLUE

                //INTAKE
                intakePower(gamepad2.left_trigger);

                // Transport moves ball up
                transportPower(0.8);

                // Close gate
                GateServo.setPosition(GateClose);
            }
            else {
                // NOTHING PRESSED
                outtakeMotor.setPower(0);
                intakePower(0);
                transportPower(0);
            }

            if (gamepad2.b) {
                GateServo.setPosition(GateOpen);
            }
            if (gamepad2.y) {
                GateServo.setPosition(GateClose);
            }

            // Send telemetry messages to explain controls and show robot status
            telemetry.addData("Drive Power", "%.2f", forward);
            telemetry.addData("Strafe Power", "%.2f", right);
            telemetry.addData("Turn Power",  "%.2f", rotate);
            telemetry.addLine();
            telemetry.addData("Intake Power",  "%.2f", intakeMotor.getPower());

            telemetry.addData("Flywheel Target", "%.0f", flyWheelVelocity);
            telemetry.addData("Flywheel Velocity", "%.0f", outtakeMotor.getVelocity());
            telemetry.addData("Flywheel Error",
                    "%.0f", flyWheelVelocity - outtakeMotor.getVelocity());

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

    public void outtakeVelocity(double velocity) {
        if (velocity < 50) {   // small deadband
            outtakeMotor.setPower(0);
        } else {
            outtakeMotor.setVelocity(velocity);
        }
    }
}