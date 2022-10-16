/**
 * 8bit stack for the system.
 * @author Livio Conzett
 * @version 16.10.2022
 */

public class Stack {

    private Memory memory;
    private UnsignedNumber stackPointer;

    // address of stack
    private final int bottomStack = 0x0100;
    private final int topStack = 0x01ff;

    public Stack(Memory memory){
        this.memory = memory;
        // initiate the stackpointer to a "random" number. That's what the real one did too. This is to force
        // the programmer to actually set the stackpointer.
        this.stackPointer = new UnsignedNumber(8,0x69);
    }


    /**
     * Get the byte at a certain address in the stack.
     * @param address Address within the stack to get.
     * @return Value of byte at that address.
     */
    public byte get(UnsignedNumber address){
        return this.memory.getByteAtAddress(this.bottomStack + address.get());
    }

    /**
     * Set the byte in the stack at an address to a certain value.
     * @param address address within stack to set.
     * @param value Value to set to.
     */
    public void set(UnsignedNumber address, byte value){
        this.memory.setByteAtAddress(this.bottomStack + address.get(), value);
    }

    /**
     * Get the value of the stack pointer
     * @return Value of the stack pointer.
     */
    public int getStackPointer() {
        return stackPointer.get();
    }

    /**
     * Set the value of the stack pointer. Despite taking an int, it works as an unsigned byte.
     * @param value Value to set the stack pointer to.
     */
    public void setStackPointer(int value){
        this.stackPointer.set(value);
    }

    /**
     * Increment the stack pointer by one.
     */
    public void incrementStackPointer(){
        this.stackPointer.increment();
    }

    /**
     * Decrements the stack pointer by one.
     */
    public void decrementStackPointer(){
        this.stackPointer.decrement();
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
