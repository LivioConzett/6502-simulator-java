package java6502;

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
    public byte immediate(){
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
    public byte absolute(){
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
    public byte zeroPage(){
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
    public short indirectAbsolute(){
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
    public byte absoluteIndex(byte addValue){
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
    public byte absoluteIndex_X(){
        return this.absoluteIndex(this.memory.getRegisterX());
    }

    /**
     * Absolute Index Y addressing mode.<br>
     * The two following bytes from the program counter hold the low and high byte of an address respectively.
     * Add value of Y register to that address and then gets the byte at that address.<br>
     * @return Byte at that address.
     */
    public byte absoluteIndex_Y(){
        return this.absoluteIndex(this.memory.getRegisterY());
    }

    /**
     * Zero Page Index addressing mode.<br>
     * The following byte from the program counter holds the address on the Zero Page Memory.
     * Add a value to that address and then gets the byte at that address.<br>
     * @param addValue Value to add to the address.
     * @return Byte at that address.
     */
    public byte zeroPageIndex(byte addValue){
        this.memory.incrementProgramCounter();
        byte low = this.memory.getCurrentByte();
        short addr = Util.bytesToAddress(low,(byte)0x00);
        addr = (short) (addr + Util.unsignByte(addValue));
        return this.memory.getByteAtAddress(addr);
    }

    /**
     * Zero Page Index X addressing mode.<br>
     * The following byte from the program counter holds an address in the Zero Page Memory.
     * Add value of X register to that address and then gets the byte at that address.<br>
     * @return Byte at that address.
     */
    public byte zeroPageIndex_X(){
        return this.zeroPageIndex(this.memory.getRegisterX());
    }

    /**
     * Zero Page Index Y addressing mode.<br>
     * The following byte from the program counter holds an address in the Zero Page Memory.
     * Add value of Y register to that address and then gets the byte at that address.<br>
     * @return Byte at that address.
     */
    public byte zeroPageIndex_Y(){
        return this.zeroPageIndex(this.memory.getRegisterY());
    }

    /**
     * Indexed Indirect Addressing.<br>
     * The following byte from the program counter plus the x register is the zero page address of the low byte
     * of an absolut address. The byte after that is the high byte. This returns the byte at that Absolute address.
     * @return byte at the address.
     */
    public byte indexedIndirect(){
        this.memory.incrementProgramCounter();
        byte base = this.memory.getCurrentByte();
        byte basePlus = (byte) (base + Util.unsignByte(this.memory.getRegisterX()));
        byte low = this.memory.getByteAtAddress(basePlus);
        byte high = this.memory.getByteAtAddress((short)(basePlus + 1));
        short addr = Util.bytesToAddress(low,high);
        return this.memory.getByteAtAddress(addr);
    }

    /**
     * Indirect Indexed Addressing.<br>
     * The following byte from the program counter is the zero page address of the low byte
     * of an absolut address. The byte after that is the high byte. This returns the byte at that Absolute address.
     * Then it adds the byte in the y register to the Absolute address and returns the byte at the addresses place.
     * @return byte at the address.
     */
    public byte indirectIndexed(){
        this.memory.incrementProgramCounter();
        byte low = this.memory.getByteAtAddress(this.memory.getCurrentByte());
        byte high = this.memory.getByteAtAddress((short)(this.memory.getCurrentByte() + 1));
        short addr = (short)((Util.bytesToAddress(low,high) + Util.unsignByte(this.memory.getRegisterY())));
        return this.memory.getByteAtAddress(addr);
    }

    /**
     * Relative Addressing mode.<br>
     * Increments the program counter and gets the byte at that position. That byte is the distance to jump
     * from the program counter (positive or negative). Returns the new address.
     * @return address.
     */
    public short relative(){
        this.memory.incrementProgramCounter();
        byte dif = this.memory.getCurrentByte();
        return (short)(this.memory.getProgramCounter() + dif);
    }

}
