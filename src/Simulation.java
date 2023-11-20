import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Defines the simulation.
 * CHANGE ANYTHING YOU WANT HERE.
 */
public class Simulation {
    private final Random random = new Random(0);

    /**
     * Defines the CEP statistics to be collected for a simulation.
     */
    static class CEPLog {
        private int count = 0;
        private int hits = 0;

        public void add(double shotX, double targetX, double maxDistance) {
            count++;
            if (Math.abs(targetX - shotX) < maxDistance) {
                hits++;
            }
        }

        public double getCEP() {
            return (double) hits / count;
        }
    }

    public static void main(final String[] arguments) throws IOException {
        new Simulation().doTest("2.D.1");
    }

    @SuppressWarnings("SameParameterValue")
    private void doTest(String test) throws IOException {
        switch (test) {
            case "1.A.1" -> doTest1A1();
            case "1.A.2" -> doTest1A2();
            case "1.A.3" -> doTest1A3();
            case "1.A.4" -> doTest1A4();
            case "1.A.5" -> doTest1A5();
            case "1.B.1" -> doTest1B1();
            case "1.B.2" -> doTest1B2();
            case "1.C.1" -> doTest1C1();
            case "1.C.2" -> doTest1C2();
            case "1.D.1" -> doTest1D1();
            case "1.E.1" -> doTest1E1();
            case "1.E.2" -> doTest1E2();
            case "2.A.1" -> doTest2A1();
            case "2.B.1" -> doTest2B1();
            case "2.B.2" -> doTest2B2();
            case "2.C.1" -> doTest2C1();
            case "2.C.2" -> doTest2C2();
            case "2.D.1" -> doTest2D1();
            default -> throw new RuntimeException("bad test " + test);
        }
    }

    /**
     * Runs the simulation on the given artillery.
     * Calls the callback after each update.
     *
     * @param artillery The artillery model.
     * @param timeStep  The time step.
     * @param callback  The callback.
     */
    private void simulateOnUpdate(Artillery artillery, double timeStep, Consumer<Double> callback) {
        boolean isDone = false;
        while (!isDone) {
            isDone = artillery.update(timeStep);
            callback.accept(artillery.getTime());
        }
    }

    /**
     * Runs the simulation on the given bomber.
     * Calls the callback after each update.
     *
     * @param bomber   The bomber model.
     * @param timeStep The time step.
     * @param callback The callback.
     */
    private void simulateOnUpdate(Bomber bomber, double timeStep, Consumer<Double> callback) {
        boolean isDone = false;
        while (!isDone) {
            isDone = bomber.update(timeStep);
            callback.accept(bomber.getTime());
        }
    }

    /**
     * Runs the simulation on the given artillery.
     * Calls the callback after the simulation is done.
     *
     * @param artillery The artillery model.
     * @param timeStep  The time step.
     * @param callback  The callback.
     */
    private void simulateOnDone(Artillery artillery, double timeStep, Consumer<Boolean> callback) {
        boolean isDone = false;
        while (!isDone) {
            isDone = artillery.update(timeStep);
        }
        callback.accept(true);
    }

    /**
     * Runs the simulation on the given bomber.
     * Calls the callback after the simulation is done.
     *
     * @param bomber   The bomber model.
     * @param timeStep The time step.
     * @param callback The callback.
     */
    private void simulateOnDone(Bomber bomber, double timeStep, Consumer<Boolean> callback) {
        boolean isDone = false;
        while (!isDone) {
            isDone = bomber.update(timeStep);
        }
        callback.accept(true);
    }

    private void doTest1A1() {
        int numRuns = 90;
        double timeStep = 0.01;

        for (int i = 5; i < numRuns; i++) {
            final int elevation = i;
            Artillery artillery = new Artillery(elevation, 500, random.nextInt(), 0, 0, 0, 0, 0, 0, 0, false);
            simulateOnDone(artillery, timeStep, (isDone) -> System.out.println("elevation: " + (elevation) + " x: " + artillery.getX()));
        }
    }

    private void doTest1A2() {
        int numRuns = 90;
        double timeStep = 0.01;

        for (double i = 5; i < numRuns; i += 0.1) {
            final double elevation = i;
            Artillery artillery = new Artillery(elevation, 500, random.nextInt(), 0, 0, 0, 0, 0, 0, 0, false);
            simulateOnDone(artillery, timeStep, (isDone) -> System.out.println("elevation: " + (elevation) + " x: " + artillery.getX()));
        }
    }

    private void doTest1A3() {
        int numRuns = 90;
        double timeStep = 0.01;

        for (double i = 5; i < numRuns; i += 0.1) {
            final double elevation = i;
            Artillery artillery = new Artillery(elevation, 500, random.nextInt(), 0, 0, 0, 0, 0, 0, 0, false);
            simulateOnUpdate(artillery, timeStep, (time) -> {
                if (Math.abs(artillery.getX() - 5000) < 5) {
                    System.out.println("elevation: " + (elevation) + " time: " + time + " x: " + artillery.getX() + " y: " + artillery.getY());
                }
            });
        }
    }

