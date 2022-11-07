package tech.livio.java6502;


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
    void adcTest(){

        // -----------------------------------
        this.memory.setRegisterA((byte)10);
        this.flags.setDecimalMode(false);
        this.flags.setCarry(false);
        this.input.set((byte)7,(short)0x0);

        this.is.adc(input);

        Assertions.assertEquals((byte)17,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getOverFlow());

        // -----------------------------------
        this.memory.setRegisterA((byte)120);
        this.flags.setDecimalMode(false);
        this.flags.setCarry(true);

        this.is.adc(input);

        Assertions.assertEquals((byte)-128,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertTrue(this.flags.getOverFlow());

        // -----------------------------------
        this.memory.setRegisterA((byte)255);
        this.flags.setDecimalMode(false);
        this.flags.setCarry(false);

        this.is.adc(input);

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

        this.is.adc(input);

        Assertions.assertEquals((byte)0,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getOverFlow());

    }

    @Test
    void andTest(){

        this.memory.setRegisterA((byte)0b00101010);
        this.flags.setZero(false);
        this.flags.setNegative(false);
        this.input.setValue((byte)0b00101010);

        this.is.and(input);
        Assertions.assertEquals((byte)0b00101010,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        this.input.setValue((byte)0b00010101);

        this.is.and(input);
        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());
        this.input.setValue((byte)0b10001000);
        this.memory.setRegisterA((byte)0b11001000);

        this.is.and(input);
        Assertions.assertEquals((byte)0b10001000,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        this.input.setValue((byte)0b10101010);
        this.memory.setRegisterA((byte)0b11111111);

        this.is.and(input);
        Assertions.assertEquals((byte)0b10101010,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    void aslAccumulatorTest(){

        this.memory.setRegisterA((byte)0b00000001);
        this.is.asl();
        Assertions.assertEquals((byte)0b00000010,this.memory.getRegisterA());

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());

        this.memory.setRegisterA((byte)0b10000001);
        this.is.asl();
        Assertions.assertEquals((byte)0b00000010,this.memory.getRegisterA());

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getCarry());

        this.flags.setCarry(false);

        this.memory.setRegisterA((byte)0b10000000);
        this.is.asl();
        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertTrue(this.flags.getCarry());

        this.memory.setRegisterA((byte)0b01000000);
        this.is.asl();
        Assertions.assertEquals((byte)0b10000000,this.memory.getRegisterA());

        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());

        this.memory.setRegisterA((byte)0b00000000);
        this.is.asl();
        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());

    }

    @Test
    void aslMemoryTest(){

        this.memory.setByteAtAddress((short)0x0345,(byte)0b00000001);
        this.input.set((byte)0b00000001,(short)0x0345);
        this.is.asl(input);
        Assertions.assertEquals((byte)0b00000010,this.memory.getByteAtAddress((short)0x0345));

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());

        this.memory.setByteAtAddress((short)0xf001,(byte)0b10000001);
        this.input.set((byte)0b10000001,(short)0xf001);
        this.is.asl(input);
        Assertions.assertEquals((byte)0b00000010,this.memory.getByteAtAddress((short)0xf001));

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getCarry());

        this.flags.setCarry(false);

        this.memory.setByteAtAddress((short)0x002,(byte)0b10000000);
        this.input.set((byte)0b10000000,(short)0x002);
        this.is.asl(input);
        Assertions.assertEquals((byte)0b00000000,this.memory.getByteAtAddress((short)0x002));

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertTrue(this.flags.getCarry());

        this.memory.setByteAtAddress((short)0x3456,(byte)0b01000000);
        this.input.set((byte)0b01000000,(short)0x3456);
        this.is.asl(input);
        Assertions.assertEquals((byte)0b10000000,this.memory.getByteAtAddress((short)0x3456));

        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());

        this.memory.setByteAtAddress((short)0x1010,(byte)0b00000000);
        this.input.set((byte)0b00000000,(short)0x1010);
        this.is.asl(input);
        Assertions.assertEquals((byte)0b00000000,this.memory.getByteAtAddress((short)0x1010));

        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getCarry());

    }

    @Test
    void bccTest(){

        this.memory.setProgramCounter((short)0x0010);
        this.flags.setCarry(true);
        this.input.setAddress((short)0x0300);

        this.is.bcc(input);

        Assertions.assertEquals((short)0x0010,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x0010);
        this.flags.setCarry(false);
        this.input.setAddress((short)0x0300);

        this.is.bcc(input);

        Assertions.assertEquals((short)0x0300,this.memory.getProgramCounter());
    }

    @Test
    void bcsTest(){

        this.memory.setProgramCounter((short)0xf010);
        this.flags.setCarry(false);
        this.input.setAddress((short)0xf300);

        this.is.bcs(input);

        Assertions.assertEquals((short)0xf010,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0xf010);
        this.flags.setCarry(true);
        this.input.setAddress((short)0xf300);

        this.is.bcs(input);

        Assertions.assertEquals((short)0xf300,this.memory.getProgramCounter());
    }

    @Test
    void beqTest(){

        this.memory.setProgramCounter((short)0x1234);
        this.flags.setZero(false);
        this.input.setAddress((short)0x6969);

        this.is.beq(input);

        Assertions.assertEquals((short)0x1234,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x1234);
        this.flags.setZero(true);
        this.input.setAddress((short)0x6969);

        this.is.beq(input);

        Assertions.assertEquals((short)0x6969,this.memory.getProgramCounter());
    }

    @Test
    void bitTest(){

        this.memory.setRegisterA((byte)0b01010101);
        this.input.setValue((byte)0b01010001);

        this.is.bit(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getOverFlow());

        this.memory.setRegisterA((byte)0b00010101);
        this.input.setValue((byte)0b00000010);

        this.is.bit(input);

        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getOverFlow());

        this.memory.setRegisterA((byte)0b10100000);
        this.input.setValue((byte)0b00100000);

        this.is.bit(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getOverFlow());

        this.memory.setRegisterA((byte)0b10100000);
        this.input.setValue((byte)0b10000000);

        this.is.bit(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getOverFlow());

    }

    @Test
    void bmiTest(){

        this.memory.setProgramCounter((short)0x3333);
        this.flags.setNegative(false);
        this.input.setAddress((short)0x458e);

        this.is.bmi(input);

        Assertions.assertEquals((short)0x3333,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x1234);
        this.flags.setNegative(true);
        this.input.setAddress((short)0x458e);

        this.is.bmi(input);

        Assertions.assertEquals((short)0x458e,this.memory.getProgramCounter());
    }

    @Test
    void bneTest(){

        this.memory.setProgramCounter((short)0xfabcd);
        this.flags.setZero(true);
        this.input.setAddress((short)0x12ef);

        this.is.bne(input);

        Assertions.assertEquals((short)0xfabcd,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x1234);
        this.flags.setZero(false);
        this.input.setAddress((short)0x12ef);

        this.is.bne(input);

        Assertions.assertEquals((short)0x12ef,this.memory.getProgramCounter());
    }

    @Test
    void bplTest(){

        this.memory.setProgramCounter((short)0x3333);
        this.flags.setNegative(false);
        this.input.setAddress((short)0x458e);

        this.is.bpl(input);

        Assertions.assertEquals((short)0x458e,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x1234);
        this.flags.setNegative(true);
        this.input.setAddress((short)0x458e);

        this.is.bpl(input);

        Assertions.assertEquals((short)0x1234,this.memory.getProgramCounter());
    }

    @Test
    void brkTest(){

        this.flags.reset();

        this.memory.setByteAtAddress((short)0xfffe,(byte)0x34);
        this.memory.setByteAtAddress((short)0xffff,(byte)0x12);
        this.stack.setStackPointer((byte)0xff);

        this.flags.setOverFlow(true);
        this.flags.setCarry(true);

        this.memory.setProgramCounter((short)0xaabb);

        this.is.brk();

        Assertions.assertEquals((short)0x1234,this.memory.getProgramCounter());
        Assertions.assertEquals((byte)0xfc,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0xaa,this.memory.getByteAtAddress((short)0x01ff));
        Assertions.assertEquals((byte)0xbc,this.memory.getByteAtAddress((short)0x01fe));
        Assertions.assertEquals((byte)0x55,this.memory.getByteAtAddress((short)0x01fd));

    }

    @Test
    void bvcTest(){

        this.memory.setProgramCounter((short)0xfabc);
        this.flags.setOverFlow(false);
        this.input.setAddress((short)0xabcd);

        this.is.bvc(input);

        Assertions.assertEquals((short)0xabcd,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0xfabc);
        this.flags.setOverFlow(true);
        this.input.setAddress((short)0xabcd);

        this.is.bvc(input);

        Assertions.assertEquals((short)0xfabc,this.memory.getProgramCounter());
    }

    @Test
    void bvsTest(){

        this.memory.setProgramCounter((short)0xc3f5);
        this.flags.setOverFlow(false);
        this.input.setAddress((short)0x1928);

        this.is.bvs(input);

        Assertions.assertEquals((short)0xc3f5,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0xc3f5);
        this.flags.setOverFlow(true);
        this.input.setAddress((short)0x1928);

        this.is.bvs(input);

        Assertions.assertEquals((short)0x1928,this.memory.getProgramCounter());
    }

    @Test
    void clcTest(){
        this.flags.setCarry(true);
        this.is.clc();
        Assertions.assertFalse(this.flags.getCarry());
    }

    @Test
    void cldTest(){
        this.flags.setDecimalMode(true);
        this.is.cld();
        Assertions.assertFalse(this.flags.getDecimalMode());
    }

    @Test
    void cliTest(){
        this.flags.setInterruptDisable(true);
        this.is.cli();
        Assertions.assertFalse(this.flags.getInterruptDisable());
    }

    @Test
    void clvTest(){
        this.flags.setOverFlow(true);
        this.is.clv();
        Assertions.assertFalse(this.flags.getOverFlow());
    }

    @Test
    void compareTest(){

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
    void cmpTest(){

        this.memory.setRegisterA((byte)0x12);
        this.flags.reset();
        AddressingModeReturn input = new AddressingModeReturn();

        input.setValue((byte)0x12);

        this.is.cmp(input);

        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        input.setValue((byte)0x10);

        this.is.cmp(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        input.setValue((byte)0x20);

        this.is.cmp(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());


        this.memory.setRegisterA((byte)0x91);

        input.setValue((byte)0xf0);

        this.is.cmp(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

        input.setValue((byte)0x90);

        this.is.cmp(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

    }

    @Test
    void cpxTest(){

        this.memory.setRegisterX((byte)0x12);
        this.flags.reset();
        AddressingModeReturn input = new AddressingModeReturn();

        input.setValue((byte)0x12);

        this.is.cpx(input);

        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        input.setValue((byte)0x10);

        this.is.cpx(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        input.setValue((byte)0x20);

        this.is.cpx(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());


        this.memory.setRegisterX((byte)0x91);

        input.setValue((byte)0xf0);

        this.is.cpx(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

        input.setValue((byte)0x90);

        this.is.cpx(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

    }

    @Test
    void cpyTest(){

        this.memory.setRegisterY((byte)0x12);
        this.flags.reset();
        AddressingModeReturn input = new AddressingModeReturn();

        input.setValue((byte)0x12);

        this.is.cpy(input);

        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        input.setValue((byte)0x10);

        this.is.cpy(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        input.setValue((byte)0x20);

        this.is.cpy(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());


        this.memory.setRegisterY((byte)0x91);

        input.setValue((byte)0xf0);

        this.is.cpy(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

        input.setValue((byte)0x90);

        this.is.cpy(input);

        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

    }

    @Test
    void decTest(){

        short address = 0x5431;

        AddressingModeReturn input = new AddressingModeReturn((byte)0x13,address);
        this.memory.setByteAtAddress(address,(byte)0x13);

        this.is.dec(input);

        Assertions.assertEquals((byte)0x12,this.memory.getByteAtAddress(address));
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.memory.setByteAtAddress(address,(byte)0x01);
        input.setValue((byte)0x01);
        this.is.dec(input);

        Assertions.assertEquals((byte)0x00,this.memory.getByteAtAddress(address));
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());


        input.setValue((byte)0x00);
        this.is.dec(input);

        Assertions.assertEquals((byte)0xff,this.memory.getByteAtAddress(address));
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    void dexTest(){

        this.memory.setRegisterX((byte)0x02);
        this.is.dex();

        Assertions.assertEquals((byte)0x01,this.memory.getRegisterX());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.is.dex();

        Assertions.assertEquals((byte)0x00,this.memory.getRegisterX());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());

        this.is.dex();

        Assertions.assertEquals((byte)0xff,this.memory.getRegisterX());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    void deyTest(){

        this.memory.setRegisterY((byte)0x02);
        this.is.dey();

        Assertions.assertEquals((byte)0x01,this.memory.getRegisterY());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.is.dey();

        Assertions.assertEquals((byte)0x00,this.memory.getRegisterY());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());

        this.is.dey();

        Assertions.assertEquals((byte)0xff,this.memory.getRegisterY());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    void eorTest(){
        AddressingModeReturn input = new AddressingModeReturn();
        input.setValue((byte)0b00000000);
        this.memory.setRegisterA((byte)0b01010101);

        this.is.eor(input);

        Assertions.assertEquals((byte)0b01010101,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        input.setValue((byte)0b10101010);
        this.memory.setRegisterA((byte)0b01010101);

        this.is.eor(input);

        Assertions.assertEquals((byte)0b11111111,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        input.setValue((byte)0b11111111);
        this.memory.setRegisterA((byte)0b11111111);

        this.is.eor(input);

        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());

        input.setValue((byte)0b01000001);
        this.memory.setRegisterA((byte)0b01000011);

        this.is.eor(input);

        Assertions.assertEquals((byte)0b00000010,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());



    }

    @Test
    void incTest(){

        short address = 0x5431;

        AddressingModeReturn input = new AddressingModeReturn((byte)0x13,address);
        this.memory.setByteAtAddress(address,(byte)0x13);

        this.is.inc(input);

        Assertions.assertEquals((byte)0x14,this.memory.getByteAtAddress(address));
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.memory.setByteAtAddress(address,(byte)0xff);
        input.setValue((byte)0xff);
        this.is.inc(input);

        Assertions.assertEquals((byte)0x00,this.memory.getByteAtAddress(address));
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());


        input.setValue((byte)0x7f);
        this.is.inc(input);

        Assertions.assertEquals((byte)0x80,this.memory.getByteAtAddress(address));
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    void inxTest(){

        this.memory.setRegisterX((byte)0x02);
        this.is.inx();

        Assertions.assertEquals((byte)0x03,this.memory.getRegisterX());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.memory.setRegisterX((byte)0xff);
        this.is.inx();

        Assertions.assertEquals((byte)0x00,this.memory.getRegisterX());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());

        this.memory.setRegisterX((byte)0x7f);
        this.is.inx();

        Assertions.assertEquals((byte)0x80,this.memory.getRegisterX());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    void inyTest(){

        this.memory.setRegisterY((byte)0x02);
        this.is.iny();

        Assertions.assertEquals((byte)0x03,this.memory.getRegisterY());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

        this.memory.setRegisterY((byte)0xff);
        this.is.iny();

        Assertions.assertEquals((byte)0x00,this.memory.getRegisterY());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getZero());

        this.memory.setRegisterY((byte)0x7f);
        this.is.iny();

        Assertions.assertEquals((byte)0x80,this.memory.getRegisterY());
        Assertions.assertTrue(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getZero());

    }

    @Test
    void jmpTest(){
        AddressingModeReturn input = new AddressingModeReturn();
        input.setAddress((short)0x1234);

        this.memory.setProgramCounter((short)0x9fa3);

        this.is.jmp(input);

        Assertions.assertEquals((short)0x1234,this.memory.getProgramCounter());
    }

    @Test
    void jsrTest(){
        this.flags.reset();
        AddressingModeReturn input = new AddressingModeReturn();
        input.setAddress((short)0x1234);
        this.memory.setProgramCounter((short)0xabcd);

        this.stack.setStackPointer((byte)0xff);

        this.flags.setZero(true);
        this.flags.setNegative(true);

        this.is.jsr(input);

        Assertions.assertEquals((byte)0xfc,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0xab, this.memory.getByteAtAddress((short)0x01ff));
        Assertions.assertEquals((byte)0xcd, this.memory.getByteAtAddress((short)0x01fe));
        Assertions.assertEquals((byte)0x86, this.memory.getByteAtAddress((short)0x01fd));
        Assertions.assertEquals((short)0x1234,this.memory.getProgramCounter());
    }

    @Test
    void ldaTest(){
        AddressingModeReturn input = new AddressingModeReturn((byte)0x69,(short)0x1234);
        this.memory.setRegisterA((byte)0xf);

        this.is.lda(input);

        Assertions.assertEquals((byte)0x69,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        input.setValue((byte)0x81);
        this.is.lda(input);

        Assertions.assertEquals((byte)0x81,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        input.setValue((byte)0x0);
        this.is.lda(input);

        Assertions.assertEquals((byte)0x0,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
    }

    @Test
    void ldxTest(){
        AddressingModeReturn input = new AddressingModeReturn((byte)0x69,(short)0x1234);
        this.memory.setRegisterX((byte)0xf);

        this.is.ldx(input);

        Assertions.assertEquals((byte)0x69,this.memory.getRegisterX());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        input.setValue((byte)0x81);
        this.is.ldx(input);

        Assertions.assertEquals((byte)0x81,this.memory.getRegisterX());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        input.setValue((byte)0x0);
        this.is.ldx(input);

        Assertions.assertEquals((byte)0x0,this.memory.getRegisterX());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
    }

    @Test
    void ldyTest(){
        AddressingModeReturn input = new AddressingModeReturn((byte)0x69,(short)0x1234);
        this.memory.setRegisterY((byte)0xf);

        this.is.ldy(input);

        Assertions.assertEquals((byte)0x69,this.memory.getRegisterY());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        input.setValue((byte)0x81);
        this.is.ldy(input);

        Assertions.assertEquals((byte)0x81,this.memory.getRegisterY());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        input.setValue((byte)0x0);
        this.is.ldy(input);

        Assertions.assertEquals((byte)0x0,this.memory.getRegisterY());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
    }

    @Test
    void lsrAccTest(){

        this.flags.reset();

        this.memory.setRegisterA((byte)0b00000000);

        this.is.lsr();

        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

        this.memory.setRegisterA((byte)0b00000001);

        this.is.lsr();

        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        this.memory.setRegisterA((byte)0b10010100);

        this.is.lsr();

        Assertions.assertEquals((byte)0b01001010,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

    }

    @Test
    void lsrMemTest(){

        short address = 0x4fe5;
        AddressingModeReturn input = new AddressingModeReturn((byte)0b00000000,address);

        this.flags.reset();

        this.memory.setByteAtAddress(address,(byte)0b00000000);

        this.is.lsr(input);

        Assertions.assertEquals((byte)0b00000000,this.memory.getByteAtAddress(address));
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

        this.memory.setByteAtAddress(address,(byte)0b00000001);
        input.setValue((byte)0b00000001);
        this.is.lsr(input);

        Assertions.assertEquals((byte)0b00000000,this.memory.getByteAtAddress(address));
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertTrue(this.flags.getCarry());

        this.memory.setByteAtAddress(address,(byte)0b10010100);
        input.setValue((byte)0b10010100);
        this.is.lsr(input);

        Assertions.assertEquals((byte)0b01001010,this.memory.getByteAtAddress(address));
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
        Assertions.assertFalse(this.flags.getCarry());

    }

    @Test
    void oraTest(){

        AddressingModeReturn input = new AddressingModeReturn((byte)0b00000000,(short)0x0);
        this.memory.setRegisterA((byte)0b00000000);

        this.is.ora(input);

        Assertions.assertEquals((byte)0b00000000,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setRegisterA((byte)0b01010101);
        input.setValue((byte)0b10101010);
        this.is.ora(input);

        Assertions.assertEquals((byte)0b11111111,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        this.memory.setRegisterA((byte)0b00100001);
        input.setValue((byte)0b00100010);
        this.is.ora(input);

        Assertions.assertEquals((byte)0b00100011,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());
    }

    @Test
    void phaTest(){
        this.stack.setStackPointer((byte)0xff);
        this.memory.setRegisterA((byte)0x35);

        this.is.pha();

        Assertions.assertEquals((byte)0x0fe,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0x35,this.memory.getByteAtAddress((short)0x01ff));

        this.memory.setRegisterA((byte)0x12);
        this.is.pha();

        Assertions.assertEquals((byte)0x0fd,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0x12,this.memory.getByteAtAddress((short)0x01fe));
    }

    @Test
    void phpTest(){

        this.stack.setStackPointer((byte)0xff);
        this.flags.setWholeRegister((byte)0b01001010);

        this.is.php();

        Assertions.assertEquals((byte)0xfe,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0b01001010,this.memory.getByteAtAddress((short)0x01ff));

        this.flags.setWholeRegister((byte)0b10000010);

        this.is.php();

        Assertions.assertEquals((byte)0xfd,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0b10000010,this.memory.getByteAtAddress((short)0x01fe));
    }

    @Test
    void plaTest(){

        this.memory.setByteAtAddress((short)0x1ff,(byte)0x0f);
        this.memory.setByteAtAddress((short)0x1fe,(byte)0x91);
        this.memory.setByteAtAddress((short)0x1fd,(byte)0x00);
        this.memory.setByteAtAddress((short)0x1fc,(byte)0x01);

        this.stack.setStackPointer((byte)0xfb);

        this.is.pla();

        Assertions.assertEquals((byte)0xfc,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0x01,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.is.pla();

        Assertions.assertEquals((byte)0xfd,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0x00,this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.is.pla();

        Assertions.assertEquals((byte)0xfe,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0x91,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        this.is.pla();

        Assertions.assertEquals((byte)0xff,this.stack.getStackPointer());
        Assertions.assertEquals((byte)0x0f,this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

    }

    @Test 
    void plpTest(){
        this.stack.setStackPointer((byte)0xfe);
        this.flags.reset();
        this.memory.setByteAtAddress((short)0x01ff, (byte)0b01001010);

        this.is.plp();

        Assertions.assertEquals((byte)0b01001010, this.flags.getWholeRegister());
        Assertions.assertEquals((byte)0xff,this.stack.getStackPointer());

    }

    @Test 
    void rolAccTest(){

        this.memory.setRegisterA((byte)0b00000001);

        this.is.rol();

        Assertions.assertEquals((byte)0b00000010, this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setRegisterA((byte)0b10000000);

        this.is.rol();

        Assertions.assertEquals((byte)0b00000000, this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.is.rol();

        Assertions.assertEquals((byte)0b00000001, this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setRegisterA((byte)0b11000000);

        this.is.rol();

        Assertions.assertEquals((byte)0b10000000, this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

    }

    @Test 
    void rolMemTest(){
        
        short address = (short) 0x1234;
        AddressingModeReturn input = new AddressingModeReturn((byte)0b00000001,address);

        this.memory.setByteAtAddress(address,(byte)0b00000001);

        this.is.rol(input);

        Assertions.assertEquals((byte)0b00000010, this.memory.getByteAtAddress(address));
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setByteAtAddress(address,(byte)0b10000000);
        input.setValue((byte)0b10000000);

        this.is.rol(input);

        Assertions.assertEquals((byte)0b00000000, this.memory.getByteAtAddress(address));
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        input.setValue((byte)0b00000000);
        this.is.rol(input);

        Assertions.assertEquals((byte)0b00000001, this.memory.getByteAtAddress(address));
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setByteAtAddress(address,(byte)0b11000000);
        input.setValue((byte)0b11000000);

        this.is.rol(input);

        Assertions.assertEquals((byte)0b10000000, this.memory.getByteAtAddress(address));
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

    }

    @Test 
    void rorAccTest(){

        this.memory.setRegisterA((byte)0b00000010);

        this.is.ror();

        Assertions.assertEquals((byte)0b00000001, this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setRegisterA((byte)0b00000001);

        this.is.ror();

        Assertions.assertEquals((byte)0b00000000, this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.is.ror();

        Assertions.assertEquals((byte)0b10000000, this.memory.getRegisterA());
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        this.memory.setRegisterA((byte)0b11000001);

        this.is.ror();

        Assertions.assertEquals((byte)0b01100000, this.memory.getRegisterA());
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

    }

    @Test 
    void rorMemTest(){
        
        short address = (short) 0x023f;
        AddressingModeReturn input = new AddressingModeReturn((byte)0b00000010,address);

        this.memory.setByteAtAddress(address,(byte)0b00000010);

        this.is.ror(input);

        Assertions.assertEquals((byte)0b00000001, this.memory.getByteAtAddress(address));
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        this.memory.setByteAtAddress(address,(byte)0b00000001);
        input.setValue((byte)0b00000001);

        this.is.ror(input);

        Assertions.assertEquals((byte)0b00000000, this.memory.getByteAtAddress(address));
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertTrue(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

        input.setValue((byte)0b00000000);
        this.is.ror(input);

        Assertions.assertEquals((byte)0b10000000, this.memory.getByteAtAddress(address));
        Assertions.assertFalse(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertTrue(this.flags.getNegative());

        this.memory.setByteAtAddress(address,(byte)0b11000001);
        input.setValue((byte)0b11000001);

        this.is.ror(input);

        Assertions.assertEquals((byte)0b01100000, this.memory.getByteAtAddress(address));
        Assertions.assertTrue(this.flags.getCarry());
        Assertions.assertFalse(this.flags.getZero());
        Assertions.assertFalse(this.flags.getNegative());

    }

    @Test
    void rtiTest(){

        this.stack.setStackPointer((byte)0xfc);
        this.memory.setByteAtAddress((short)0x01ff,(byte)0x12);
        this.memory.setByteAtAddress((short)0x01fe,(byte)0x34);
        this.memory.setByteAtAddress((short)0x01fd,(byte)0b01001110);

        this.memory.setProgramCounter((short)0x0001);

        this.is.rti();

        Assertions.assertEquals((short)0x1234, this.memory.getProgramCounter());
        Assertions.assertEquals((byte)0b01001110, this.flags.getWholeRegister());
        Assertions.assertEquals((byte)0xff, this.stack.getStackPointer());

    }
}
