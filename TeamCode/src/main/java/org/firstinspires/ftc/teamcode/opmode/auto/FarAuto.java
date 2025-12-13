package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Decode2025.auto.AutoMecanum;

@Autonomous(name = "FarAuto")
public class FarAuto extends LinearOpMode {

    private AutoMecanum drive;

    @Override
    public void runOpMode() {

        drive = new AutoMecanum(this, hardwareMap);

        waitForStart();

        if (opModeIsActive()) {
            drive.goForward(800);
        }
    }

}
