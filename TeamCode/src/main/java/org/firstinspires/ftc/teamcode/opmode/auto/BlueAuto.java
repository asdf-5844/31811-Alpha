package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.AutoMecanum;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Transport;
import org.firstinspires.ftc.teamcode.subsystem.Outtake;

@Autonomous(name = "Blue Auto")
public class BlueAuto extends LinearOpMode {
    private Servo GateServo;
    private final double GateClose = 0.7;
    private final double GateOpen = 1.0;

    @Override
    public void runOpMode() {

        AutoMecanum drive = new AutoMecanum(this, hardwareMap);
        Transport transport = new Transport(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Outtake outtake = new Outtake(hardwareMap);

        // Initialize Gate Servo
        GateServo = hardwareMap.get(Servo.class, "GateServo");

        waitForStart();

        if (opModeIsActive()) {
            GateServo.setPosition(GateClose);
            drive.goForward(-500);

            // Start flywheel
            outtake.shoot();
            sleep(2000);

            // Feed balls
            GateServo.setPosition(GateOpen); // open gate to feed
            transport.move(1.0);
            intake.intake(0.7);
            sleep(4000); // shoot for 4 seconds

            // Stop
            outtake.stop();
            intake.stop();
            transport.stop();
            GateServo.setPosition(GateClose); // close gate after shooting

            drive.turnLeft(450);
            drive.goForward(-400);
            drive.strafeLeft(1400);
            intake.intake(1.0);
            transport.move(0.4);
            sleep(50);
            drive.moveTo(1500, 1400, 1400, 1400, 0.4, 2.8); // Custom speed
            intake.stop();
            transport.stop();
            drive.goForward(-1000);
            drive.strafeLeft(-1300);
            drive.turnLeft(-450);
            drive.goForward(300);

            outtake.shoot();
            sleep(2500);

            GateServo.setPosition(GateOpen);
            transport.move(1.0);
            intake.intake(0.7);
            sleep(4000); // shoot for 4 seconds

            // Stop everything
            outtake.stop();
            transport.stop();
            GateServo.setPosition(GateClose); // close gate after shooting

            // Get out of launch zone
            drive.goForward(-1000);
            drive.strafeLeft(1000);
        }
    }

}