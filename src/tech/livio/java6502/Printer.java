package tech.livio.java6502;

/**
 * Use for printing out memory
 */
public class Printer {

    private Memory memory;
    private static final int ROW_WIDTH = 16;

    Printer(Memory memory){
        this.memory = memory;
    }


    /**
     * Returns the content of the memory as a hexdump String.
     * @return String of the hexdump
     */
    String hexDumpMem(){

        byte[] memoryArray = this.memory.getMemoryRange((short)0x0000,(short)0xffff);

        StringBuilder hexDump = new StringBuilder();
        hexDump.append("       01   23   45   67   89   ab   cd   ef\n");

        StringBuilder hexCode;
        StringBuilder ascii;

        String lastLine = "";
        boolean dotWrote = false;

        // length minus ROW_WIDTH so that the last line can always be printed.
        for(int i = 0; i < memoryArray.length - ROW_WIDTH; i += ROW_WIDTH){

            hexCode = getHexRow(memoryArray, i, ROW_WIDTH);
            ascii = getAsciiRow(memoryArray, i, ROW_WIDTH);

            if(lastLine.equals(hexCode.toString())){
                if(!dotWrote) hexDump.append("  .\n");
                dotWrote = true;

            } else{
                dotWrote = false;
                lastLine = hexCode.toString();
                hexDump.append(Util.hexToString((short)i));
                hexDump.append(" ");
                hexDump.append(hexCode);
                hexDump.append(ascii);
            }
        }
        // Print the last line
        hexDump.append(Util.hexToString((short)(memoryArray.length - ROW_WIDTH)));
        hexDump.append(" ");
        hexDump.append(getHexRow(memoryArray,memoryArray.length-ROW_WIDTH,ROW_WIDTH));
        hexDump.append(getAsciiRow(memoryArray,memoryArray.length-ROW_WIDTH,ROW_WIDTH));

        return hexDump.toString();
    }

    /**
     * Creates a StringBuilder object from a row of numbers in an array
     * @param array array to build from
     * @param start where to start in array
     * @param amount how many bytes to add
     * @return StringBuilder object
     */
    private StringBuilder getHexRow(byte[] array, int start, int amount){
        StringBuilder row = new StringBuilder();

        for(int i = start; i < (start + amount); i++){
            if(i % 2 == 0){
                row.append(" ");
            }
            row.append(Util.hexToString(array[i]));
        }
        return row;
    }

    /**
     * Creates a StringBuilder object from a row of numbers in an array
     * @param array array to build from
     * @param start where to start in array
     * @param amount how many bytes to add
     * @return StringBuilder object
     */
    private StringBuilder getAsciiRow(byte[] array, int start, int amount){
        StringBuilder row = new StringBuilder();
        row.append("  |");
        for(int i = start; i < (start + amount); i++){
            if(array[i] < 0x20 || array[i] > 0x7e){
                row.append(".");
            } else {
                row.append((char)array[i]);
            }
        }
        row.append("|\n");
        return row;
    }

}
