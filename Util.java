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
}
