package java6502.tests;


import java6502.Flags;
import java6502.InstructionSet;
import java6502.Memory;
import java6502.Stack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InstructionSetTests{

    private Memory memory;
    private Flags flags;
    private InstructionSet is;
    private Stack stack;

    @BeforeEach
    public void init(){
        this.memory = new Memory();
        this.stack = new Stack(this.memory);
        this.flags = new Flags();
        this.is = new InstructionSet(memory,stack,flags);
    }

    @Test
    public void adcTest(){

        // -----------------------------------
        this.memory.setRegisterA((byte)10);
        this.flags.setDecimalMode(false);
        this.flags.setCarry(false);

        this.is.ADC((byte)7);

        Assertions.assertEquals((byte)17,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getOverFlow());

        // -----------------------------------
        this.memory.setRegisterA((byte)120);
        this.flags.setDecimalMode(false);
        this.flags.setCarry(true);

        this.is.ADC((byte)7);

        Assertions.assertEquals((byte)-128,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertTrue(this.flags.getOverFlow());

        // -----------------------------------
        this.memory.setRegisterA((byte)255);
        this.flags.setDecimalMode(false);
        this.flags.setCarry(false);

        this.is.ADC((byte)7);

        Assertions.assertEquals((byte)6,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getOverFlow());

        // -----------------------------------
        this.memory.setRegisterA((byte)-1);
        this.flags.setDecimalMode(false);
        this.flags.setCarry(true);

        this.is.ADC((byte)0);

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

        this.is.AND((byte)0b00101010);
        Assertions.assertEquals((byte)0b00101010,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.is.AND((byte)0b00010101);
        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());

        this.memory.setRegisterA((byte)0b11001000);
        this.is.AND((byte)0b10001000);
        Assertions.assertEquals((byte)0b10001000,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.memory.setRegisterA((byte)0b11111111);
        this.is.AND((byte)0b10101010);
        Assertions.assertEquals((byte)0b10101010,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }
}
