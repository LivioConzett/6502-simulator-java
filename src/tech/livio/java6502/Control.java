package tech.livio.java6502;

/**
 * Holds control data for the 6502 simulator. Has nothing to do with the actual 6502 chip.
 */
public class Control {

    private boolean skipIncrement;
    private boolean run;
    private CallBack doOnExt;
    private CallBack doOnStackOverflow;

    /**
     * Initialize all the variables.
     */
    Control(){
        this.reset();
    }

    /**
     * Resets the whole control object.
     */
    void reset(){
        // set to true in the beginning since the program counter is on the begin vector
        this.skipIncrement = true;
        this.run = false;
        this.doOnExt = (new CallBack() {
            @Override
            public void run(Object e) {
                System.out.println("Ended Program. Program Counter: " + e);
            }
        });
        this.doOnStackOverflow = (new CallBack() {
            @Override
            public void run(Object e) {
                System.err.println("StackOverflow. Program Counter: " + e);
            }
        });
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
     * @param doOnExt CallBack object to run on ext instruction.
     */
    void setDoOnExt(CallBack doOnExt){
        this.doOnExt = doOnExt;
    }

    /**
     * Runs the doOnExt function specified.
     */
    void runDoOnExt(Object e){
        this.doOnExt.run(e);
    }

    /**
     * Sets what should be done when the program encounters a stack overflow.<br>
     * The program will exit and then call the method.
     * @param doOnStackOverflow CallBack object to run when stackoverflow occurs.
     */
    void setDoOnStackOverflow(CallBack doOnStackOverflow){
        this.doOnStackOverflow = doOnStackOverflow;
    }

    /**
     * Runs the doOnStackOverflow method
     */
    void runDoOnStackOverflow(Object e){
        this.doOnStackOverflow.run(e);
    }
}
