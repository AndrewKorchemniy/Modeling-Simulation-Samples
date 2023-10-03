import java.util.Random;

//=============================================================================================================================================================
/**
 * Defines the dragster with the manual transmission as describe in the task document. Add your solution here. Get DragsterAutomatic to work first, then copy it
 * here and modify it.
 * 
 * IMPLEMENT PARTS 3 AND 4 HERE.
 */
public class DragsterManual extends A_Dragster
{
   /** the target RPM to shift at */
   private static final int SHIFT_RPM = 9000;

   /** the human response time +/- */
   private static final double SHIFT_RESPONSE_SECONDS = 0.125;

   /** the random number generator; change the seed to meet your needs */
   private final Random _random = new Random(1);

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates a dragster with a manual transmission
    *
    * @param engineModel - the engine model
    */
   public DragsterManual(final A_EngineModel engineModel)
   {
      super(engineModel);

      System.out.println("time,distance,speed,gear_num,rpm,shift_rpm");
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * {@inheritDoc}
    *
    * Implement Parts 3 and 4 here.
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
