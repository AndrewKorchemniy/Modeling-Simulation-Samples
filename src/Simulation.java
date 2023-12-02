import java.util.List;
import java.util.Random;

//=============================================================================================================================================================

/**
 * Defines the simulation as specified in the task document.
 * <p>
 * IMPLEMENT THIS CLASS YOURSELF.
 */
public class Simulation {
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void main(final String[] arguments) {
        Simulation simulation = new Simulation();

        simulation.doTest();
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Executes the initial test
     */
    private void doTest() {
        int randomSeed = 1;
        double lambda = 0.75; // the random arrival rate of cars; larger is more frequent
        double simulationRunTime = 300; // the total number of seconds to simulate
        double lengthGreen = 30; // red every 30 seconds starting at 30, green every 30 seconds starting at 60
        double lengthRed = 30;
        double spread = (lengthGreen + lengthRed);
        double bucketSize = 10; // the size of the histogram buckets in seconds

        // populate the traffic-light transitions
        TrafficQueue queue = new TrafficQueue("queue");
        populateLights(queue, simulationRunTime, lengthGreen, spread, spread, spread);
        // populate the cars randomly
        populateCars(queue, simulationRunTime, lambda, randomSeed);
        // execute the simulation and generate a histogram
        executeSimulation(queue, bucketSize);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Executes the simulation after setting it up with events.
     *
     * @param queue      - the event queue
     * @param bucketSize - the bucket size in seconds
     */
    private void executeSimulation(final TrafficQueue queue, double bucketSize) {
        // TODO: create the five analysis groups
        AnalysisGroup all = new AnalysisGroup("all", 0);
        AnalysisGroup on_green_all = new AnalysisGroup("on_green:all", 0);
        AnalysisGroup on_red_all = new AnalysisGroup("on_red:all", 0);
        AnalysisGroup on_green_n = new AnalysisGroup("on_green_1", 1);
        AnalysisGroup on_red_n = new AnalysisGroup("on_red_1", 1);
        int cycles = 1;

        // TODO: keep looping while the queue has events
        while (queue.hasEvents()) {
            List<Event> events = queue.service();
            for (Event e : events) {
                double time = e.getTime();
                switch (e.getType()) {
                    case CAR_ARRIVAL -> {
                        all.addEventTime(time);
                        if (!queue.isGreenElseRed()) {
                            on_green_all.addEventTime(time);
                            on_green_n.addEventTime(time);
                        } else {
                            on_red_all.addEventTime(time);
                            on_red_n.addEventTime(time);
                        }
                    }
                    case LIGHT_TO_GREEN -> {
                        all.addEventSeparator(time);
                        on_red_all.addEventSeparator(time);
                        on_red_n.finalizeInterval(time);
                        on_red_n.generateHistogram(bucketSize);
                        on_green_n = new AnalysisGroup("on_green_" + cycles, time);
                    }
                    case LIGHT_TO_RED -> {
                        all.addEventSeparator(time);
                        on_green_all.addEventSeparator(time);
                        on_green_n.finalizeInterval(time);
                        on_green_n.generateHistogram(bucketSize);
                        on_red_n = new AnalysisGroup("on_red_" + cycles, time);
                        cycles++;
                    }
                }
            }
        }

        double finalTime = queue.getCurrentTime();
        on_green_all.finalizeInterval(finalTime);
        on_red_all.finalizeInterval(finalTime);
        all.finalizeInterval(finalTime);

        on_red_all.generateHistogram(bucketSize);
        on_green_all.generateHistogram(bucketSize);
        all.generateHistogram(bucketSize);

        // TODO: > pop off all events queued up for this turn of the light
        // TODO: >> for a CAR_ARRIVAL event, add it to the all analysis group and the on_green:n/green:all or on_red:n/red:all groups, depending on the light state
        // TODO: >> for a LIGHT_TO_GREEN event, add an event separator with the current time to all and red:all,
        // finalize the on_red:n group with this time, and create a new on_green:n group for the next turn. Finalizing prints its statistics
        // TODO: >> for a LIGHT_TO_RED event, add an event separator with the current time to all and green:all,
        // finalize the on_green:n group with this time, and create a new on_red:n group for the next turn. Finalizing prints its statistics
        // TODO: finalize all the all analysis groups with the final simulation time
        // TODO: generate the histograms on the all groups
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Populates a queue with car arrivals.
     *
     * @param queue             - the event queue
     * @param simulationRunTime - the end time of the simulation
     * @param lambda            - the lambda term of the exponential random distribution
     * @param randomSeed        - the random seed
     */
    private void populateCars(final TrafficQueue queue, final double simulationRunTime, final double lambda, final int randomSeed) {
        Random random = new Random(randomSeed);

        double time = Math.log(1 - random.nextDouble()) / -lambda;
        do {
            queue.addEvent(new Event(time, "car_" + (int) (time + 1), Event.E_EventType.CAR_ARRIVAL));
            time += Math.log(1 - random.nextDouble()) / -lambda;
        } while (time < simulationRunTime);

        // TODO: create a java.util.Random object random with randomSeed
        // TODO: starting at current time 0, loop until it reaches or exceeds simulationRunTime
        // TODO: > generate a random time with lambda from the exponential-distribution formula in the specs
        // TODO: create a CAR_ARRIVAL event at this time, add it to the queue, and advance the current time by this amount
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Populates a queue with traffic-light transitions of both types.
     *
     * @param queue             - the event queue
     * @param simulationRunTime - the end time of the simulation
     * @param startTimeRed      - the start time of the red lights
     * @param intervalRed       - the time interval between red lights
     * @param startTimeGreen    - the start time of the green lights
     * @param intervalGreen     - the time interval between green lights
     */
    private void populateLights(final TrafficQueue queue,
                                final double simulationRunTime,
                                final double startTimeRed,
                                final double intervalRed,
                                final double startTimeGreen,
                                final double intervalGreen) {
        populateTransitions(queue, Event.E_EventType.LIGHT_TO_RED, startTimeRed, simulationRunTime, intervalRed);
        populateTransitions(queue, Event.E_EventType.LIGHT_TO_GREEN, startTimeGreen, simulationRunTime, intervalGreen);
        // TODO: call populateTransitions for the LIGHT_TO_RED transitions
        // TODO: call populateTransitions for the LIGHT_TO_GREEN transitions
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Populates a queue with traffic-light transitions of one type.
     *
     * @param queue     - the queue
     * @param type      - the type of event, either LIGHT_TO_GREEN or LIGHT_TO_RED
     * @param timeStart - the time of the first event
     * @param timeEnd   - the time of the last event
     * @param timeStep  - the interval between each event; the last interval may not divide evenly
     */
    private void populateTransitions(final TrafficQueue queue,
                                     final Event.E_EventType type,
                                     final double timeStart,
                                     final double timeEnd,
                                     final double timeStep) {
        double i = timeStart;
        while (i <= timeEnd) {
            queue.addEvent(new Event(i, "transition_" + (int) ((i + timeStep - timeStart) / timeStep), type));
            i += timeStep;
        }

        // TODO: loop from timeStart up to and including timeEnd by timeStep, create an event for each, and add it to the queue
    }
}
