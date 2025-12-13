package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.AutoMecanum;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Transport;
import org.firstinspires.ftc.teamcode.subsystem.Outtake;

@Autonomous(name = "BlueClose")
public class BlueAuto extends LinearOpMode {
    private Servo GateServo;
    private final double GateClose = 0.7;
    private final double GateOpen = 1.0;
    private AutoMecanum drive;
    private Transport transport;
    private Intake intake;
    private Outtake outtake;

    @Override
    public void runOpMode() {

        drive = new AutoMecanum(this, hardwareMap);
        transport = new Transport(hardwareMap);
        intake = new Intake(hardwareMap);
        outtake = new Outtake(hardwareMap);

        // Initialize Gate Servo
        GateServo = hardwareMap.get(Servo.class, "GateServo");

        waitForStart();

        if (opModeIsActive()) {
            GateServo.setPosition(GateClose);
            drive.goForward(-700);

            // First shooting cycle, 2 sec spinup
            shootSequence(2000, 4000);

            drive.turnLeft(450);
            drive.goForward(-400);
            drive.strafeLeft(1400);

            // Start Intake and Transport
            intakeSequence(1.0, 0.4, 50);

            drive.moveTo(1500, 1400, 1400, 1400, 0.4, 2.8); // Custom speed
            intake.stop();
            transport.stop();

            drive.goForward(-1000);
            drive.strafeLeft(-1300);
            drive.turnLeft(-450);
            drive.goForward(200);

            // Second shooting cycle, 2.5 sec spinup
            shootSequence(2500, 4000);

            // Get out of launch zone
            drive.goForward(-1000);
            drive.strafeLeft(1000);
        }
    }

    private void shootSequence(long spinUpTime, long feedTime) {
        outtake.shoot();
        sleep(spinUpTime);

        GateServo.setPosition(GateOpen);
        transport.move(1.0);
        intake.intake(0.7);

        sleep(feedTime);

        outtake.stop();
        transport.stop();
        intake.stop();
        GateServo.setPosition(GateClose);
    }

    private void intakeSequence(double intakePower, double transportPower, long duration) {
        intake.intake(intakePower);
        transport.move(transportPower);
        sleep(duration);
    }
}
