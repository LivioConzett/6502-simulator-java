/**
 * Class with all the CPU Operations.
 * @author Livio Conzett
 * @version 16.10.2022
 */
public class Operations {

    private final Memory memory;
    private final Stack stack;
    private final Flags flags;

    /**
     * Initialize the Class.
     * @param memory Memory object
     */
    public Operations(Memory memory, Stack stack, Flags flags){
        this.memory = memory;
        this.stack = stack;
        this.flags = flags;
    }

    

}
