//=============================================================================================================================================================

import static java.lang.Double.isFinite;

/**
 * Defines the dragster with the automatic transmission as describe in the task document. Add your solution here. Get this one to work first, then copy it to
 * DragsterManual and modify it there.
 * <p>
 * IMPLEMENT PARTS 1 AND 2 HERE.
 */
public class DragsterAutomatic extends A_Dragster {
    /** Target RPM to shift at */
    private static final int SHIFT_RPM = 9000;
    /** Gear number currently in use */
    private static int gearNum = 1;
    /** Total time */
    private static double timeTotal = 0;
    /** Total distance */
    private static double distanceTotal = 0;
    /** Total time since last shift */
    private static double lastShiftingtime = 0;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Creates a dragster with an automatic transmission
     *
     * @param engineModel - the engine model
     */
    public DragsterAutomatic(final A_EngineModel engineModel) {
        super(engineModel);

        System.out.println("time,distance,speed,gear_num,rpm");
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * <p>
     * Implement Parts 1 and 2 here. There should be no difference in the code based on the engine model.
     */
    @Override
    public boolean update(final double timeStep) {
        super.update(timeStep);

        int rpm = _engineModel.getRPM(timeTotal - lastShiftingtime, gearNum);
        double gearRatio = GEAR_RATIOS[gearNum - 1];

        double distanceDelta = (rpm / SECONDS_PER_MINUTE) * timeStep * gearRatio * FEET_PER_REVOLUTION;
        double speedTotal = (distanceTotal / FEET_PER_MILE) / (timeTotal / SECONDS_PER_MINUTE / MINUTES_PER_HOUR);
        speedTotal = isFinite(speedTotal) ? speedTotal : 0;

        System.out.printf("%.1f,%f,%f,%d,%d%n", timeTotal, distanceTotal, speedTotal, gearNum, rpm);

        distanceTotal += distanceDelta;
        timeTotal += timeStep;

        if (rpm >= SHIFT_RPM) {
            gearNum++;
            lastShiftingtime = timeTotal;
        }

        return gearNum > GEAR_RATIOS.length;
    }
}
