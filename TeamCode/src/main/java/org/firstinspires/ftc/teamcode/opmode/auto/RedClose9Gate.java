package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.paths.Path9Gate;
import org.firstinspires.ftc.teamcode.util.AllianceMirror;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Transport;
import org.firstinspires.ftc.teamcode.subsystem.Outtake3;

@Autonomous(name = "RedClose9Gate", group = "Pedro")
public class RedClose9Gate extends LinearOpMode {

    private Follower follower;
    private Path9Gate paths;

    private enum PathState {
        DRIVE_TO_PRELOAD_SHOT,
        LAUNCH_PRELOADS,
        MOVE1,
        INTAKE1,
        OPENGATE,
        DRIVE_TO_SCORE1,
        LAUNCH1,
        MOVE2,
        INTAKE2,
        DRIVE_TO_SCORE2,
        LAUNCH2,
        PARK,
        DONE
    }

    private void setState(PathState newState) {
        state = newState;
        shotsTriggered = false; // allows each LAUNCH state to trigger once
        if (newState != PathState.OPENGATE) {
            spillTimerStarted = false;
        }
    }

    private PathState state = PathState.DRIVE_TO_PRELOAD_SHOT;

    // Speeds
    private static final double TRAVEL_POWER = 0.80;  // normal driving
    private static final double INTAKE_POWER = 0.45;  // slow for picking up balls
    private static final double GATE_POWER = 0.70;  // gate power


    // Flywheel / Intake
    private final Outtake3 outtake = new Outtake3();
    private boolean shotsTriggered = false;
    private Intake intake;
    private Transport transport;

    // Gate Open
    private final ElapsedTime gateTimer = new ElapsedTime();
    private boolean spillTimerStarted = false;

    private void startIntaking() {
        intake.intake(1.0);
        transport.move(0.7);
    }

    private void stopIntaking() {
        intake.stop();
        transport.stop();
    }

    @Override
    public void runOpMode() {

        follower = Constants.createFollower(hardwareMap);
        follower.setMaxPower(TRAVEL_POWER);

        intake = new Intake(hardwareMap);
        transport = new Transport(hardwareMap);
        stopIntaking();

        outtake.init(hardwareMap);

        boolean mirror = true; // Red

        // blue start pose
        double startX = 23.224;
        double startY = 125.196;
        double startHeading = Math.toRadians(145);

        Pose blueStart = new Pose(startX, startY, startHeading);
        Pose start = AllianceMirror.mirrorPose(blueStart, mirror); // Mirror blue start

        follower.setStartingPose(start);

        paths = new Path9Gate(follower, mirror);

        waitForStart();

        // Start first path ONLY ONCE
        follower.setMaxPower(TRAVEL_POWER);
        follower.followPath(paths.scorepreload);

        // finite state machine loop
        while (opModeIsActive()) {

            follower.update();
            outtake.update();

            switch (state) {
                case DRIVE_TO_PRELOAD_SHOT:
                    if (!follower.isBusy()) {
                        setState(PathState.LAUNCH_PRELOADS);
                    }
                    break;

                case LAUNCH_PRELOADS:
                    if (follower.isBusy()) break; // wait until path finished

                    if (!shotsTriggered) {
                        outtake.startLaunch(3);
                        shotsTriggered = true;
                    } else if (!outtake.isBusy()) {
                        follower.setMaxPower(TRAVEL_POWER);
                        follower.followPath(paths.move1);
                        setState(PathState.MOVE1);
                    }
                    break;

                case MOVE1:
                    if (!follower.isBusy()) {
                        startIntaking();
                        follower.setMaxPower(INTAKE_POWER);
                        follower.followPath(paths.intake1);
                        setState(PathState.INTAKE1);
                    }
                    break;

                case INTAKE1:
                    if (!follower.isBusy()) {
                        stopIntaking();

                        // gate-opening motion/path
                        follower.setMaxPower(GATE_POWER);
                        follower.followPath(paths.opengate);
                        setState(PathState.OPENGATE);
                    }
                    break;

                case OPENGATE:
                    if (!follower.isBusy()) {
                        // Start timer once when opengate path finishes
                        if (!spillTimerStarted) {
                            gateTimer.reset();
                            spillTimerStarted = true;
                        }

                        // After 2 seconds, go to score1
                        if (gateTimer.seconds() >= 2.5) {
                            follower.setMaxPower(TRAVEL_POWER);
                            follower.followPath(paths.score1);
                            setState(PathState.DRIVE_TO_SCORE1);
                        }
                    }
                    break;

                case DRIVE_TO_SCORE1:
                    if (!follower.isBusy()) {
                        setState(PathState.LAUNCH1);
                    }
                    break;

                case LAUNCH1:
                    if (follower.isBusy()) break; // wait until score1 finished

                    if (!shotsTriggered) {
                        outtake.startLaunch(3);
                        shotsTriggered = true;
                    } else if (!outtake.isBusy()) {
                        follower.setMaxPower(TRAVEL_POWER);
                        follower.followPath(paths.move2);
                        setState(PathState.MOVE2);
                    }
                    break;

                case MOVE2:
                    if (!follower.isBusy()) {
                        startIntaking();
                        follower.setMaxPower(INTAKE_POWER);
                        follower.followPath(paths.intake2);
                        setState(PathState.INTAKE2);
                    }
                    break;

                case INTAKE2:
                    if (!follower.isBusy()) {
                        stopIntaking();
                        follower.setMaxPower(TRAVEL_POWER);
                        follower.followPath(paths.score2);
                        setState(PathState.DRIVE_TO_SCORE2);
                    }
                    break;

                case DRIVE_TO_SCORE2:
                    if (!follower.isBusy()) {
                        setState(PathState.LAUNCH2);
                    }
                    break;

                case LAUNCH2:
                    if (follower.isBusy()) break; // wait until score2 finished

                    if (!shotsTriggered) {
                        outtake.startLaunch(3);
                        shotsTriggered = true;
                    } else if (!outtake.isBusy()) {
                        follower.setMaxPower(TRAVEL_POWER);
                        follower.followPath(paths.park);
                        setState(PathState.PARK);
                    }
                    break;

                case PARK:
                    if (!follower.isBusy()) {
                        setState(PathState.DONE);
                    }
                    break;

                case DONE:
                    // Auto finished
                    break;
            }

            telemetry.addData("path state", state);
            telemetry.addData("x", follower.getPose().getX());
            telemetry.addData("y", follower.getPose().getY());
            telemetry.addData("heading", follower.getPose().getHeading());
            telemetry.update();
        }
    }
}
