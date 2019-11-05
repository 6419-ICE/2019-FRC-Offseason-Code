package frc.robot;

public final class Utilities {

    public static double applyDeadband(double x, double db) {
        if (Math.abs(x) < db) {
            return 0;
        } else {
            return x;
        }
    }
}