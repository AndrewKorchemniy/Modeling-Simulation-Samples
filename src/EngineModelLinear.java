//=============================================================================================================================================================
/**
 * Defines a linear engine model that correlates time in a gear with an RPM.
 *
 * DO NOT CHANGE THIS CODE!
 */
public class EngineModelLinear extends A_EngineModel
{
   /** the unitless slopes for each line defining a gear (as rise in RPM over run in time) */
   private static final double[] LOAD_RATIOS = { 5000, 4000, 3000, 2000, 1500 };

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates a linear engine model.
    */
   public EngineModelLinear()
   {
      super(LOAD_RATIOS);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * {@inheritDoc}
    */
   @Override
   public int getRPM(final double timeInGear, final int gearNum)
   {
      validateGear(gearNum);

      double gearRatio = LOAD_RATIOS[gearNum - 1];

      int rpm = (int) Math.round((gearRatio * timeInGear) + RPM_MIN); // y = mx + b

      return rpm;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * {@inheritDoc}
    */
   @Override
   public double getTimeInGear(final double rpm, final int gearNum)
   {
      validateGear(gearNum);

      double gearRatio = LOAD_RATIOS[gearNum - 1];

      double timeInGear = ((rpm - RPM_MIN) / gearRatio); // x = (y - b) / m

      return timeInGear;
   }
}
