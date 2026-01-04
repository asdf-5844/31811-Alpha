package org.firstinspires.ftc.teamcode.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;

public class BluePath9 {

    public PathChain scorepreload;
    public PathChain move1;
    public PathChain intake1;
    public PathChain score1;
    public PathChain move2;
    public PathChain intake2;
    public PathChain move3;
    public PathChain score2;
    public PathChain park;

    public BluePath9(Follower follower) {

        scorepreload = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(21.762, 126.474),
                                new Pose(32.316, 109.106)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(135))
                .build();

        move1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(32.316, 109.106),
                                new Pose(46.468, 83.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                .build();

        intake1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.468, 83.000),
                                new Pose(13.245, 83.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        score1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(13.245, 83.000),
                                new Pose(40.829, 102.208)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(125))
                .build();

        move2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(40.829, 102.208),
                                new Pose(42.562, 59.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                .build();

        intake2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(42.562, 59.000),
                                new Pose(11.555, 59.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        move3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(11.555, 59.000),
                                new Pose(59.510, 86.609)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(125))
                .build();

        score2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(59.510, 86.609),
                                new Pose(41.406, 102.016)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(125))
                .build();

        park = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(41.406, 102.016),
                                new Pose(24.407, 69.966)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(125), Math.toRadians(180))
                .build();
    }
}
