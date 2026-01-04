package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.paths.Path9;
import org.firstinspires.ftc.teamcode.util.AllianceMirror;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Outtake;
import org.firstinspires.ftc.teamcode.subsystem.Transport;

@Autonomous(name = "RedClose9", group = "Pedro")
public class RedClose9 extends LinearOpMode {

    private Follower follower;
    Path9 paths;

    private enum PathState {
        SCORE_PRELOAD,
        MOVE1,
        INTAKE1,
        SCORE1,
        MOVE2,
        INTAKE2,
        MOVE3,
        SCORE2,
        PARK,
        DONE
    }

    private PathState state = PathState.SCORE_PRELOAD;
    private Intake intake;
    private Outtake outtake;
    private Transport transport;

    @Override
    public void runOpMode() {

        follower = Constants.createFollower(hardwareMap);
        intake = new Intake(hardwareMap);
        outtake = new Outtake(hardwareMap);
        transport = new Transport(hardwareMap);
        intake.stop();
        transport.stop();
        outtake.stop();

        boolean mirror = true; // cuz red side

        // visualizer start pose
        double startX = 21.762;
        double startY = 126.474;
        double startHeading = Math.toRadians(135);

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

                case SCORE_PRELOAD:
                    outtake.setShootVelocity(1000);
                    if (!follower.isBusy()) {
                        follower.followPath(paths.move1);
                        state = PathState.MOVE1;
                    }
                    break;

                case MOVE1:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.intake1);
                        state = PathState.INTAKE1;
                    }
                    break;

                case INTAKE1:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.score1);
                        state = PathState.SCORE1;
                    }
                    break;

                case SCORE1:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.move2);
                        state = PathState.MOVE2;
                    }
                    break;

                case MOVE2:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.intake2);
                        state = PathState.INTAKE2;
                    }
                    break;

                case INTAKE2:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.move3);
                        state = PathState.MOVE3;
                    }
                    break;

                case MOVE3:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.score2);
                        state = PathState.SCORE2;
                    }
                    break;

                case SCORE2:
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
