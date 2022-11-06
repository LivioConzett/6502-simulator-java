package java6502;


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

    @Test
    public void specialVectorTest(){

        Memory mem = new Memory();

        mem.setByteAtAddress((short)0xfffa,(byte)0x34);
        mem.setByteAtAddress((short)0xfffb,(byte)0x12);

        mem.setByteAtAddress((short)0xfffc,(byte)0x78);
        mem.setByteAtAddress((short)0xfffd,(byte)0x56);

        mem.setByteAtAddress((short)0xfffe,(byte)0xcd);
        mem.setByteAtAddress((short)0xffff,(byte)0xab);

        Assertions.assertEquals((short)0x1234,mem.getNMIAddress());
        Assertions.assertEquals((short)0x5678,mem.getStartUpAddress());
        Assertions.assertEquals((short)0xabcd,mem.getBreakAddress());


    }
}

