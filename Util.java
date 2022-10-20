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
        int number = signedByte;
        return number & 0xff;
    }
}
