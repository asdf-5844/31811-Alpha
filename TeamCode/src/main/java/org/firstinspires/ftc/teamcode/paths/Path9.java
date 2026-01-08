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

    private double scoreX = 32.316;
    private double scoreY = 109.106;
    // mirror true = Red, false = Blue

    public Path9(Follower follower, boolean mirror) {

        scorepreload = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(21.762, 126.474, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(scoreX, scoreY, 0), mirror)
                )
        ).setConstantHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(135), mirror)
        ).build();

        move1 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(32.316, 109.106, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(46.468, 83.000, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(135), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        intake1 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(46.468, 83.000, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(13.245, 83.000, 0), mirror)
                )
        ).setConstantHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        score1 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(13.245, 83.000, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(scoreX, scoreY, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(125), mirror)
        ).build();

        move2 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(40.829, 102.208, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(42.562, 59.000, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(135), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        intake2 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(42.562, 59.000, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(11.555, 59.000, 0), mirror)
                )
        ).setConstantHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();

        move3 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(11.555, 59.000, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(59.510, 86.609, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(125), mirror)
        ).build();

        score2 = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(59.510, 86.609, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(scoreX, scoreY, 0), mirror)
                )
        ).setConstantHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(125), mirror)
        ).build();

        park = follower.pathBuilder().addPath(
                new BezierLine(
                        AllianceMirror.mirrorPose(new Pose(41.406, 102.016, 0), mirror),
                        AllianceMirror.mirrorPose(new Pose(24.407, 69.966, 0), mirror)
                )
        ).setLinearHeadingInterpolation(
                AllianceMirror.mirrorHeading(Math.toRadians(125), mirror),
                AllianceMirror.mirrorHeading(Math.toRadians(180), mirror)
        ).build();
    }
}
