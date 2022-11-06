package tech.livio.java6502;

/**
 * This class handles the Addressing Modes for the Operations
 * @author Livio Conzett
 * @version 22.10.2022
 */
class AddressingMode {
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
    public AddressingModeReturn immediate(){
        this.memory.incrementProgramCounter();
        return new AddressingModeReturn(this.memory.getCurrentByte(),this.memory.getProgramCounter());
    }

    /**
     * Absolute addressing mode.<br>
     * The two following bytes from the program counter hold the low and high byte of an address respectively.
     * Gets the byte at that address.<br>
     * Program counter gets incremented twice.
     * @return Byte at absolute address.
     */
    public AddressingModeReturn absolute(){
        this.memory.incrementProgramCounter();
        byte low = this.memory.getCurrentByte();
        this.memory.incrementProgramCounter();
        byte high = this.memory.getCurrentByte();
        short addr = Util.bytesToAddress(low,high);
        return new AddressingModeReturn(this.memory.getByteAtAddress(addr),addr);
    }

    /**
     * Zero Page addressing mode.<br>
     * Increment the program counter. The byte at that position is the address on the Zero Page to get the byte from.
     * @return Byte on the Zero Page.
     */
    public AddressingModeReturn zeroPage(){
        this.memory.incrementProgramCounter();
        byte low = this.memory.getCurrentByte();
        short addr = Util.bytesToAddress(low,(byte) 0x00);
        return new AddressingModeReturn(this.memory.getByteAtAddress(addr),addr);
    }

    /**
     * Indirect Absolute addressing mode.<br>
     * Only used for the JMP command.<br>
     * Get an address that is created from the byte saved in the location of the address given and the next
     * consecutive address.
     * @return address
     */
    public AddressingModeReturn indirectAbsolute(){
        this.memory.incrementProgramCounter();
        byte low = this.memory.getCurrentByte();
        this.memory.incrementProgramCounter();
        byte high = this.memory.getCurrentByte();
        short addr = Util.bytesToAddress(low,high);
        low = this.memory.getByteAtAddress(addr);
        high = this.memory.getByteAtAddress((short) (addr +1));
        short finalAddr = Util.bytesToAddress(low,high);
        return new AddressingModeReturn(this.memory.getByteAtAddress(finalAddr),finalAddr);
    }

    /**
     * Absolute Index addressing mode.<br>
     * The two following bytes from the program counter hold the low and high byte of an address respectively.
     * Add a value to that address and then gets the byte at that address.<br>
     * @param addValue Value to add to the address.
     * @return Byte at that address.
     */
    public AddressingModeReturn absoluteIndex(byte addValue){
        this.memory.incrementProgramCounter();
        byte low = this.memory.getCurrentByte();
        this.memory.incrementProgramCounter();
        byte high = this.memory.getCurrentByte();
        short addr = Util.bytesToAddress(low,high);
        addr = (short) (addr + Util.unsignByte(addValue));
        return new AddressingModeReturn(this.memory.getByteAtAddress(addr),addr);
    }

    /**
     * Absolute Index X addressing mode.<br>
     * The two following bytes from the program counter hold the low and high byte of an address respectively.
     * Add value of X register to that address and then gets the byte at that address.<br>
     * @return Byte at that address.
     */
    public AddressingModeReturn absoluteIndex_X(){
        return this.absoluteIndex(this.memory.getRegisterX());
    }

    /**
     * Absolute Index Y addressing mode.<br>
     * The two following bytes from the program counter hold the low and high byte of an address respectively.
     * Add value of Y register to that address and then gets the byte at that address.<br>
     * @return Byte at that address.
     */
    public AddressingModeReturn absoluteIndex_Y(){
        return this.absoluteIndex(this.memory.getRegisterY());
    }

    /**
     * Zero Page Index addressing mode.<br>
     * The following byte from the program counter holds the address on the Zero Page Memory.
     * Add a value to that address and then gets the byte at that address.<br>
     * @param addValue Value to add to the address.
     * @return Byte at that address.
     */
    public AddressingModeReturn zeroPageIndex(byte addValue){
        this.memory.incrementProgramCounter();
        byte low = this.memory.getCurrentByte();
        short addr = Util.bytesToAddress(low,(byte)0x00);
        addr = (short) (addr + Util.unsignByte(addValue));
        return new AddressingModeReturn(this.memory.getByteAtAddress(addr),addr);
    }

    /**
     * Zero Page Index X addressing mode.<br>
     * The following byte from the program counter holds an address in the Zero Page Memory.
     * Add value of X register to that address and then gets the byte at that address.<br>
     * @return Byte at that address.
     */
    public AddressingModeReturn zeroPageIndex_X(){
        return this.zeroPageIndex(this.memory.getRegisterX());
    }

    /**
     * Zero Page Index Y addressing mode.<br>
     * The following byte from the program counter holds an address in the Zero Page Memory.
     * Add value of Y register to that address and then gets the byte at that address.<br>
     * @return Byte at that address.
     */
    public AddressingModeReturn zeroPageIndex_Y(){
        return this.zeroPageIndex(this.memory.getRegisterY());
    }

    /**
     * Indexed Indirect Addressing.<br>
     * The following byte from the program counter plus the x register is the zero page address of the low byte
     * of an absolut address. The byte after that is the high byte. This returns the byte at that Absolute address.
     * @return byte at the address.
     */
    public AddressingModeReturn indexedIndirect(){
        this.memory.incrementProgramCounter();
        byte base = this.memory.getCurrentByte();
        byte basePlus = (byte) (base + Util.unsignByte(this.memory.getRegisterX()));
        byte low = this.memory.getByteAtAddress(basePlus);
        byte high = this.memory.getByteAtAddress((short)(basePlus + 1));
        short addr = Util.bytesToAddress(low,high);
        return new AddressingModeReturn(this.memory.getByteAtAddress(addr),addr);
    }

    /**
     * Indirect Indexed Addressing.<br>
     * The following byte from the program counter is the zero page address of the low byte
     * of an absolut address. The byte after that is the high byte. This returns the byte at that Absolute address.
     * Then it adds the byte in the y register to the Absolute address and returns the byte at the addresses place.
     * @return byte at the address.
     */
    public AddressingModeReturn indirectIndexed(){
        this.memory.incrementProgramCounter();
        byte low = this.memory.getByteAtAddress(this.memory.getCurrentByte());
        byte high = this.memory.getByteAtAddress((short)(this.memory.getCurrentByte() + 1));
        short addr = (short)((Util.bytesToAddress(low,high) + Util.unsignByte(this.memory.getRegisterY())));
        return new AddressingModeReturn(this.memory.getByteAtAddress(addr),addr);
    }

    /**
     * Relative Addressing mode.<br>
     * Increments the program counter and gets the byte at that position. That byte is the distance to jump
     * from the program counter (positive or negative). Returns the new address.
     * @return address.
     */
    public AddressingModeReturn relative(){
        this.memory.incrementProgramCounter();
        byte dif = this.memory.getCurrentByte();
        short addr = (short)(this.memory.getProgramCounter() + dif);
        return new AddressingModeReturn(this.memory.getByteAtAddress(addr),addr);
    }

}
