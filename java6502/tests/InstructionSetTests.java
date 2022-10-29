package java6502.tests;


import java6502.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InstructionSetTests{

    private Memory memory;
    private Flags flags;
    private InstructionSet is;
    private Stack stack;
    private AddressingModeReturn input;

    @BeforeEach
    public void init(){
        this.memory = new Memory();
        this.stack = new Stack(this.memory);
        this.flags = new Flags();
        this.is = new InstructionSet(memory,stack,flags);
        this.input = new AddressingModeReturn();
    }

    @Test
    public void adcTest(){

        // -----------------------------------
        this.memory.setRegisterA((byte)10);
        this.flags.setDecimalMode(false);
        this.flags.setCarry(false);
        this.input.set((byte)7,(short)0x0);

        this.is.ADC(input);

        Assertions.assertEquals((byte)17,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getOverFlow());

        // -----------------------------------
        this.memory.setRegisterA((byte)120);
        this.flags.setDecimalMode(false);
        this.flags.setCarry(true);

        this.is.ADC(input);

        Assertions.assertEquals((byte)-128,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertTrue(this.flags.getOverFlow());

        // -----------------------------------
        this.memory.setRegisterA((byte)255);
        this.flags.setDecimalMode(false);
        this.flags.setCarry(false);

        this.is.ADC(input);

        Assertions.assertEquals((byte)6,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getOverFlow());

        // -----------------------------------
        this.memory.setRegisterA((byte)-1);
        this.flags.setDecimalMode(false);
        this.flags.setCarry(true);
        this.input.setValue((byte)0);

        this.is.ADC(input);

        Assertions.assertEquals((byte)0,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getOverFlow());

    }

    @Test
    public void andTest(){

        this.memory.setRegisterA((byte)0b00101010);
        this.flags.setZero(false);
        this.flags.setNegative(false);
        this.input.setValue((byte)0b00101010);

        this.is.AND(input);
        Assertions.assertEquals((byte)0b00101010,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        this.input.setValue((byte)0b00010101);

        this.is.AND(input);
        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());
        this.input.setValue((byte)0b10001000);
        this.memory.setRegisterA((byte)0b11001000);

        this.is.AND(input);
        Assertions.assertEquals((byte)0b10001000,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        this.input.setValue((byte)0b10101010);
        this.memory.setRegisterA((byte)0b11111111);

        this.is.AND(input);
        Assertions.assertEquals((byte)0b10101010,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    public void aslAccumulatorTest(){

        this.memory.setRegisterA((byte)0b00000001);
        this.is.ASL();
        Assertions.assertEquals((byte)0b00000010,this.memory.getRegisterA());

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());

        this.memory.setRegisterA((byte)0b10000001);
        this.is.ASL();
        Assertions.assertEquals((byte)0b00000010,this.memory.getRegisterA());

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getCarry());

        this.flags.setCarry(false);

        this.memory.setRegisterA((byte)0b10000000);
        this.is.ASL();
        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertTrue(this.flags.getCarry());

        this.memory.setRegisterA((byte)0b01000000);
        this.is.ASL();
        Assertions.assertEquals((byte)0b10000000,this.memory.getRegisterA());

        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());

        this.memory.setRegisterA((byte)0b00000000);
        this.is.ASL();
        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());

    }

    @Test
    public void aslMemoryTest(){

        this.memory.setByteAtAddress((short)0x0345,(byte)0b00000001);
        this.input.set((byte)0b00000001,(short)0x0345);
        this.is.ASL(input);
        Assertions.assertEquals((byte)0b00000010,this.memory.getByteAtAddress((short)0x0345));

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());

        this.memory.setByteAtAddress((short)0xf001,(byte)0b10000001);
        this.input.set((byte)0b10000001,(short)0xf001);
        this.is.ASL(input);
        Assertions.assertEquals((byte)0b00000010,this.memory.getByteAtAddress((short)0xf001));

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getCarry());

        this.flags.setCarry(false);

        this.memory.setByteAtAddress((short)0x002,(byte)0b10000000);
        this.input.set((byte)0b10000000,(short)0x002);
        this.is.ASL(input);
        Assertions.assertEquals((byte)0b00000000,this.memory.getByteAtAddress((short)0x002));

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertTrue(this.flags.getCarry());

        this.memory.setByteAtAddress((short)0x3456,(byte)0b01000000);
        this.input.set((byte)0b01000000,(short)0x3456);
        this.is.ASL(input);
        Assertions.assertEquals((byte)0b10000000,this.memory.getByteAtAddress((short)0x3456));

        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());

        this.memory.setByteAtAddress((short)0x1010,(byte)0b00000000);
        this.input.set((byte)0b00000000,(short)0x1010);
        this.is.ASL(input);
        Assertions.assertEquals((byte)0b00000000,this.memory.getByteAtAddress((short)0x1010));

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());

    }

    @Test
    public void bccTest(){

        this.memory.setProgramCounter((short)0x0010);
        this.flags.setCarry(true);
        this.input.setAddress((short)0x0300);

        this.is.BCC(input);

        Assertions.assertEquals((short)0x0010,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x0010);
        this.flags.setCarry(false);
        this.input.setAddress((short)0x0300);

        this.is.BCC(input);

        Assertions.assertEquals((short)0x0300,this.memory.getProgramCounter());
    }

    @Test
    public void bcsTest(){

        this.memory.setProgramCounter((short)0xf010);
        this.flags.setCarry(false);
        this.input.setAddress((short)0xf300);

        this.is.BCS(input);

        Assertions.assertEquals((short)0xf010,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0xf010);
        this.flags.setCarry(true);
        this.input.setAddress((short)0xf300);

        this.is.BCS(input);

        Assertions.assertEquals((short)0xf300,this.memory.getProgramCounter());
    }

    @Test
    public void beqTest(){

        this.memory.setProgramCounter((short)0x1234);
        this.flags.setZero(false);
        this.input.setAddress((short)0x6969);

        this.is.BEQ(input);

        Assertions.assertEquals((short)0x1234,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x1234);
        this.flags.setZero(true);
        this.input.setAddress((short)0x6969);

        this.is.BEQ(input);

        Assertions.assertEquals((short)0x6969,this.memory.getProgramCounter());
    }
}
