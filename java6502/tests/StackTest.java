package java6502.tests;


import java6502.Memory;
import java6502.Stack;
import java6502.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StackTest{

    @Test
    public void stack(){
        Memory mem = new Memory();

        Stack stack = new Stack(new Memory());
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
}

