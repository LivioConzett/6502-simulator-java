package java6502;

/**
 * Class with all the CPU Operations. <br>
 * For more info on each of the Operations visit: <br>
 * https://6502.livio.tech/instructionset/
 * @author Livio Conzett
 * @version 16.10.2022
 */
public class InstructionSet {

    private final Memory memory;
    private final Stack stack;
    private final Flags flags;

    /**
     * Initialize tha class
     * @param memory Memory object.
     * @param stack Stack object.
     * @param flags Flags object.
     */
    public InstructionSet(Memory memory, Stack stack, Flags flags){
        this.memory = memory;
        this.stack = stack;
        this.flags = flags;
    }

    /**
     * Add to accumulator with carry.
     * @param value AddressingModeReturn object with value to add to accumulator
     */
    public void ADC(AddressingModeReturn value){
        // clear the overflow bit
        this.flags.setOverFlow(false);
        byte number = value.getValue();

        boolean likeSigned = Util.areLikeSigned(number,this.memory.getRegisterA());

        if(this.flags.getDecimalMode()){

            int bcd = Util.bcdToDec(number);
            int ans = bcd + this.memory.getRegisterA() + this.flags.getCarryInt();

            // set the carry flag if the ans is over 99 since we are in bcd mode.
            this.flags.setCarry(ans > 99);
            // cut off the overflow
            this.memory.setRegisterA(Util.decToBcd((byte)(ans % 99)));
        }
        else{
            int ans = Util.unsignByte(this.memory.getRegisterA()) +
                        Util.unsignByte(number) +
                        this.flags.getCarryInt();

            // 255 is the highest number that can be saved in one byte.
            this.flags.setCarry(ans > 255);
            this.memory.setRegisterA((byte)ans);

            if(likeSigned){
                if(ans > 127 || ans < -128){
                    this.flags.setOverFlow(true);
                }
            }
        }

        this.flags.setZero(this.memory.getRegisterA()==0);
        this.flags.setNegative(this.memory.getRegisterA()<0);
    }

    /**
     * ANDs a byte with the accumulator.
     * @param value AddressingModeReturn object with value to AND with accumulator
     */
    public void AND(AddressingModeReturn value){
        byte number = value.getValue();
        this.memory.setRegisterA((byte)(this.memory.getRegisterA() & number));
        this.flags.setZero(this.memory.getRegisterA()==0);
        this.flags.setNegative(this.memory.getRegisterA()<0);
    }

    /**
     * Accumulator Shift Left<br>
     * Shifts the Accumulator left
     */
    public void ASL(){
        byte regA = this.memory.getRegisterA();

        // if the number to shift left is negative, it has a 1 in the msb.
        // if that is then shifted left it will overflow into the carry.
        this.flags.setCarry(regA < 0);

        int shift = regA << 1;
        this.memory.setRegisterA((byte)shift);
        this.flags.setZero(this.memory.getRegisterA()==0);
        this.flags.setNegative(this.memory.getRegisterA()<0);
    }

    /**
     * Accumulator Shift Left.<br>
     * Shifts the value at a memory address to the left.
     * @param address Address of the byte to shift left.
     */
    public void ASL(AddressingModeReturn address){
        byte value = address.getValue();

        // if the number to shift left is negative, it has a 1 in the msb.
        // if that is then shifted left it will overflow into the carry.
        this.flags.setCarry(value < 0);

        int shift = value << 1;
        this.memory.setByteAtAddress(address.getAddress(),(byte)shift);
        this.flags.setZero((byte)shift == 0);
        this.flags.setNegative((byte)shift < 0);
    }

