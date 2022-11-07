package tech.livio.java6502;

/**
 * 8bit stack for the system.
 * @author Livio Conzett
 * @version 16.10.2022
 */

class Stack {

    private final Memory memory;
    private byte stackPointer;

    // address of stack
    private static final int BOTTOM_STACK = 0x0100;
    private static final int TOP_STACK = 0x01ff;

    public Stack(Memory memory){
        this.memory = memory;
        // initiate the stackpointer to a "random" number. That's what the real one did too. This is to force
        // the programmer to actually set the stackpointer.
        this.stackPointer = 0x69;
    }


    /**
     * Get the byte at a certain address in the stack.
     * @param address Address within the stack to get.
     * @return Value of byte at that address.
     */
    public byte get(byte address){
        return this.memory.getByteAtAddress((short) (BOTTOM_STACK + Util.unsignByte(address)));
    }

    /**
     * Set the byte in the stack at an address to a certain value.
     * @param address address within stack to set.
     * @param value Value to set to.
     */
    public void set(byte address, byte value){
        this.memory.setByteAtAddress((short) (BOTTOM_STACK + Util.unsignByte(address)), value);
    }

    /**
     * Get the value of the stack pointer
     * @return Value of the stack pointer.
     */
    public byte getStackPointer() {
        return this.stackPointer;
    }

    /**
     * Set the value of the stack pointer. Despite taking an int, it works as an unsigned byte.
     * @param value Value to set the stack pointer to.
     */
    public void setStackPointer(byte value){
        this.stackPointer = value;
    }

    /**
     * Increment the stack pointer by one.
     */
    public void incrementStackPointer(){
        this.stackPointer = (byte)(Util.unsignByte(this.stackPointer) + 1);
    }

    /**
     * Decrements the stack pointer by one.
     */
    public void decrementStackPointer(){
        this.stackPointer = (byte)(Util.unsignByte(this.stackPointer) - 1);
    }

    /**
     * Push a value onto the stack, then decrements the stackpointer.
     * @param value Value to push to stack.
     */
    public void push(byte value){
        this.set(this.stackPointer,value);
        this.decrementStackPointer();
    }

    /**
     * Increment the stackpointer, then pull the value from the stack.
     */
    public byte pull(){
        this.incrementStackPointer();
        return this.get(this.stackPointer);
    }
}