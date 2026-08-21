package org.firstinspires.ftc.teamcode.vision;

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
    final double Kp = 0.025;

    // Hardware members
    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack   = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack  = hardwareMap.get(DcMotor.class, "rightBack");

        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                aprilTag);

        telemetry.addData("Status", "Initialized. Waiting for start...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            AprilTagDetection targetTag = null;

            for (AprilTagDetection detection : currentDetections) {
                if (detection.metadata != null) {
                    targetTag = detection;
                    break; // Found one, stop looking
                }
            }

            if (targetTag != null) {
                double error = targetTag.ftcPose.bearing;

                double turnPower = error * Kp;

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