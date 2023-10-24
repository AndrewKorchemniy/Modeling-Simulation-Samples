import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//=============================================================================================================================================================
/**
 * This is the airplane model.
 *
 * DO NOT CHANGE THIS CODE.
 */
public class Airplane
{
   private static final double TWEAK_XY = 1;
   private static final double TWEAK_ALTITUDE = 0.3;
   private static final double TWEAK_ALTITUDE_LOSS = 1;
   private static final double TWEAK_ROLL = 10;
   private static final double TWEAK_WIND = 1;

   private static final double MINUTES_PER_HOUR = 60;
   private static final double SECONDS_PER_MINUTE = 60;
   private static final double FEET_PER_MILE = 5280;

   private double _time = 0;

   private double _x;
   private double _y;
   private double _altitude;

   private double _pitch = 0;
   private double _roll = 0;
   private double _yaw = 0;

   private double _speed = 0;

   private double _windDirection = 0;
   private double _windSpeed = 0;

   private final BufferedWriter _outfileExcel;
   private final BufferedWriter _outfileGnuplot;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates an airplane.
    *
    * @param x - the x position in feet
    * @param y - the y position in feet
    * @param altitude - the altitude in feet
    * @param speed - the speed in miles per hour
    * @param heading - the initial heading in compass degrees
    * @param filename - the fully qualified filename for the output files. The .csv and .gnu extensions will be added automatically
    *
    * @throws IOException if creating the files fails
    */
   public Airplane(final double x,
                   final double y,
                   final double altitude,
                   final double speed,
                   final double heading,
                   final String filename) throws IOException
   {
      _x = x;
      _y = y;

      _altitude = altitude;

      _speed = speed;
      
      _yaw = heading;

      _outfileExcel = new BufferedWriter(new FileWriter(filename + ".csv"));
      _outfileGnuplot = new BufferedWriter(new FileWriter(filename +".gnu"));

      _outfileExcel.write("time,x,y,altitude,yaw,pitch,roll,speed,wind_dir,wind_spd,dx,dy,wind_dx,wind_dy,vmg_dx,vmg_dy\n");

      _outfileGnuplot.write("# load '" + filename + ".gnu'\n");
      _outfileGnuplot.write("reset\n");
      _outfileGnuplot.write("set xlabel 'x'\n");
      _outfileGnuplot.write("set ylabel 'y'\n");
      _outfileGnuplot.write("set zlabel 'altitude'\n");
      _outfileGnuplot.write("set view equal xy\n");
      _outfileGnuplot.write("set xyplane at 0\n");
      _outfileGnuplot.write("splot '-' with lines\n");
      _outfileGnuplot.newLine();
      _outfileGnuplot.write("-1000 0 0\n");
      _outfileGnuplot.write("+1000 0 0\n");
      _outfileGnuplot.newLine();
      _outfileGnuplot.write("0 -1000 0\n");
      _outfileGnuplot.write("0 +1000 0\n");
      _outfileGnuplot.newLine();
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the current simulation time.
    *
    * @return the time in seconds
    */
   public double getTime()
   {
      return _time;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Sets the pitch.
    *
    * @param pitch - the pitch angle in degrees, with position up (climb)
    */
   public void setPitch(final double pitch)
   {
      _pitch = pitch;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Sets the roll.
    *
    * @param roll - the roll angle in degrees, with position left wing up (clockwise)
    */
   public void setRoll(final double roll)
   {
      _roll = roll;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Sets the speed
    *
    * @param speed - the speed in miles per hour
    */
   public void setSpeed(final double speed)
   {
      _speed = speed;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Sets the wind configuration
    *
    * @param windDirection - the direction from which the wind is coming in compass degrees
    * @param windSpeed - the speed in miles per hour
    */
   public void setWind(final double windDirection, final double windSpeed)
   {
      _windDirection = (windDirection % 360);

      _windSpeed = windSpeed;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Closes the output files.
    *
    * @throws IOException if closing the files fails
    */
   public void terminate() throws IOException
   {
      _outfileGnuplot.write("end\n");

      _outfileExcel.close();
      _outfileGnuplot.close();
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Updates the state of the airplane.
    *
    * @param timeStep - the time in seconds since the previous update
    *
    * @throws IOException if writing the files fails
    */
   public void update(final double timeStep) throws IOException
   {
      double airplaneDeltaDistance = ((_speed / MINUTES_PER_HOUR / SECONDS_PER_MINUTE) * timeStep * FEET_PER_MILE);

      // update altitude
      double airplaneDeltaAltitude = (Math.sin(Math.toRadians(_pitch)) * airplaneDeltaDistance * TWEAK_ALTITUDE);

      _altitude += airplaneDeltaAltitude;

      double airplaneAltitudeLoss = (Math.sin(Math.toRadians(_roll)) * timeStep * TWEAK_ALTITUDE_LOSS); 
      
      _altitude -= airplaneAltitudeLoss;
      
      // update position
      double airplaneDeltaYaw = (Math.sin(Math.toRadians(_roll)) * timeStep * TWEAK_ROLL);

      _yaw += airplaneDeltaYaw;
      _yaw %= 360;

      if (_yaw < 0)
      {
         _yaw += 360;
      }

      double yaw2 = Math.toRadians(90 - _yaw);

      double airplaneDeltaX = (Math.cos(yaw2) * airplaneDeltaDistance * TWEAK_XY);
      double airplaneDeltaY = (Math.sin(yaw2) * airplaneDeltaDistance * TWEAK_XY);

      _x += airplaneDeltaX;
      _y += airplaneDeltaY;

      // update position again for wind
      double windDeltaDistance = ((_windSpeed / MINUTES_PER_HOUR / SECONDS_PER_MINUTE) * timeStep * FEET_PER_MILE);

      double windAngle = Math.toRadians(-_windDirection - 90);

      double windDeltaX = (Math.cos(windAngle) * windDeltaDistance * TWEAK_WIND);
      double windDeltaY = (Math.sin(windAngle) * windDeltaDistance * TWEAK_WIND);

      _x += windDeltaX;
      _y += windDeltaY;

      double vmgDeltaX = (airplaneDeltaX + windDeltaX);
      double vmgDeltaY = (airplaneDeltaY + windDeltaY);

      _outfileExcel.write(_time + "," + _x + "," + _y + "," + _altitude + "," + _yaw + "," + _pitch + "," + _roll + "," + _speed + "," + _windDirection + ","
            + _windSpeed + "," + airplaneDeltaX + "," + airplaneDeltaY + "," + windDeltaX + "," + windDeltaY + "," + vmgDeltaX + "," + vmgDeltaY + "\n");

      _outfileGnuplot.write(_x + " " + _y + " " + _altitude + "\n");

      _time += timeStep;
   }
}
