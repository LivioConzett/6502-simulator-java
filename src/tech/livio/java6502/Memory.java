package tech.livio.java6502;

import java.util.Arrays;

/**
 * Class that handles all the memory related things.<br>
 * Memory in entire address range.<br>
 * The three registers.<br>
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
class Memory {

    // entire addressable memory space 2^16
    private final byte[] memoryArray;

    // registers
    private byte registerA;
    private byte registerX;
    private byte registerY;

    // counters
    private short programCounter;


    /**
     * Initialize the Memory.
     */
    public Memory(){
        // initialize the memory to the max range of a 16bit address system.
        this.memoryArray = new byte[0x10000];

        // reset the rest.
        this.hardReset();
    }

    /**
     * Reset everything. Like power-cycling the 6502 chip.
     */
    public void hardReset(){

        Arrays.fill(this.memoryArray, (byte) 0x00);

        // program counter always starts at address 0xfffc
        // that's where it gets the address to the start of the program.
        this.programCounter = (short) 0xfffc;

        this.registerA = 0;
        this.registerX = 0;
        this.registerY = 0;

    }

    /**
     * Get the byte at a certain address.
     * @param address Address to get.
     * @return Value of byte at that address.
     */
    public byte getByteAtAddress(short address){
        return this.memoryArray[Util.unsignShort(address)];
    }

    /**
     * Get the byte at the current position of the program counter.
     * @return Byte at address of program counter.
     */
    public byte getCurrentByte(){
        return this.memoryArray[Util.unsignShort(this.programCounter)];
    }

    /**
     * Set the byte at an address to a certain value.
     * @param address Address to set.
     * @param value Value to set.
     */
    public void setByteAtAddress(short address, byte value){
        this.memoryArray[Util.unsignShort(address)] = value;
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
        this.incrementProgramCounter(1);
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

    /**
     * Gets the address stored in the Non Maskable Interrupt vector.<br>
     * 0xfffa - 0xfffb
     * @return address stored in 0xfffa - 0xfffb
     */
    public short getNMIAddress(){
        byte highByte = this.getByteAtAddress((short)0xfffb);
        byte lowByte = this.getByteAtAddress((short)0xfffa);

        return Util.bytesToAddress(lowByte,highByte);
    }

    /**
     * Gets the address stored in the Startup vector.<br>
     * 0xfffc - 0xfffd
     * @return address stored in 0xfffc - 0xfffd
     */
    public short getStartUpAddress(){
        byte highByte = this.getByteAtAddress((short)0xfffd);
        byte lowByte = this.getByteAtAddress((short)0xfffc);

        return Util.bytesToAddress(lowByte,highByte);
    }

    /**
     * Gets the address stored in the break vector.<br>
     * 0xfffe - 0xffff
     * @return address stored in 0xfffe - 0xffff
     */
    public short getBreakAddress(){
        byte highByte = this.getByteAtAddress((short)0xffff);
        byte lowByte = this.getByteAtAddress((short)0xfffe);

        return Util.bytesToAddress(lowByte,highByte);
    }
}