package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.paths.Path9;
import org.firstinspires.ftc.teamcode.tests.PedroTest;
import org.firstinspires.ftc.teamcode.util.AllianceMirror;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Outtake2;
import org.firstinspires.ftc.teamcode.subsystem.Transport;

@Autonomous(name = "BlueClose9", group = "Pedro")
public class BlueClose9 extends LinearOpMode {

    private Follower follower;
    Path9 paths;

    private enum PathState {
        DRIVE_TO_PRELOAD_SHOT,
        LAUNCH_PRELOADS,
        MOVE1,
        INTAKE1,
        DRIVE_TO_SCORE1,
        LAUNCH1,
        MOVE2,
        INTAKE2,
        DRIVE_TO_SCORE2,
        LAUNCH2,
        PARK,
        DONE
    }


    private PathState state = PathState.SCORE_PRELOAD;

    // Flywheel Setup
    private Outtake2 outtake = new Outtake2();
    private boolean shotsTriggered = false;


    private Intake intake;
    private Transport transport;

    @Override
    public void runOpMode() {

        follower = Constants.createFollower(hardwareMap);
        intake = new Intake(hardwareMap);
        transport = new Transport(hardwareMap);
        intake.stop();
        transport.stop();

        outtake.init(hardwareMap);

        boolean mirror = false;
        // visualizer start pose
        double startX = 21.762;
        double startY = 126.474;
        double startHeading = Math.toRadians(145);

        Pose blueStart = new Pose(startX, startY, startHeading);
        Pose start = AllianceMirror.mirrorPose(blueStart, mirror);

        follower.setStartingPose(start);

        follower.setStartingPose(
                new Pose(21.762, 126.474, Math.toRadians(135))
        );

        paths = new Path9(follower, false);

        waitForStart();

        // Start first path ONCE
        follower.followPath(paths.scorepreload);

        // Loop
        while (opModeIsActive()) {

            follower.update();

            switch (state) {

                case DRIVE_TO_PRELOAD_SHOT:
                     if (!follower.isBusy()) {
                        follower.followPath(paths.move1);
                        state = PathState.LAUNCH_PRELOADS;
                    }
                    break;
                case LAUNCH_PRELOADS:

                case MOVE1:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.intake1);
                        state = PathState.INTAKE1;
                    }
                    break;

                case INTAKE1:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.score1);
                        state = PathState.DRIVE_TO_SCORE1;
                    }
                    break;

                case DRIVE_TO_SCORE1:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.move2);
                        state = PathState.LAUNCH1;
                    }
                    break;

                case LAUNCH1:

                case MOVE2:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.intake2);
                        state = PathState.INTAKE2;
                    }
                    break;

                case INTAKE2:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.move3);
                        state = PathState.DRIVE_TO_SCORE2;
                    }
                    break;

                case DRIVE_TO_SCORE2:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.score2);
                        state = PathState.LAUNCH2;
                    }
                    break;

                case LAUNCH2:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.park);
                        state = PathState.PARK;
                    }
                    break;

                case PARK:
                    if (!follower.isBusy()) {
                        state = PathState.DONE;
                    }
                    break;

                case DONE:
                    // Auto finished
                    break;
            }

            // Feedback to Driver Hub for debugging
            telemetry.addData("path state", state);
            telemetry.addData("x", follower.getPose().getX());
            telemetry.addData("y", follower.getPose().getY());
            telemetry.addData("heading", follower.getPose().getHeading());
            telemetry.update();
        }
    }
}
