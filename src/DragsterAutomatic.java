//=============================================================================================================================================================
/**
 * Defines the dragster with the automatic transmission as describe in the task document. Add your solution here. Get this one to work first, then copy it to
 * DragsterManual and modify it there.
 * 
 * IMPLEMENT PARTS 1 AND 2 HERE.
 */
public class DragsterAutomatic extends A_Dragster
{
   /** the target RPM to shift at */
   private static final int SHIFT_RPM = 9000;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates a dragster with an automatic transmission
    *
    * @param engineModel - the engine model
    */
   public DragsterAutomatic(final A_EngineModel engineModel)
   {
      super(engineModel);

      System.out.println("time,distance,speed,gear_num,rpm");
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * {@inheritDoc}
    *
    * Implement Parts 1 and 2 here. There should be no difference in the code based on the engine model.
    */
   @Override
   public boolean update(final double timeStep)
   {
      super.update(timeStep);

      // IMPLEMENT YOUR SOLUTION HERE

      boolean isDone = false;
      
      return isDone;
   }
}
