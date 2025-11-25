package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import android.graphics.Color;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

@TeleOp(name = "Tele")
public class Tele extends LinearOpMode {
    private DcMotor BackRight;
    private DcMotor FrontRight;
    private DcMotor FrontLeft;
    private DcMotor BackLeft;
    private DcMotor intakeMotor, outtakeMotor;
    private CRServo s0, s1, s2, s3, TopServo;
    private Servo GateServo;
    private NormalizedColorSensor colorSensor;
    final float[] hsvValues = new float[3];

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

        // Define and initialize ALL installed servos.
        s0 = hardwareMap.get(CRServo.class, "s0");
        s1 = hardwareMap.get(CRServo.class, "s1");
        s2 = hardwareMap.get(CRServo.class, "s2");
        s3 = hardwareMap.get(CRServo.class, "s3");
        TopServo = hardwareMap.get(CRServo.class, "TopServo");
        GateServo = hardwareMap.get(Servo.class, "GateServo");

        intakeMotor = hardwareMap.get(DcMotor.class, "m1");
        outtakeMotor = hardwareMap.get(DcMotor.class, "m2");

        // Get a reference to our sensor object. It's recommended to use NormalizedColorSensor over
        // ColorSensor, because NormalizedColorSensor consistently gives values between 0 and 1, while
        // the values you get from ColorSensor are dependent on the specific sensor you're using.
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "i1");

        telemetry.addData(">", "Hardware Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            // Variables that continuously change
            double forward, right, rotate, intakeP;

            boolean ballDetected = false;

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

            // Set MaxSpeed based on mode
            double MaxSpeed;
            if (slowMode) {
                MaxSpeed = 0.3;
            } else {
                MaxSpeed = 0.6;
            }

            MecanumDrive(forward, right, rotate, MaxSpeed);

            // Intake, Outtake, and Transport

            // set intake motor based on left trigger
            intakeP = gamepad2.left_trigger;
            intakePower(intakeP);

            // --- OUTTAKE + TRANSPORT LOGIC ---
            // PRIORITY 1 — REVERSE EVERYTHING
            if (gamepad2.left_bumper) {
                outtakePower(-0.6);
                transportPower(-1.0);
                intakePower(0.7);
            }
            else if (gamepad2.left_trigger > 0.1 && gamepad2.right_trigger > 0.1) {
                // BOTH TRIGGERS → shoot AND intake
                // Flywheel ON
                outtakePower(0.5);

                // Feed balls in
                transportPower(1.0);

                // Intake running to bring next ball up
                intakePower(gamepad2.left_trigger);

                // Open Gate to let balls go through
                GateServo.setPosition(0.5);
            }
            else if (gamepad2.right_trigger > 0.1) {
                // ONLY FLYWHEEL SPIN-UP
                outtakePower(0.5);

                // NO FEEDING unless A or intake pressed
                transportPower(0);
                intakePower(0);
            }
            else if (gamepad2.left_trigger > 0.1) {
                // ONLY INTAKE
                intakePower(gamepad2.left_trigger);
                // Transport moves ball up
                transportPower(0.4);
                // outtakePower(-0.1);
            }
            else if (gamepad2.a) {
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

            // Prevents balls from entering the shooter
            if (gamepad2.b) {
                GateServo.setPosition(-1.0); // close
            } else if (gamepad2.y) {
                GateServo.setPosition(0.5); // open
            }

            // Color Sensor for Presence of Ball Close to the Shooting Flywheel
            String ballColor = getColorSensor();
            if (ballColor.equals("PURPLE") || ballColor.equals("GREEN")) {
                ballDetected = true;
            } else {
                ballDetected = false;
            }


            // Send telemetry messages to explain controls and show robot status
            telemetry.addData("Drive Power", "%.2f", forward);
            telemetry.addData("Strafe Power", "%.2f", right);
            telemetry.addData("Turn Power",  "%.2f", rotate);
            telemetry.addLine();
            telemetry.addData("Intake Trigger",  "%.2f", intakeP);
            telemetry.addData("Transport Power (s0)", "%.2f", s0.getPower());
            telemetry.addData("Outtake Motor Power", "%.2f", outtakeMotor.getPower());
            telemetry.addData("Ball Detected", ballDetected);
            telemetry.addData("Color Sensor", ballColor);
            telemetry.update();

            // Pace this loop so hands move at a reasonable speed.
            sleep(50);
        }
    }
    public void MecanumDrive(double forward, double strafe, double rotate, double MaxSpeed) {

        // the denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(rotate), 1);

        double frontLeftPower = (forward + strafe + rotate) / denominator;
        double frontRightPower = (forward - strafe - rotate) / denominator;
        double backLeftPower = (forward - strafe + rotate) / denominator;
        double backRightPower = (forward + strafe - rotate) / denominator;

        // Apply MaxSpeed scaling (adjust for outreach / testing)
        FrontLeft.setPower(frontLeftPower * MaxSpeed);
        FrontRight.setPower(frontRightPower * MaxSpeed);
        BackLeft.setPower(backLeftPower * MaxSpeed);
        BackRight.setPower(backRightPower * MaxSpeed);
    }


    public void intakePower(double mPower) {
        intakeMotor.setPower(-mPower);
    }

    public void transportPower(double mPower) {
        s0.setPower(mPower);
        s1.setPower(mPower);
        s2.setPower(-mPower);
        s3.setPower(-mPower);
        TopServo.setPower(mPower);
    }

    public void outtakePower(double mPower) {
        outtakeMotor.setPower(mPower);
    }

    public String getColorSensor() {

        // Get the normalized colors from the sensor
        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        // Update the hsvValues array by passing it to Color.colorToHSV()
        Color.colorToHSV(colors.toColor(), hsvValues);

        int hue = (int) hsvValues[0];
        if (hue > 220 && hue < 320) {
            return "PURPLE";
        } else if (hue > 70 && hue < 170) {
            return "GREEN";
        }

        return "Unknown";
    }


}