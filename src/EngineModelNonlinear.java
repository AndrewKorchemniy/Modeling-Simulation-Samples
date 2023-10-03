//=============================================================================================================================================================
/**
 * Defines a nonlinear engine model that correlates time in a gear with an RPM.
 *
 * DO NOT CHANGE THIS CODE!
 */
public class EngineModelNonlinear extends A_EngineModel
{
   /** the unitless curve coefficients for each line defining a gear (as rise in RPM over run in time) */
   private static final double[] LOAD_RATIOS = { 2832, 2646, 2450, 2198, 2057 };

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates a nonlinear engine model.
    */
   public EngineModelNonlinear()
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

      int rpm = (int) Math.round((Math.log(timeInGear * 15) * gearRatio));

      if (rpm < RPM_MIN)
      {
         rpm = RPM_MIN;
      }

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

      double timeInGear = (Math.exp(rpm / gearRatio) / 15.0);

      return timeInGear;
   }
}
