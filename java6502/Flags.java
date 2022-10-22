package java6502;

/**
 * Holds the processor flags.<br>
 * The flags are held in the Processor Status Register byte.
 * <pre>
 *     2<sup>7</sup> = Negative Flag        1 = Negative
 *     2<sup>6</sup> = Overflow Flag        1 = True
 *     2<sup>5</sup> = [not used]
 *     2<sup>4</sup> = Break Command Flag   1 = Break
 *     2<sup>3</sup> = Decimal Mode Flag    1 = True
 *     2<sup>2</sup> = Interrupt
 *                     Disable Command      1 = Disable
 *     2<sup>1</sup> = Zero Flag            1 = Result Zero
 *     2<sup>0</sup> = Carry Flag           1 = True
 * </pre>
 * @author Livio Conzett
 */
class Flags {

    private boolean negative;
    private boolean overFlow;
    private boolean breakCommand;
    private boolean decimalMode;
    private boolean interruptDisabled;
    private boolean zero;
    private boolean carry;


    /**
     * Initializes the Status flags
     */
    public Flags(){
        this.reset();
    }

    /**
     * Reset the flags like power-cycling the system.
     */
    public void reset(){
        this.setWholeRegister((byte)0);
        this.interruptDisabled = true;
    }

    /**
     * The processor status register is the register where the processor flags are saved.
     * This returns the whole register as a byte.
     * @return the processor flags as one byte
     */
    public byte getWholeRegister(){
        byte registerP = 0;

        if(this.carry) registerP += 1;
        if(this.zero) registerP += 2;
        if(this.interruptDisabled) registerP += 4;
        if(this.decimalMode) registerP += 8;
        if(this.breakCommand) registerP += 16;
        if(this.overFlow) registerP += 64;
        if(this.negative) registerP += 128;

        return registerP;
    }

    /**
     * The processor status register is the register where the processor flags are saved.
     * This sets the whole register as a byte.
     * @param statusRegister Byte of all the status Flags.
     */
    public void setWholeRegister(byte statusRegister){
        this.carry = (((statusRegister) & 1) == 1);
        this.zero = (((statusRegister) & 2) == 2);
        this.interruptDisabled = (((statusRegister) & 4) == 4);
        this.decimalMode = (((statusRegister) & 8) == 8);
        this.breakCommand = (((statusRegister) & 16) == 16);
        this.overFlow = (((statusRegister) & 64) == 64);
        this.negative = (((statusRegister) & 128) == 128);
    }

    /**
     * Set the carry flag.
     * @param carryFlag Value of carry flag.
     */
    public void setCarry(boolean carryFlag) {
        this.carry = carryFlag;
    }

    /**
     * Get the status of the carry flag.
     * @return Status of the carry flag.
     */
    public boolean getCarry(){
        return this.carry;
    }

    /**
     * Returns the carry flag as an int.
     * @return 1 if true, 0 if false.
     */
    public int getCarryInt(){
        if(this.carry){
            return 1;
        }
        return 0;
    }
    /**
     * Set the zero flag.
     * @param zeroFlag Value of zero flag.
     */
    public void setZero(boolean zeroFlag) {
        this.zero = zeroFlag;
    }

    /**
     * Get the status of the zero flag.
     * @return Status of the zero flag.
     */
    public boolean getZero(){
        return this.zero;
    }

    /**
     * Set the interruptDisabled flag.
     * @param interruptDisabledFlag Value of interruptDisabled flag.
     */
    public void setInterruptDisable(boolean interruptDisabledFlag) {
        this.interruptDisabled = interruptDisabledFlag;
    }

    /**
     * Get the status of the interruptDisabled flag.
     * @return Status of the interruptDisabled flag.
     */
    public boolean getInterruptDisable(){
        return this.interruptDisabled;
    }

    /**
     * Set the decimalMode flag.
     * @param decimalModeFlag Value of decimalMode flag.
     */
    public void setDecimalMode(boolean decimalModeFlag) {
        this.decimalMode = decimalModeFlag;
    }

    /**
     * Get the status of the decimalMode flag.
     * @return Status of the decimalMode flag.
     */
    public boolean getDecimalMode(){
        return this.decimalMode;
    }

    /**
     * Set the breakCommand flag.
     * @param breakCommandFlag Value of breakCommand flag.
     */
    public void setBreakCommand(boolean breakCommandFlag) {
        this.breakCommand = breakCommandFlag;
    }

    /**
     * Get the status of the breakCommand flag.
     * @return Status of the breakCommand flag.
     */
    public boolean getBreakCommand(){
        return this.breakCommand;
    }

    /**
     * Set the overFlow flag.
     * @param overFlowFlag Value of overFlow flag.
     */
    public void setOverFlow(boolean overFlowFlag) {
        this.overFlow = overFlowFlag;
    }

    /**
     * Get the status of the overFlow flag.
     * @return Status of the overFlow flag.
     */
    public boolean getOverFlow(){
        return this.overFlow;
    }

    /**
     * Set the negative flag.
     * @param negativeFlag Value of negative flag.
     */
    public void setNegative(boolean negativeFlag) {
        this.negative = negativeFlag;
    }

    /**
     * Get the status of the negative flag.
     * @return Status of the negative flag.
     */
    public boolean getNegative(){
        return this.negative;
    }

}
