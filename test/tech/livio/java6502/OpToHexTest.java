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

    @Test
    void cpx(){
        OpCodes opCode = OpCodes.CPX;

        Assertions.assertEquals((byte)0xe0, OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xe4, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xec, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
    }

    @Test
    void cpy(){
        OpCodes opCode = OpCodes.CPY;

        Assertions.assertEquals((byte)0xc0, OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xc4, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xcc, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
    }

    @Test
    void dec(){
        OpCodes opCode = OpCodes.DEC;

        Assertions.assertEquals((byte)0xc6, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xd6, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0xce, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xde, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
    }

    @Test
    void dex(){
        OpCodes opCode = OpCodes.DEX;

        Assertions.assertEquals((byte)0xca, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void dey(){
        OpCodes opCode = OpCodes.DEY;

        Assertions.assertEquals((byte)0x88, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void eor(){

        OpCodes opCode = OpCodes.EOR;

        Assertions.assertEquals((byte)0x49, OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0x45, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x55, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x4d, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x5d, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0x59, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0x41, OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0x51, OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void inc(){
        OpCodes opCode = OpCodes.INC;

        Assertions.assertEquals((byte)0xe6, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xf6, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0xee, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xfe, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
    }

    @Test
    void inx(){
        OpCodes opCode = OpCodes.INX;

        Assertions.assertEquals((byte)0xe8, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void iny(){
        OpCodes opCode = OpCodes.INY;

        Assertions.assertEquals((byte)0xc8, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void jmp(){
        OpCodes opCode = OpCodes.JMP;

        Assertions.assertEquals((byte)0x4c, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x6c, OpToHex.getHex(opCode, AddressingModes.INDIRECT_ABSOLUTE));
    }

    @Test
    void jsr(){
        OpCodes opCode = OpCodes.JSR;

        Assertions.assertEquals((byte)0x20, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
    }

    @Test
    void lda(){

        OpCodes opCode = OpCodes.LDA;

        Assertions.assertEquals((byte)0xa9, OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xa5, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xb5, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0xad, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xbd, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0xb9, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0xa1, OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0xb1, OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void ldx(){
        OpCodes opCode = OpCodes.LDX;

        Assertions.assertEquals((byte)0xa2, OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xa6, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xb6, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_Y));
        Assertions.assertEquals((byte)0xae, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xbe, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
    }

    @Test
    void ldy(){
        OpCodes opCode = OpCodes.LDY;

        Assertions.assertEquals((byte)0xa0, OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xa4, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xb4, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0xac, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xbc, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
    }

    @Test
    void lsr(){
        OpCodes opCode = OpCodes.LSR;

        Assertions.assertEquals((byte)0x4a, OpToHex.getHex(opCode, AddressingModes.ACCUMULATOR));
        Assertions.assertEquals((byte)0x46, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x56, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x4e, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x5e, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
    }

    @Test
    void nop(){
        OpCodes opCode = OpCodes.NOP;

        Assertions.assertEquals((byte)0xea, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void ora(){

        OpCodes opCode = OpCodes.ORA;

        Assertions.assertEquals((byte)0x09, OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0x05, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x15, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x0d, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x1d, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0x19, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0x01, OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0x11, OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void pha(){
        OpCodes opCode = OpCodes.PHA;

        Assertions.assertEquals((byte)0x48, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void php(){
        OpCodes opCode = OpCodes.PHP;

        Assertions.assertEquals((byte)0x08, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void pla(){
        OpCodes opCode = OpCodes.PLA;

        Assertions.assertEquals((byte)0x68, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void plp(){
        OpCodes opCode = OpCodes.PLP;

        Assertions.assertEquals((byte)0x28, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void rol(){
        OpCodes opCode = OpCodes.ROL;

        Assertions.assertEquals((byte)0x2a, OpToHex.getHex(opCode, AddressingModes.ACCUMULATOR));
        Assertions.assertEquals((byte)0x26, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x36, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x2e, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x3e, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
    }

    @Test
    void ror(){
        OpCodes opCode = OpCodes.ROR;

        Assertions.assertEquals((byte)0x6a, OpToHex.getHex(opCode, AddressingModes.ACCUMULATOR));
        Assertions.assertEquals((byte)0x66, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x76, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x6e, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x7e, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
    }

    @Test
    void rti(){
        OpCodes opCode = OpCodes.RTI;

        Assertions.assertEquals((byte)0x40, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void rts(){
        OpCodes opCode = OpCodes.RTS;

        Assertions.assertEquals((byte)0x60, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void sbc(){

        OpCodes opCode = OpCodes.SBC;

        Assertions.assertEquals((byte)0xe9, OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xe5, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xf5, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0xeD, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xfD, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0xf9, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0xe1, OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0xf1, OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void sec(){
        OpCodes opCode = OpCodes.SEC;

        Assertions.assertEquals((byte)0x38, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void sed(){
        OpCodes opCode = OpCodes.SED;

        Assertions.assertEquals((byte)0xf8, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void sei(){
        OpCodes opCode = OpCodes.SEI;

        Assertions.assertEquals((byte)0x78, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void sta(){

        OpCodes opCode = OpCodes.STA;

        Assertions.assertEquals((byte)0x85, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x95, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x8D, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x9D, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0x99, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0x81, OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0x91, OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void stx(){

        OpCodes opCode = OpCodes.STX;

        Assertions.assertEquals((byte)0x86, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x96, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_Y));
        Assertions.assertEquals((byte)0x8e, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
    }

    @Test
    void sty(){

        OpCodes opCode = OpCodes.STY;

        Assertions.assertEquals((byte)0x84, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x94, OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x8c, OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
    }

    @Test
    void tax(){
        OpCodes opCode = OpCodes.TAX;

        Assertions.assertEquals((byte)0xaa, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void tay(){
        OpCodes opCode = OpCodes.TAY;

        Assertions.assertEquals((byte)0xa8, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void tsx(){
        OpCodes opCode = OpCodes.TSX;

        Assertions.assertEquals((byte)0xba, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void txa(){
        OpCodes opCode = OpCodes.TXA;

        Assertions.assertEquals((byte)0x8a, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void txs(){
        OpCodes opCode = OpCodes.TXS;

        Assertions.assertEquals((byte)0x9a, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void tya(){
        OpCodes opCode = OpCodes.TYA;

        Assertions.assertEquals((byte)0x98, OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }


}
