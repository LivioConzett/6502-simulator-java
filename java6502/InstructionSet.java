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
     */
    public void ADC(byte number){
        // clear the overflow bit
        this.flags.setOverFlow(false);

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
     * @param number Byte to AND with accumulator
     */
    public void AND(byte number){
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

}
