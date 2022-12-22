package tech.livio.java6502;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.livio.java6502.Control;
import tech.livio.java6502.Memory;
import tech.livio.java6502.Stack;

class StackTest{

    private Memory mem;
    private Control ctrl;
    private Stack stack;

    private int test;

    @BeforeEach
    void init(){
        this.mem = new Memory();
        this.ctrl = new Control();
        this.stack = new Stack(this.mem,this.ctrl);
        this.test = 0;
    }

    @Test
    void stack(){

        byte test = (byte) 0xff;

        stack.setStackPointer((byte) 0xff);

        stack.push((byte) 0xff);

        Assertions.assertEquals((byte)0xfe, stack.getStackPointer());
        Assertions.assertEquals((byte)0xff, stack.get(test));

        stack.push((byte) 0x1);
        test--;

        Assertions.assertEquals((byte)0xfd, stack.getStackPointer());
        Assertions.assertEquals((byte)0x1, stack.get(test));

        Assertions.assertEquals((byte)0x1, stack.pull());
        Assertions.assertEquals((byte)0xfe, stack.getStackPointer());
    }

    private void testMethod(Object e){
        this.test = (short)e;
    }

    @Test
    void overFlowTest(){

        this.mem.setProgramCounter((short)0x1234);

        this.ctrl.setDoOnStackOverflow(e -> testMethod(e));

        stack.setStackPointer((byte) 0xff);
        stack.incrementStackPointer();

        Assertions.assertEquals((short)0x1234,this.test);

        this.test = 0;
        this.mem.setProgramCounter((short)0x9876);


        stack.setStackPointer((byte) 0x00);
        stack.decrementStackPointer();

        Assertions.assertEquals((short)0x00,this.test);

    }
}

