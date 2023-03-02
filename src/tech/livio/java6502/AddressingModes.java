/*
Copyright (c) 2023, Livio Conzett
All rights reserved.

This source code is licensed under the BSD-style license found in the
LICENSE file in the root directory of this source tree.
*/

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
    ACCUMULATOR,
    NONE
}
