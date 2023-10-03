//=============================================================================================================================================================
/**
 * Defines the shared elements of a dragster.
 *
 * DO NOT CHANGE THIS CODE!
 */
public abstract class A_Dragster
{
   /** the gear ratios for each gear */
   protected static final double[] GEAR_RATIOS = { 0.5, 0.75, 1.2, 1.4, 1.9 };

   /** the circumference of the drive wheels in feet */
   protected static final double FEET_PER_REVOLUTION = 3.14;

   /** the length of the track */
   protected static final double TRACK_LENGTH_FEET = 5280;

   protected static final double SECONDS_PER_MINUTE = 60;
   protected static final double MINUTES_PER_HOUR = 60;
   protected static final double FEET_PER_MILE = 5280;

   /** the engine model */
   protected final A_EngineModel _engineModel;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates a dragster.
    *
    * @param engineModel - the engine model
    */
   public A_Dragster(final A_EngineModel engineModel)
   {
      if (engineModel == null)
      {
         throw new NullPointerException();
      }

      _engineModel = engineModel;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Updates the position and speed of the dragster based on the time elapsed since the previous update.
    *
    * @param timeStep - the elapsed time in seconds
    *
    * @return whether the dragster has reached the end of the track
    */
   public boolean update(final double timeStep)
   {
      if (timeStep <= 0)
      {
         throw new IllegalArgumentException("timeStep " + timeStep);
      }

      return false;
   }
}
