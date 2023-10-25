import java.io.IOException;
import java.util.function.Consumer;

//=============================================================================================================================================================

/**
 * This is the simulation that drives the model.
 * <p>
 * PUT YOUR SIMULATIONS HERE. KEEP EACH TEST SEPARATE. DO NOT CHANGE Airplane.
 */
public class Simulation {

    /**
     * Defines the simulation parameters.
     */
    public static class SimulationParameters {
        public double timeStep = 0.1;
        public double x = 0;
        public double y = 0;
        public double altitude = 1000;
        public double speed = 100;
        public double heading = 0;
        public String filename;
        public double timeLimit;

        public SimulationParameters(String filename, double timeLimit) {
            this.filename = filename;
            this.timeLimit = timeLimit;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * ZZZ
     *
     * @param arguments - there are no arguments
     */
    public static void main(final String[] arguments) throws IOException {
        Simulation simulation = new Simulation();

        int testNum = 8; // change this to run each test separately

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
     * @param p        The simulation parameters.
     * @param airplane The airplane to simulate.
     * @param update   The function to call while updating the airplane.
     * @throws IOException If there is an error writing the output file.
     */
    public static void run(SimulationParameters p, Airplane airplane, Consumer<Double> update) throws IOException {
        double time = 0;
        while (time < p.timeLimit) {
            airplane.update(p.timeStep);
            time = airplane.getTime();
            update.accept(time);
        }
        airplane.terminate();
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 1 here.
     * Fly straight north for 30 seconds while holding altitude.
     */
    public void runTest1() throws IOException {
        SimulationParameters p = new SimulationParameters("test1", 30);

        Airplane airplane = new Airplane(p.x, p.y, p.altitude, p.speed, p.heading, p.filename);

        run(p, airplane, (time) -> {});
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 2 here.
     * Fly straight north for 30 seconds while climbing at 10 degrees of pitch.
     */
    public void runTest2() throws IOException {
        SimulationParameters p = new SimulationParameters("test2", 30);

        Airplane airplane = new Airplane(p.x, p.y, p.altitude, p.speed, p.heading, p.filename);
        airplane.setPitch(10);

        run(p, airplane, (time) -> {});
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 3 here.
     * Fly a complete clockwise circle at bank 30 and pitch 0.
     */
    public void runTest3() throws IOException {
        SimulationParameters p = new SimulationParameters("test3", 72);

        Airplane airplane = new Airplane(p.x, p.y, p.altitude, p.speed, p.heading, p.filename);
        airplane.setRoll(30);

        run(p, airplane, (time) -> {});
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 4 here.
     * Fly a complete clockwise circle at bank angle 30 while climbing at 10 degrees of pitch.
     */
    public void runTest4() throws IOException {
        SimulationParameters p = new SimulationParameters("test4", 72);

        Airplane airplane = new Airplane(p.x, p.y, p.altitude, p.speed, p.heading, p.filename);
        airplane.setRoll(30);
        airplane.setPitch(10);

        run(p, airplane, (time) -> {});
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 5 here.
     * Fly a complete clockwise circle at bank angle 30 while holding altitude with pitch. Adjust the vertical scale in
     * Gnuplot (see below) so the deviation is apparent but not misleading. Your solution will not be perfect. Gnuplot by
     * default will scale any error to fill the vertical graph limits, which makes minor (acceptable) errors look extreme
     * against the scale of the horizontal plane.
     */
    public void runTest5() throws IOException {
        SimulationParameters p = new SimulationParameters("test5", 72);

        Airplane airplane = new Airplane(p.x, p.y, p.altitude, p.speed, p.heading, p.filename);
        airplane.setRoll(30);
        airplane.setPitch(0.6);

        run(p, airplane, (time) -> {});
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 6 here.
     * Fly a complete clockwise circle at bank 30, 45, and 60 and pitch 0.
     * Do all three flights simultaneously with three airplanes. Use a different filename for each. Manually combine the
     * three Gnuplot datasets into one output file with a blank line between each. Keep the configuration the same at the
     * top.
     */
    public void runTest6() throws IOException {
        SimulationParameters p30 = new SimulationParameters("test6.30", 72);
        SimulationParameters p45 = new SimulationParameters("test6.45", 51);
        SimulationParameters p60 = new SimulationParameters("test6.60", 41.5);

        Airplane airplane30 = new Airplane(p30.x, p30.y, p30.altitude, p30.speed, p30.heading, p30.filename);
        airplane30.setRoll(30);
        airplane30.setPitch(0.6);
        Airplane airplane45 = new Airplane(p45.x, p45.y, p45.altitude, p45.speed, p45.heading, p45.filename);
        airplane45.setRoll(45);
        airplane45.setPitch(0.8);
        Airplane airplane60 = new Airplane(p60.x, p60.y, p60.altitude, p60.speed, p60.heading, p60.filename);
        airplane60.setRoll(60);
        airplane60.setPitch(1);

        run(p30, airplane30, (time) -> {});
        run(p45, airplane45, (time) -> {});
        run(p60, airplane60, (time) -> {});
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 7 here.
     * Fly a complete clockwise circle at bank 30 with speeds 50, 100, and 150 and pitch 0.
     * Do all three experiments simultaneously with three airplanes as in Test 6.
     */
    public void runTest7() throws IOException {
        SimulationParameters p50 = new SimulationParameters("test7.50", 73);
        SimulationParameters p100 = new SimulationParameters("test7.100", 73);
        SimulationParameters p150 = new SimulationParameters("test7.150", 73);

        Airplane airplane50 = new Airplane(p50.x, p50.y, p50.altitude, p50.speed, p50.heading, p50.filename);
        airplane50.setRoll(30);
        airplane50.setSpeed(50);
        Airplane airplane100 = new Airplane(p100.x, p100.y, p100.altitude, p100.speed, p100.heading, p100.filename);
        airplane100.setRoll(30);
        airplane100.setSpeed(100);
        Airplane airplane150 = new Airplane(p150.x, p150.y, p150.altitude, p150.speed, p150.heading, p150.filename);
        airplane150.setRoll(30);
        airplane150.setSpeed(150);

        run(p50, airplane50, (time) -> {});
        run(p100, airplane100, (time) -> {});
        run(p150, airplane150, (time) -> {});
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 8 here.
     * Fly an S-turn to the east at bank 30 right and left while holding altitude.
     */
    public void runTest8() throws IOException {
        SimulationParameters p = new SimulationParameters("test8", 72);

        Airplane airplane = new Airplane(p.x, p.y, p.altitude, p.speed, p.heading, p.filename);
        airplane.setRoll(30);
        airplane.setPitch(0.6);

        run(p, airplane, (time) -> {
            if (time >= 36) {
                airplane.setRoll(-30);
                airplane.setPitch(-0.6);
            }
        });
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 9 here.
     * Fly two clockwise turns at bank 30 starting at speed 100. Halfway through the first turn (A), increase speed to
     * 150. At one complete turn (B), increase to 200 and complete the second turn. The flight path should resemble
     * something like this:
     */
    public void runTest9() throws IOException {
        SimulationParameters p = new SimulationParameters("test9", 144);

        Airplane airplane = new Airplane(p.x, p.y, p.altitude, p.speed, p.heading, p.filename);
        airplane.setRoll(30);
        airplane.setPitch(0.6);

        run(p, airplane, (time) -> {
            if (time >= 36) {
                airplane.setSpeed(150);
                airplane.setPitch(0.55);
            }
            if (time >= 72) {
                airplane.setSpeed(200);
                airplane.setPitch(0.35);
            }
        });
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Put Test 10 here.
     * Assume a runway 5,000 feet long facing east at 0 feet altitude. Start at the left (0,0) at 60 mph down the runway.
     * At the three-quarter point, increase speed to 80 and climb to 500 feet. Turn left 90 degrees at 30 degrees of
     * bank and continue climbing to 1,000 feet. Turn left (parallel to the runway in the opposite direction of takeoff), fly
     * level until abeam the start of the runway (x=0), descend to 750 feet, turn left, descend to 500, turn left one last
     * time to align with the runway, reduce speed to 60, land, and stop halfway down the runway. The flight path
     * should resemble this.
     */
    public void runTest10() throws IOException {
        SimulationParameters p = new SimulationParameters("test10", 144);
        p.altitude = 0;
        p.heading = 90;

        Airplane airplane = new Airplane(p.x, p.y, p.altitude, p.speed, p.heading, p.filename);
        airplane.setRoll(30);
        airplane.setPitch(0.6);

        run(p, airplane, (time) -> {
            if (time >= 36) {
                airplane.setSpeed(150);
                airplane.setPitch(0.55);
            }
            if (time >= 72) {
                airplane.setSpeed(200);
                airplane.setPitch(0.35);
            }
        });
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
