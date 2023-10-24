import java.io.IOException;

//=============================================================================================================================================================

/**
 * This is the simulation that drives the model.
 * <p>
 * PUT YOUR SIMULATIONS HERE. KEEP EACH TEST SEPARATE. DO NOT CHANGE Airplane.
 */
public class Simulation {

    /**
     * For updating an airplane's state with time.
     */
    @FunctionalInterface
    interface AirplaneUpdate {
        void update(Airplane airplane, double time);
    }

    /**
     * Defines the simulation parameters.
     */
    public static class SimulationParameters {
        public final double timeStep = 0.1;
        public final double x = 0;
        public final double y = 0;
        public final double altitude = 1000;
        public final double speed = 100;
        public final double heading = 0;
        public String filename;
        public double timeLimit;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * ZZZ
     *
     * @param arguments - there are no arguments
     */
    public static void main(final String[] arguments) throws IOException {
        Simulation simulation = new Simulation();

        int testNum = 1; // change this to run each test separately

        System.out.println("Executing Test " + testNum);

        switch (testNum) {
            case 0 -> simulation.runTestExample();
            case 1 -> simulation.runTest1();
            case 2 -> simulation.runTest2();
            case 3 -> simulation.runTest3();
            case 4 -> simulation.runTest4();
            case 5 -> simulation.runTest5();
            case 6 -> simulation.runTest6();
            case 7 -> simulation.runTest7();
            case 8 -> simulation.runTest8();
            case 9 -> simulation.runTest9();
            case 10 -> simulation.runTest10();
            case 11 -> simulation.runTest11();
            case 12 -> simulation.runTest12();
            case 13 -> simulation.runTest13();
            case 14 -> simulation.runTest14();
            case 15 -> simulation.runTest15();
            case 16 -> simulation.runTest16();
            case 17 -> simulation.runTest17();
            case 18 -> simulation.runTest18();
            case 19 -> simulation.runTest19();
            case 20 -> simulation.runTest20();
            default -> throw new RuntimeException("unknown test " + testNum);
        }

        System.out.println("Done");
    }

    /**
     * Runs the simulation based on the given parameters.
     * AirplaneUpdate is called after each time step,
     * allowing for additional simulation logic.
     *
     * @param timeStep  The time step.
     * @param timeLimit The total time to run the simulation.
     * @param plane     The airplane to simulate.
     * @param update    The function to call while updating the airplane.
     * @throws IOException If there is an error writing the output file.
     */
    public static void update(double timeStep, double timeLimit, Airplane plane, AirplaneUpdate update) throws IOException {
        double time = 0;
        while (time < timeLimit) {
            plane.update(timeStep);
            time = plane.getTime();
            update.update(plane, time);
        }
        plane.terminate();
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 1 here.
     */
    public void runTest1() throws IOException {
        SimulationParameters p = new SimulationParameters();
        p.filename = "test1";
        p.timeLimit = 30;

        Airplane airplane = new Airplane(p.x, p.y, p.altitude, p.speed, p.heading, p.filename);

        update(p.timeStep, p.timeLimit, airplane, (plane, time) -> {
            // Fly straight north for 30 seconds while holding altitude.
        });
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 2 here.
     */
    public void runTest2() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 3 here.
     */
    public void runTest3() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 4 here.
     */
    public void runTest4() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 5 here.
     */
    public void runTest5() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 6 here.
     */
    public void runTest6() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 7 here.
     */
    public void runTest7() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 8 here.
     */
    public void runTest8() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 9 here.
     */
    public void runTest9() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 10 here.
     */
    public void runTest10() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 11 here.
     */
    public void runTest11() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 12 here.
     */
    public void runTest12() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 13 here.
     */
    public void runTest13() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 14 here.
     */
    public void runTest14() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 15 here.
     */
    public void runTest15() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 16 here.
     */
    public void runTest16() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 17 here.
     */
    public void runTest17() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 18 here.
     */
    public void runTest18() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 19 here.
     */
    public void runTest19() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 20 here.
     */
    public void runTest20() throws IOException {
        // your code
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This is an example test.
     */
    public void runTestExample() throws IOException {
        double x = 0;
        double y = 0;
        double altitude = 0;
        double speed = 100;
        double heading = 0;
        String filename = "out";

        Airplane airplane = new Airplane(x, y, altitude, speed, heading, filename);

        double timeStep = 0.1;
        double timeLimit = 30;

        airplane.setPitch(10);
        airplane.setRoll(0);

        // airplane.setWind(45, 10);

        double time = 0;

        while (time < timeLimit) {
            airplane.update(timeStep);

            time = airplane.getTime();

//         // after time 10, roll 50 degrees to the right
//         if (time > 10)
//         {
//            airplane.setRoll(50);
//         }
        }

        airplane.terminate();
    }
}
