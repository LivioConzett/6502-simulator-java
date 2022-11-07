package tech.livio.java6502;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StackTest{

    @Test
    void stack(){
        Memory mem = new Memory();

        Stack stack = new Stack(mem);
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

