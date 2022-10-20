import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class UtilTests{

    @Test
    public void unsignByteTest(){
        byte testByte = -1;
        Assertions.assertEquals(0xff, Util.unsignByte(testByte));

        testByte = (byte) 30;
        Assertions.assertEquals(30, Util.unsignByte(testByte));

        testByte = (byte) 255;
        Assertions.assertEquals(0xff, Util.unsignByte(testByte));
    }
}

class FlagTests {
    @Test
    public void flagTests() {
        Flags flags = new Flags();

        assertTrue(flags.getInterruptDisable());

        flags.setWholeRegister((byte)0xff);
        assertTrue(flags.getCarry());
        assertTrue(flags.getZero());
        assertTrue(flags.getInterruptDisable());
        assertTrue(flags.getDecimalMode());
        assertTrue(flags.getBreakCommand());
        assertTrue(flags.getOverFlow());
        assertTrue(flags.getNegative());

        flags.reset();
        assertFalse(flags.getCarry());
        assertFalse(flags.getZero());
        assertTrue(flags.getInterruptDisable());
        assertFalse(flags.getDecimalMode());
        assertFalse(flags.getBreakCommand());
        assertFalse(flags.getOverFlow());
        assertFalse(flags.getNegative());

        flags.setCarry(true);
        flags.setZero(true);
        flags.setInterruptDisable(true);
        flags.setDecimalMode(true);
        flags.setBreakCommand(true);
        flags.setOverFlow(true);
        flags.setNegative(true);

        assertTrue(flags.getCarry());
        assertTrue(flags.getZero());
        assertTrue(flags.getInterruptDisable());
        assertTrue(flags.getDecimalMode());
        assertTrue(flags.getBreakCommand());
        assertTrue(flags.getOverFlow());
        assertTrue(flags.getNegative());

        flags.setCarry(false);
        flags.setZero(false);
        flags.setInterruptDisable(false);
        flags.setDecimalMode(false);
        flags.setBreakCommand(false);
        flags.setOverFlow(false);
        flags.setNegative(false);

        assertFalse(flags.getCarry());
        assertFalse(flags.getZero());
        assertFalse(flags.getInterruptDisable());
        assertFalse(flags.getDecimalMode());
        assertFalse(flags.getBreakCommand());
        assertFalse(flags.getOverFlow());
        assertFalse(flags.getNegative());

        flags.setWholeRegister((byte)0xff);

        assertEquals((byte) 0b11011111, flags.getWholeRegister());
        assertNotEquals((byte) 0b11111111, flags.getWholeRegister());

    }
}

class MemoryTest{

    @Test
    public void programCounterTest(){

        Memory mem = new Memory();

        assertEquals(0xfffc, mem.getProgramCounter());

        mem.setProgramCounter(0x0);

        assertEquals(0,mem.getProgramCounter());

        mem.incrementProgramCounter(1);

        assertEquals(1,mem.getProgramCounter());

        mem.incrementProgramCounter(29);

        assertEquals(30, mem.getProgramCounter());

        mem.setByteAtAddress(0x8484, (byte)0xd0);
        mem.setByteAtAddress(0x8485, (byte)0x69);
        mem.setProgramCounter(0x8484);

        assertEquals(0xd0,Util.unsignByte(mem.getCurrentByte()));

        mem.incrementProgramCounter();
        assertEquals(0x69,Util.unsignByte(mem.getCurrentByte()));

    }
}

class UnsignedNumberTest{

    @Test
    public void unsignedNumber(){

        UnsignedNumber num = new UnsignedNumber(8);

        assertEquals(0, num.get());

        num.set(30);

        assertEquals(30,num.get());

        num.increment();

        assertEquals(31, num.get());

        num.decrement();

        assertEquals(30,num.get());

        num.add(34);

        assertEquals(64,num.get());

        num.subtract(11);

        assertEquals(53,num.get());

        num.set(0xfd);
        num.add(4);

        assertEquals(1,num.get());

        num.set(0xff69);

        assertEquals(0x69, num.get());

        num.set(2);
        num.subtract(3);

        assertEquals(255,num.get());

    }
}

class StackTest{

    @Test
    public void stack(){
        Memory mem = new Memory();

        Stack stack = new Stack(new Memory());
        UnsignedNumber test = new UnsignedNumber(8,0xff);

        stack.setStackPointer(0xff);

        stack.push((byte) 0xff);

        assertEquals(0xfe,stack.getStackPointer());
        assertEquals(0xff, Util.unsignByte(stack.get(test)));

        stack.push((byte) 0x1);
        test.decrement();

        assertEquals(0xfd,stack.getStackPointer());
        assertEquals(0x1, stack.get(test));

        assertEquals(0x1,stack.pull());
        assertEquals(0xfe, stack.getStackPointer());
    }
}