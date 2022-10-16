/**
 * Java doesn't have an unsigned byte or short, so I'll make one myself.
 * @author Livio Conzett
 * @version 16.10.2022
 */
public class UnsignedNumber {

    private int value;
    private final int amountToAnd;

    /**
     * Initializes the class.
     * @param numberOfBits How many bits the number has (needed for rollover).
     * @param value Value to set the number too.
     */
    public UnsignedNumber(double numberOfBits, int value){
        this.value = value;
        this.amountToAnd = (int) Math.pow(2.0,numberOfBits);
    }

    /**
     * Initializes the class with the default value of 0.
     * @param numberOfBits How many bits the number has (needed for rollover).
     */
    public UnsignedNumber(double numberOfBits){
        this(numberOfBits,0);
    }

    /**
     * Returns the value of the number.
     * @return The value of the number.
     */
    public int get(){
        return this.value;
    }

    /**
     * Sets the value of the number. Will roll over depending on the number of bits initialized in the class.
     * @param value Value to set.
     */
    public void set(int value){
        this.value = value & this.amountToAnd;
    }

    /**
     * Increment the number by one. Will roll over depending on the number of bits initialized in the class.
     */
    public void increment(){
        this.value ++;

        this.value = this.value & this.amountToAnd;
    }

    /**
     * Decrement the number by one. Will roll over depending on the number of bits initialized in the class.
     */
    public void decrement(){
        this.value --;

        this.value = this.value & this.amountToAnd;
    }
}
