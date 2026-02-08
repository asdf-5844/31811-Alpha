package org.firstinspires.ftc.teamcode.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.util.AllianceMirror;

public class Path9Gate {

    public PathChain scorepreload;
    public PathChain move1;
    public PathChain intake1;
    public PathChain opengate;
    public PathChain score1;
    public PathChain move2;
    public PathChain intake2;
    public PathChain score2;
    public PathChain park;

    private static final double SCORE_X = 32.316;
    private static final double SCORE_Y = 109.106;

    // mirror true = Red, false = Blue
    public Path9Gate(Follower follower, boolean mirror) {

        scorepreload = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(23.224, 125.196, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(SCORE_X, SCORE_Y, 0), mirror)
                )
        ).setConstantHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(145), mirror)
        ).build();

        move1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                AllianceMirror.mirrorPose(new Pose(SCORE_X, SCORE_Y, 0), mirror),
                                AllianceMirror.mirrorPose(new Pose(52.863, 84.250, 0), mirror)
                        )
        ).setLinearHeadingInterpolation(
                        AllianceMirror.mirrorHeading(Math.toRadians(145), mirror),
                        AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        intake1 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(52.863, 84.250, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(14.690, 84.250, 0), mirror)
                )
        ).setConstantHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        opengate = follower.pathBuilder().addPath(
                new BezierCurve(
                        AllianceMirror.mirrorPose(new Pose(14.690, 84.250, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(44.919, 84.896, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(16.962, 75.546, 0), mirror)
                )
        ).setConstantHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        score1 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(16.962, 75.546, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(SCORE_X, SCORE_Y, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(125), mirror)
        ).build();

        move2 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(SCORE_X, SCORE_Y, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(50.500, 58.500, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(135), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        intake2 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(50.500, 58.500, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(12.650, 58.500, 0), mirror)
                )
        ).setConstantHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        score2 = follower.pathBuilder().addPath(
                new BezierCurve(
                        AllianceMirror.mirrorPose(new Pose(12.650, 58.500, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(72.540, 53.171, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(SCORE_X, SCORE_Y, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(125), mirror)
        ).build();

        park = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(SCORE_X, SCORE_Y, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(25.788, 64.652, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(125), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();
    }
}
