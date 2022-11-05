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

    @Test
    public void bitTest(){

        this.memory.setRegisterA((byte)0b01010101);
        this.input.setValue((byte)0b01010001);

        this.is.BIT(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getOverFlow());

        this.memory.setRegisterA((byte)0b00010101);
        this.input.setValue((byte)0b00000010);

        this.is.BIT(input);

        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getOverFlow());

        this.memory.setRegisterA((byte)0b10100000);
        this.input.setValue((byte)0b00100000);

        this.is.BIT(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getOverFlow());

        this.memory.setRegisterA((byte)0b10100000);
        this.input.setValue((byte)0b10000000);

        this.is.BIT(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getOverFlow());

    }

    @Test
    public void bmiTest(){

        this.memory.setProgramCounter((short)0x3333);
        this.flags.setNegative(false);
        this.input.setAddress((short)0x458e);

        this.is.BMI(input);

        Assertions.assertEquals((short)0x3333,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x1234);
        this.flags.setNegative(true);
        this.input.setAddress((short)0x458e);

        this.is.BMI(input);

        Assertions.assertEquals((short)0x458e,this.memory.getProgramCounter());
    }

    @Test
    public void bneTest(){

        this.memory.setProgramCounter((short)0xfabcd);
        this.flags.setZero(true);
        this.input.setAddress((short)0x12ef);

        this.is.BNE(input);

        Assertions.assertEquals((short)0xfabcd,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x1234);
        this.flags.setZero(false);
        this.input.setAddress((short)0x12ef);

        this.is.BNE(input);

        Assertions.assertEquals((short)0x12ef,this.memory.getProgramCounter());
    }

    @Test
    public void bplTest(){

        this.memory.setProgramCounter((short)0x3333);
        this.flags.setNegative(false);
        this.input.setAddress((short)0x458e);

        this.is.BPL(input);

        Assertions.assertEquals((short)0x458e,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x1234);
        this.flags.setNegative(true);
        this.input.setAddress((short)0x458e);

        this.is.BPL(input);

        Assertions.assertEquals((short)0x1234,this.memory.getProgramCounter());
    }

    @Test
    public void brkTest(){

        this.flags.reset();

        this.memory.setByteAtAddress((short)0xfffe,(byte)0x34);
        this.memory.setByteAtAddress((short)0xffff,(byte)0x12);
        this.stack.setStackPointer((byte)0xff);

        this.flags.setOverFlow(true);
        this.flags.setCarry(true);

        this.memory.setProgramCounter((short)0xaabb);

        this.is.BRK();

        Assertions.assertEquals((short)0x1234,this.memory.getProgramCounter());
        Assertions.assertEquals((byte)0xfc,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0xaa,this.memory.getByteAtAddress((short)0x01ff));
        Assertions.assertEquals((byte)0xbc,this.memory.getByteAtAddress((short)0x01fe));
        Assertions.assertEquals((byte)0x55,this.memory.getByteAtAddress((short)0x01fd));

    }

    @Test
    public void bvcTest(){

        this.memory.setProgramCounter((short)0xfabc);
        this.flags.setOverFlow(false);
        this.input.setAddress((short)0xabcd);

        this.is.BVC(input);

        Assertions.assertEquals((short)0xabcd,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0xfabc);
        this.flags.setOverFlow(true);
        this.input.setAddress((short)0xabcd);

        this.is.BVC(input);

        Assertions.assertEquals((short)0xfabc,this.memory.getProgramCounter());
    }

    @Test
    public void bvsTest(){

        this.memory.setProgramCounter((short)0xc3f5);
        this.flags.setOverFlow(false);
        this.input.setAddress((short)0x1928);

        this.is.BVS(input);

        Assertions.assertEquals((short)0xc3f5,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0xc3f5);
        this.flags.setOverFlow(true);
        this.input.setAddress((short)0x1928);

        this.is.BVS(input);

        Assertions.assertEquals((short)0x1928,this.memory.getProgramCounter());
    }

    @Test
    public void clcTest(){
        this.flags.setCarry(true);
        this.is.CLC();
        Assertions.assertFalse(this.flags.getCarry());
    }

    @Test
    public void cldTest(){
        this.flags.setDecimalMode(true);
        this.is.CLD();
        Assertions.assertFalse(this.flags.getDecimalMode());
    }

    @Test
    public void cliTest(){
        this.flags.setInterruptDisable(true);
        this.is.CLI();
        Assertions.assertFalse(this.flags.getInterruptDisable());
    }

    @Test
    public void clvTest(){
        this.flags.setOverFlow(true);
        this.is.CLV();
        Assertions.assertFalse(this.flags.getOverFlow());
    }

    @Test
    public void compareTest(){

        byte register = 0x12;
        byte memory = 0x12;
        this.flags.reset();


        this.is.compare(register,memory);

        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        memory = 0x10;

        this.is.compare(register,memory);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        memory = 0x20;

        this.is.compare(register,memory);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());


        register = (byte)0x91;

        memory = (byte)0xf0;

        this.is.compare(register, memory);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

        memory = (byte)0x90;

        this.is.compare(register,memory);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

    }

    @Test
    public void cmpTest(){

        this.memory.setRegisterA((byte)0x12);
        this.flags.reset();
        AddressingModeReturn input = new AddressingModeReturn();

        input.setValue((byte)0x12);

        this.is.CMP(input);

        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        input.setValue((byte)0x10);

        this.is.CMP(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        input.setValue((byte)0x20);

        this.is.CMP(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());


        this.memory.setRegisterA((byte)0x91);

        input.setValue((byte)0xf0);

        this.is.CMP(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

        input.setValue((byte)0x90);

        this.is.CMP(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

    }

    @Test
    public void cpxTest(){

        this.memory.setRegisterX((byte)0x12);
        this.flags.reset();
        AddressingModeReturn input = new AddressingModeReturn();

        input.setValue((byte)0x12);

        this.is.CPX(input);

        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        input.setValue((byte)0x10);

        this.is.CPX(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        input.setValue((byte)0x20);

        this.is.CPX(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());


        this.memory.setRegisterX((byte)0x91);

        input.setValue((byte)0xf0);

        this.is.CPX(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

        input.setValue((byte)0x90);

        this.is.CPX(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

    }

    @Test
    public void cpyTest(){

        this.memory.setRegisterY((byte)0x12);
        this.flags.reset();
        AddressingModeReturn input = new AddressingModeReturn();

        input.setValue((byte)0x12);

        this.is.CPY(input);

        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        input.setValue((byte)0x10);

        this.is.CPY(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        input.setValue((byte)0x20);

        this.is.CPY(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());


        this.memory.setRegisterY((byte)0x91);

        input.setValue((byte)0xf0);

        this.is.CPY(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

        input.setValue((byte)0x90);

        this.is.CPY(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

    }

    @Test
    public void decTest(){

        short addr = 0x5431;

        AddressingModeReturn input = new AddressingModeReturn((byte)0x13,addr);
        this.memory.setByteAtAddress(addr,(byte)0x13);

        this.is.DEC(input);

        Assertions.assertEquals((byte)0x12,this.memory.getByteAtAddress(addr));
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.memory.setByteAtAddress(addr,(byte)0x01);
        input.setValue((byte)0x01);
        this.is.DEC(input);

        Assertions.assertEquals((byte)0x00,this.memory.getByteAtAddress(addr));
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());


        input.setValue((byte)0x00);
        this.is.DEC(input);

        Assertions.assertEquals((byte)0xff,this.memory.getByteAtAddress(addr));
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    public void dexTest(){

        this.memory.setRegisterX((byte)0x02);
        this.is.DEX();

        Assertions.assertEquals((byte)0x01,this.memory.getRegisterX());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.is.DEX();

        Assertions.assertEquals((byte)0x00,this.memory.getRegisterX());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());

        this.is.DEX();

        Assertions.assertEquals((byte)0xff,this.memory.getRegisterX());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    public void deyTest(){

        this.memory.setRegisterY((byte)0x02);
        this.is.DEY();

        Assertions.assertEquals((byte)0x01,this.memory.getRegisterY());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.is.DEY();

        Assertions.assertEquals((byte)0x00,this.memory.getRegisterY());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());

        this.is.DEY();

        Assertions.assertEquals((byte)0xff,this.memory.getRegisterY());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    public void eorTest(){
        AddressingModeReturn input = new AddressingModeReturn();
        input.setValue((byte)0b00000000);
        this.memory.setRegisterA((byte)0b01010101);

        this.is.EOR(input);

        Assertions.assertEquals((byte)0b01010101,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        input.setValue((byte)0b10101010);
        this.memory.setRegisterA((byte)0b01010101);

        this.is.EOR(input);

        Assertions.assertEquals((byte)0b11111111,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        input.setValue((byte)0b11111111);
        this.memory.setRegisterA((byte)0b11111111);

        this.is.EOR(input);

        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());

        input.setValue((byte)0b01000001);
        this.memory.setRegisterA((byte)0b01000011);

        this.is.EOR(input);

        Assertions.assertEquals((byte)0b00000010,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());



    }

    @Test
    public void incTest(){

        short addr = 0x5431;

        AddressingModeReturn input = new AddressingModeReturn((byte)0x13,addr);
        this.memory.setByteAtAddress(addr,(byte)0x13);

        this.is.INC(input);

        Assertions.assertEquals((byte)0x14,this.memory.getByteAtAddress(addr));
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.memory.setByteAtAddress(addr,(byte)0xff);
        input.setValue((byte)0xff);
        this.is.INC(input);

        Assertions.assertEquals((byte)0x00,this.memory.getByteAtAddress(addr));
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());


        input.setValue((byte)0x7f);
        this.is.INC(input);

        Assertions.assertEquals((byte)0x80,this.memory.getByteAtAddress(addr));
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    public void inxTest(){

        this.memory.setRegisterX((byte)0x02);
        this.is.INX();

        Assertions.assertEquals((byte)0x03,this.memory.getRegisterX());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.memory.setRegisterX((byte)0xff);
        this.is.INX();

        Assertions.assertEquals((byte)0x00,this.memory.getRegisterX());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());

        this.memory.setRegisterX((byte)0x7f);
        this.is.INX();

        Assertions.assertEquals((byte)0x80,this.memory.getRegisterX());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    public void inyTest(){

        this.memory.setRegisterY((byte)0x02);
        this.is.INY();

        Assertions.assertEquals((byte)0x03,this.memory.getRegisterY());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.memory.setRegisterY((byte)0xff);
        this.is.INY();

        Assertions.assertEquals((byte)0x00,this.memory.getRegisterY());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());

        this.memory.setRegisterY((byte)0x7f);
        this.is.INY();

        Assertions.assertEquals((byte)0x80,this.memory.getRegisterY());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    public void jmpTest(){
        AddressingModeReturn input = new AddressingModeReturn();
        input.setAddress((short)0x1234);

        this.memory.setProgramCounter((short)0x9fa3);

        this.is.JMP(input);

        Assertions.assertEquals((short)0x1234,this.memory.getProgramCounter());
    }

    @Test
    public void jsrTest(){
        this.flags.reset();
        AddressingModeReturn input = new AddressingModeReturn();
        input.setAddress((short)0x1234);
        this.memory.setProgramCounter((short)0xabcd);

        this.stack.setStackPointer((byte)0xff);

        this.flags.setZero(true);
        this.flags.setNegative(true);

        this.is.JSR(input);

        Assertions.assertEquals((byte)0xfc,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0xab, this.memory.getByteAtAddress((short)0x01ff));
        Assertions.assertEquals((byte)0xcd, this.memory.getByteAtAddress((short)0x01fe));
        Assertions.assertEquals((byte)0x86, this.memory.getByteAtAddress((short)0x01fd));
        Assertions.assertEquals((short)0x1234,this.memory.getProgramCounter());
    }

    @Test
    public void ldaTest(){
        AddressingModeReturn input = new AddressingModeReturn((byte)0x69,(short)0x1234);
        this.memory.setRegisterA((byte)0xf);

        this.is.LDA(input);

        Assertions.assertEquals((byte)0x69,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        input.setValue((byte)0x81);
        this.is.LDA(input);

        Assertions.assertEquals((byte)0x81,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        input.setValue((byte)0x0);
        this.is.LDA(input);

        Assertions.assertEquals((byte)0x0,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
    }

    @Test
    public void ldxTest(){
        AddressingModeReturn input = new AddressingModeReturn((byte)0x69,(short)0x1234);
        this.memory.setRegisterX((byte)0xf);

        this.is.LDX(input);

        Assertions.assertEquals((byte)0x69,this.memory.getRegisterX());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        input.setValue((byte)0x81);
        this.is.LDX(input);

        Assertions.assertEquals((byte)0x81,this.memory.getRegisterX());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        input.setValue((byte)0x0);
        this.is.LDX(input);

        Assertions.assertEquals((byte)0x0,this.memory.getRegisterX());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
    }

    @Test
    public void ldyTest(){
        AddressingModeReturn input = new AddressingModeReturn((byte)0x69,(short)0x1234);
        this.memory.setRegisterY((byte)0xf);

        this.is.LDY(input);

        Assertions.assertEquals((byte)0x69,this.memory.getRegisterY());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        input.setValue((byte)0x81);
        this.is.LDY(input);

        Assertions.assertEquals((byte)0x81,this.memory.getRegisterY());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        input.setValue((byte)0x0);
        this.is.LDY(input);

        Assertions.assertEquals((byte)0x0,this.memory.getRegisterY());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
    }

    @Test
    public void lsrAccTest(){

        this.flags.reset();

        this.memory.setRegisterA((byte)0b00000000);

        this.is.LSR();

        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

        this.memory.setRegisterA((byte)0b00000001);

        this.is.LSR();

        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        this.memory.setRegisterA((byte)0b10010100);

        this.is.LSR();

        Assertions.assertEquals((byte)0b01001010,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

    }

    @Test
    public void lsrMemTest(){

        short addr = 0x4fe5;
        AddressingModeReturn input = new AddressingModeReturn((byte)0b00000000,addr);

        this.flags.reset();

        this.memory.setByteAtAddress(addr,(byte)0b00000000);

        this.is.LSR(input);

        Assertions.assertEquals((byte)0b00000000,this.memory.getByteAtAddress(addr));
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

        this.memory.setByteAtAddress(addr,(byte)0b00000001);
        input.setValue((byte)0b00000001);
        this.is.LSR(input);

        Assertions.assertEquals((byte)0b00000000,this.memory.getByteAtAddress(addr));
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        this.memory.setByteAtAddress(addr,(byte)0b10010100);
        input.setValue((byte)0b10010100);
        this.is.LSR(input);

        Assertions.assertEquals((byte)0b01001010,this.memory.getByteAtAddress(addr));
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

    }

    @Test
    public void oraTest(){

        AddressingModeReturn input = new AddressingModeReturn((byte)0b00000000,(short)0x0);
        this.memory.setRegisterA((byte)0b00000000);

        this.is.ORA(input);

        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setRegisterA((byte)0b01010101);
        input.setValue((byte)0b10101010);
        this.is.ORA(input);

        Assertions.assertEquals((byte)0b11111111,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        this.memory.setRegisterA((byte)0b00100001);
        input.setValue((byte)0b00100010);
        this.is.ORA(input);

        Assertions.assertEquals((byte)0b00100011,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
    }

    @Test
    public void phaTest(){
        this.stack.setStackPointer((byte)0xff);
        this.memory.setRegisterA((byte)0x35);

        this.is.PHA();

        Assertions.assertEquals((byte)0x0fe,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0x35,this.memory.getByteAtAddress((short)0x01ff));

        this.memory.setRegisterA((byte)0x12);
        this.is.PHA();

        Assertions.assertEquals((byte)0x0fd,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0x12,this.memory.getByteAtAddress((short)0x01fe));
    }

    @Test
    public void phpTest(){

        this.stack.setStackPointer((byte)0xff);
        this.flags.setWholeRegister((byte)0b01001010);

        this.is.PHP();

        Assertions.assertEquals((byte)0xfe,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0b01001010,this.memory.getByteAtAddress((short)0x01ff));

        this.flags.setWholeRegister((byte)0b10000010);

        this.is.PHP();

        Assertions.assertEquals((byte)0xfd,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0b10000010,this.memory.getByteAtAddress((short)0x01fe));
    }

    @Test
    public void plaTest(){

        this.memory.setByteAtAddress((short)0x1ff,(byte)0x0f);
        this.memory.setByteAtAddress((short)0x1fe,(byte)0x91);
        this.memory.setByteAtAddress((short)0x1fd,(byte)0x00);
        this.memory.setByteAtAddress((short)0x1fc,(byte)0x01);

        this.stack.setStackPointer((byte)0xfb);

        this.is.PLA();

        Assertions.assertEquals((byte)0xfc,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0x01,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.is.PLA();

        Assertions.assertEquals((byte)0xfd,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0x00,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.is.PLA();

        Assertions.assertEquals((byte)0xfe,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0x91,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        this.is.PLA();

        Assertions.assertEquals((byte)0xff,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0x0f,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

    }

    @Test 
    public void plpTest(){
        this.stack.setStackPointer((byte)0xfe);
        this.flags.reset();
        this.memory.setByteAtAddress((short)0x01ff, (byte)0b01001010);

        this.is.PLP();

        Assertions.assertEquals((byte)0b01001010, this.flags.getWholeRegister());
        Assertions.assertEquals((byte)0xff,this.stack.getStackPointer());

    }

    @Test 
    public void rolAccTest(){

        this.memory.setRegisterA((byte)0b00000001);

        this.is.ROL();

        Assertions.assertEquals((byte)0b00000010, this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setRegisterA((byte)0b10000000);

        this.is.ROL();

        Assertions.assertEquals((byte)0b00000000, this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.is.ROL();

        Assertions.assertEquals((byte)0b00000001, this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setRegisterA((byte)0b11000000);

        this.is.ROL();

        Assertions.assertEquals((byte)0b10000000, this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

    }

    @Test 
    public void rolMemTest(){
        
        short addr = (short) 0x1234;
        AddressingModeReturn input = new AddressingModeReturn((byte)0b00000001,addr);

        this.memory.setByteAtAddress(addr,(byte)0b00000001);

        this.is.ROL(input);

        Assertions.assertEquals((byte)0b00000010, this.memory.getByteAtAddress(addr));
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setByteAtAddress(addr,(byte)0b10000000);
        input.setValue((byte)0b10000000);

        this.is.ROL(input);

        Assertions.assertEquals((byte)0b00000000, this.memory.getByteAtAddress(addr));
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        input.setValue((byte)0b00000000);
        this.is.ROL(input);

        Assertions.assertEquals((byte)0b00000001, this.memory.getByteAtAddress(addr));
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setByteAtAddress(addr,(byte)0b11000000);
        input.setValue((byte)0b11000000);

        this.is.ROL(input);

        Assertions.assertEquals((byte)0b10000000, this.memory.getByteAtAddress(addr));
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

    }

    @Test 
    public void rorAccTest(){

        this.memory.setRegisterA((byte)0b00000010);

        this.is.ROR();

        Assertions.assertEquals((byte)0b00000001, this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setRegisterA((byte)0b00000001);

        this.is.ROR();

        Assertions.assertEquals((byte)0b00000000, this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.is.ROR();

        Assertions.assertEquals((byte)0b10000000, this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        this.memory.setRegisterA((byte)0b11000001);

        this.is.ROR();

        Assertions.assertEquals((byte)0b01100000, this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

    }

    @Test 
    public void rorMemTest(){
        
        short addr = (short) 0x023f;
        AddressingModeReturn input = new AddressingModeReturn((byte)0b00000010,addr);

        this.memory.setByteAtAddress(addr,(byte)0b00000010);

        this.is.ROR(input);

        Assertions.assertEquals((byte)0b00000001, this.memory.getByteAtAddress(addr));
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setByteAtAddress(addr,(byte)0b00000001);
        input.setValue((byte)0b00000001);

        this.is.ROR(input);

        Assertions.assertEquals((byte)0b00000000, this.memory.getByteAtAddress(addr));
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        input.setValue((byte)0b00000000);
        this.is.ROR(input);

        Assertions.assertEquals((byte)0b10000000, this.memory.getByteAtAddress(addr));
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        this.memory.setByteAtAddress(addr,(byte)0b11000001);
        input.setValue((byte)0b11000001);

        this.is.ROR(input);

        Assertions.assertEquals((byte)0b01100000, this.memory.getByteAtAddress(addr));
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

    }

    //TODO test for RTI
}
