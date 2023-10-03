//=============================================================================================================================================================
/**
 * Defines the simulator that executes the model.
 *
 * DO NOT CHANGE THIS CODE EXCEPT partNum IN MAIN!
 */
public class RaceSimulator
{
   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Executes the simulator.
    *
    * @param arguments - there are no arguments
    */
   public static void main(final String[] arguments)
   {
      RaceSimulator simulator = new RaceSimulator();

      int partNum = 1; // change this to match the part you are running

      simulator.execute(partNum);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates a simulator.
    */
   public RaceSimulator()
   {
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Executes the simulator. Change the arguments as necessary for the parts of the task. It is not necessary to retain the code for each part because we the
    * analysis in the report is what really matter, not this code.
    *
    * @param partNum - the number of the part to run from the task description
    */
   public void execute(final int partNum)
   {
      A_EngineModel engineModelLinear = new EngineModelLinear();
      A_EngineModel engineModelNonlinear = new EngineModelNonlinear();

      A_Dragster dragster;

      double timeStep = 0.1;

      switch (partNum)
      {
         case 1:

            dragster = new DragsterAutomatic(engineModelLinear);

            break;

         case 2:

            dragster = new DragsterAutomatic(engineModelNonlinear);

            break;

         case 3:

            dragster = new DragsterManual(engineModelLinear);

            break;

         case 4:

            dragster = new DragsterManual(engineModelNonlinear);

            break;

         case 5:

            timeStep = 0.01;

            dragster = new DragsterAutomatic(engineModelLinear);

            break;

         default:

            throw new IllegalArgumentException();
      }

      boolean isDone = false;

      while (!isDone)
      {
         isDone = dragster.update(timeStep);
      }
   }
}