    private void doTest1A4() {
        int numRuns = 90;
        double timeStep = 0.1;

        for (int i = 5; i < numRuns; i++) {
            final int elevation = i;
            Artillery artillery = new Artillery(elevation, 500, random.nextInt(), 0, 0, 0, 0, 0, 0, 0, false);
            simulateOnDone(artillery, timeStep, (isDone) -> System.out.println("elevation: " + (elevation) + " x: " + artillery.getX()));
        }
    }

    private void doTest1A5() {
        int numRuns = 90;
        double timeStep = 0.001;

        for (int i = 5; i < numRuns; i++) {
            final int elevation = i;
            Artillery artillery = new Artillery(elevation, 500, random.nextInt(), 0, 0, 0, 0, 0, 0, 0, false);
            simulateOnDone(artillery, timeStep, (isDone) -> System.out.println("elevation: " + (elevation) + " x: " + artillery.getX()));
        }
    }

    private void doTest1B1() {
        CEPLog shotLog = new CEPLog();
        final double targetX = 5000;
        final double maxDistance = 100;
        int numRuns = 1000;
        double timeStep = 0.01;

        for (double variationElevation = 0; variationElevation < 4; variationElevation += 0.1) {
            for (int i = 0; i < numRuns; i++) {
                Artillery artillery = new Artillery(20, 500, random.nextInt(), variationElevation, 0, 0, 0, 0, 0, 0, false);
                simulateOnDone(artillery, timeStep, (isDone) -> shotLog.add(artillery.getX(), targetX, maxDistance));
            }

            System.out.println("variationElevation: " + variationElevation);
            System.out.println("CEP(100): " + shotLog.getCEP());
            System.out.println("Success: " + (shotLog.getCEP() >= 0.5));
        }
    }

    private void doTest1B2() {
        CEPLog shotLog = new CEPLog();
        final double targetX = 5000;
        final double maxDistance = 100;
        int numRuns = 1000;
        double timeStep = 0.01;

        for (double variationInitVelocity = 0; variationInitVelocity < 25; variationInitVelocity += 0.5) {
            for (int i = 0; i < numRuns; i++) {
                Artillery artillery = new Artillery(20, 500, random.nextInt(), 0, variationInitVelocity, 0, 0, 0, 0, 0, false);
                simulateOnDone(artillery, timeStep, (isDone) -> shotLog.add(artillery.getX(), targetX, maxDistance));
            }

            System.out.println("variationInitVelocity: " + variationInitVelocity);
            System.out.println("CEP(100): " + shotLog.getCEP());
            System.out.println("Success: " + (shotLog.getCEP() >= 0.5));
        }
    }

    private void doTest1C1() {
        CEPLog shotLog = new CEPLog();
        final double targetX = 5000;
        final double maxDistance = 100;
        int numRuns = 1000;
        double timeStep = 0.01;

        for (double variationVelocityX = 0; variationVelocityX < 20; variationVelocityX += 0.5) {
            for (int i = 0; i < numRuns; i++) {
                Artillery artillery = new Artillery(20, 500, random.nextInt(), 0, 0, variationVelocityX, 0, 0, 0, 0, false);
                simulateOnDone(artillery, timeStep, (isDone) -> shotLog.add(artillery.getX(), targetX, maxDistance));
            }

            System.out.println("variationVelocityX: " + variationVelocityX);
            System.out.println("CEP(100): " + shotLog.getCEP());
            System.out.println("Success: " + (shotLog.getCEP() >= 0.5));
        }
    }

    private void doTest1C2() {
        CEPLog shotLog = new CEPLog();
        final double targetX = 5000;
        final double maxDistance = 100;
        int numRuns = 1000;
        double timeStep = 0.01;

        for (double variationVelocityY = 0; variationVelocityY < 6; variationVelocityY += 0.1) {
            for (int i = 0; i < numRuns; i++) {
                Artillery artillery = new Artillery(20, 500, random.nextInt(), 0, 0, 0, variationVelocityY, 0, 0, 0, false);
                simulateOnDone(artillery, timeStep, (isDone) -> shotLog.add(artillery.getX(), targetX, maxDistance));
            }

            System.out.println("variationVelocityY: " + variationVelocityY);
            System.out.println("CEP(100): " + shotLog.getCEP());
            System.out.println("Success: " + (shotLog.getCEP() >= 0.5));
        }
    }

