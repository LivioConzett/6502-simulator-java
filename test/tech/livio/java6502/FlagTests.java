package tech.livio.java6502;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.livio.java6502.Flags;

class FlagTests {

    private Flags flags;

    @BeforeEach
    void init(){
        this.flags = new Flags();
    }

    @Test
    void flagInitTests() {

        Assertions.assertTrue(flags.getInterruptDisable());

        flags.setWholeRegister((byte) 0xff);
        Assertions.assertTrue(flags.getCarry());
        Assertions.assertTrue(flags.getZero());
        Assertions.assertTrue(flags.getInterruptDisable());
        Assertions.assertTrue(flags.getDecimalMode());
        Assertions.assertTrue(flags.getBreakCommand());
        Assertions.assertTrue(flags.getOverFlow());
        Assertions.assertTrue(flags.getNegative());
    }

    @Test
    void flagResetTest() {

        flags.reset();
        Assertions.assertFalse(flags.getCarry());
        Assertions.assertFalse(flags.getZero());
        Assertions.assertTrue(flags.getInterruptDisable());
        Assertions.assertFalse(flags.getDecimalMode());
        Assertions.assertFalse(flags.getBreakCommand());
        Assertions.assertFalse(flags.getOverFlow());
        Assertions.assertFalse(flags.getNegative());

    }

    @Test
    void flagSetTest() {

        flags.setCarry(true);
        flags.setZero(true);
        flags.setInterruptDisable(true);
        flags.setDecimalMode(true);
        flags.setBreakCommand(true);
        flags.setOverFlow(true);
        flags.setNegative(true);

        Assertions.assertTrue(flags.getCarry());
        Assertions.assertEquals(1, flags.getCarryInt());
        Assertions.assertTrue(flags.getZero());
        Assertions.assertTrue(flags.getInterruptDisable());
        Assertions.assertTrue(flags.getDecimalMode());
        Assertions.assertTrue(flags.getBreakCommand());
        Assertions.assertTrue(flags.getOverFlow());
        Assertions.assertTrue(flags.getNegative());

        flags.setCarry(false);
        flags.setZero(false);
        flags.setInterruptDisable(false);
        flags.setDecimalMode(false);
        flags.setBreakCommand(false);
        flags.setOverFlow(false);
        flags.setNegative(false);

        Assertions.assertFalse(flags.getCarry());
        Assertions.assertEquals(0, flags.getCarryInt());
        Assertions.assertFalse(flags.getZero());
        Assertions.assertFalse(flags.getInterruptDisable());
        Assertions.assertFalse(flags.getDecimalMode());
        Assertions.assertFalse(flags.getBreakCommand());
        Assertions.assertFalse(flags.getOverFlow());
        Assertions.assertFalse(flags.getNegative());

    }

    @Test
    void flagSetWholeTest(){

        flags.setWholeRegister((byte) 0xff);

        Assertions.assertEquals((byte) 0b11111111, flags.getWholeRegister());
        Assertions.assertNotEquals((byte) 0b11011111, flags.getWholeRegister());
    }
}