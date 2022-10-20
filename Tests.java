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

        Assertions.assertTrue(flags.getInterruptDisable());

        flags.setWholeRegister((byte)0xff);
        Assertions.assertTrue(flags.getCarry());
        Assertions.assertTrue(flags.getZero());
        Assertions.assertTrue(flags.getInterruptDisable());
        Assertions.assertTrue(flags.getDecimalMode());
        Assertions.assertTrue(flags.getBreakCommand());
        Assertions.assertTrue(flags.getOverFlow());
        Assertions.assertTrue(flags.getNegative());

        flags.reset();
        Assertions.assertFalse(flags.getCarry());
        Assertions.assertFalse(flags.getZero());
        Assertions.assertTrue(flags.getInterruptDisable());
        Assertions.assertFalse(flags.getDecimalMode());
        Assertions.assertFalse(flags.getBreakCommand());
        Assertions.assertFalse(flags.getOverFlow());
        Assertions.assertFalse(flags.getNegative());

        flags.setCarry(true);
        flags.setZero(true);
        flags.setInterruptDisable(true);
        flags.setDecimalMode(true);
        flags.setBreakCommand(true);
        flags.setOverFlow(true);
        flags.setNegative(true);

        Assertions.assertTrue(flags.getCarry());
        Assertions.assertTrue(flags.getZero());
        Assertions.assertTrue(flags.getInterruptDisable());
        Assertions.assertTrue(flags.getDecimalMode());
        Assertions.assertTrue(flags.getBreakCommand());
        Assertions.assertTrue(flags.getOverFlow());
        Assertions.assertTrue(flags.getNegative());

        flags.setCarry(false);
        flags.setZero(false);
        flags.setInterruptDisable(false);
        flags.setDecimalMode(false);
        flags.setBreakCommand(false);
        flags.setOverFlow(false);
        flags.setNegative(false);

        Assertions.assertFalse(flags.getCarry());
        Assertions.assertFalse(flags.getZero());
        Assertions.assertFalse(flags.getInterruptDisable());
        Assertions.assertFalse(flags.getDecimalMode());
        Assertions.assertFalse(flags.getBreakCommand());
        Assertions.assertFalse(flags.getOverFlow());
        Assertions.assertFalse(flags.getNegative());

        flags.setWholeRegister((byte) 0xff);

        Assertions.assertEquals((byte) 0b11011111, flags.getWholeRegister());
        Assertions.assertNotEquals((byte) 0b11111111, flags.getWholeRegister());
    }
}

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

class StackTest{

    @Test
    public void stack(){
        Memory mem = new Memory();

        Stack stack = new Stack(new Memory());
        byte test = (byte) 0xff;

        stack.setStackPointer((byte) 0xff);

        stack.push((byte) 0xff);

        Assertions.assertEquals(0xfe, stack.getStackPointer());
        Assertions.assertEquals(0xff, Util.unsignByte(stack.get(test)));

        stack.push((byte) 0x1);
        test--;

        Assertions.assertEquals(0xfd, stack.getStackPointer());
        Assertions.assertEquals(0x1, stack.get(test));

        Assertions.assertEquals(0x1, stack.pull());
        Assertions.assertEquals(0xfe, stack.getStackPointer());
    }
}