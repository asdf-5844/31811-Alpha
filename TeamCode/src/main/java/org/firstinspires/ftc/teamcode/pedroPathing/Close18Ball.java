package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;

public class Paths {
    public PathChain Path1, Path2, Path3, Path4, Path5, Path6, Path7, Path9, Path10, Path11, Path12, Path13, Path14;

    // made mrrior pose so that red and blue alliance works
    public Pose mirrorPose(Pose pose, boolean isRed) {
        if (isRed) {

            return new Pose(pose.getX(), 144 - pose.getY(), -pose.getHeading());
        }
        return pose;
    }


    public double mirrorHeading(double radians, boolean isRed) {
        return isRed ? -radians : radians;
    }

    public Paths(Follower follower, boolean isRed) {
        

        Path1 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(27.149, 134.762, Math.toRadians(142)), isRed),
                mirrorPose(new Pose(46.983, 118.210, Math.toRadians(133)), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(142), isRed), mirrorHeading(Math.toRadians(133), isRed))
            .build();


        Path2 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(46.983, 118.210, Math.toRadians(133)), isRed),
                mirrorPose(new Pose(52.202, 60.494, Math.toRadians(180)), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(133), isRed), mirrorHeading(Math.toRadians(180), isRed))
            .build();


        Path3 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(52.202, 60.494, Math.toRadians(180)), isRed),
                mirrorPose(new Pose(20.028, 60.326, Math.toRadians(180)), isRed)
            ))
            .setTangentHeadingInterpolation()
            .build();

 
        Path4 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(20.028, 60.326, Math.toRadians(180)), isRed),
                mirrorPose(new Pose(71.873, 71.834, Math.toRadians(133)), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(180), isRed), mirrorHeading(Math.toRadians(133), isRed))
            .setReversed(true)
            .build();


        
        Path14 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(38.514, 103.923, Math.toRadians(133)), isRed),
                mirrorPose(new Pose(26.210, 93.044, Math.toRadians(133)), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(133), isRed), mirrorHeading(Math.toRadians(133), isRed))
            .build();
    }
}
