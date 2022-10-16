import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class FlagTests {
    @Test
    public void flagTests() {
        Flags flags = new Flags();

        assertTrue(flags.getInterruptDisable());

        flags.setWholeRegister((byte)0xff);
        assertTrue(flags.getCarry());
        assertTrue(flags.getZero());
        assertTrue(flags.getInterruptDisable());
        assertTrue(flags.getDecimalMode());
        assertTrue(flags.getBreakCommand());
        assertTrue(flags.getOverFlow());
        assertTrue(flags.getNegative());

        flags.reset();
        assertFalse(flags.getCarry());
        assertFalse(flags.getZero());
        assertTrue(flags.getInterruptDisable());
        assertFalse(flags.getDecimalMode());
        assertFalse(flags.getBreakCommand());
        assertFalse(flags.getOverFlow());
        assertFalse(flags.getNegative());

        flags.setCarry(true);
        flags.setZero(true);
        flags.setInterruptDisable(true);
        flags.setDecimalMode(true);
        flags.setBreakCommand(true);
        flags.setOverFlow(true);
        flags.setNegative(true);

        assertTrue(flags.getCarry());
        assertTrue(flags.getZero());
        assertTrue(flags.getInterruptDisable());
        assertTrue(flags.getDecimalMode());
        assertTrue(flags.getBreakCommand());
        assertTrue(flags.getOverFlow());
        assertTrue(flags.getNegative());

        flags.setCarry(false);
        flags.setZero(false);
        flags.setInterruptDisable(false);
        flags.setDecimalMode(false);
        flags.setBreakCommand(false);
        flags.setOverFlow(false);
        flags.setNegative(false);

        assertFalse(flags.getCarry());
        assertFalse(flags.getZero());
        assertFalse(flags.getInterruptDisable());
        assertFalse(flags.getDecimalMode());
        assertFalse(flags.getBreakCommand());
        assertFalse(flags.getOverFlow());
        assertFalse(flags.getNegative());

        flags.setWholeRegister((byte)0xff);

        assertEquals((byte) 0b11011111, flags.getWholeRegister());
        assertNotEquals((byte) 0b11111111, flags.getWholeRegister());

    }
}
