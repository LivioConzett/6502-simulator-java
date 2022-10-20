/**
 * Main class to use the Sim6502 class
 */
public class Main {

    public static void main(String[] args){
        System.out.println("hoi");

        byte test = (byte) 126;
        System.out.println(Util.unsignByte(test));
        test+=10;
        System.out.println(Util.unsignByte(test));

    }
}
