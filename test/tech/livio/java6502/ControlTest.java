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
        Assertions.assertTrue(this.control.getSkipNextIncrement());
    }

    @Test
    void skipIncrementTest(){
        this.control.setSkipNextIncrement();

        Assertions.assertTrue(this.control.getSkipNextIncrement());

        this.control.allowNextIncrement();

        Assertions.assertFalse(this.control.getSkipNextIncrement());
    }

    @Test
    void doOnExtTest(){
        this.control.setRun(true);
        this.control.setDoOnExt(e -> this.control.setRun(e == (short)1));

        this.control.runDoOnExt((short)0);

        Assertions.assertFalse(this.control.getRun());

        this.control.setRun(false);
        this.control.setDoOnExt(e -> this.control.setRun(e == (short)1));

        this.control.runDoOnExt((short)1);

        Assertions.assertTrue(this.control.getRun());

    }

    @Test
    void doOnStackOverflowTest(){
        this.control.setRun(true);
        this.control.setDoOnStackOverflow(e -> this.control.setRun(e == (short)1));

        this.control.runDoOnStackOverflow((short)0);

        Assertions.assertFalse(this.control.getRun());

        this.control.setRun(true);

        this.control.setRun(false);
        this.control.setDoOnStackOverflow((e) -> {
            this.control.setRun(e == (short)1);
        });

        this.control.runDoOnStackOverflow((short)1);

        Assertions.assertTrue(this.control.getRun());

        this.control.setRun(false);
        this.control.setDoOnStackOverflow((e) -> {
            this.control.setRun(e == (short)1);
        });

        this.control.runDoOnStackOverflow((short)1);

        Assertions.assertFalse(this.control.getRun());

    }
}
