import java.util.Scanner;

public class BallisticsCalculator {
    private static final double GRAVITY = ;

    public static void main(String[] args) {
        // User input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the muzzle velocity in feet per second: ");
        double muzzleVelocity = input.nextDouble();
        System.out.print("Enter the bullet weight in grains: ");
        double bulletWeight = input.nextDouble();
        System.out.print("Enter the ballistic coefficient: ");
        double ballisticCoefficient = input.nextDouble();
        System.out.print("Enter the zero range in yards: ");
        double zeroRange = input.nextDouble();
        System.out.print("Enter the wind speed in miles per hour: ");
        double windSpeed = input.nextDouble();
        System.out.print("Enter the wind angle in degrees: ");
        double windAngle = input.nextDouble();
        System.out.print("Enter the temperature in degrees Fahrenheit: ");
        double temperature = input.nextDouble();
        System.out.print("Enter the altitude in feet: ");
        double altitude = input.nextDouble();

        // Constants
        final double GRAVITY = 32.174;
        final double PI = 3.14159265358979323846;
        final double INCHES_TO_YARDS = 0.0277777778;
        final double MPH_TO_FEET_PER_SECOND = 1.46667;
        final double KNOTS_TO_FEET_PER_SECOND = 1.68781;
        final double GRAINS_TO_POUNDS = 0.000142857142857;
        final double KILOGRAMS_TO_POUNDS = 2.20462;
        final double CELSIUS_TO_FAHRENHEIT = 1.8;

        // Calculations
        double velocityAtZero = muzzleVelocity - GRAVITY * zeroRange / (MPH_TO_FEET_PER_SECOND * 2);
        double ballisticCoefficientCorrection = ballisticCoefficient / 0.29519413;
        double dragFunction = ballisticCoefficientCorrection * (bulletWeight * GRAINS_TO_POUNDS / KILOGRAMS_TO_POUNDS) / (PI * Math.pow(0.5, 2));
        double zeroAngle = Math.toRadians(0);
        double zeroHeight = 0;
        double zeroVelocity = velocityAtZero;
        double zeroDistance = zeroRange;
        double windSpeedInFeetPerSecond = windSpeed * MPH_TO_FEET_PER_SECOND;
        double windAngleInRadians = Math.toRadians(windAngle);
        double windDrift = windSpeedInFeetPerSecond * Math.sin(windAngleInRadians) * timeOfFlight(zeroAngle, zeroHeight, zeroVelocity, zeroDistance, dragFunction) / INCHES_TO_YARDS;
        double timeOfFlight = timeOfFlight(zeroAngle, zeroHeight, zeroVelocity, zeroDistance, dragFunction);
        double bulletDrop = bulletDrop(zeroAngle, zeroHeight, zeroVelocity, zeroDistance, dragFunction) / INCHES_TO_YARDS;
        double temperatureInCelsius = (temperature - 32) / CELSIUS_TO_FAHRENHEIT;
        double pressure = Math.pow(1 - 0.0000068756 * altitude, 5.2561);
        double densityRatio = Math.exp((-0.000116 * altitude) / (temperatureInCelsius + 273.15 + 16));
        double airDensity = 0.0765 * pressure / (temperatureInCelsius + 273.;
        // Output
        System.out.println("Bullet Drop: " + bulletDrop + " inches");
        System.out.println("Wind Drift: " + windDrift + " inches");
        System.out.println("Time of Flight: " + timeOfFlight + " seconds");

    }

    // Functions
    public static double timeOfFlight(double angle, double height, double velocity, double distance, double dragFunction) {
        double timeStep = 0.01;
        double time = 0;
        double x = 0;
        double y = height;
        double vx = velocity * Math.cos(angle);
        double vy = velocity * Math.sin(angle);
        while (y >= 0) {
            double ax = -dragFunction * velocity * vx;
            double ay = -dragFunction * velocity * vy - GRAVITY;
            vx += ax * timeStep;
            vy += ay * timeStep;
            x += vx * timeStep;
            y += vy * timeStep;
            time += timeStep;
        }
        return time;
    }

    public static double bulletDrop(double angle, double height, double velocity, double distance, double dragFunction) {
        double timeStep = 0.01;
        double time = 0;
        double x = 0;
        double y = height;
        double vx = velocity * Math.cos(angle);
        double vy = velocity * Math.sin(angle);
        double drop = 0;
        while (y >= 0) {
            double ax = -dragFunction * velocity * vx;
            double ay = -dragFunction * velocity * vy - GRAVITY;
            vx += ax * timeStep;
            vy += ay * timeStep;
            x += vx * timeStep;
            y += vy * timeStep;
            time += timeStep;
            if (x >= distance) {
                drop = y;
            }
        }
        return drop;
    }
