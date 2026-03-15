package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import com.pedropathing.util.Timer;
import com.pedropathing.localization.Pose;

// These imports link to your other files in the pedroPathing folder
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.Close18ball; 

@Autonomous(name = "Decode 18 Ball Auto", group = "Autonomous")
public class DecodeAuto extends OpMode {
    private Follower follower;
    private Timer pathTimer;
    private int pathState;
    private Paths myPaths;

    // Set this to true if on Red Alliance, false for Blue Alliance
    private boolean isRed = false; 

    @Override
    public void init() {
        pathTimer = new Timer();
        

        follower = Constants.createFollower(hardwareMap);
        

        myPaths = new Paths(follower, isRed);
        
    
        follower.setStartingPose(myPaths.Path1.getPath(0).getPoint(0));
    }

    @Override
    public void loop() {
        // update robot position
        follower.update();

        // state machine 
        switch (pathState) {
            case 0: // path 1 start
                follower.followPath(myPaths.Path1);
                pathState = 1;
                break;

            case 1: 
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path2);
                    pathState = 2;
                }
                break;

            case 2: 
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path3);
                    pathState = 3;
                }
                break;
            
            case 14:
                 if (!follower.isBusy()) {
                    // Robot has finished all paths
                    pathState = -1; 
                }
                break;
        }

        // dubugging using telemetry
        telemetry.addData("Current Path State", pathState);
        telemetry.addData("Is Busy", follower.isBusy());
        telemetry.addData("X Position", follower.getPose().getX());
        telemetry.addData("Y Position", follower.getPose().getY());
        telemetry.update();
    }
}