    /**
     * Branch on Carry Clear<br>
     * Branch to the address given if the carry flag is false
     * @param address address to branch to.
     */
    public void BCC(AddressingModeReturn address){
        if(this.flags.getCarry()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Branch on Carry Set<br>
     * Branch to the address given if the carry flag is true
     * @param address address to branch to.
     */
    public void BCS(AddressingModeReturn address){
        if(!this.flags.getCarry()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Branch on Result Equals to Zero<br>
     * Branch to the address given if the zero flag is true
     * @param address address to branch to.
     */
    public void BEQ(AddressingModeReturn address){
        if(!this.flags.getZero()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * ANDs a value with the accumulator without saving the result.
     * @param value value to AND the accumulator with.
     */
    public void BIT(AddressingModeReturn value){
        int number = value.getValue();
        int ans = number & this.memory.getRegisterA();

        this.flags.setZero(ans == 0);
        this.flags.setNegative(value.getValue() < 0);
        this.flags.setOverFlow((value.getValue() & 0b01000000) == 0b01000000);
    }

    /**
     * Branch on Result Minus<br>
     * Branch to the address given if the negative flag is true.
     * @param address address to branch to.
     */
    public void BMI(AddressingModeReturn address){
        if(!this.flags.getNegative()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Branch on Result Not Equals to Zero<br>
     * Branch to the address given if the zero flag is false
     * @param address address to branch to.
     */
    public void BNE(AddressingModeReturn address){
        if(this.flags.getZero()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Branch on Result Plus<br>
     * Branch to the address given if the negative flag is false.
     * @param address address to branch to.
     */
    public void BPL(AddressingModeReturn address){
        if(this.flags.getNegative()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Break command <br>
     * Will cause the cpu to jump to the address saved in the break vector.
     */
    public void BRK(){

        this.memory.incrementProgramCounter();
        byte[] address = Util.addressToBytes(this.memory.getProgramCounter());

        // push the program-counter onto the stack
        this.stack.push(address[1]);
        this.stack.push(address[0]);

        // set the break flag
        this.flags.setBreakCommand(true);
        this.flags.setInterruptDisable(true);

        // push the whole status register onto the stack
        this.stack.push(this.flags.getWholeRegister());

        // reset the break flag since this doesn't actually exist.
        // (don't know why)
        this.flags.setBreakCommand(false);

        this.memory.setProgramCounter(this.memory.getBreakAddress());
    }

    /**
     * Branch on Overflow Clear<br>
     * Branch to the address given if the overflow flag is false.
     * @param address Address to branch to.
     */
    public void BVC(AddressingModeReturn address){
        if(this.flags.getOverFlow()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Branch on Overflow Set<br>
     * Branch to the address given if the overflow flag is true.
     * @param address Address to branch to.
     */
    public void BVS(AddressingModeReturn address){
        if(!this.flags.getOverFlow()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Clear Carry Flag<br>
     * Sets the carry flag to false.
     */
    public void CLC(){
        this.flags.setCarry(false);
    }

    /**
     * Clear Decimal Flag<br>
     * Sets the decimal flag to false.
     */
    public void CLD(){
        this.flags.setDecimalMode(false);
    }

    /**
     * Clear Interrupt Disable Flag<br>
     * Sets the interrupt disable flag to false.
     */
    public void CLI(){
        this.flags.setInterruptDisable(false);
    }

    /**
     * Clear Overflow Flag<br>
     * Sets the overflow flag to false.
     */
    public void CLV(){
        this.flags.setOverFlow(false);
    }

    /**
     * Compares two numbers<br>
     * Compares two values and sets the Status flags  accordingly.
     * @param register the value in the register to compare to.
     * @param memory the value to compare the register to.
     */
    public void compare(byte register, byte memory){

        this.flags.setNegative(register < memory);
        this.flags.setZero(register == memory);
        this.flags.setCarry((register == memory)||(register > memory));

    }

    /**
     * Compare Memory with Accumulator<br>
     * Subtracts a value from the accumulator without saving the result. Status flags will be set accordingly.
     * @param value Value to compare accumulator to.
     */
    public void CMP(AddressingModeReturn value){
        this.compare(this.memory.getRegisterA(),value.getValue());
    }

    /**
     * Compare Memory and Index X<br>
     * Subtracts a value from the register X without saving the result. Status flags will be set accordingly.
     * @param value Value to compare accumulator to.
     */
    public void CPX(AddressingModeReturn value){
        this.compare(this.memory.getRegisterX(),value.getValue());
    }

    /**
     * Compare Memory and Index Y<br>
     * Subtracts a value from the register Y without saving the result. Status flags will be set accordingly.
     * @param value Value to compare accumulator to.
     */
    public void CPY(AddressingModeReturn value){
        this.compare(this.memory.getRegisterY(),value.getValue());
    }

    /**
     * Decrement Memory by One<br>
     * Decrements a value in memory by one.
     * @param address address of value to decrement.
     */
    public void DEC(AddressingModeReturn address){
        byte ans = (byte)(address.getValue() - 1);

        this.flags.setZero(ans == 0);
        this.flags.setNegative(ans < 0);

        this.memory.setByteAtAddress(address.getAddress(),ans);
    }

    /**
     * Decrement Index X by One<br>
     * Decrements a value in register X by one.
     */
    public void DEX(){
        byte ans = (byte)(this.memory.getRegisterX() - 1);

        this.flags.setZero(ans == 0);
        this.flags.setNegative(ans < 0);

        this.memory.setRegisterX(ans);
    }

    /**
     * Decrement Index Y by One<br>
     * Decrements a value in register Y by one.
     */
    public void DEY(){
        byte ans = (byte)(this.memory.getRegisterY() - 1);

        this.flags.setZero(ans == 0);
        this.flags.setNegative(ans < 0);

        this.memory.setRegisterY(ans);
    }

    /**
     * Exclusive or Memory with Accumulator<br>
     * Result will be stored in Accumulator.
     * @param value value to xor with Accumulator.
     */
    public void EOR(AddressingModeReturn value) {
        byte ans = (byte)(this.memory.getRegisterA() ^ value.getValue());

        this.flags.setNegative(ans < 0);
        this.flags.setZero(ans == 0);

        this.memory.setRegisterA(ans);
    }

    /**
     * Increment Memory by One<br>
     * Increments a memory location by one.
     * @param address address to increment by one.
     */
    public void INC(AddressingModeReturn address){
        byte ans = (byte)(address.getValue() + 1);

        this.flags.setZero(ans == 0);
        this.flags.setNegative(ans < 0);

        this.memory.setByteAtAddress(address.getAddress(),ans);
    }

}
