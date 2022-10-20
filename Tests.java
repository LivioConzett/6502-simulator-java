import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    public void byteToAddressTest(){

        byte low = 0x34;
        byte high = 0x12;

        Assertions.assertEquals(0x1234, Util.bytesToAddress(low,high));

        low = (byte) 0xff;
        high = (byte) 0xff;

        Assertions.assertEquals(0xffff, Util.unsignShort(Util.bytesToAddress(low,high)));

        low = (byte) 0x69;
        high = (byte) 0x32;

        Assertions.assertEquals(0x3269, Util.unsignShort(Util.bytesToAddress(low,high)));
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

class AddressingModeTest{

    private Memory memory;
    private Flags flags;
    private Stack stack;
    private Operations op;

    @BeforeEach
    public void init(){
        this.memory = new Memory();
        this.flags = new Flags();
        this.stack = new Stack(this.memory);
        this.op = new Operations(this.memory,this.stack,this.flags);
    }

    @Test
    public void immediateTest(){
        this.memory.setProgramCounter((short) 0x01);
        this.memory.setByteAtAddress((short) 0x02, (byte) 0x69);

        Assertions.assertEquals((byte)0x69,this.op.addr_Immediate());
        Assertions.assertEquals((short)0x02,this.memory.getProgramCounter());

        this.memory.setByteAtAddress((short) 0x03, (byte) 0xff);
        Assertions.assertEquals((byte)0xff,this.op.addr_Immediate());
        Assertions.assertEquals((short)0x03,this.memory.getProgramCounter());
    }

    @Test
    public void absoluteTest(){
        this.memory.setProgramCounter((short) 0x1000);
        this.memory.setByteAtAddress((short) 0x1001, (byte) 0x34);
        this.memory.setByteAtAddress((short) 0x1002, (byte) 0x12);
        this.memory.setByteAtAddress((short) 0x1234,(byte) 0xf1);

        Assertions.assertEquals((byte)0xf1,this.op.addr_Absolute());
        Assertions.assertEquals((short)0x1002,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short) 0xfff0);

        this.memory.setByteAtAddress((short) 0xfff1, (byte) 0x4b);
        this.memory.setByteAtAddress((short) 0xfff2, (byte) 0xa1);
        this.memory.setByteAtAddress((short) 0xa14b,(byte) 0x03);

        Assertions.assertEquals((byte)0x03,this.op.addr_Absolute());
        Assertions.assertEquals((short)0xfff2,this.memory.getProgramCounter());
    }

    @Test
    public void zeroPageTest(){
        this.memory.setProgramCounter((short) 0x0020);
        this.memory.setByteAtAddress((short) 0x0021, (byte) 0x1f);
        this.memory.setByteAtAddress((short) 0x001f,(byte) 0xff);

        Assertions.assertEquals((byte)0xff,this.op.addr_ZeroPage());
        Assertions.assertEquals((short)0x0021,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short) 0x00ff);
        this.memory.setByteAtAddress((short) 0x0100, (byte) 0xab);
        this.memory.setByteAtAddress((short) 0x00ab, (byte) 0xcd);

        Assertions.assertEquals((byte)0xcd,this.op.addr_ZeroPage());
        Assertions.assertEquals((short)0x0100,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short) 0x0fff);
        this.memory.setByteAtAddress((short) 0x1000, (byte) 0x30);
        this.memory.setByteAtAddress((short) 0x0030,(byte) 0xfe);

        Assertions.assertEquals((byte)0xfe,this.op.addr_ZeroPage());
        Assertions.assertEquals((short)0x1000,this.memory.getProgramCounter());
    }

    @Test
    public void indexAbsoluteTest(){
        this.memory.setProgramCounter((short) 0x1000);
        this.memory.setByteAtAddress((short) 0x1001, (byte) 0x34);
        this.memory.setByteAtAddress((short) 0x1002, (byte) 0x12);
        this.memory.setByteAtAddress((short) 0x1237,(byte) 0xf1);

        Assertions.assertEquals((byte)0xf1,this.op.addr_AbsoluteIndex((byte)0x03));
        Assertions.assertEquals((short)0x1002,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short) 0xfff0);
        this.memory.setByteAtAddress((short) 0xfff1, (byte) 0x4b);
        this.memory.setByteAtAddress((short) 0xfff2, (byte) 0xa1);
        this.memory.setByteAtAddress((short) 0xa24a,(byte) 0x03);

        Assertions.assertEquals((byte)0x03,this.op.addr_AbsoluteIndex((byte)0xff));
        Assertions.assertEquals((short)0xfff2,this.memory.getProgramCounter());

        this.memory.setRegisterX((byte) 0x80);

        this.memory.setProgramCounter((short) 0x8080);
        this.memory.setByteAtAddress((short) 0x8081, (byte) 0x80);
        this.memory.setByteAtAddress((short) 0x8082, (byte) 0x08);
        this.memory.setByteAtAddress((short) 0x0900,(byte) 0x44);

        Assertions.assertEquals((byte)0x44,this.op.addr_AbsoluteIndex_X());
        Assertions.assertEquals((short)0x8082,this.memory.getProgramCounter());

        this.memory.setRegisterY((byte) 0x92);

        this.memory.setProgramCounter((short) 0x0001);
        this.memory.setByteAtAddress((short) 0x0002, (byte) 0x32);
        this.memory.setByteAtAddress((short) 0x0003, (byte) 0xfe);
        this.memory.setByteAtAddress((short) 0xfec4,(byte) 0x09);

        Assertions.assertEquals((byte)0x09,this.op.addr_AbsoluteIndex_Y());
        Assertions.assertEquals((short)0x0003,this.memory.getProgramCounter());
    }

}