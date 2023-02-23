package tech.livio.java6502;

/**
 * Class that can simulate a 6502 processor system.
 * @author Livio Conzett
 * @version 16.10.2022
 */
public class Sim6502 {

    private final Memory memory;
    private final Stack stack;
    private final Flags flags;
    private final InstructionSet is;
    private final AddressingMode am;
    private final Control control;
    private final Printer printer;
    private final ProgramRunner programRunner;

    /**
     * Initialize the 6502 simulator
     */
    public Sim6502(){
        this.control = new Control();
        this.memory = new Memory();
        this.stack = new Stack(this.memory, this.control);
        this.flags = new Flags();
        this.is = new InstructionSet(this.memory, this.stack, this.flags, this.control);
        this.am = new AddressingMode(this.memory);
        this.printer = new Printer(this.memory);

        this.programRunner = new ProgramRunner(
                this.memory,
                this.stack,
                this.flags,
                this.is,
                this.am,
                this.control
        );
    }

    /**
     * Resets the Memory, Control object, and flags. This will delete the loaded code and stack too.
     */
    public void hardReset(){
        this.memory.hardReset();
        this.control.reset();
        this.flags.reset();
    }

    /**
     * Resets the Memory, Control object and flags. Leaves the loaded code. Stack will not be reset.
     */
    public void reset(){
        this.memory.reset();
        this.control.reset();
        this.flags.reset();
    }



    /**
     * Gets the byte at a certain memory address.
     * @param address Address to get byte from.
     * @return value of byte at address.
     */
    public byte getByteAtAddress(short address){
        return this.memory.getByteAtAddress(address);
    }

    /**
     * Gets the value of the Program counter.
     * @return value of the program counter
     */
    public short getProgramCounter(){
        return this.memory.getProgramCounter();
    }

    /**
     * Load code from String into memory.<br>
     * String needs to be Hex codes formatted 2 chars at a time with spaces in between.<br>
     * eg: ff ab 12 34 dc<br>
     * Memory will be loaded starting by address 0x0000.
     * @param code String of hex code
     */
    public void load(String code){
        this.memory.load(code);
    }

    /**
     * Load code from String into memory starting from an address.<br>
     * String needs to be Hex codes formatted 2 chars at a time with spaces in between.<br>
     * eg: ff ab 12 34 dc<br>
     * Memory will be loaded starting by address 0x0000.
     * @param code String of hex code
     */
    public void load(short beginAddress, String code){
        this.memory.load(beginAddress, code);
    }

    /**
     * Load code from a byte array into memory starting from an address.<br>
     * @param code String of hex code
     */
    public void load(short beginAddress, byte[] code){
        this.memory.load(beginAddress, code);
    }

    /**
     * Load code from a byte array into memory<br>
     * Memory will be loaded starting by address 0x0000.
     * @param code String of hex code
     */
    public void load(byte[] code) {
        this.load((short)0x0000, code);
    }

    /**
     * Get a range of memory.<br>
     * If the highAddress is <= lowAddress the method will return an empty byte array.
     * @param lowAddress address to being at
     * @param highAddress address to end at
     * @return byte array of the values between the two addresses
     */
    public byte[] getMemoryInRange(short lowAddress, short highAddress){
        return this.memory.getMemoryRange(lowAddress, highAddress);
    }

    /**
     * Runs the program until it encounters the ext instruction (0x80) or the run control flag is set to false.
     */
    public void run(){
        this.start();
        this.programRunner.start();
    }

    /**
     * Get the thread the 6502 programm is running in.
     * @return returns the thread the 6502 program is running in.
     */
    Thread getRunningThread(){
        return this.programRunner;
    }

    /**
     * Waits for the 6502 program to finish.
     */
    public void waitForProgramEnd(){
        if(!this.programRunner.isAlive()) return;
        try{
            this.programRunner.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Step the program by one instruction
     */
    public void step(){
        this.programRunner.step();
    }

    /**
     * Sets the run control flag to true.
     */
    public void start(){
        this.control.setRun(true);
    }

    /**
     * Call a interrupt.
     */
    public void interrupt(){
        this.programRunner.irq();
    }

    /**
     * Call a Non-Maskable interrupt.
     */
    public void nonMaskableInterrupt(){
        this.programRunner.nmi();
    }

    /**
     * Sets the run control flag to false. Then waits for the thread to stop.
     */
    public void stop(){
        this.programRunner.terminate();
        this.control.runDoOnManualHalt(this.memory.getProgramCounter());
        this.waitForProgramEnd();
    }

    /**
     * Add a method that should be done when the program ends (encounters the ext instruction (0x80)).<br>
     * @param doOnExt CallBack interface with the method in it.
     */
    public void setDoOnExt(ProgramCallBack doOnExt){
        this.control.setDoOnExt(doOnExt);
    }

    /**
     * Add a method that should be done when the program encounters a stackoverflow.<br>
     * @param doOnStackOverflow CallBack interface with the method in it.
     */
    public void setDoOnStackOverflow(ProgramCallBack doOnStackOverflow){
        this.control.setDoOnStackOverflow(doOnStackOverflow);
    }

    /**
     * Add a method that should be done when the program is stopped by user.<br>
     * @param doOnManualHalt CallBack interface with the method in it.
     */
    public void setDoOnManualHalt(ProgramCallBack doOnManualHalt){
        this.control.setDoOnManualHalt(doOnManualHalt);
    }

    /**
     * Returns a String of the hexdump of the entire memory.
     * @return String of memory.
     */
    public String hexDump(){
        return this.printer.hexDumpMem();
    }


    //TODO: Make a dump of the entire memory with registers and program counter and all. maybe as xml

}
