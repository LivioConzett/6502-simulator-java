package java6502.tests;


import java6502.Memory;
import java6502.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MemoryTest{

    @Test
    public void programCounterTest(){

        Memory mem = new Memory();

        Assertions.assertEquals((short) 0xfffc, mem.getProgramCounter());

        mem.setProgramCounter((short) 0x0);

        Assertions.assertEquals(0, mem.getProgramCounter());

        mem.incrementProgramCounter(1);

        Assertions.assertEquals(1, mem.getProgramCounter());

        mem.incrementProgramCounter(29);

        Assertions.assertEquals(30, mem.getProgramCounter());

        mem.setByteAtAddress((short) 0x8484, (byte)0xd0);
        mem.setByteAtAddress((short) 0x8485, (byte)0x69);
        mem.setProgramCounter((short) 0x8484);

        Assertions.assertEquals(0xd0, Util.unsignByte(mem.getCurrentByte()));

        mem.incrementProgramCounter();
        Assertions.assertEquals(0x69, Util.unsignByte(mem.getCurrentByte()));

    }
}

