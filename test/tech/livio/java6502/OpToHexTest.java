package tech.livio.java6502;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpToHexTest {


    @Test
    void noAddressingMode(){

        Assertions.assertNull(OpToHex.getHex(OpCodes.BCC, AddressingModes.ABSOLUTE));

    }

    @Test
    void adc(){

        OpCodes opCode = OpCodes.ADC;

        Assertions.assertEquals((byte)0x69, OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0x65, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x75, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x6D, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x7D, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0x79, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0x61, OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0x71, OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void and(){

        OpCodes opCode = OpCodes.AND;

        Assertions.assertEquals((byte)0x29, OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0x25, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x35, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x2d, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x3d, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0x39, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0x21, OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0x31, OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void asl(){
        OpCodes opCode = OpCodes.ASL;

        Assertions.assertEquals((byte)0x0a, OpToHex.getHex(opCode, AddressingModes.ACCUMULATOR));
        Assertions.assertEquals((byte)0x06, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x16, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x0e, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x1e, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));

    }

    @Test
    void bcc(){
        OpCodes opCode = OpCodes.BCC;

        Assertions.assertEquals((byte)0x90, OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void bcs(){
        OpCodes opCode = OpCodes.BCS;

        Assertions.assertEquals((byte)0xb0, OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void beq(){
        OpCodes opCode = OpCodes.BEQ;

        Assertions.assertEquals((byte)0xf0, OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void bit(){
        OpCodes opCode = OpCodes.BIT;

        Assertions.assertEquals((byte)0x24, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x2c, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
    }

    @Test
    void bmi(){
        OpCodes opCode = OpCodes.BMI;

        Assertions.assertEquals((byte)0x30, OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void bne(){
        OpCodes opCode = OpCodes.BNE;

        Assertions.assertEquals((byte)0xd0, OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void bpl(){
        OpCodes opCode = OpCodes.BPL;

        Assertions.assertEquals((byte)0x10, OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void brk(){
        OpCodes opCode = OpCodes.BRK;

        Assertions.assertEquals((byte)0x00, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void bvc(){
        OpCodes opCode = OpCodes.BVC;

        Assertions.assertEquals((byte)0x50, OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void bvs(){
        OpCodes opCode = OpCodes.BVS;

        Assertions.assertEquals((byte)0x70, OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void clc(){
        OpCodes opCode = OpCodes.CLC;

        Assertions.assertEquals((byte)0x18, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void cld(){
        OpCodes opCode = OpCodes.CLD;

        Assertions.assertEquals((byte)0xD8, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void cli(){
        OpCodes opCode = OpCodes.CLI;

        Assertions.assertEquals((byte)0x58, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void clv(){
        OpCodes opCode = OpCodes.CLV;

        Assertions.assertEquals((byte)0xB8, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void cmp(){

        OpCodes opCode = OpCodes.CMP;

        Assertions.assertEquals((byte)0xc9, OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xc5, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xd5, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0xcd, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xdd, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0xd9, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0xc1, OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0xd1, OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }



}
