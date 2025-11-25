package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.AutoMecanum;

@Autonomous(name = "Blue Auto")
public class BlueAuto extends LinearOpMode {

    @Override
    public void runOpMode() {

        AutoMecanum drive = new AutoMecanum(this, hardwareMap);

        waitForStart();

        if (opModeIsActive()) {
            drive.goForward(1000);
            drive.strafeLeft(800);
            drive.turnRight(600);
        }
    }
}
