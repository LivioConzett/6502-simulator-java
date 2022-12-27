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

    @Test
    void hexStringToByteTest(){

        Assertions.assertEquals((byte) 0xff, Util.hexStringToByte("ff"));
        Assertions.assertEquals((byte) 0xff, Util.hexStringToByte("FF"));
        Assertions.assertEquals((byte) 0x00, Util.hexStringToByte("00"));
        Assertions.assertEquals((byte) 0x80, Util.hexStringToByte("80"));
        Assertions.assertEquals((byte) 0xac, Util.hexStringToByte("ac"));
        Assertions.assertEquals((byte) 0x1a, Util.hexStringToByte("1a"));

        Assertions.assertEquals((byte) 0xc1, Util.hexStringToByte("abc1"));

    }

    @Test
    void sanitizeHexStringTest(){

        String input = "ab cd ef gh ij kl mn op qr st uv wx yz 01 23 45 67 89 .,:;-";
        String output = "ab cd ef 01 23 45 67 89 ";

        Assertions.assertEquals(output, Util.sanitizeHexString(input));

    }

    @Test
    void hexToStringTest(){

        Assertions.assertEquals("00",Util.hexToString((byte)0x00));
        Assertions.assertEquals("01",Util.hexToString((byte)0x01));
        Assertions.assertEquals("a9",Util.hexToString((byte)0xa9));
        Assertions.assertEquals("ff",Util.hexToString((byte)0xff));

        Assertions.assertEquals("0000",Util.hexToString((short)0x0000));
        Assertions.assertEquals("0100",Util.hexToString((short)0x0100));
        Assertions.assertEquals("a9c2",Util.hexToString((short)0xa9c2));
        Assertions.assertEquals("ffff",Util.hexToString((short)0xffff));

    }


    @Test
    void asciiToHexTest(){

        String input = "'this is a test'";

        String output = "74 68 69 73 20 69 73 20 61 20 74 65 73 74";

        Assertions.assertEquals(output,Util.asciiToHex(input));

    }


    @Test
    void intToAddressStringTest(){

        int input = 1;
        String output = "01 00";

        Assertions.assertEquals(output,Util.intToAddressString(input));

        input = 15;
        output = "0f 00";

        Assertions.assertEquals(output,Util.intToAddressString(input));

        input = 16;
        output = "10 00";

        Assertions.assertEquals(output,Util.intToAddressString(input));

        input = 255;
        output = "ff 00";

        Assertions.assertEquals(output,Util.intToAddressString(input));

        input = 256;
        output = "00 01";

        Assertions.assertEquals(output,Util.intToAddressString(input));

        input = 65535;
        output = "ff ff";

        Assertions.assertEquals(output,Util.intToAddressString(input));

    }

    @Test
    void codeNumberToHexTest(){

        String number = "$fa";
        String output = "$fa";

        Assertions.assertEquals(output, Util.codeNumberToHex(number,16));

        number = "14";
        output = "$e";

        Assertions.assertEquals(output, Util.codeNumberToHex(number,10));

        number = "@115";
        output = "$4d";

        Assertions.assertEquals(output, Util.codeNumberToHex(number,8));

        number = "%100110";
        output = "$26";

        Assertions.assertEquals(output, Util.codeNumberToHex(number,2));

        number = "'(";
        output = "$28";

        Assertions.assertEquals(output, Util.codeNumberToHex(number,128));

    }

    @Test
    void stringToOpCodesTest(){

        String input = "bcc";
        OpCodes output = OpCodes.BCC;

        Assertions.assertEquals(output,Util.stringToOpCodes(input));

        input = "bbb";
        output = OpCodes.NONE;

        Assertions.assertEquals(output,Util.stringToOpCodes(input));

        input = "AdC";
        output = OpCodes.ADC;

        Assertions.assertEquals(output,Util.stringToOpCodes(input));

    }

}