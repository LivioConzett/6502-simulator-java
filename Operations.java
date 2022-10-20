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
     * Immediate addressing mode.<br>
     * Increments the program counter and returns the byte at that place.
     * @return byte to work with
     */
    public byte addr_Immediate(){
        this.memory.incrementProgramCounter();
        return this.memory.getCurrentByte();
    }

    /**
     * Absolute addressing mode.<br>
     * The two following bytes from the program counter hold the low and high byte of an address respectively.
     * Gets the byte at that address.<br>
     * Program counter gets incremented twice.
     * @return Byte at absolute address.
     */
    public byte addr_Absolute(){
        this.memory.incrementProgramCounter();
        byte low = this.memory.getCurrentByte();
        this.memory.incrementProgramCounter();
        byte high = this.memory.getCurrentByte();
        short addr = Util.bytesToAddress(low,high);
        return this.memory.getByteAtAddress(addr);
    }

    /**
     * Zero Page addressing mode.<br>
     * Increment the program counter. The byte at that position is the address on the Zero Page to get the byte from.
     * @return Byte on the Zero Page.
     */
    public byte addr_ZeroPage(){
        this.memory.incrementProgramCounter();
        byte low = this.memory.getCurrentByte();
        short addr = Util.bytesToAddress(low,(byte) 0x00);
        return this.memory.getByteAtAddress(addr);
    }

    /**
     * Absolute Index addressing mode.<br>
     * The two following bytes from the program counter hold the low and high byte of an address respectively.
     * Add a value to that address and then gets the byte at that address.<br>
     * @param addValue Value to add to the address.
     * @return Byte at that address.
     */
    public byte addr_AbsoluteIndex(byte addValue){
        this.memory.incrementProgramCounter();
        byte low = this.memory.getCurrentByte();
        this.memory.incrementProgramCounter();
        byte high = this.memory.getCurrentByte();
        short addr = Util.bytesToAddress(low,high);
        addr = (short) (addr + Util.unsignByte(addValue));
        return this.memory.getByteAtAddress(addr);
    }

    /**
     * Absolute Index X addressing mode.<br>
     * The two following bytes from the program counter hold the low and high byte of an address respectively.
     * Add value of X register to that address and then gets the byte at that address.<br>
     * @return Byte at that address.
     */
    public byte addr_AbsoluteIndex_X(){
        return this.addr_AbsoluteIndex(this.memory.getRegisterX());
    }

    /**
     * Absolute Index Y addressing mode.<br>
     * The two following bytes from the program counter hold the low and high byte of an address respectively.
     * Add value of Y register to that address and then gets the byte at that address.<br>
     * @return Byte at that address.
     */
    public byte addr_AbsoluteIndex_Y(){
        return this.addr_AbsoluteIndex(this.memory.getRegisterY());
    }



    /**
     * Add to accumulator with carry.
     */
    public void ADC(byte number){
        int sum = memory.getRegisterA() + number;
        this.flags.setZero(sum == 0);


    }

}
