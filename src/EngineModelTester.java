//=============================================================================================================================================================
/**
 * Defines a tester to generate the performance profile for engine models. You do not need to use this. It is provided only as a reference to show where the
 * data in the spreadsheets came from.
 *
 * DO NOT CHANGE THIS CODE!
 */
public class EngineModelTester
{
   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Executes the tester
    *
    * @param arguments - there are no arguments
    */
   public static void main(final String[] arguments)
   {
      A_EngineModel engineModelLinear = new EngineModelLinear();
      A_EngineModel engineModelNonlinear = new EngineModelNonlinear();

      EngineModelTester testerLinear = new EngineModelTester(engineModelLinear);

      testerLinear.testModel();

      EngineModelTester testerNonlinear = new EngineModelTester(engineModelNonlinear);

      testerNonlinear.testModel();
   }

   /** the engine model being tested */
   private final A_EngineModel _engineModel;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates a model tester
    *
    * @param engineModel - the engine model being tested
    */
   public EngineModelTester(final A_EngineModel engineModel)
   {
      if (engineModel == null)
      {
         throw new NullPointerException();
      }

      _engineModel = engineModel;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Generates the <i>RPM vs Time in Gear</i> graph for the engine model.
    *
    * @param timeInGearMax - the time to simulate in seconds, start at 0
    * @param timeInGearStep - the time step
    * @param gearMax - the highest gear to shift into, starting at 1
    */
   private void testGetRPM(final double timeInGearMax, final double timeInGearStep, final int gearMax)
   {
      if (timeInGearMax <= 0)
      {
         throw new IllegalArgumentException("timeInGearMax " + timeInGearMax);
      }
      if (timeInGearStep <= 0)
      {
         throw new IllegalArgumentException("timeInGearStep " + timeInGearStep);
      }

      System.out.println();
      System.out.println("RPM vs Time in Gear");
      System.out.println("-------------------");

      System.out.print("time_in_gear,");

      for (int gearNum = 1; gearNum <= gearMax; gearNum++)
      {
         System.out.print("gear_" + gearNum + "_rpm,");
      }

      System.out.println();

      for (double timeInGear = 0; timeInGear <= timeInGearMax; timeInGear += timeInGearStep)
      {
         System.out.print(timeInGear + ",");

         for (int gearNum = 1; gearNum <= gearMax; gearNum++)
         {
            double rpm = _engineModel.getRPM(timeInGear, gearNum);

            System.out.print(rpm + ",");
         }

         System.out.println();
      }
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Generates the <i>Time in Gear vs RPM</i> graph for the engine model.
    *
    * @param rpmMin - the minimum RPM to start at
    * @param rpmMax - the maximum RPM to reach
    * @param rpmStep - the RPM step from minimum to maximum
    * @param gearMax - the highest gear to shift into, starting at 1
    */
   private void testGetTimeInGear(final int rpmMin, final int rpmMax, final double rpmStep, final int gearMax)
   {
      if (rpmMin < 0)
      {
         throw new IllegalArgumentException("rpmMin " + rpmMin);
      }
      if (rpmMax < rpmMin)
      {
         throw new IllegalArgumentException("rpmMax " + rpmMax);
      }
      if (rpmStep <= 0)
      {
         throw new IllegalArgumentException("rpmStep " + rpmStep);
      }

      System.out.println();
      System.out.println("Time in Gear vs RPM");
      System.out.println("-------------------");

      System.out.print("rpm,");

      for (int gearNum = 1; gearNum <= gearMax; gearNum++)
      {
         System.out.print("gear_" + gearNum + "_time_in_gear,");
      }

      System.out.println();

      for (double rpm = rpmMin; rpm <= rpmMax; rpm += rpmStep)
      {
         System.out.print(rpm + ",");

         for (int gearNum = 1; gearNum <= gearMax; gearNum++)
         {
            double timeInGear = _engineModel.getTimeInGear(rpm, gearNum);

            System.out.print(timeInGear + ",");
         }

         System.out.println();
      }
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Tests the model.
    */
   public void testModel()
   {
      int rpmMin = A_EngineModel.RPM_MIN;
      int gearCount = _engineModel.getGearCount();

      EngineModelTester tester = new EngineModelTester(_engineModel);

      double timeInGearMax = 6;
      double timeInGearStep = 0.1;

      tester.testGetRPM(timeInGearMax, timeInGearStep, gearCount);

      int rpmMax = 10000;
      int rpmStep = 50;

      tester.testGetTimeInGear(rpmMin, rpmMax, rpmStep, gearCount);
   }
}
