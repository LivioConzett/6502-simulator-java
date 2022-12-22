package tech.livio.java6502;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpToHexTest {


    @Test
    void noAddressingMode(){

        Assertions.assertNull(Compiler.OpToHex.getHex(OpCodes.BCC, AddressingModes.ABSOLUTE));

    }

    @Test
    void adc(){

        OpCodes opCode = OpCodes.ADC;

        Assertions.assertEquals((byte)0x69, Compiler.OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0x65, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x75, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x6D, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x7D, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0x79, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0x61, Compiler.OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0x71, Compiler.OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void and(){

        OpCodes opCode = OpCodes.AND;

        Assertions.assertEquals((byte)0x29, Compiler.OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0x25, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x35, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x2d, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x3d, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0x39, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0x21, Compiler.OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0x31, Compiler.OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void asl(){
        OpCodes opCode = OpCodes.ASL;

        Assertions.assertEquals((byte)0x0a, Compiler.OpToHex.getHex(opCode, AddressingModes.ACCUMULATOR));
        Assertions.assertEquals((byte)0x06, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x16, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x0e, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x1e, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));

    }

    @Test
    void bcc(){
        OpCodes opCode = OpCodes.BCC;

        Assertions.assertEquals((byte)0x90, Compiler.OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void bcs(){
        OpCodes opCode = OpCodes.BCS;

        Assertions.assertEquals((byte)0xb0, Compiler.OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void beq(){
        OpCodes opCode = OpCodes.BEQ;

        Assertions.assertEquals((byte)0xf0, Compiler.OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void bit(){
        OpCodes opCode = OpCodes.BIT;

        Assertions.assertEquals((byte)0x24, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x2c, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
    }

    @Test
    void bmi(){
        OpCodes opCode = OpCodes.BMI;

        Assertions.assertEquals((byte)0x30, Compiler.OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void bne(){
        OpCodes opCode = OpCodes.BNE;

        Assertions.assertEquals((byte)0xd0, Compiler.OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void bpl(){
        OpCodes opCode = OpCodes.BPL;

        Assertions.assertEquals((byte)0x10, Compiler.OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void brk(){
        OpCodes opCode = OpCodes.BRK;

        Assertions.assertEquals((byte)0x00, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void bvc(){
        OpCodes opCode = OpCodes.BVC;

        Assertions.assertEquals((byte)0x50, Compiler.OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void bvs(){
        OpCodes opCode = OpCodes.BVS;

        Assertions.assertEquals((byte)0x70, Compiler.OpToHex.getHex(opCode, AddressingModes.RELATIVE));
    }

    @Test
    void clc(){
        OpCodes opCode = OpCodes.CLC;

        Assertions.assertEquals((byte)0x18, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void cld(){
        OpCodes opCode = OpCodes.CLD;

        Assertions.assertEquals((byte)0xD8, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void cli(){
        OpCodes opCode = OpCodes.CLI;

        Assertions.assertEquals((byte)0x58, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void clv(){
        OpCodes opCode = OpCodes.CLV;

        Assertions.assertEquals((byte)0xB8, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void cmp(){

        OpCodes opCode = OpCodes.CMP;

        Assertions.assertEquals((byte)0xc9, Compiler.OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xc5, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xd5, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0xcd, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xdd, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0xd9, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0xc1, Compiler.OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0xd1, Compiler.OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void cpx(){
        OpCodes opCode = OpCodes.CPX;

        Assertions.assertEquals((byte)0xe0, Compiler.OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xe4, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xec, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
    }

    @Test
    void cpy(){
        OpCodes opCode = OpCodes.CPY;

        Assertions.assertEquals((byte)0xc0, Compiler.OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xc4, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xcc, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
    }

    @Test
    void dec(){
        OpCodes opCode = OpCodes.DEC;

        Assertions.assertEquals((byte)0xc6, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xd6, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0xce, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xde, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
    }

    @Test
    void dex(){
        OpCodes opCode = OpCodes.DEX;

        Assertions.assertEquals((byte)0xca, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void dey(){
        OpCodes opCode = OpCodes.DEY;

        Assertions.assertEquals((byte)0x88, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void eor(){

        OpCodes opCode = OpCodes.EOR;

        Assertions.assertEquals((byte)0x49, Compiler.OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0x45, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x55, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x4d, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x5d, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0x59, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0x41, Compiler.OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0x51, Compiler.OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void inc(){
        OpCodes opCode = OpCodes.INC;

        Assertions.assertEquals((byte)0xe6, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xf6, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0xee, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xfe, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
    }

    @Test
    void inx(){
        OpCodes opCode = OpCodes.INX;

        Assertions.assertEquals((byte)0xe8, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void iny(){
        OpCodes opCode = OpCodes.INY;

        Assertions.assertEquals((byte)0xc8, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void jmp(){
        OpCodes opCode = OpCodes.JMP;

        Assertions.assertEquals((byte)0x4c, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x6c, Compiler.OpToHex.getHex(opCode, AddressingModes.INDIRECT_ABSOLUTE));
    }

    @Test
    void jsr(){
        OpCodes opCode = OpCodes.JSR;

        Assertions.assertEquals((byte)0x20, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
    }

    @Test
    void lda(){

        OpCodes opCode = OpCodes.LDA;

        Assertions.assertEquals((byte)0xa9, Compiler.OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xa5, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xb5, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0xad, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xbd, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0xb9, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0xa1, Compiler.OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0xb1, Compiler.OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void ldx(){
        OpCodes opCode = OpCodes.LDX;

        Assertions.assertEquals((byte)0xa2, Compiler.OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xa6, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xb6, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_Y));
        Assertions.assertEquals((byte)0xae, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xbe, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
    }

    @Test
    void ldy(){
        OpCodes opCode = OpCodes.LDY;

        Assertions.assertEquals((byte)0xa0, Compiler.OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xa4, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xb4, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0xac, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xbc, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
    }

    @Test
    void lsr(){
        OpCodes opCode = OpCodes.LSR;

        Assertions.assertEquals((byte)0x4a, Compiler.OpToHex.getHex(opCode, AddressingModes.ACCUMULATOR));
        Assertions.assertEquals((byte)0x46, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x56, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x4e, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x5e, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
    }

    @Test
    void nop(){
        OpCodes opCode = OpCodes.NOP;

        Assertions.assertEquals((byte)0xea, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void ora(){

        OpCodes opCode = OpCodes.ORA;

        Assertions.assertEquals((byte)0x09, Compiler.OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0x05, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x15, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x0d, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x1d, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0x19, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0x01, Compiler.OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0x11, Compiler.OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void pha(){
        OpCodes opCode = OpCodes.PHA;

        Assertions.assertEquals((byte)0x48, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void php(){
        OpCodes opCode = OpCodes.PHP;

        Assertions.assertEquals((byte)0x08, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void pla(){
        OpCodes opCode = OpCodes.PLA;

        Assertions.assertEquals((byte)0x68, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void plp(){
        OpCodes opCode = OpCodes.PLP;

        Assertions.assertEquals((byte)0x28, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void rol(){
        OpCodes opCode = OpCodes.ROL;

        Assertions.assertEquals((byte)0x2a, Compiler.OpToHex.getHex(opCode, AddressingModes.ACCUMULATOR));
        Assertions.assertEquals((byte)0x26, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x36, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x2e, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x3e, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
    }

    @Test
    void ror(){
        OpCodes opCode = OpCodes.ROR;

        Assertions.assertEquals((byte)0x6a, Compiler.OpToHex.getHex(opCode, AddressingModes.ACCUMULATOR));
        Assertions.assertEquals((byte)0x66, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x76, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x6e, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x7e, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
    }

    @Test
    void rti(){
        OpCodes opCode = OpCodes.RTI;

        Assertions.assertEquals((byte)0x40, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void rts(){
        OpCodes opCode = OpCodes.RTS;

        Assertions.assertEquals((byte)0x60, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void sbc(){

        OpCodes opCode = OpCodes.SBC;

        Assertions.assertEquals((byte)0xe9, Compiler.OpToHex.getHex(opCode, AddressingModes.IMMEDIATE));
        Assertions.assertEquals((byte)0xe5, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0xf5, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0xeD, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0xfD, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0xf9, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0xe1, Compiler.OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0xf1, Compiler.OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void sec(){
        OpCodes opCode = OpCodes.SEC;

        Assertions.assertEquals((byte)0x38, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void sed(){
        OpCodes opCode = OpCodes.SED;

        Assertions.assertEquals((byte)0xf8, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void sei(){
        OpCodes opCode = OpCodes.SEI;

        Assertions.assertEquals((byte)0x78, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void sta(){

        OpCodes opCode = OpCodes.STA;

        Assertions.assertEquals((byte)0x85, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x95, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x8D, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
        Assertions.assertEquals((byte)0x9D, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_X));
        Assertions.assertEquals((byte)0x99, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE_INDEXED_Y));
        Assertions.assertEquals((byte)0x81, Compiler.OpToHex.getHex(opCode, AddressingModes.INDEXED_INDIRECT));
        Assertions.assertEquals((byte)0x91, Compiler.OpToHex.getHex(opCode, AddressingModes.INDIRECT_INDEXED));
    }

    @Test
    void stx(){

        OpCodes opCode = OpCodes.STX;

        Assertions.assertEquals((byte)0x86, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x96, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_Y));
        Assertions.assertEquals((byte)0x8e, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
    }

    @Test
    void sty(){

        OpCodes opCode = OpCodes.STY;

        Assertions.assertEquals((byte)0x84, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE));
        Assertions.assertEquals((byte)0x94, Compiler.OpToHex.getHex(opCode, AddressingModes.ZERO_PAGE_INDEXED_X));
        Assertions.assertEquals((byte)0x8c, Compiler.OpToHex.getHex(opCode, AddressingModes.ABSOLUTE));
    }

    @Test
    void tax(){
        OpCodes opCode = OpCodes.TAX;

        Assertions.assertEquals((byte)0xaa, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void tay(){
        OpCodes opCode = OpCodes.TAY;

        Assertions.assertEquals((byte)0xa8, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void tsx(){
        OpCodes opCode = OpCodes.TSX;

        Assertions.assertEquals((byte)0xba, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void txa(){
        OpCodes opCode = OpCodes.TXA;

        Assertions.assertEquals((byte)0x8a, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void txs(){
        OpCodes opCode = OpCodes.TXS;

        Assertions.assertEquals((byte)0x9a, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }

    @Test
    void tya(){
        OpCodes opCode = OpCodes.TYA;

        Assertions.assertEquals((byte)0x98, Compiler.OpToHex.getHex(opCode, AddressingModes.IMPLIED));
    }


}
