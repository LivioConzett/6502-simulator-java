package tech.livio.java6502;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class ControlTest {

    private Control control;

    @BeforeEach
    void init(){
        this.control = new Control();
    }

    @Test
    void initTest(){
        Assertions.assertFalse(this.control.getSkipNextIncrement());
    }

    @Test
    void skipIncrementTest(){
        this.control.setSkipNextIncrement();

        Assertions.assertTrue(this.control.getSkipNextIncrement());

        this.control.allowNextIncrement();

        Assertions.assertFalse(this.control.getSkipNextIncrement());

    }
}
