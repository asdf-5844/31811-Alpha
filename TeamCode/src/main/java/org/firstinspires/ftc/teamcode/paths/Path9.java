package org.firstinspires.ftc.teamcode.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.util.AllianceMirror;

public class Path9 {

    public PathChain scorepreload;
    public PathChain move1;
    public PathChain intake1;
    public PathChain score1;
    public PathChain move2;
    public PathChain intake2;
    public PathChain move3;
    public PathChain score2;
    public PathChain park;

    private double SCORE_X = 32.316;
    private double SCORE_Y = 109.106;
    // mirror true = Red, false = Blue

    public Path9(Follower follower, boolean mirror) {

        scorepreload = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(21.762, 126.474, Math.toRadians(145)), mirror),
                        AllianceMirror.mirrorPose(new Pose(SCORE_X, SCORE_Y, 0), mirror)
                )
        ).setConstantHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(135), mirror)
        ).build();

        move1 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(SCORE_X, SCORE_Y, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(52.863, 84.500, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(135), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        intake1 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(52.863, 84.500, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(15.927, 84.500, 0), mirror)
                )
        ).setConstantHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        score1 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(15.927, 83.000, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(SCORE_X, SCORE_Y, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(125), mirror)
        ).build();

        move2 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(SCORE_X, SCORE_Y, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(50.500, 60.000, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(135), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        intake2 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(48.513, 60.000, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(14.649, 60.000, 0), mirror)
                )
        ).setConstantHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        move3 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(14.649, 59.000, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(28.391, 65.049, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(125), mirror)
        ).build();

        score2 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(28.391, 65.049, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(SCORE_X, SCORE_Y, 0), mirror)
                )
        ).setConstantHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(125), mirror)
        ).build();

        park = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(SCORE_X, SCORE_Y, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(24.407, 69.966, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(125), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();
    }
}
