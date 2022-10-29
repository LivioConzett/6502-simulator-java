package java6502.tests;

import java6502.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UtilTests{

    @Test
    public void unsignByteTest(){
        byte testByte = -1;
        Assertions.assertEquals(0xff, Util.unsignByte(testByte));

        testByte = (byte) 30;
        Assertions.assertEquals(30, Util.unsignByte(testByte));

        testByte = (byte) 255;
        Assertions.assertEquals(0xff, Util.unsignByte(testByte));
    }

    @Test
    public void byteToAddressTest(){

        byte low = 0x34;
        byte high = 0x12;

        Assertions.assertEquals(0x1234, Util.bytesToAddress(low,high));

        low = (byte) 0xff;
        high = (byte) 0xff;

        Assertions.assertEquals(0xffff, Util.unsignShort(Util.bytesToAddress(low,high)));

        low = (byte) 0x69;
        high = (byte) 0x32;

        Assertions.assertEquals(0x3269, Util.unsignShort(Util.bytesToAddress(low,high)));
    }

    @Test
    public void bcdTest(){

        Assertions.assertEquals((byte)99,Util.bcdToDec((byte)0b10011001));
        Assertions.assertEquals((byte)10,Util.bcdToDec((byte)0b00010000));
        Assertions.assertEquals((byte)69,Util.bcdToDec((byte)0b01101001));

        Assertions.assertEquals((byte)0b00010000,Util.decToBcd((byte)10));
        Assertions.assertEquals((byte)0b10011001,Util.decToBcd((byte)99));
        Assertions.assertEquals((byte)0b00100001,Util.decToBcd((byte)21));
    }

    @Test
    public void likeSignedTest(){

        Assertions.assertTrue(Util.areLikeSigned((byte)0b10011001,(byte)0b10000000));
        Assertions.assertTrue(Util.areLikeSigned((byte)0b00011001,(byte)0b00100000));

        Assertions.assertFalse(Util.areLikeSigned((byte)0b10011001,(byte)0b0010000));
        Assertions.assertFalse(Util.areLikeSigned((byte)0b01011001,(byte)0b10000000));

    }
}