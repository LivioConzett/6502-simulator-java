package tech.livio.java6502;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Load some code into the sim6502 and run them. See if the run correctly.
 */
public class codeTest {

    @Test
    void code1(){

        Sim6502 sim = new Sim6502();

        // set the start vector
        sim.load((short) 0xfffc, "00 00");

        String code = "a9 10 " + // load 10 into the A register
                        "4c 34 12"; // jump to address 0x1234

        sim.load((short)0x0000, code);

        code = "18 " + // clear the carry flag
                "69 10 " + // add ten to the accumulator
                "8d 78 9a " + // store the contents of register A at memory address 9a78
                "80"; // exit the program

        sim.load((short)0x1234, code);


        sim.run();

        sim.waitForProgramEnd();

        Assertions.assertEquals((byte)0x20, sim.getByteAtAddress((short)0x9a78));

    }


}
