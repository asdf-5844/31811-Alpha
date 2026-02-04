package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.paths.Path12;
import org.firstinspires.ftc.teamcode.util.AllianceMirror;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Transport;
import org.firstinspires.ftc.teamcode.subsystem.Outtake3;

@Autonomous(name = "BlueClose12", group = "Twelve")
public class BlueClose12 extends LinearOpMode {

    private Follower follower;
    private Path12 paths;

    private enum PathState {
        DRIVE_TO_PRELOAD_SHOT,
        LAUNCH_PRELOADS,
        INTAKE1,
        OPENGATE,
        DRIVE_TO_SCORE1,
        LAUNCH1,
        INTAKE2,
        DRIVE_TO_SCORE2,
        LAUNCH2,
        INTAKE3,
        DRIVE_TO_SCORE3,
        LAUNCH3,
        PARK,
        DONE
    }

    private void setState(PathState newState) {
        state = newState;
        shotsTriggered = false;      // allows each LAUNCH state to trigger once
        if (newState != PathState.OPENGATE) spillTimerStarted = false;
    }

    private PathState state = PathState.DRIVE_TO_PRELOAD_SHOT;

    // Speeds
    private static final double TRAVEL_POWER = 0.85;  // normal driving
    private static final double INTAKE_POWER = 0.60;  // slower for picking up balls

    // Flywheel / Intake
    private final Outtake3 outtake = new Outtake3();
    private boolean shotsTriggered = false;
    private Intake intake;
    private Transport transport;

    // Gate wait
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

        boolean mirror = false; // Blue

        // visualizer start pose (same style as BlueClose9)
        double startX = 23.224;
        double startY = 125.196;
        double startHeading = Math.toRadians(145);

        Pose blueStart = new Pose(startX, startY, startHeading);
        Pose start = AllianceMirror.mirrorPose(blueStart, mirror);

        follower.setStartingPose(start);

        paths = new Path12(follower, mirror);

        waitForStart();

        // Start first path ONLY ONCE
        follower.setMaxPower(TRAVEL_POWER);
        follower.followPath(paths.scorepreload);

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
                    if (follower.isBusy()) break;

                    if (!shotsTriggered) {
                        outtake.startLaunch(3);
                        shotsTriggered = true;
                    } else if (!outtake.isBusy()) {
                        startIntaking();
                        follower.setMaxPower(INTAKE_POWER);
                        follower.followPath(paths.intake1);
                        setState(PathState.INTAKE1);
                    }
                    break;

                case INTAKE1:
                    if (!follower.isBusy()) {
                        stopIntaking();
                        follower.setMaxPower(TRAVEL_POWER);
                        follower.followPath(paths.opengate);
                        setState(PathState.OPENGATE);
                    }
                    break;

                case OPENGATE:
                    // wait for opengate path to finish, then wait 4 seconds for spill
                    if (!follower.isBusy()) {

                        if (!spillTimerStarted) {
                            gateTimer.reset();
                            spillTimerStarted = true;
                        }

                        if (gateTimer.seconds() >= 2.0) {
                            spillTimerStarted = false;
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
                    if (follower.isBusy()) break;

                    if (!shotsTriggered) {
                        outtake.startLaunch(3);
                        shotsTriggered = true;
                    } else if (!outtake.isBusy()) {
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
                    if (follower.isBusy()) break;

                    if (!shotsTriggered) {
                        outtake.startLaunch(3);
                        shotsTriggered = true;
                    } else if (!outtake.isBusy()) {
                        startIntaking();
                        follower.setMaxPower(INTAKE_POWER);
                        follower.followPath(paths.intake3);
                        setState(PathState.INTAKE3);
                    }
                    break;

                case INTAKE3:
                    if (!follower.isBusy()) {
                        stopIntaking();
                        follower.setMaxPower(TRAVEL_POWER);
                        follower.followPath(paths.score3);
                        setState(PathState.DRIVE_TO_SCORE3);
                    }
                    break;

                case DRIVE_TO_SCORE3:
                    if (!follower.isBusy()) {
                        setState(PathState.LAUNCH3);
                    }
                    break;

                case LAUNCH3:
                    if (follower.isBusy()) break;

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
