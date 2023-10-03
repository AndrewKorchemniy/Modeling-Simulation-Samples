//=============================================================================================================================================================
/**
 * Defines the shared elements of an engine model that correlates time in a gear with an RPM.
 *
 * DO NOT CHANGE THIS CODE!
 */
public abstract class A_EngineModel
{
   /** the minimum RPM for the engine to be running */
   protected static final int RPM_MIN = 1000;

   /** the performance characteristics in each gear, as defined by the subclasses */
   protected final double[] _loadRatios;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates an engine model.
    *
    * @param loadRatios - the performance characteristics in each gear, as defined by the subclasses. The number of elements dictates the number of gears.
    */
   protected A_EngineModel(final double[] loadRatios)
   {
      if (loadRatios == null)
      {
         throw new NullPointerException("loadRatios");
      }

      if (loadRatios.length < 1)
      {
         throw new IllegalArgumentException("loadRatios " + loadRatios.length);
      }

      _loadRatios = loadRatios;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the number of gears.
    *
    * @return the number
    */
   public int getGearCount()
   {
      return _loadRatios.length;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the RPM in a gear at a time. This is the mathematical inverse of {@link #getTimeInGear}.
    *
    * @param timeInGear - the time in gear in seconds starting from zero
    * @param gearNum - the gear number
    *
    * @return the RPM
    */
   public abstract int getRPM(final double timeInGear, final int gearNum);

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the time what which an RPM is reached in a gear. This is the mathematical inverse of {@link #getRPM}.
    *
    * @param rpm - the RPM
    * @param gearNum - the gear number
    *
    * @return the time
    */
   public abstract double getTimeInGear(final double rpm, final int gearNum);

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Validates a gear.
    *
    * @param gearNum - the gear number
    *
    * @throws IllegalArgumentException if the gear is not valid
    */
   protected void validateGear(final int gearNum)
   {
      if ((gearNum < 1) || (gearNum > _loadRatios.length))
      {
         throw new IllegalArgumentException("gearNum " + gearNum);
      }
   }
}
