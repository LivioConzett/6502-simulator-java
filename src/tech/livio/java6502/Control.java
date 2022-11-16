package tech.livio.java6502;

/**
 * Holds control data for the 6502 simulator. Has nothing to do with the actual 6502 chip.
 */
public class Control {

    private boolean skipIncrement;
    private boolean run;
    private Runnable doOnExt;

    /**
     * Initialize all the variables.
     */
    Control(){
        this.skipIncrement = false;
        this.run = false;
        this.doOnExt = ()-> System.out.println("Simulator finished running program.");
    }

    /**
     * Returns the boolean value of the skipIncrement control flag.<br>
     * This flag is set when a 6502 instruction loads the programCounter with a value and doesn't want you to increment
     * the programCounter yet.<br>
     * Eg: The JMP instruction sets the programCounter to a value, but doesn't execute it yet. In order for the next
     * iteration of the simulation loop to run that instruction the programCounter must not be incremented.
     * @return true if method doesn't want you to increment the programCounter.
     */
    boolean getSkipNextIncrement(){
        return this.skipIncrement;
    }

    /**
     * Sets the control flag skipIncrement to true.<br>
     * This will stop the simulation loop from incrementing the programCounter.
     */
    void setSkipNextIncrement(){
        this.skipIncrement = true;
    }

    /**
     * Sets the control flag skipIncrement to false.<br>
     * This will allow the simulation loop to increment the programCounter.
     */
    void allowNextIncrement(){
        this.skipIncrement = false;
    }

    /**
     * Get the run flag.
     * @return true if run is set.
     */
    boolean getRun(){
        return this.run;
    }

    /**
     * Set the run flag.
     * @param run value the run flag should be set to.
     */
    void setRun(boolean run){
        this.run = run;
    }

    /**
     * Sets what should be done when the program encounters the ext (0x80) instruction.<br>
     * The ext instruction will also set the run flag to false.
     * @param doOnExt
     */
    void setDoOnExt(Runnable doOnExt){
        this.doOnExt = doOnExt;
    }

    /**
     * Runs the doOnExt function specified.
     */
    void runDoOnExt(){
        this.doOnExt.run();
    }


}
