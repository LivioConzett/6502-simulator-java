/**
 * This class handles the Addressing Modes for the Operations
 * @author Livio Conzett
 * @version 22.10.2022
 */
public class AddressingMode {
    private final Memory memory;

    /**
     * Initialize the class.
     * @param memory Memory object.
     */
    public AddressingMode(Memory memory) {
        this.memory = memory;

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
     * Indirect Absolute addressing mode.<br>
     * Only used for the JMP command.<br>
     * Get an address that is created from the byte saved in the location of the address given and the next
     * consecutive address.
     * @return address
     */
    public short addr_IndirectAbsolute(){
        this.memory.incrementProgramCounter();
        byte low = this.memory.getCurrentByte();
        this.memory.incrementProgramCounter();
        byte high = this.memory.getCurrentByte();
        short addr = Util.bytesToAddress(low,high);
        low = this.memory.getByteAtAddress(addr);
        high = this.memory.getByteAtAddress((short) (addr +1));
        return Util.bytesToAddress(low,high);
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




}
