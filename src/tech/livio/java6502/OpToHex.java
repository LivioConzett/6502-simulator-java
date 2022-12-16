package tech.livio.java6502;

import java.util.EnumMap;
import java.util.Map;

public class OpToHex {


    static final Map<OpCodes,Map<AddressingModes,Byte>> reference;
    static{

        reference = new EnumMap<>(OpCodes.class);

        Map<AddressingModes,Byte> temp = new EnumMap(AddressingModes.class);

        temp.put(AddressingModes.INDEXED_INDIRECT, (byte)0x61);
        temp.put(AddressingModes.ZERO_PAGE, (byte)0x65);
        temp.put(AddressingModes.IMMEDIATE, (byte)0x69);
        temp.put(AddressingModes.ABSOLUTE, (byte)0x6d);
        temp.put(AddressingModes.INDIRECT_INDEXED, (byte)0x71);
        temp.put(AddressingModes.ZERO_PAGE_INDEXED_X, (byte)0x75);
        temp.put(AddressingModes.ABSOLUTE_INDEXED_Y, (byte)0x79);
        temp.put(AddressingModes.ABSOLUTE_INDEXED_X, (byte)0x7d);
        reference.put(OpCodes.ADC,temp);

        temp.clear();

        temp.put(AddressingModes.INDEXED_INDIRECT, (byte)0x21);
        temp.put(AddressingModes.ZERO_PAGE, (byte)0x25);
        temp.put(AddressingModes.IMMEDIATE, (byte)0x29);
        temp.put(AddressingModes.ABSOLUTE, (byte)0x2d);
        temp.put(AddressingModes.INDIRECT_INDEXED, (byte)0x31);
        temp.put(AddressingModes.ZERO_PAGE_INDEXED_X, (byte)0x35);
        temp.put(AddressingModes.ABSOLUTE_INDEXED_Y, (byte)0x39);
        temp.put(AddressingModes.ABSOLUTE_INDEXED_X, (byte)0x3d);
        reference.put(OpCodes.AND,temp);

        temp.clear();

        temp.put(AddressingModes.ZERO_PAGE, (byte)0x06);
        temp.put(AddressingModes.ACCUMULATOR, (byte)0x0a);
        temp.put(AddressingModes.ABSOLUTE, (byte)0x0e);
        temp.put(AddressingModes.ZERO_PAGE_INDEXED_X, (byte)0x16);
        temp.put(AddressingModes.ABSOLUTE_INDEXED_X, (byte)0x1e);
        reference.put(OpCodes.ASL,temp);

        temp.clear();

        temp.put(AddressingModes.RELATIVE, (byte)0x90);
        reference.put(OpCodes.BCC,temp);

        temp.clear();

        temp.put(AddressingModes.RELATIVE, (byte)0xb0);
        reference.put(OpCodes.BCS,temp);

        temp.clear();

        temp.put(AddressingModes.RELATIVE, (byte)0xf0);
        reference.put(OpCodes.BEQ,temp);

        temp.clear();

        temp.put(AddressingModes.ZERO_PAGE, (byte)0x24);
        temp.put(AddressingModes.ABSOLUTE, (byte)0x2c);
        reference.put(OpCodes.BIT,temp);

        temp.clear();

        temp.put(AddressingModes.RELATIVE, (byte)0xd0);
        reference.put(OpCodes.BNE,temp);

        temp.clear();

        temp.put(AddressingModes.RELATIVE, (byte)0x30);
        reference.put(OpCodes.BMI,temp);

        temp.clear();

        temp.put(AddressingModes.RELATIVE, (byte)0x10);
        reference.put(OpCodes.BPL,temp);

        temp.clear();

        temp.put(AddressingModes.RELATIVE, (byte)0x70);
        reference.put(OpCodes.BVS,temp);

        temp.clear();

        temp.put(AddressingModes.RELATIVE, (byte)0x00);
        reference.put(OpCodes.BRK,temp);

        temp.clear();

        temp.put(AddressingModes.RELATIVE, (byte)0x50);
        reference.put(OpCodes.BVC,temp);

        temp.clear();

        temp.put(AddressingModes.IMPLIED, (byte)0x18);
        reference.put(OpCodes.CLC,temp);

        temp.clear();

        temp.put(AddressingModes.IMPLIED, (byte)0xd8);
        reference.put(OpCodes.CLD,temp);

        temp.clear();

        temp.put(AddressingModes.IMPLIED, (byte)0x58);
        reference.put(OpCodes.CLI,temp);

        temp.clear();

        temp.put(AddressingModes.IMPLIED, (byte)0xb8);
        reference.put(OpCodes.CLV,temp);

    }

    //TODO: create test for this


    /**
     * Get the byte value for the corresponding opcode and Addressing mode
     * @param opCode opcode
     * @param addressingMode addressing mode
     * @return commad byte
     */
    static byte getHex(OpCodes opCode, AddressingModes addressingMode){
        return reference.get(opCode).get(addressingMode);
    }
}
