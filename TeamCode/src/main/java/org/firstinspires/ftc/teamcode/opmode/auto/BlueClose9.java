package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.paths.BluePath9;

@Autonomous(name = "BlueClose9", group = "Auto")
public class BlueClose9 extends LinearOpMode {

    private Follower follower;
    private Paths paths;

    private enum AutoState {
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

    private AutoState state = AutoState.SCORE_PRELOAD;

    @Override
    public void runOpMode() {

        follower = new Follower(hardwareMap);

        // MUST match your visualizer start pose
        follower.setStartingPose(
                new Pose(21.762, 126.474, Math.toRadians(135))
        );

        paths = new Paths(follower);

        waitForStart();

        // Start first path ONCE
        follower.followPath(paths.scorepreload);

        // Loop
        while (opModeIsActive()) {

            follower.update();

            switch (state) {

                case SCORE_PRELOAD:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.move1);
                        state = AutoState.MOVE1;
                    }
                    break;

                case MOVE1:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.intake1);
                        state = AutoState.INTAKE1;
                    }
                    break;

                case INTAKE1:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.score1);
                        state = AutoState.SCORE1;
                    }
                    break;

                case SCORE1:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.move2);
                        state = AutoState.MOVE2;
                    }
                    break;

                case MOVE2:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.intake2);
                        state = AutoState.INTAKE2;
                    }
                    break;

                case INTAKE2:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.move3);
                        state = AutoState.MOVE3;
                    }
                    break;

                case MOVE3:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.score2);
                        state = AutoState.SCORE2;
                    }
                    break;

                case SCORE2:
                    if (!follower.isBusy()) {
                        follower.followPath(paths.park);
                        state = AutoState.PARK;
                    }
                    break;

                case PARK:
                    if (!follower.isBusy()) {
                        state = AutoState.DONE;
                    }
                    break;

                case DONE:
                    // Auto finished
                    break;
            }
        }
    }
}
