package tech.livio.java6502;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UtilTests{

    @Test
    void unsignedByteTest(){
        byte testByte = -1;
        Assertions.assertEquals(0xff, Util.unsignByte(testByte));

        testByte = (byte) 30;
        Assertions.assertEquals(30, Util.unsignByte(testByte));

        testByte = (byte) 255;
        Assertions.assertEquals(0xff, Util.unsignByte(testByte));
    }

    @Test
    void byteToAddressTest(){

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
    void addressToBytesTest(){
        byte[] ans = Util.addressToBytes((short)0x1234);

        Assertions.assertEquals((byte)0x34,ans[0]);
        Assertions.assertEquals((byte)0x12,ans[1]);

        ans = Util.addressToBytes((short)0x0069);

        Assertions.assertEquals((byte)0x69,ans[0]);
        Assertions.assertEquals((byte)0x00,ans[1]);

        ans = Util.addressToBytes((short)0x7f00);

        Assertions.assertEquals((byte)0x00,ans[0]);
        Assertions.assertEquals((byte)0x7f,ans[1]);

    }

    @Test
    void bcdTest(){

        Assertions.assertEquals((byte)99,Util.bcdToDec((byte)0b10011001));
        Assertions.assertEquals((byte)10,Util.bcdToDec((byte)0b00010000));
        Assertions.assertEquals((byte)69,Util.bcdToDec((byte)0b01101001));

        Assertions.assertEquals((byte)0b00010000,Util.decToBcd((byte)10));
        Assertions.assertEquals((byte)0b10011001,Util.decToBcd((byte)99));
        Assertions.assertEquals((byte)0b00100001,Util.decToBcd((byte)21));
    }

    @Test
    void likeSignedTest(){

        Assertions.assertTrue(Util.areLikeSigned((byte)0b10011001,(byte)0b10000000));
        Assertions.assertTrue(Util.areLikeSigned((byte)0b00011001,(byte)0b00100000));

        Assertions.assertFalse(Util.areLikeSigned((byte)0b10011001,(byte)0b0010000));
        Assertions.assertFalse(Util.areLikeSigned((byte)0b01011001,(byte)0b10000000));

    }

    @Test
    void notLikeSignedTest(){

        Assertions.assertTrue(Util.areNotLikeSigned((byte)0b00011001,(byte)0b10000000));
        Assertions.assertTrue(Util.areNotLikeSigned((byte)0b10011001,(byte)0b00100000));

        Assertions.assertFalse(Util.areNotLikeSigned((byte)0b10011001,(byte)0b11010000));
        Assertions.assertFalse(Util.areNotLikeSigned((byte)0b01011001,(byte)0b00000000));

    }
}