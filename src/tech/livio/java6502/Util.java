package tech.livio.java6502;

/**
 * Util functions
 * @author Livio Conzett
 * @version 20.10.2022
 */
class Util {

    /**
     * Private Constructor<br>
     * Needed so that Sonar Linter doesn't complain.
     */
    private Util(){
        // Sonar Linter wants this.
    }

    /**
     * Gets the unsigned value of a byte.
     * @param signedByte Byte to un-sign.
     * @return int of the un-singed byte
     */
    static int unsignByte(byte signedByte) {
        return signedByte & 0xff;
    }

    /**
     * Gets the un-signed value of a short.
     * @param signedShort short to un-sign;
     * @return int of the unsigned short
     */
    static int unsignShort(short signedShort) {
        return signedShort & 0xffff;
    }

    /**
     * Takes two bytes in little endian and adds them together into a short.
     * <pre>
     *     Util.bytesToAddress(0x34,0x12) => 0x1234
     * </pre>
     * @param low low byte of short
     * @param high high byte of short
     * @return short
     */
    static short bytesToAddress(byte low, byte high){
        int address = Util.unsignByte(high);
        address = address << 8;
        address = address | Util.unsignByte(low);
        return (short) address;
    }

    /**
     * Splits up an address into a high-byte and a low-byte.
     * @param address address to split up into bytes.
     * @return array of bytes {low-byte,high-byte}
     */
    static byte[] addressToBytes(short address){
        byte[] bytes = {0,0};

        bytes[0] = (byte)(address & 0b11111111);
        bytes[1] = (byte)(address >> 8);

        return bytes;
    }

    /**
     * Turns a bcd encoded byte into a "normal" byte.
     * @param bcdNumber bcd encoded byte.
     * @return "normalized" byte.
     */
    static byte bcdToDec(byte bcdNumber){
        int low = bcdNumber & 0xf;
        int high = (bcdNumber & 0xf0)>>4;
        return (byte) ((high * 10) + low);
    }

    /**
     * Turn a "normal" number into a bcd encoded number.
     * @param number "normal" number.
     * @return bcd encoded number
     */
    static byte decToBcd(byte number){
        int high = number / 10;
        int low = number - (high * 10);
        byte result = (byte)(high << 4);
        return (byte)(result | low);
    }

    /**
     * Tests if two bytes are like signed. i.e. if both numbers are negative or positive.
     * @param one first number for comparison.
     * @param two second number for comparison
     * @return true if likely signed false if not
     */
    static boolean areLikeSigned(byte one, byte two){
        return (one >> 7) == (two >> 7);
    }

    /**
     * Tests if two bytes are not like signed. i.e. if one number is negativean the other positive.
     * @param one first number for comparison.
     * @param two second number for comparison
     * @return true if not likely signed false if they are
     */
    static boolean areNotLikeSigned(byte one, byte two){
        return (one >> 7) != (two >> 7);
    }

    /**
     * Turns a string containing a hex into a byte
     * @param hexString hex-code in a String
     * @return byte of the hex-code
     */
    static byte hexStringToByte(String hexString){
        return (byte) Integer.parseInt(hexString, 16);
    }

    /**
     * Converts a byte into a hex String.
     * @param hex byte to convert
     * @return String of the hex
     */
    static String hexToString(byte hex){
        String hexString = Integer.toHexString(unsignByte(hex));

        if(hexString.length() == 1){
            hexString = "0" + hexString;
        }

        return hexString;
    }

    /**
     * Converts a short into a hex String.
     * @param hex short to convert
     * @return String of the hex
     */
    static String hexToString(short hex){
        StringBuilder hexString = new StringBuilder(Integer.toHexString(unsignShort(hex)));

        for(int i = hexString.length(); i < 4; i++){
            hexString.insert(0, "0");
        }

        return hexString.toString();
    }

    /**
     * Replaces everything not '0-9' 'a-f' 'A-F' or a space with ''.
     * @param hexString String to sanitize.
     * @return sanitized String.
     */
    static String sanitizeHexString(String hexString){
        return hexString.replaceAll("[^0-9a-fA-F ]+","").replaceAll(" +"," ");
    }
}
