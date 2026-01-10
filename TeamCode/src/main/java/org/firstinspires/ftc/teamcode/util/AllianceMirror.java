package org.firstinspires.ftc.teamcode.util;

import com.pedropathing.geometry.Pose;

public class AllianceMirror {
    public static final double FIELD_SIZE = 144.0;

    public static Pose mirrorPose(Pose p, boolean isRed) {
        if (!isRed) return p;
        return new Pose(
                FIELD_SIZE - p.getX(),
                p.getY(),
                mirrorHeading(p.getHeading(), true)
        );
    }
    // Pose in pedro is (x,y)
    // Headings are handled separately by mirroring angles
    public static double mirrorHeading(double headingRad, boolean isRed) {
        if (!isRed) return headingRad;
        return Math.toRadians(180)-headingRad; // The heading is mirrored
    }
}

