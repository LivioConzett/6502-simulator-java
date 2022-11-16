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
    Memory(){
        // initialize the memory to the max range of a 16bit address system.
        this.memoryArray = new byte[0x10000];

        // reset the rest.
        this.hardReset();
    }

    /**
     * Reset everything. Like power-cycling the 6502 chip.
     */
    void hardReset(){

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
    byte getByteAtAddress(short address){
        return this.memoryArray[Util.unsignShort(address)];
    }

    /**
     * Get the byte at the current position of the program counter.
     * @return Byte at address of program counter.
     */
    byte getCurrentByte(){
        return this.memoryArray[Util.unsignShort(this.programCounter)];
    }

    /**
     * Get a range of memory.
     * @param lowAddress address to start at
     * @param highAddress address to end at
     * @return byte array of the memory between low and high address
     */
    byte[] getMemoryRange(short lowAddress, short highAddress){
        if(highAddress <= lowAddress){
            return new byte[0];
        }
        // +1 because the highAddress needs to be included.
        int size = highAddress - lowAddress + 1;
        byte[] memoryRange = new byte[size];

        System.arraycopy(this.memoryArray, lowAddress, memoryRange, 0, size);

        return memoryRange;
    }

    /**
     * Set the byte at an address to a certain value.
     * @param address Address to set.
     * @param value Value to set.
     */
    void setByteAtAddress(short address, byte value){
        this.memoryArray[Util.unsignShort(address)] = value;
    }

    /**
     * Get the value of the program counter.
     * @return Value of the program counter.
     */
    short getProgramCounter(){
        return this.programCounter;
    }

    /**
     * Set the value of the program counter.
     * @param value value to set the program counter to.
     */
    void setProgramCounter(short value){
        this.programCounter = value;
    }

    /**
     * Increments the program counter by a certain amount.
     * @param amount amount to increment the program counter to.
     */
    void incrementProgramCounter(int amount){
        this.programCounter += amount;
    }

    /**
     * Increments the program counter by one.
     */
    void incrementProgramCounter(){
        this.incrementProgramCounter(1);
    }

    /**
     * Set the value of register A
     * @param registerA Value to set register A to.
     */
    void setRegisterA(byte registerA) {
        this.registerA = registerA;
    }

    /**
     * Get the value of register A.
     * @return Value of register A.
     */
    byte getRegisterA(){
        return this.registerA;
    }

    /**
     * Set the value of register X
     * @param registerX Value to set register X to.
     */
    void setRegisterX(byte registerX) {
        this.registerX = registerX;
    }

    /**
     * Get the value of register X.
     * @return Value of register X.
     */
    byte getRegisterX(){
        return this.registerX;
    }

    /**
     * Set the value of register Y
     * @param registerY Value to set register Y to.
     */
    void setRegisterY(byte registerY) {
        this.registerY = registerY;
    }

    /**
     * Get the value of register Y.
     * @return Value of register Y.
     */
    byte getRegisterY(){
        return this.registerY;
    }

    /**
     * Gets the address stored in the Non Maskable Interrupt vector.<br>
     * 0xfffa - 0xfffb
     * @return address stored in 0xfffa - 0xfffb
     */
    short getNMIAddress(){
        byte highByte = this.getByteAtAddress((short)0xfffb);
        byte lowByte = this.getByteAtAddress((short)0xfffa);

        return Util.bytesToAddress(lowByte,highByte);
    }

    /**
     * Gets the address stored in the Startup vector.<br>
     * 0xfffc - 0xfffd
     * @return address stored in 0xfffc - 0xfffd
     */
    short getStartUpAddress(){
        byte highByte = this.getByteAtAddress((short)0xfffd);
        byte lowByte = this.getByteAtAddress((short)0xfffc);

        return Util.bytesToAddress(lowByte,highByte);
    }

    /**
     * Gets the address stored in the break vector.<br>
     * 0xfffe - 0xffff
     * @return address stored in 0xfffe - 0xffff
     */
    short getBreakAddress(){
        byte highByte = this.getByteAtAddress((short)0xffff);
        byte lowByte = this.getByteAtAddress((short)0xfffe);

        return Util.bytesToAddress(lowByte,highByte);
    }

    /**
     * Loads the Memory with code stored in a string starting at a certain address.
     * @param beginAddress start address of the code in memory
     * @param code code as a string
     */
    void loadString(short beginAddress, String code){
        String sanitizedString = Util.sanitizeHexString(code);
        String[] codeArray = sanitizedString.split("\s");

        for(int i = Util.unsignShort(beginAddress); i < codeArray.length; i++){
            this.memoryArray[i] = Util.hexStringToByte(codeArray[i]);
        }
    }

    /**
     * Loads the Memory with code stored in a string.
     * @param code String to load.
     */
    void loadString(String code){
        this.loadString((short)0x0000, code);
    }
}
