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
     * Gets the unsigned value of a byte.
     * @param signedShort short to unsign;
     * @return int of the unsigned short
     */
    public static int unsignShort(short signedShort) {
        return (int) signedShort & 0xffff;
    }
}
