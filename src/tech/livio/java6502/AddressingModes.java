package tech.livio.java6502;

/**
 * Enums for the addressing modes
 */
public enum AddressingModes {
    IMMEDIATE,
    ABSOLUTE,
    ZERO_PAGE,
    IMPLIED,
    INDIRECT_ABSOLUTE,
    ABSOLUTE_INDEXED_X,
    ABSOLUTE_INDEXED_Y,
    ZERO_PAGE_INDEXED_X,
    ZERO_PAGE_INDEXED_Y,
    INDEXED_INDIRECT,
    INDIRECT_INDEXED,
    RELATIVE,
    ACCUMULATOR
}
