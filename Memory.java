/**
 * Class that handles all the memory related things.<br>
 * Memory in entire address range.<br>
 * The three registers.<br>
 * Stack (which is within the memory).<br>
 * <table border="1">
 * <tr>
 *     <td> address range </td> <td> what is there</td>
 * </tr>
 * <tr>
 *     <td>0x0000 - 0x00ff</td> <td>Zero Page</td>
 * </tr>
 * <tr>
 *     <td>0x0100 - 0x01ff</td><td>Stack</td>
 * </tr>
 * <tr>
 *     <td>0xfffc - 0xfffd</td><td>place for the address where to start running the program.</td>
 * </tr>
 * </table>
 * @author Livio Conzett
 * @version 16.10.2022
 */
public class Memory {

    // entire addressable memory space 2^16
    private byte[] memory;

    // registers
    private byte registerA;
    private byte registerX;
    private byte registerY;

    // counters
    private short programCounter;
    private byte stackPointer;

    // address of stack
    private final short bottomStack = 0x0100;
    private final short topStack = 0x01ff;

    /**
     * Initialize the Memory.
     */
    public Memory(){
        // initialize the memory to the max range of a 16bit address system.
        this.memory = new byte[0xffff];

        // reset the rest.
        this.hardReset();

    }

    /**
     * Reset everything. Like power-cycling the 6502 chip.
     */
    public void hardReset(){

        for(Byte mem: this.memory){
            mem = 0;
        }

        // program counter always starts at address 0xfffc
        // that's where it gets the address to the start of the program.
        this.programCounter = (byte) 0xfffc;

        this.registerA = 0;
        this.registerX = 0;
        this.registerY = 0;

        // initiate the stackpointer to a "random" number. That's what the real one did too. This is to force
        // the programmer to actually set the stackpointer.
        this.stackPointer = (byte) 0x69;
    }

    /**
     * Get the byte at a certain address.
     * @param address Address to get.
     * @return Value of byte at that address.
     */
    public byte getByteAtAddress(short address){
        return this.memory[address];
    }

    /**
     * Set the byte at an address to a certain value. Has safety in it to ignore the stack range.
     * @param address Address to set.
     * @param value Value to set.
     */
    public void setByteAtAddress(short address, byte value){
        if(address < this.topStack && address > this.bottomStack){
            return;
        }
        this.memory[address] = value;
    }

    /**
     * Get the byte at a certain address in the stack.
     * @param address Address within the stack to get.
     * @return Value of byte at that address.
     */
    public byte getStackByteAtAddress(byte address){
        return this.memory[this.bottomStack + address];
    }

    /**
     * Set the byte in the stack at an address to a certain value.
     * @param address address within stack to set.
     * @param value Value to set to.
     */
    public void setStackByteAtAddress(byte address, byte value){
        this.memory[this.bottomStack + address] = value;
    }

    /**
     * Get the value of the stack pointer
     * @return Value of the stack pointer.
     */
    public byte getStackPointer() {
        return stackPointer;
    }

    /**
     * Set the value of the stack pointer.
     * @param value Value to set the stack pointer to.
     */
    public void setStackPointer(byte value){
        this.stackPointer = value;
    }

    /**
     * Increment the stack pointer by one.
     */
    public void incrementStackPointer(){
        this.stackPointer ++;
    }

    /**
     * Decrements the stack pointer by one.
     */
    public void decrementStackPointer(){
        this.stackPointer --;
    }

    /**
     * Get the value of the program counter.
     * @return Value of the program counter.
     */
    public short getProgramCounter(){
        return this.programCounter;
    }

    /**
     * Set the value of the program counter.
     * @param value value to set the program counter to.
     */
    public void setProgramCounter(short value){
        this.programCounter = value;
    }

    /**
     * Increments the program counter by a certain amount.
     * @param amount amount to increment the program counter to.
     */
    public void incrementProgramCounter(int amount){
        this.programCounter += amount;
    }

    /**
     * Increments the program counter by one.
     */
    public void incrementProgramCounter(){
        this.programCounter ++;
    }

    /**
     * Set the value of register A
     * @param registerA Value to set register A to.
     */
    public void setRegisterA(byte registerA) {
        this.registerA = registerA;
    }

    /**
     * Get the value of register A.
     * @return Value of register A.
     */
    public byte getRegisterA(){
        return this.registerA;
    }

    /**
     * Set the value of register X
     * @param registerX Value to set register X to.
     */
    public void setRegisterX(byte registerX) {
        this.registerX = registerX;
    }

    /**
     * Get the value of register X.
     * @return Value of register X.
     */
    public byte getRegisterX(){
        return this.registerX;
    }

    /**
     * Set the value of register Y
     * @param registerY Value to set register Y to.
     */
    public void setRegisterY(byte registerY) {
        this.registerY = registerY;
    }

    /**
     * Get the value of register Y.
     * @return Value of register Y.
     */
    public byte getRegisterY(){
        return this.registerY;
    }
}
