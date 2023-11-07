import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//=============================================================================================================================================================
//
// IMPLEMENT THIS CLASS HOWEVER YOU WANT, BUT DO NOT CHANGE THE SIGNATURES OF Analyzer(), addEntry(), or printResults(). YOU MUST USE addEntry() TO GET THE
// DATA; DO NOT ACCESS OTHER CLASSES IN THE PROJECT (UNLESS THEY ARE YOURS).
//
public class Analyzer {
    static class BodyAnalytics {
        public double totalDistance;
        public double totalTime;
        public double minVelocity = Double.MAX_VALUE;
        public double maxVelocity = Double.MIN_VALUE;
        public double minAcceleration = Double.MAX_VALUE;
        public double maxAcceleration = Double.MIN_VALUE;
        private double totalVelocity;
        private double totalAcceleration;
        private double prevX = Double.NaN;
        private double prevY = Double.NaN;
        private double prevTime = Double.NaN;
        private double prevVelocity = Double.NaN;
        private final List<Double> velocities = new ArrayList<>();
        private final List<Double> accelerations = new ArrayList<>();

        public void addEntry(final double x, final double y, final double time) {
            totalTime = time;
            if (!Double.isNaN(prevX) && !Double.isNaN(prevY) && !Double.isNaN(prevTime)) {
                double timeChange = time - prevTime;
                double distance = Math.sqrt(Math.pow(x - prevX, 2) + Math.pow(y - prevY, 2));
                totalDistance += distance;

                double velocity = distance / timeChange;
                if (velocity < minVelocity) minVelocity = velocity;
                if (velocity > maxVelocity) maxVelocity = velocity;
                totalVelocity += velocity;
                velocities.add(velocity);

                if (!Double.isNaN(prevVelocity)) {
                    double acceleration = (velocity - prevVelocity) / timeChange;
                    if (acceleration < minAcceleration) minAcceleration = acceleration;
                    if (acceleration > maxAcceleration) maxAcceleration = acceleration;
                    totalAcceleration += acceleration;
                    accelerations.add(acceleration);
                }
                prevVelocity = velocity;
            }
            prevX = x;
            prevY = y;
            prevTime = time;
        }

        public double getAvgVelocity() {
            if (velocities.size() == 0) return 0;
            return totalVelocity / velocities.size();
        }

        public double getAvgAcceleration() {
            if (accelerations.size() == 0) return 0;
            return totalAcceleration / accelerations.size();
        }

        public double getStdVelocity() {
            if (velocities.size() == 0) return 0;
            double avg = getAvgVelocity();
            double sum = 0;
            for (double velocity : velocities) {
                sum += Math.pow(velocity - avg, 2);
            }
            return Math.sqrt(sum / velocities.size());
        }

        public double getStdAcceleration() {
            if (accelerations.size() == 0) return 0;
            double avg = getAvgAcceleration();
            double sum = 0;
            for (double acceleration : accelerations) {
                sum += Math.pow(acceleration - avg, 2);
            }
            return Math.sqrt(sum / accelerations.size());
        }
    }

    private final Map<String, BodyAnalytics> bodies = new HashMap<>();

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public void addEntry(final long step, final double time, final String id, final double x, final double y) {
        BodyAnalytics body = bodies.get(id);
        if (body == null) {
            body = new BodyAnalytics();
            bodies.put(id, body);
        }
        body.addEntry(x, y, time);
         System.out.println("<log>: " + step + "," + time + "," + id + "," + x + "," + y);
        // Use the println statement above to slow the program down if you want to see the output in real time.
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public void printResults() {
        for (Map.Entry<String, BodyAnalytics> entry : bodies.entrySet()) {
            String id = entry.getKey();
            BodyAnalytics body = entry.getValue();
            System.out.println("Body: " + id);
            System.out.println("Time: " + Math.round(body.totalTime));
            System.out.println("Distance: " + Math.round(body.totalDistance * 100.0) / 100.0);
            System.out.println("Velocity");
            System.out.println("  min: " + body.minVelocity);
            System.out.println("  max: " + body.maxVelocity);
            System.out.println("  avg: " + body.getAvgVelocity());
            System.out.println("  std: " + body.getStdVelocity());
            System.out.println("Acceleration");
            System.out.println("  min: " + body.minAcceleration);
            System.out.println("  max: " + body.maxAcceleration);
            System.out.println("  avg: " + body.getAvgAcceleration());
            System.out.println("  std: " + body.getStdAcceleration());
            System.out.println();
        }
    }
}