    private void doTest1D1() {
        double targetX = 7767.567991333964;
        int numRuns = 1000;
        double timeStep = 0.01;

        for (double acceptableRange = 50; acceptableRange < 500; acceptableRange += 50) {
            CEPLog shotLog = new CEPLog();
            final double maxDistance = acceptableRange;
            for (int i = 0; i < numRuns; i++) {
                Artillery artillery = new Artillery(45, 500, random.nextInt(), 2, 2, 2, 2, 0, 0, 0, false);
                simulateOnDone(artillery, timeStep, (isDone) -> shotLog.add(artillery.getX(), targetX, maxDistance));
            }
            System.out.println("CEP(" + maxDistance + "): " + shotLog.getCEP());
        }
    }

    private void doTest1E1() throws IOException {
        double timeStep = 0.01;
        int elevation = 20;

        // without wind
        FileWriter fileWriter = new FileWriter("1E1-no-wind.csv");
        StringBuilder output = new StringBuilder();
        Artillery artillery = new Artillery(elevation, 500, random.nextInt(), 0, 0, 0, 0, 0, 0, 0, false);
        output.append("time,x,y\n");
        simulateOnUpdate(artillery, timeStep, (time) -> output.append(time).append(",").append(artillery.getX()).append(",").append(artillery.getY()).append("\n"));

        try {
            fileWriter.write(output + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // with wind
        FileWriter fileWriterWind = new FileWriter("1E1-wind.csv");
        StringBuilder outputWind = new StringBuilder();
        Artillery artilleryWind = new Artillery(elevation, 500, random.nextInt(), 0, 0, 0, 0, 0, 15, 15, false);
        outputWind.append("time,x,y\n");
        simulateOnUpdate(artilleryWind, timeStep, (time) -> outputWind.append(time).append(",").append(artilleryWind.getX()).append(",").append(artilleryWind.getY()).append("\n"));

        try {
            fileWriterWind.write(outputWind.toString());
            fileWriterWind.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doTest1E2() throws IOException {
        double timeStep = 0.01;
        int elevation = 20;

        // without wind
        FileWriter fileWriter = new FileWriter("1E2-no-wind.csv");
        StringBuilder output = new StringBuilder();
        Artillery artillery = new Artillery(elevation, 500, random.nextInt(), 0, 0, 0, 0, 0, 0, 0, false);
        output.append("time,x,y\n");
        simulateOnUpdate(artillery, timeStep, (time) -> output.append(time).append(",").append(artillery.getX()).append(",").append(artillery.getY()).append("\n"));

        try {
            fileWriter.write(output + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // with wind
        FileWriter fileWriterWind = new FileWriter("1E2-wind.csv");
        StringBuilder outputWind = new StringBuilder();
        Artillery artilleryWind = new Artillery(elevation, 500, random.nextInt(), 0, 0, 0, 0, 0, -15, -15, false);
        outputWind.append("time,x,y\n");
        simulateOnUpdate(artilleryWind, timeStep, (time) -> outputWind.append(time).append(",").append(artilleryWind.getX()).append(",").append(artilleryWind.getY()).append("\n"));

        try {
            fileWriterWind.write(outputWind.toString());
            fileWriterWind.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doTest2A1() {
        double timeStep = 0.01;

        Bomber bomber500300 = new Bomber(500, 300, random.nextInt(), 0, 0, 0, 0, false);
        Bomber bomber500600 = new Bomber(500, 600, random.nextInt(), 0, 0, 0, 0, false);
        Bomber bomber1000300 = new Bomber(1000, 300, random.nextInt(), 0, 0, 0, 0, false);
        Bomber bomber1000600 = new Bomber(1000, 600, random.nextInt(), 0, 0, 0, 0, false);

        StringBuilder output500300 = new StringBuilder();
        StringBuilder output500600 = new StringBuilder();
        StringBuilder output1000300 = new StringBuilder();
        StringBuilder output1000600 = new StringBuilder();
        output500300.append("time,x,y\n");
        output500600.append("time,x,y\n");
        output1000300.append("time,x,y\n");
        output1000600.append("time,x,y\n");

        simulateOnUpdate(bomber500300, timeStep, (time) -> output500300.append(time).append(",").append(bomber500300.getBombX()).append(",").append(bomber500300.getBombY()).append("\n"));
        simulateOnUpdate(bomber500600, timeStep, (time) -> output500600.append(time).append(",").append(bomber500600.getBombX()).append(",").append(bomber500600.getBombY()).append("\n"));
        simulateOnUpdate(bomber1000300, timeStep, (time) -> output1000300.append(time).append(",").append(bomber1000300.getBombX()).append(",").append(bomber1000300.getBombY()).append("\n"));
        simulateOnUpdate(bomber1000600, timeStep, (time) -> output1000600.append(time).append(",").append(bomber1000600.getBombX()).append(",").append(bomber1000600.getBombY()).append("\n"));

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("2A1-500-300.csv");
            fileWriter.write(output500300.toString());
            fileWriter.close();
            fileWriter = new FileWriter("2A1-500-600.csv");
            fileWriter.write(output500600.toString());
            fileWriter.close();
            fileWriter = new FileWriter("2A1-1000-300.csv");
            fileWriter.write(output1000300.toString());
            fileWriter.close();
            fileWriter = new FileWriter("2A1-1000 -600.csv");
            fileWriter.write(output1000600.toString());
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doTest2B1() {
        double maxDistance = 100;
        double timeStep = 0.01;
        double targetX = 3945;
        int numRuns = 1000;

        for (double variationBomberSpeed = 10; variationBomberSpeed < 25; variationBomberSpeed += 1) {
            CEPLog cepLog = new CEPLog();
            for (int i = 0; i < numRuns; i++) {
                Bomber bomber = new Bomber(1000, 500, random.nextInt(), 0, variationBomberSpeed, 0, 0, false);
                simulateOnDone(bomber, timeStep, (isDone) -> cepLog.add(bomber.getBombX(), targetX, maxDistance));
            }
            System.out.println("variationBomberSpeed: " + variationBomberSpeed);
            System.out.println("CEP 100: " + cepLog.getCEP());
            System.out.println("Success: " + (cepLog.getCEP() >= 0.5));
        }
    }

    private void doTest2B2() {
        double maxDistance = 100;
        double timeStep = 0.01;
        double targetX = 3945;
        int numRuns = 1000;

        for (double variationBomberAltitude = 60; variationBomberAltitude < 80; variationBomberAltitude += 1) {
            CEPLog cepLog = new CEPLog();
            for (int i = 0; i < numRuns; i++) {
                Bomber bomber = new Bomber(1000, 500, random.nextInt(), variationBomberAltitude, 0, 0, 0, false);
                simulateOnDone(bomber, timeStep, (isDone) -> cepLog.add(bomber.getBombX(), targetX, maxDistance));
            }
            System.out.println("variationBomberAltitude: " + variationBomberAltitude);
            System.out.println("CEP 100: " + cepLog.getCEP());
            System.out.println("Success: " + (cepLog.getCEP() >= 0.5));
        }
    }

    private void doTest2C1() {
        double maxDistance = 100;
        double timeStep = 0.01;
        double targetX = 3945;
        int numRuns = 2000;

        for (double variationBombVelocityX = 3; variationBombVelocityX < 8; variationBombVelocityX += 0.5) {
            CEPLog cepLog = new CEPLog();
            for (int i = 0; i < numRuns; i++) {
                Bomber bomber = new Bomber(1000, 500, random.nextInt(), 0, 0, variationBombVelocityX, 0, false);
                simulateOnDone(bomber, timeStep, (isDone) -> cepLog.add(bomber.getBombX(), targetX, maxDistance));
            }
            System.out.println("variationBombVelocityX: " + variationBombVelocityX);
            System.out.println("CEP 100: " + cepLog.getCEP());
            System.out.println("Success: " + (cepLog.getCEP() >= 0.5));
        }
    }

    private void doTest2C2() {
        double maxDistance = 100;
        double timeStep = 0.01;
        double targetX = 3945;
        int numRuns = 2000;

        for (double variationBombVelocityY = 0; variationBombVelocityY < 4; variationBombVelocityY += 0.1) {
            CEPLog cepLog = new CEPLog();
            for (int i = 0; i < numRuns; i++) {
                Bomber bomber = new Bomber(1000, 500, random.nextInt(), 0, 0, 0, variationBombVelocityY, false);
                simulateOnDone(bomber, timeStep, (isDone) -> cepLog.add(bomber.getBombX(), targetX, maxDistance));
            }
            System.out.println("variationBombVelocityY: " + variationBombVelocityY);
            System.out.println("CEP 100: " + cepLog.getCEP());
            System.out.println("Success: " + (cepLog.getCEP() >= 0.5));
        }
    }

    private void doTest2D1() {
        double targetX = 3789.341592927449;
        int numRuns = 1000;
        double timeStep = 0.01;

//        Bomber bomber = new Bomber(1200, 450, random.nextInt(), 5, 5, 5, 5, false);
//        simulateOnDone(bomber, timeStep, (isDone) -> System.out.println(bomber.getBombX()));

        for (double acceptableRange = 50; acceptableRange < 500; acceptableRange += 25) {
            CEPLog shotLog = new CEPLog();
            final double maxDistance = acceptableRange;
            for (int i = 0; i < numRuns; i++) {
                Bomber bomber = new Bomber(1200, 450, random.nextInt(), 5, 5, 5, 5, false);
                simulateOnDone(bomber, timeStep, (isDone) -> shotLog.add(bomber.getBombX(), targetX, maxDistance));
            }
            System.out.println("CEP(" + maxDistance + "): " + shotLog.getCEP());
        }
    }
}
