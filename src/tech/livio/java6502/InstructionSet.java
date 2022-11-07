package tech.livio.java6502;

/**
 * Class with all the CPU Operations. <br>
 * For more info on each of the Operations visit: <br>
 * <a href="https://6502.livio.tech/instructionset/">6502.livio.tech/instructionset</a>
 * @author Livio Conzett
 * @version 16.10.2022
 */
class InstructionSet {

    private final Memory memory;
    private final Stack stack;
    private final Flags flags;

    /**
     * Initialize tha class
     * @param memory Memory object.
     * @param stack Stack object.
     * @param flags Flags object.
     */
    InstructionSet(Memory memory, Stack stack, Flags flags){
        this.memory = memory;
        this.stack = stack;
        this.flags = flags;
    }

    /**
     * Add to accumulator with carry.
     * @param value AddressingModeReturn object with value to add to accumulator
     */
    void adc(AddressingModeReturn value){
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

            if(likeSigned && (ans > 127 || ans < -128)){
                this.flags.setOverFlow(true);
            }
        }

        this.flags.setZero(this.memory.getRegisterA()==0);
        this.flags.setNegative(this.memory.getRegisterA()<0);
    }

    /**
     * ANDs a byte with the accumulator.
     * @param value AddressingModeReturn object with value to AND with accumulator
     */
    void and(AddressingModeReturn value){
        byte number = value.getValue();
        this.memory.setRegisterA((byte)(this.memory.getRegisterA() & number));
        this.flags.setZero(this.memory.getRegisterA()==0);
        this.flags.setNegative(this.memory.getRegisterA()<0);
    }

    /**
     * Accumulator Shift Left<br>
     * Shifts the Accumulator left
     */
    void asl(){
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
    void asl(AddressingModeReturn address){
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
    void bcc(AddressingModeReturn address){
        if(this.flags.getCarry()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Branch on Carry Set<br>
     * Branch to the address given if the carry flag is true
     * @param address address to branch to.
     */
    void bcs(AddressingModeReturn address){
        if(!this.flags.getCarry()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Branch on Result Equals to Zero<br>
     * Branch to the address given if the zero flag is true
     * @param address address to branch to.
     */
    void beq(AddressingModeReturn address){
        if(!this.flags.getZero()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * ANDs a value with the accumulator without saving the result.
     * @param value value to AND the accumulator with.
     */
    void bit(AddressingModeReturn value){
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
    void bmi(AddressingModeReturn address){
        if(!this.flags.getNegative()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Branch on Result Not Equals to Zero<br>
     * Branch to the address given if the zero flag is false
     * @param address address to branch to.
     */
    void bne(AddressingModeReturn address){
        if(this.flags.getZero()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Branch on Result Plus<br>
     * Branch to the address given if the negative flag is false.
     * @param address address to branch to.
     */
    void bpl(AddressingModeReturn address){
        if(this.flags.getNegative()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Break command <br>
     * Will cause the cpu to jump to the address saved in the break vector.
     */
    void brk(){

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
    void bvc(AddressingModeReturn address){
        if(this.flags.getOverFlow()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Branch on Overflow Set<br>
     * Branch to the address given if the overflow flag is true.
     * @param address Address to branch to.
     */
    void bvs(AddressingModeReturn address){
        if(!this.flags.getOverFlow()) return;
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Clear Carry Flag<br>
     * Sets the carry flag to false.
     */
    void clc(){
        this.flags.setCarry(false);
    }

    /**
     * Clear Decimal Flag<br>
     * Sets the decimal flag to false.
     */
    void cld(){
        this.flags.setDecimalMode(false);
    }

    /**
     * Clear Interrupt Disable Flag<br>
     * Sets the interrupt disable flag to false.
     */
    void cli(){
        this.flags.setInterruptDisable(false);
    }

    /**
     * Clear Overflow Flag<br>
     * Sets the overflow flag to false.
     */
    void clv(){
        this.flags.setOverFlow(false);
    }

    /**
     * Compares two numbers<br>
     * Compares two values and sets the Status flags  accordingly.
     * @param register the value in the register to compare to.
     * @param memory the value to compare the register to.
     */
    void compare(byte register, byte memory){

        this.flags.setNegative(register < memory);
        this.flags.setZero(register == memory);
        this.flags.setCarry((register == memory)||(register > memory));
    }

    /**
     * Compare Memory with Accumulator<br>
     * Subtracts a value from the accumulator without saving the result. Status flags will be set accordingly.
     * @param value Value to compare accumulator to.
     */
    void cmp(AddressingModeReturn value){
        this.compare(this.memory.getRegisterA(),value.getValue());
    }

    /**
     * Compare Memory and Index X<br>
     * Subtracts a value from the register X without saving the result. Status flags will be set accordingly.
     * @param value Value to compare accumulator to.
     */
    void cpx(AddressingModeReturn value){
        this.compare(this.memory.getRegisterX(),value.getValue());
    }

    /**
     * Compare Memory and Index Y<br>
     * Subtracts a value from the register Y without saving the result. Status flags will be set accordingly.
     * @param value Value to compare accumulator to.
     */
    void cpy(AddressingModeReturn value){
        this.compare(this.memory.getRegisterY(),value.getValue());
    }

    /**
     * Decrement Memory by One<br>
     * Decrements a value in memory by one.
     * @param address address of value to decrement.
     */
    void dec(AddressingModeReturn address){
        byte ans = (byte)(address.getValue() - 1);

        this.flags.setZero(ans == 0);
        this.flags.setNegative(ans < 0);

        this.memory.setByteAtAddress(address.getAddress(),ans);
    }

    /**
     * Decrement Index X by One<br>
     * Decrements a value in register X by one.
     */
    void dex(){
        byte ans = (byte)(this.memory.getRegisterX() - 1);

        this.flags.setZero(ans == 0);
        this.flags.setNegative(ans < 0);

        this.memory.setRegisterX(ans);
    }

    /**
     * Decrement Index Y by One<br>
     * Decrements a value in register Y by one.
     */
    void dey(){
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
    void eor(AddressingModeReturn value) {
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
    void inc(AddressingModeReturn address){
        byte ans = (byte)(address.getValue() + 1);

        this.flags.setZero(ans == 0);
        this.flags.setNegative(ans < 0);

        this.memory.setByteAtAddress(address.getAddress(),ans);
    }

    /**
     * Increment Index X by One<br>
     * Increments a value in register X by one.
     */
    void inx(){
        byte ans = (byte)(this.memory.getRegisterX() + 1);

        this.flags.setZero(ans == 0);
        this.flags.setNegative(ans < 0);

        this.memory.setRegisterX(ans);
    }

    /**
     * Increment Index X by One<br>
     * Increments a value in register X by one.
     */
    void iny(){
        byte ans = (byte)(this.memory.getRegisterY() + 1);

        this.flags.setZero(ans == 0);
        this.flags.setNegative(ans < 0);

        this.memory.setRegisterY(ans);
    }

    /**
     * Jump<br>
     * Jumps to an address.
     * @param address address to jump to.
     */
    void jmp(AddressingModeReturn address){
        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Jump to Subroutine<br>
     * Pushes the current address and status register onto the stack. Then jumps to an address.
     * @param address address to jump to.
     */
    void jsr(AddressingModeReturn address){
        byte[] memAddress = Util.addressToBytes(this.memory.getProgramCounter());

        this.stack.push(memAddress[1]);
        this.stack.push(memAddress[0]);
        this.stack.push(this.flags.getWholeRegister());

        this.memory.setProgramCounter(address.getAddress());
    }

    /**
     * Load Accumulator with Memory<br>
     * Puts a value into the Accumulator.
     * @param value value to put into accumulator.
     */
    void lda(AddressingModeReturn value){
        this.flags.setZero(value.getValue() == 0);
        this.flags.setNegative(value.getValue() < 0);
        this.memory.setRegisterA(value.getValue());
    }

    /**
     * Load Index X with Memory<br>
     * Puts a value into the register X.
     * @param value value to put into accumulator.
     */
    void ldx(AddressingModeReturn value){
        this.flags.setZero(value.getValue() == 0);
        this.flags.setNegative(value.getValue() < 0);
        this.memory.setRegisterX(value.getValue());
    }

    /**
     * Load Index Y with Memory<br>
     * Puts a value into the register Y.
     * @param value value to put into accumulator.
     */
    void ldy(AddressingModeReturn value){
        this.flags.setZero(value.getValue() == 0);
        this.flags.setNegative(value.getValue() < 0);
        this.memory.setRegisterY(value.getValue());
    }

    /**
     * Logical Shift Right<br>
     * Shift the accumulator one bit to the right.
     */
    void lsr(){
        // if the lsb of the accumulator is a 1 then shifting it right will
        // turn the carry to a one.
        this.flags.setCarry((this.memory.getRegisterA() & 0b1) == 0b1);

        byte shift = (byte)(Util.unsignByte(this.memory.getRegisterA()) >>> 1);

        this.flags.setZero(shift == 0);
        this.flags.setNegative(false);
        this.memory.setRegisterA(shift);
    }

    /**
     * Logical Shift Right<br>
     * Shift a memory location one bit to the right.
     * @param address address of value to shift right
     */
    void lsr(AddressingModeReturn address){
        // if the lsb of the accumulator is a 1 then shifting it right will
        // turn the carry to a one.
        this.flags.setCarry((address.getValue() & 0b1) == 0b1);

        byte shift = (byte)(Util.unsignByte(address.getValue()) >>> 1);

        this.flags.setZero(shift == 0);
        this.flags.setNegative(false);
        this.memory.setByteAtAddress(address.getAddress(),shift);
    }

    /**
     * No Operator<br>
     * Doesn't do anything.
     */
    void nop(){
        // Comment so Sonar linter is happy.
    }

    /**
     * OR Memory with Accumulator<br>
     * ORs a value with the Accumulator and stores it in the accumulator
     * @param value value to OR the Accumulator with.
     */
    void ora(AddressingModeReturn value){
        this.memory.setRegisterA((byte)(this.memory.getRegisterA() | value.getValue()));
        this.flags.setZero(this.memory.getRegisterA() == 0);
        this.flags.setNegative(this.memory.getRegisterA() < 0);
    }

    /**
     * Push accumulator on Stack
     */
    void pha(){
        this.stack.push(this.memory.getRegisterA());
    }

    /**
     * Push Processor Status on Stack
     */
    void php(){
        this.stack.push(this.flags.getWholeRegister());
    }

    /**
     * Pull Accumulator from Stack.
     */
    void pla(){
        this.memory.setRegisterA(this.stack.pull());
        this.flags.setZero(this.memory.getRegisterA() == 0);
        this.flags.setNegative(this.memory.getRegisterA() < 0);
    }

    /**
     * Pull Processor Status from Stack
     */
    void plp(){
        byte number = this.stack.pull();
        this.flags.setWholeRegister(number);
    }

    /**
     * Rotate Left<br>
     * Rotates the Accumulator left by one.
     */
    void rol(){
        // get the value of the carry and save it for later
        int carry = this.flags.getCarryInt();
        // if the msb of the accumulator is a 1 then rotating it right will
        // turn the carry to a one.
        this.flags.setCarry((this.memory.getRegisterA() & 0b10000000) == 0b10000000);

        byte shift = (byte)((Util.unsignByte(this.memory.getRegisterA()) << 1) + carry);

        this.flags.setNegative(shift < 0);
        this.flags.setZero(shift == 0);
        this.memory.setRegisterA(shift);
    }

    /**
     * Rotate Left<br>
     * Rotates the value at and address left by one.
     * @param address address of the byte to rotate.
     */
    void rol(AddressingModeReturn address){
        // get the value of the carry and save it for later
        int carry = this.flags.getCarryInt();
        // if the msb of the accumulator is a 1 then rotating it right will
        // turn the carry to a one.
        this.flags.setCarry((address.getValue() & 0b10000000) == 0b10000000);

        byte shift = (byte)((Util.unsignByte(address.getValue()) << 1) + carry);

        this.flags.setNegative(shift < 0);
        this.flags.setZero(shift == 0);
        this.memory.setByteAtAddress(address.getAddress(), shift);
    }

    /**
     * Rotate Right<br>
     * Rotates the accumulator right by one.
     */
    void ror(){
        // get the value of the carry and save it for later
        int carry = this.flags.getCarryInt();
        // if the lsb of the accumulator is a 1 then rotating it right will
        // turn the carry to a one.
        this.flags.setCarry((this.memory.getRegisterA() & 0b1) == 0b1);

        byte shift = (byte)((Util.unsignByte(this.memory.getRegisterA()) >>> 1) | (carry * 128));

        this.flags.setNegative(shift < 0);
        this.flags.setZero(shift == 0);
        this.memory.setRegisterA(shift);
    }

    /**
     * Rotate Right<br>
     * Rotates the value at and address right by one.
     * @param address address of the byte to rotate.
     */
    void ror(AddressingModeReturn address){
        // get the value of the carry and save it for later
        int carry = this.flags.getCarryInt();
        // if the msb of the accumulator is a 1 then rotating it right will
        // turn the carry to a one.
        this.flags.setCarry((address.getValue() & 0b1) == 0b1);

        byte shift = (byte)((Util.unsignByte(address.getValue()) >>> 1) | (carry * 128));

        this.flags.setNegative(shift < 0);
        this.flags.setZero(shift == 0);
        this.memory.setByteAtAddress(address.getAddress(), shift);
    }

    /**
     * Return from Interrupt
     */
    void rti(){
        byte status = this.stack.pull();
        byte lowByte = this.stack.pull();
        byte highByte = this.stack.pull();

        this.flags.setWholeRegister(status);
        this.flags.setBreakCommand(false);
        this.memory.setProgramCounter(Util.bytesToAddress(lowByte, highByte));
    }

    /**
     * Return from Subroutine
     */
    void rts(){
        byte lowByte = this.stack.pull();
        byte highByte = this.stack.pull();

        this.memory.setProgramCounter(Util.bytesToAddress(lowByte,highByte));
    }

    /**
     * Subtract from Accumulator with Carry<br>
     * Subtracts from the Accumulator with carry and then saves the result in the Accumulator.
     * @param value value to subtract.
     */
    void sbc(AddressingModeReturn value){
        // clear the overflow bit
        this.flags.setOverFlow(false);
        byte number = value.getValue();

        boolean unLikeSigned = Util.areNotLikeSigned(number,this.memory.getRegisterA());

        if(this.flags.getDecimalMode()){

            int bcd = Util.bcdToDec(number);
            int ans = this.memory.getRegisterA() - bcd - this.flags.getCarryInt();

            // set the carry flag if the ans is over 99 since we are in bcd mode.
            this.flags.setCarry(ans >= 0);
            // cut off the overflow
            this.memory.setRegisterA(Util.decToBcd((byte)(ans % 99)));
        }
        else{
            int ans = Util.unsignByte(this.memory.getRegisterA()) -
                    Util.unsignByte(number) -
                    this.flags.getCarryInt();

            // 255 is the highest number that can be saved in one byte.
            this.flags.setCarry(ans >= 0);
            this.memory.setRegisterA((byte)ans);

            System.out.println(ans);
            if(unLikeSigned && (ans > 127 || ans < -128)){
                this.flags.setOverFlow(true);
            }
        }

        this.flags.setZero(this.memory.getRegisterA() == 0);
        this.flags.setNegative(this.memory.getRegisterA() < 0);
    }

    /**
     * Set Carry Flag
     */
    void sec(){
        this.flags.setCarry(true);
    }

    /**
     * Set Decimal Mode
     */
    void sed(){
        this.flags.setDecimalMode(true);
    }

    /**
     * Set Interrupt Disable Mode
     */
    void sei(){
        this.flags.setInterruptDisable(true);
    }

    /**
     * Store a byte to a memory address.
     * @param value value to set
     * @param address address to put value in.
     */
    void store(byte value, short address){
        this.memory.setByteAtAddress(address,value);
    }

    /**
     * Store Accumulator in Memory.
     * @param address address to store the value in the accumulator.
     */
    void sta(AddressingModeReturn address){
        this.store(this.memory.getRegisterA(),address.getAddress());
    }

    /**
     * Store Register X in Memory.
     * @param address address to store the value.
     */
    void stx(AddressingModeReturn address){
        this.store(this.memory.getRegisterX(),address.getAddress());
    }


}
