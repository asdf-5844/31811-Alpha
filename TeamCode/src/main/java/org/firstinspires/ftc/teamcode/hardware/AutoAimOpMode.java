package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;

@TeleOp(name="Auto Aim to Tag")
public class AutoAimOpMode extends LinearOpMode {

    // Adjust Kp to change how fast the robot turns.
    // Start small (0.01 - 0.03) to avoid violent shaking.
    final double Kp = 0.025;

    // Hardware members
    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        // 1. Initialize Drive Motors
        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack   = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack  = hardwareMap.get(DcMotor.class, "rightBack");

        // Reverse the right side so power 1.0 moves both sides forward
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        // 2. Initialize AprilTag Processor
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // 3. Initialize Vision Portal (Fixes the previous line 32 error)
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                aprilTag);

        telemetry.addData("Status", "Initialized. Waiting for start...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            AprilTagDetection targetTag = null;

            // Search for a specific Tag ID (e.g., ID 7 for a goal)
            for (AprilTagDetection detection : currentDetections) {
                if (detection.metadata != null) {
                    targetTag = detection;
                    break; // Found one, stop looking
                }
            }

            if (targetTag != null) {
                // 'bearing' is the angle error to the tag (left/right)
                double error = targetTag.ftcPose.bearing;

                // Calculate turning power based on error
                double turnPower = error * Kp;

                // Rotate the robot to center the tag
                moveRobot(0, 0, turnPower);

                telemetry.addData("Target", "ID %d", targetTag.id);
                telemetry.addData("Bearing", "%.2f degrees", error);
            } else {
                // No tag in sight? Don't move.
                moveRobot(0, 0, 0);
                telemetry.addData("Status", "Searching for AprilTag...");
            }

            telemetry.update();
        }

        // Clean up vision resources
        visionPortal.close();
    }

    /**
     * Helper to set power to mecanum motors
     * @param x  Side-to-side (Strafing)
     * @param y  Forward/Backward
     * @param rx Rotation
     */
    public void moveRobot(double x, double y, double rx) {
        leftFront.setPower(y + x + rx);
        leftBack.setPower(y - x + rx);
        rightFront.setPower(y - x - rx);
        rightBack.setPower(y + x - rx);
    }
}