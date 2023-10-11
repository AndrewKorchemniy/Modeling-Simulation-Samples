import java.util.Random;

import static java.lang.Double.isFinite;

//=============================================================================================================================================================

/**
 * Defines the dragster with the manual transmission as describe in the task document. Add your solution here. Get DragsterAutomatic to work first, then copy it
 * here and modify it.
 * <p>
 * IMPLEMENT PARTS 3 AND 4 HERE.
 */
public class DragsterManual extends A_Dragster {
    /** Target RPM to shift at */
    private static final int SHIFT_RPM = 9000;
    /** The human response time +/- */
    private static final double SHIFT_RESPONSE_SECONDS = 0.125;
    /** Gear number currently in use */
    private static int gearNum = 1;
    /** Total distance */
    private static double distanceTotal = 0;
    /** Total time */
    private static double timeTotal = 0;
    /** Total time since last shift */
    private static double lastShiftingTime = 0;
    /** The randomly generated human-error shifting time-based RPMs */
    private static int[] randomShiftRPMs;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Creates a dragster with a manual transmission
     *
     * @param engineModel - the engine model
     */
    public DragsterManual(final A_EngineModel engineModel) {
        super(engineModel);

        generateRandomShiftRPMs();
        System.out.println("time,distance,speed,gear_num,rpm,shift_rpm");
    }

    /**
     * Generates the random shifting RPMs for each gear
     */
    private void generateRandomShiftRPMs() {
        Random _random = new Random(421);
        int gearCount = _engineModel.getGearCount();
        randomShiftRPMs = new int[gearCount];

        for (int gear = 0; gear < gearCount; gear++) {
            double timeOffset = _random.nextGaussian() * SHIFT_RESPONSE_SECONDS;
            double timeAtShiftRPM = _engineModel.getTimeInGear(SHIFT_RPM, gearNum);
            randomShiftRPMs[gear] = _engineModel.getRPM(timeAtShiftRPM + timeOffset, gearNum);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * <p>
     * Implement Parts 3 and 4 here.
     */
    @Override
    public boolean update(final double timeStep) {
        super.update(timeStep);

        int rpm = _engineModel.getRPM(timeTotal - lastShiftingTime, gearNum);
        double gearRatio = GEAR_RATIOS[gearNum - 1];

        double distanceDelta = (rpm / SECONDS_PER_MINUTE) * timeStep * gearRatio * FEET_PER_REVOLUTION;
        double speedTotal = (distanceTotal / FEET_PER_MILE) / (timeTotal / SECONDS_PER_MINUTE / MINUTES_PER_HOUR);
        speedTotal = isFinite(speedTotal) ? speedTotal : 0;

        System.out.printf("%.1f,%f,%f,%d,%d,%d%n", timeTotal, distanceTotal, speedTotal, gearNum, rpm, randomShiftRPMs[gearNum - 1]);

        distanceTotal += distanceDelta;
        timeTotal += timeStep;

        if (rpm > randomShiftRPMs[gearNum - 1]) {
            gearNum++;
            lastShiftingTime = timeTotal;
        }

        return gearNum > GEAR_RATIOS.length;
    }
}
