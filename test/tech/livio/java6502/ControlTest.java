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

    @Test
    void doOnExtTest(){
        this.control.setRun(true);
        this.control.setDoOnExt(e -> this.control.setRun((boolean)e));

        this.control.runDoOnExt(false);

        Assertions.assertFalse(this.control.getRun());

        this.control.setRun(false);
        this.control.setDoOnExt(e -> this.control.setRun((boolean)e));

        this.control.runDoOnExt(true);

        Assertions.assertTrue(this.control.getRun());

    }

    @Test
    void doOnStackOverflowTest(){
        this.control.setRun(true);
        this.control.setDoOnStackOverflow(e -> this.control.setRun((boolean)e));

        this.control.runDoOnStackOverflow(false);

        Assertions.assertFalse(this.control.getRun());

        this.control.setRun(false);
        this.control.setDoOnStackOverflow(e -> this.control.setRun((boolean)e));

        this.control.runDoOnStackOverflow(true);

        Assertions.assertTrue(this.control.getRun());

    }
}
