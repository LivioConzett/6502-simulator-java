package tech.livio.java6502;

import java.util.Map;

public class OpToHex {

    static final Map<OpCodes, Map<AddressingModes, Byte>> reference = Map.ofEntries(
            Map.entry(OpCodes.ADC, Map.of(
                    AddressingModes.INDEXED_INDIRECT, (byte)0x61,
                    AddressingModes.ZERO_PAGE, (byte)0x65,
                    AddressingModes.IMMEDIATE, (byte)0x69,
                    AddressingModes.ABSOLUTE, (byte)0x6d,
                    AddressingModes.INDIRECT_INDEXED, (byte)0x71,
                    AddressingModes.ZERO_PAGE_INDEXED_X, (byte)0x75,
                    AddressingModes.ABSOLUTE_INDEXED_Y, (byte)0x79,
                    AddressingModes.ABSOLUTE_INDEXED_X, (byte)0x7d
            )),
            Map.entry(OpCodes.AND, Map.of(
                    AddressingModes.INDEXED_INDIRECT, (byte)0x21,
                    AddressingModes.ZERO_PAGE, (byte)0x25,
                    AddressingModes.IMMEDIATE, (byte)0x29,
                    AddressingModes.ABSOLUTE, (byte)0x2d,
                    AddressingModes.INDIRECT_INDEXED, (byte)0x31,
                    AddressingModes.ZERO_PAGE_INDEXED_X, (byte)0x35,
                    AddressingModes.ABSOLUTE_INDEXED_Y, (byte)0x39,
                    AddressingModes.ABSOLUTE_INDEXED_X, (byte)0x3d
            )),
            Map.entry(OpCodes.ASL, Map.of(
                    AddressingModes.ZERO_PAGE, (byte)0x06,
                    AddressingModes.ACCUMULATOR, (byte)0x0a,
                    AddressingModes.ABSOLUTE, (byte)0x0e,
                    AddressingModes.ZERO_PAGE_INDEXED_X, (byte)0x16,
                    AddressingModes.ABSOLUTE_INDEXED_X, (byte)0x1e
            )),
            Map.entry(OpCodes.BCC, Map.of(
                    AddressingModes.RELATIVE, (byte) 0x90
            )),
            Map.entry(OpCodes.BCS, Map.of(
                    AddressingModes.RELATIVE, (byte)0xb0
            )),
            Map.entry(OpCodes.BEQ, Map.of(
                    AddressingModes.RELATIVE, (byte)0xf0
            )),
            Map.entry(OpCodes.BIT, Map.of(
                    AddressingModes.ZERO_PAGE, (byte)0x24,
                    AddressingModes.ABSOLUTE, (byte)0x2c
            )),
            Map.entry(OpCodes.BNE, Map.of(
                    AddressingModes.RELATIVE, (byte)0xd0
            )),
            Map.entry(OpCodes.BMI, Map.of(
                    AddressingModes.RELATIVE, (byte)0x30
            )),
            Map.entry(OpCodes.BPL, Map.of(
                    AddressingModes.RELATIVE, (byte)0x10
            )),
            Map.entry(OpCodes.BVS, Map.of(
                    AddressingModes.RELATIVE, (byte)0x70
            )),
            Map.entry(OpCodes.BRK, Map.of(
                    AddressingModes.RELATIVE, (byte)0x00
            )),
            Map.entry(OpCodes.BVC, Map.of(
                    AddressingModes.RELATIVE, (byte)0x50
            )),
            Map.entry(OpCodes.CLC, Map.of(
                    AddressingModes.IMPLIED, (byte)0x18
            )),
            Map.entry(OpCodes.CLD, Map.of(
                    AddressingModes.IMPLIED, (byte)0xd8
            )),
            Map.entry(OpCodes.CLI, Map.of(
                    AddressingModes.IMPLIED, (byte)0x58
            )),
            Map.entry(OpCodes.CLV, Map.of(
                    AddressingModes.IMPLIED, (byte)0xb8
            )),
            Map.entry(OpCodes.CMP, Map.of(
                    AddressingModes.INDEXED_INDIRECT, (byte)0xc1,
                    AddressingModes.ZERO_PAGE, (byte)0xc5,
                    AddressingModes.IMMEDIATE, (byte)0xc9,
                    AddressingModes.ABSOLUTE, (byte)0xcd,
                    AddressingModes.INDIRECT_INDEXED, (byte)0xd1,
                    AddressingModes.ZERO_PAGE_INDEXED_X, (byte)0xd5,
                    AddressingModes.ABSOLUTE_INDEXED_Y, (byte)0xd9,
                    AddressingModes.ABSOLUTE_INDEXED_X, (byte)0xdd
            )),
            Map.entry(
                    OpCodes.CPX, Map.of(
                            AddressingModes.IMMEDIATE, (byte)0xe0,
                            AddressingModes.ZERO_PAGE, (byte)0xe4,
                            AddressingModes.ABSOLUTE, (byte)0xec
                    )
            ),
            Map.entry(
                    OpCodes.CPY, Map.of(
                            AddressingModes.IMMEDIATE, (byte)0xc0,
                            AddressingModes.ZERO_PAGE, (byte)0xc4,
                            AddressingModes.ABSOLUTE, (byte)0xcc
                    )
            ),
            Map.entry(
                    OpCodes.DEC, Map.of(
                            AddressingModes.ZERO_PAGE, (byte)0xc6,
                            AddressingModes.ABSOLUTE, (byte)0xce,
                            AddressingModes.ABSOLUTE_INDEXED_X, (byte)0xde
                    )
            )

    );


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
