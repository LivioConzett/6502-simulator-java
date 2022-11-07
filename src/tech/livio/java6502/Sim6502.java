package tech.livio.java6502;

/**
 * Class that can simulate a 6502 processor system.
 * @author Livio Conzett
 * @version 16.10.2022
 */
public class Sim6502 {

    private final Memory memory;
    private final Stack stack;
    private final Flags flags;
    private final InstructionSet instructionSet;

    /**
     * Initialize the 6502 simulator
     */
    public Sim6502(){
        this.memory = new Memory();
        this.stack = new Stack(this.memory);
        this.flags = new Flags();
        this.instructionSet = new InstructionSet(this.memory, this.stack, this.flags);
    }

    /**
     * Takes a Byte and runs the corresponding instruction
     * @param instruction byte to run.
     */
    void runInstruction(byte instruction){
        //TODO: makes switch case
    }

}
