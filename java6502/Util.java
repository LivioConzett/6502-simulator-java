package java6502;

/**
 * Util functions
 * @author Livio Conzett
 * @version 20.10.2022
 */
public class Util {

    /**
     * Gets the unsigned value of a byte.
     * @param signedByte Byte to unsign.
     * @return int of the unsinged byte
     */
    public static int unsignByte(byte signedByte) {
        return (int) signedByte & 0xff;
    }

    /**
     * Gets the unsigned value of a short.
     * @param signedShort short to unsign;
     * @return int of the unsigned short
     */
    public static int unsignShort(short signedShort) {
        return (int) signedShort & 0xffff;
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
    public static short bytesToAddress(byte low, byte high){
        int address = Util.unsignByte(high);
        address = address << 8;
        address = address | Util.unsignByte(low);
        return (short) address;
    }

    /**
     * Turns a bcd encoded byte into a "normal" byte.
     * @param bcdNumber bcd encoded byte.
     * @return "normalized" byte.
     */
    public static byte bcdToDec(byte bcdNumber){
        int low = bcdNumber & 0xf;
        int high = (bcdNumber & 0xf0)>>4;
        return (byte) ((high * 10) + low);
    }

    /**
     * Turn a "normal" number into a bcd encoded number.
     * @param number "normal" number.
     * @return bcd encuded number
     */
    public static byte decToBcd(byte number){
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
    public static boolean areLikeSigned(byte one, byte two){
        return (one >> 7) == (two >> 7);
    }
}
