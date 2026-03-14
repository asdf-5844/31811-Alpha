public static class Paths {
    public PathChain Path1, Path2, Path3, Path4, Path5, Path6, Path7, Path9, Path10, Path11, Path12, Path13, Path14;


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
                mirrorPose(new Pose(26.884, 137.414), isRed),
                mirrorPose(new Pose(53.613, 60.133), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(142), isRed), mirrorHeading(Math.toRadians(180), isRed))
            .build();

        Path2 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(53.613, 60.133), isRed),
                mirrorPose(new Pose(20.028, 60.326), isRed)
            ))
            .setTangentHeadingInterpolation()
            .build();

        Path3 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(20.028, 60.326), isRed),
                mirrorPose(new Pose(71.873, 71.834), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(180), isRed), mirrorHeading(Math.toRadians(133), isRed))
            .setReversed()
            .build();

        Path4 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(71.873, 71.834), isRed),
                mirrorPose(new Pose(41.558, 35.569), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(133), isRed), mirrorHeading(Math.toRadians(180), isRed))
            .build();

        Path5 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(41.558, 35.569), isRed),
                mirrorPose(new Pose(13.989, 35.503), isRed)
            ))
            .setTangentHeadingInterpolation()
            .build();

        Path6 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(13.989, 35.503), isRed),
                mirrorPose(new Pose(71.779, 71.696), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(180), isRed), mirrorHeading(Math.toRadians(133), isRed))
            .build();

        Path7 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(71.779, 71.696), isRed),
                mirrorPose(new Pose(12.414, 65.293), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(133), isRed), mirrorHeading(Math.toRadians(150), isRed))
            .build();

        Path9 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(12.414, 65.293), isRed),
                mirrorPose(new Pose(61.166, 82.591), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(150), isRed), mirrorHeading(Math.toRadians(133), isRed))
            .build();

        Path10 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(61.166, 82.591), isRed),
                mirrorPose(new Pose(13.492, 66.464), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(133), isRed), mirrorHeading(Math.toRadians(160), isRed))
            .build();

        Path11 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(13.492, 66.464), isRed),
                mirrorPose(new Pose(58.287, 85.492), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(160), isRed), mirrorHeading(Math.toRadians(133), isRed))
            .build();

        Path12 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(58.287, 85.492), isRed),
                mirrorPose(new Pose(16.221, 84.978), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(133), isRed), mirrorHeading(Math.toRadians(182), isRed))
            .build();

        Path13 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(16.221, 84.978), isRed),
                mirrorPose(new Pose(38.514, 103.923), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(182), isRed), mirrorHeading(Math.toRadians(133), isRed))
            .build();

        Path14 = follower.pathBuilder()
            .addPath(new BezierLine(
                mirrorPose(new Pose(38.514, 103.923), isRed),
                mirrorPose(new Pose(26.210, 93.044), isRed)
            ))
            .setLinearHeadingInterpolation(mirrorHeading(Math.toRadians(133), isRed), mirrorHeading(Math.toRadians(133), isRed))
            .build();
    }
}
