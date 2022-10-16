import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

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

        mem.setProgramCounter((short)0x0);

        assertEquals(0,mem.getProgramCounter());

        mem.incrementProgramCounter(1);

        assertEquals(1,mem.getProgramCounter());

        mem.incrementProgramCounter(29);

        assertEquals(30, mem.getProgramCounter());


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
