package tech.livio.java6502;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Sim6502Test {

    private Sim6502 sim;

    @BeforeEach
    void init(){
        this.sim = new Sim6502();
    }

    @Test
    void loadMemoryFromStringTest(){
        String input = "12 33 ff f3 a1";

        byte[] output = {
                (byte) 0x12,
                (byte) 0x33,
                (byte) 0xff,
                (byte) 0xf3,
                (byte) 0xa1
        };

        sim.loadFromString(input);

        Assertions.assertArrayEquals(output,sim.getMemoryInRange((short)0x0000,(short)0x0004));

        input = "00 xx ff";

        output = new byte[] {
                (byte) 0x00,
                (byte) 0xff
        };

        sim.loadFromString(input);

        Assertions.assertArrayEquals(output,sim.getMemoryInRange((short)0x0000,(short)0x0001));
    }

}
