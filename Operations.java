/**
 * Class with all the CPU Operations. <br>
 * For more info on each of the Operations visit: <br>
 * https://6502.livio.tech/instructionset/
 * @author Livio Conzett
 * @version 16.10.2022
 */
public class Operations {

    private final Memory memory;
    private final Stack stack;
    private final Flags flags;

    /**
     * Initialize tha class
     * @param memory Memory object.
     * @param stack Stack object.
     * @param flags Flags object.
     */
    public Operations(Memory memory, Stack stack, Flags flags){
        this.memory = memory;
        this.stack = stack;
        this.flags = flags;
    }


    /**
     * Add to accumulator with carry.
     */
    public void ADC(byte number){
        int sum = memory.getRegisterA() + number;
        this.flags.setZero(sum == 0);


    }

}
