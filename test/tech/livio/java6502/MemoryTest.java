package tech.livio.java6502;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MemoryTest{

    @Test
    void programCounterTest(){

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
    void specialVectorTest(){

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

    @Test
    void getMemoryRangeTest(){

        byte[] output = {
                (byte) 0xff,
                (byte) 0x13,
                (byte) 0x03,
                (byte) 0x81,
                (byte) 0x00
        };

        Memory mem = new Memory();

        mem.setByteAtAddress((short)0x1234,output[0]);
        mem.setByteAtAddress((short)0x1235,output[1]);
        mem.setByteAtAddress((short)0x1236,output[2]);
        mem.setByteAtAddress((short)0x1237,output[3]);
        mem.setByteAtAddress((short)0x1238,output[4]);

        Assertions.assertArrayEquals(output,mem.getMemoryRange((short)0x1234,(short)0x1238));

        output = new byte[]{
        };

        Assertions.assertArrayEquals(output,mem.getMemoryRange((short)0xff01,(short)0xff01));

    }

    @Test
    void loadMemoryFromStringTest(){

        Memory mem = new Memory();

        String input = "12 33 ff f3 a1";

        byte[] output = {
                (byte) 0x12,
                (byte) 0x33,
                (byte) 0xff,
                (byte) 0xf3,
                (byte) 0xa1
        };

        mem.load(input);

        Assertions.assertArrayEquals(output,mem.getMemoryRange((short)0x0000,(short)0x0004));

        input = "00 xx ff";

        output = new byte[] {
                (byte) 0x00,
                (byte) 0xff
        };

        mem.load(input);

        Assertions.assertArrayEquals(output,mem.getMemoryRange((short)0x0000,(short)0x0001));

        output = new byte[]{
                (byte) 0x12,
                (byte) 0x33,
                (byte) 0xff,
                (byte) 0xf3,
                (byte) 0xa1
        };

        input = "12 33 ff f3 a1";

        mem.load((short)0xfff0, input);

        Assertions.assertArrayEquals(output, mem.getMemoryRange((short)0xfff0,(short)0xfff4));


        mem.load((short)0x1233, input);

        Assertions.assertArrayEquals(output, mem.getMemoryRange((short)0x1233,(short)0x1237));

    }

    @Test
    void loadMemoryFromByteArrayTest() {

        Memory mem = new Memory();

        byte[] input = {
                (byte) 0x12,
                (byte) 0x33,
                (byte) 0xff,
                (byte) 0xf3,
                (byte) 0xa1
        };

        mem.load(input);

        Assertions.assertArrayEquals(input, mem.getMemoryRange((short)0x0000, (short)0x0004));

        mem.load((short)0x1233, input);

        byte[] test = mem.getMemoryRange((short)0x1233, (short)0x1237);

        Assertions.assertArrayEquals(input, mem.getMemoryRange((short)0x1233, (short)0x1237));

    }

}

