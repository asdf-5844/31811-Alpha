package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.AutoMecanum;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Transport;
import org.firstinspires.ftc.teamcode.subsystem.Outtake;

@Autonomous(name = "Red Auto")
public class RedAuto extends LinearOpMode {
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
            drive.goForward(1000);
            drive.strafeLeft(-800);
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
