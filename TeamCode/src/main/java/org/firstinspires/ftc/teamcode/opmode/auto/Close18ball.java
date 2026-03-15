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
        // The State Machine - Runs through Path1 to Path14
        switch (pathState) {
            case 0: // Start Path 1
                follower.followPath(myPaths.Path1);
                pathState = 1;
                break;

            case 1: // Wait for Path 1, then start Path 2
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path14); // Note: Following your Path14 order
                    pathState = 2;
                }
                break;

            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path2);
                    pathState = 3;
                }
                break;

            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path3);
                    pathState = 4;
                }
                break;

            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path4);
                    pathState = 5;
                }
                break;

            case 5:
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path5);
                    pathState = 6;
                }
                break;

            case 6:
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path6);
                    pathState = 7;
                }
                break;

            case 7:
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path7);
                    pathState = 8;
                }
                break;

            case 8: // Skipping Path 8 as it was missing in your original snippet
                follower.followPath(myPaths.Path9);
                pathState = 9;
                break;

            case 9:
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path10);
                    pathState = 10;
                }
                break;

            case 10:
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path11);
                    pathState = 11;
                }
                break;

            case 11:
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path12);
                    pathState = 12;
                }
                break;

            case 12:
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path13);
                    pathState = 13;
                }
                break;

            case 13:
                if (!follower.isBusy()) {
                    follower.followPath(myPaths.Path14); // Final Path14
                    pathState = 14;
                }
                break;

            case 14:
                if (!follower.isBusy()) {
                    // All paths complete!
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
