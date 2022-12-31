package tech.livio.java6502;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Compiles 6502 assembly code
 */
public class Compiler {

    private CompilerCallBack statusChangeCallback;
    private int opArrayPointer;
    private int line;
    private CompilerStatus status;
    private byte[] opList;
    private static final int MEM_SIZE = 0xffff;
    private final Map<String, String> labelToAddress;
    private static final String REG_COMMENT = " *;.*";
    private static final String REG_VARIABLE = "^[\\w\\d]+ *= *.+";
    private static final String REG_HEX = "(?<!')\\$[a-fA-F\\d]+";
    private static final String REG_DEC = "(?<![@%$'])\\b\\d+\\b";
    private static final String REG_OCT = "(?<!')@\\d+";
    private static final String REG_BIN = "(?<!')%[01]+";
    private static final String REG_CHAR = "'.'";
    private static final String REG_ASCII = "(\"((?!\").|\\n)*\")|('((?!').|\\n)*')";
    private static final String REG_LABEL = "^[\\w\\d]+: *";
    private static final String REG_OP_CODE = "  [\\w]{3}\\b";
    private static final String REG_AM_IMMEDIATE = "^#\\$[a-f\\d]{2}$";
    private static final String REG_AM_ABSOLUTE = "^\\$[a-f\\d]{4}$";
    private static final String REG_AM_ZERO_PAGE = "^\\$[a-f\\d]{2}$";
    private static final String REG_AM_INDIRECT_ABSOLUTE = "^\\(\\$[a-f\\d]{4}\\)$";
    private static final String REG_AM_ABSOLUTE_INDEXED_X = "^\\$[a-f\\d]{4},? *[Xx]$";
    private static final String REG_AM_ABSOLUTE_INDEXED_Y = "^\\$[a-f\\d]{4},? *[Yy]$";
    private static final String REG_AM_ZERO_PAGE_INDEXED_X = "^\\$[a-f\\d]{2},? *[Xx]$";
    private static final String REG_AM_ZERO_PAGE_INDEXED_Y = "^\\$[a-f\\d]{2},? *[Yy]$";
    private static final String REG_AM_INDEXED_INDIRECT = "^\\(\\$[a-f\\d]{2},? *[Xx]\\)$";
    private static final String REG_AM_INDIRECT_INDEXED = "^\\(\\$[a-f\\d]{2}\\),? *[Yy]";
    private static final String REG_AM_ACCUMULATOR = "^[Aa]$";
    private static final String REG_AM_IMPLIED = "^[\\W\\n]*$";
    private static final String REG_DOT_WORD = "\\.\\w+";


    public Compiler(){
        this.statusChangeCallback = c -> {

            if(c.hasError()){
                System.out.println(c.errorType().toString());
                System.out.println("line: "+c.getLine());
            }
            System.out.print(c.details());
        };

        this.status = new CompilerStatus("Initialization of Compiler\n");
        this.opList = new byte[MEM_SIZE];
        this.opArrayPointer = 0;
        this.line = 0;
        this.labelToAddress = new HashMap<>();

    }

    /**
     * Set the Callback for the compiler Status change
     * @param compilerCallBack callback to run when status changes.
     */
    public void setCallback(CompilerCallBack compilerCallBack){
        this.statusChangeCallback = compilerCallBack;
    }

    /**
     * Changes the status of the compiler.<br>
     * Calles the callback for the status change.
     * @param compilerStatus status of the compiler.
     */
    private void changeStatus(CompilerStatus compilerStatus){
        this.status = compilerStatus;
        this.statusChangeCallback.run(compilerStatus);
    }

    /**
     * Resets the compiler
     */
    void reset(){
        this.opList = new byte[MEM_SIZE];
        this.opArrayPointer = 0;
        this.line = 0;
        this.labelToAddress.clear();
    }

    /**
     * Get the opArraypointer
     * @return opArray Pointer
     */
    int getOpArrayPointer(){
        return this.opArrayPointer;
    }

    /**
     * Gets the compiler status.
     * @return status of the compiler
     */
    CompilerStatus getCompilerStatus(){
        return this.status;
    }

    /**
     * Turns the opList into a byteArray
     * @return Array of bytes
     */
    private byte[] listToArray(){
        byte[] opArray = new byte[MEM_SIZE];

        for(int i = 0; i < MEM_SIZE; i++){
            opArray[i] = this.opList[i];
        }
        return opArray;
    }

    /**
     * Takes a string and compiles it.
     * @param code 6502 assembly code to compile
     */
    byte[] compile(String code){
        this.reset();

        changeStatus(new CompilerStatus("-- Compiler starting --\n"));

        String[] codeArray = code.split("\s");

        // remove the comments from the code
        codeArray = removeComments(codeArray);

        // convert the numbers to dec
        codeArray = convertNumbers(codeArray);

        // replace the variables
        codeArray = replaceVariables(codeArray);

        // convert the strings to hex codes
        codeArray = convertString(codeArray);

        this.changeStatus(new CompilerStatus("Compiling Op codes..."));

        for(this.line = 0;  this.line < codeArray.length; this.line++){

            // check if the pointer is still within the memory size
            if(this.opArrayPointer < MEM_SIZE){
                this.changeStatus(new CompilerStatus(
                        this.line,
                        CompErrType.SIZE,
                        "Trying to write outside the " + MEM_SIZE + " bytes of available size.\n"
                ));
                return new byte[0];
            }

            // handle the labels
            Matcher m = Pattern.compile(REG_LABEL).matcher(codeArray[this.line]);
            if(m.find()){
                if(!this.saveLabel(m.group(0))) return new byte[0];
                codeArray[this.line] = codeArray[this.line].replaceAll(REG_LABEL,"");
            }

            // handle the opcodes
            if(Pattern.matches(REG_OP_CODE,codeArray[this.line])){
                if(!this.handelOpCode(codeArray[this.line])) return new byte[0];
            }

            // handel the dot words
            if(Pattern.matches(REG_DOT_WORD,codeArray[this.line])){
                if(!this.handelDotWords(codeArray[this.line])) return new byte[0];
            }




        }

        this.changeStatus(new CompilerStatus("done\nCompiler finished with no errors.\n"));

        return this.listToArray();
    }

    /**
     * Add a value to the opArray
     * @param value String of the value to add
     */
    private boolean addToOpArray(String value){

        if(Util.hexStringToInt(value) > 0xff){
            this.changeStatus(new CompilerStatus(
                    this.line,
                    CompErrType.LOGIC,
                    "'" + value + "' is larger than one byte."
            ));
            return false;
        }

        this.opList[this.opArrayPointer] = Util.hexStringToByte(value);
        this.opArrayPointer ++;
        return true;
    }

    /**
     * Remove the comments from the code
     * @param code code to remove the comments from
     * @return code without comments
     */
    String[] removeComments(String[] code){

        changeStatus(new CompilerStatus("Removing comments..."));

        for(int lineNumber = 0; lineNumber < code.length; lineNumber++){
            code[lineNumber] = code[lineNumber].replaceAll(REG_COMMENT,"");
        }

        changeStatus(new CompilerStatus("done.\n"));

        return code;
    }

    /**
     * Removes the variables with the corresponding values
     * @param code code to replace the variables in
     * @return code with replaced variables
     */
    String[] replaceVariables(String[] code){

        changeStatus(new CompilerStatus("Replacing variables..."));

        Map<String,String> variables = new HashMap<>();

        // create the map of variables and their value
        for(String line: code){
            // does the line have a variable declared
            if(Pattern.matches(REG_VARIABLE,line)){
                String[] split = line.split("=");
                variables.put(split[0].trim(),split[1].trim());
            }
        }

        // go through the array again to remove the var declarations
        // and put the value of the var into the instances of them
        for(int i = 0; i < code.length; i++) {
            // remove the
            code[i] = code[i].replaceAll(REG_VARIABLE,"");

            // go through the variable map and see if one matches
            for(Map.Entry<String,String> entry: variables.entrySet()){
                // the stuff at the end ist to make sure the var is not replaced if it is in a string
                String regex = "\\b"+entry.getKey()+"\\b(?=([^']*'[^']*')*[^']*$)";
                code[i] = code[i].replaceAll(regex,entry.getValue());
            }
        }

        changeStatus(new CompilerStatus("done.\n"));
        return code;
    }

    /**
     * Convert the numbers from whatever to dec
     * @param code code to convert numbers in
     * @return code with the numbers converted
     */
    String[] convertNumbers(String[] code){
        changeStatus(new CompilerStatus("Converting numbers..."));

        for(int i = 0; i < code.length; i++){

            // Hex code
            Matcher m = Pattern.compile(REG_HEX).matcher(code[i]);
            if(m.find()){
                code[i] = code[i].replace(m.group(0),Util.codeNumberToHex(m.group(0),16));
            }

            // Dec code
            m = Pattern.compile(REG_DEC).matcher(code[i]);
            if(m.find()){
                code[i] = code[i].replace(m.group(0),Util.codeNumberToHex(m.group(0),10));
            }

            // Oct Code
            m = Pattern.compile(REG_OCT).matcher(code[i]);
            if(m.find()){
                code[i] = code[i].replace(m.group(0),Util.codeNumberToHex(m.group(0),8));
            }

            // Bin Code
            m = Pattern.compile(REG_BIN).matcher(code[i]);
            if(m.find()){
                code[i] = code[i].replace(m.group(0),Util.codeNumberToHex(m.group(0),2));
            }

            // char Code
            m = Pattern.compile(REG_CHAR).matcher(code[i]);
            if(m.find()){
                code[i] = code[i].replace(m.group(0),"$"+Util.asciiToHex(m.group(0)));
            }
        }

        changeStatus(new CompilerStatus("done.\n"));
        return code;
    }

    /**
     * Convert all the Strings into hex numbers
     * @return code with strings replaced with hex codes
     */
    String[] convertString(String[] code){
        changeStatus(new CompilerStatus("Converting Strings..."));

        Pattern p = Pattern.compile(REG_ASCII);


        for(int i = 0; i < code.length; i++){

            Matcher m = p.matcher(code[i]);

            if(m.find()){
                code[i] = code[i].replaceAll(REG_ASCII,Util.asciiToHex(m.group(0)));
            }
        }

        changeStatus(new CompilerStatus("done.\n"));
        return code;
    }

    /**
     * Saves the label in the LabelToAddress map. Will use the address from the opArrayPointer.
     * @param label label to save
     */
    boolean saveLabel(String label){
        label = label.replace(":","").trim();

        // check if the label already exists.
        if(this.labelToAddress.get(label) == null){
            this.changeStatus(new CompilerStatus(
                    this.line,
                    CompErrType.LABEL,
                    "Label '"+label+"' already exists."
            ));
        }

        this.labelToAddress.put(
                label,
                Util.intToAddressString(this.opArrayPointer)
                );

        return true;
    }

    /**
     * Convert the code into the correct hex code.
     * @param line line of code to convert
     * @return false if error occurred
     */
    boolean handelOpCode(String line){
        String[] lineArray = line.trim().split(" ");
        String opCode = lineArray[0];
        String values = lineArray[1];

        // check if the op code exists
        if(!OpToHex.opCodeExists(opCode)){
            this.changeStatus(new CompilerStatus(
                    this.line,
                    CompErrType.SYNTAX,
                    "Op Code: '" + opCode + "' doesn't exist.\n"
            ));
            return false;
        }

        AddressingModes mode = getAddressingMode(values);

        if(mode.equals(AddressingModes.NONE)){
            changeStatus(new CompilerStatus(
                    this.line,
                    CompErrType.SYNTAX,
                    values + "not a valid addressing mode.\n"
            ));
            return false;
        }

        Byte byteOpCode = OpToHex.getHex(Util.stringToOpCodes(opCode),mode);

        if(byteOpCode == null){
             changeStatus(new CompilerStatus(
                     this.line,
                     CompErrType.SYNTAX,
                     "Op Code: '" + opCode + "' does not have this addressing mode.\n"
             ));
             return false;
        }

        // remove all the unused stuff from the values
        values = values.replaceAll("[$#(),YyXxAa ]","").trim();

        String code = "" + Util.hexToString(byteOpCode) + " " + Util.formatFour(values);

        for(String hexString: code.split(" ")) {
            if(!this.addToOpArray(hexString)) return false;
        }

        return true;
    }

    /**
     * Get the addressing mode from a value
     * @param value value to check wha kind of addressing mode it is
     * @return addressing mode for the value
     */
    AddressingModes getAddressingMode(String value){

        value = value.trim().toLowerCase();

        if(Pattern.matches(REG_AM_IMMEDIATE,value)) return AddressingModes.IMMEDIATE;
        if(Pattern.matches(REG_AM_ABSOLUTE,value)) return AddressingModes.ABSOLUTE;
        if(Pattern.matches(REG_AM_ZERO_PAGE,value)) return AddressingModes.ZERO_PAGE;
        if(Pattern.matches(REG_AM_INDIRECT_ABSOLUTE,value)) return AddressingModes.INDIRECT_ABSOLUTE;
        if(Pattern.matches(REG_AM_ABSOLUTE_INDEXED_X,value)) return AddressingModes.ABSOLUTE_INDEXED_X;
        if(Pattern.matches(REG_AM_ABSOLUTE_INDEXED_Y,value)) return AddressingModes.ABSOLUTE_INDEXED_Y;
        if(Pattern.matches(REG_AM_ZERO_PAGE_INDEXED_X,value)) return AddressingModes.ZERO_PAGE_INDEXED_X;
        if(Pattern.matches(REG_AM_ZERO_PAGE_INDEXED_Y,value)) return AddressingModes.ZERO_PAGE_INDEXED_Y;
        if(Pattern.matches(REG_AM_INDEXED_INDIRECT,value)) return AddressingModes.INDEXED_INDIRECT;
        if(Pattern.matches(REG_AM_INDIRECT_INDEXED,value)) return AddressingModes.INDIRECT_INDEXED;
        if(Pattern.matches(REG_AM_ACCUMULATOR,value)) return AddressingModes.ACCUMULATOR;
        if(Pattern.matches(REG_AM_IMPLIED,value)) return AddressingModes.IMPLIED;

        return AddressingModes.NONE;
    }

    /**
     * Handle the dot words
     * @param line line where the dot word occurred
     * @return true if no error has occurred
     */
    boolean handelDotWords(String line){
        Matcher m = Pattern.compile(REG_DOT_WORD).matcher(line);
        m.find();
        String dotWord = m.group(0).toLowerCase();

        String value = line.replace(dotWord,"").trim();

        // handle the .org word
        switch (dotWord){
            case ".org" -> {
                if(!this.orgWord(value)) return false;
            }
            case ".byte" -> {
                if(!this.byteWord(value)) return false;
            }
            default -> {
                this.changeStatus(new CompilerStatus(
                        this.line,
                        CompErrType.SYNTAX,
                        "'" + dotWord + "' is not a valid command word.\n"
                ));
            }
        }

        return true;
    }

    /**
     * Handel the .ORG code word
     * @param value value following the .ORG command
     * @return false if error occurred
     */
    boolean orgWord(String value) {
        Matcher hex = Pattern.compile(REG_HEX).matcher(value);
        if(!hex.find()) {
            this.changeStatus(new CompilerStatus(
                    this.line,
                    CompErrType.SYNTAX,
                    "'" + value + "' not a correct value for the .org command.\n"
            ));
            return false;
        }

        int origin = Util.hexStringToInt(value);

        if(origin < this.opArrayPointer) {
            this.changeStatus(new CompilerStatus(
                    this.line,
                    CompErrType.SIZE,
                    "'.org " + value + "' is smaller than memory already written: " + this.opArrayPointer + "\n"
            ));
            return false;
        }

        if(origin >= MEM_SIZE) {
            this.changeStatus(new CompilerStatus(
                    this.line,
                    CompErrType.SIZE,
                    "'.org " + value + "' is greater than total memory: " + MEM_SIZE + "\n"
            ));
        }

        this.opArrayPointer = origin;
        return true;
    }

    /**
     * Handle the .BYTE code word
     * @param value value following the .BYTE command
     * @return false if error occurred
     */
    boolean byteWord(String value){

        Matcher hex = Pattern.compile(REG_HEX).matcher(value);
        if(!hex.find()) {
            this.changeStatus(new CompilerStatus(
                    this.line,
                    CompErrType.SYNTAX,
                    "'" + value + "' not a correct value for the .byte command.\n"
            ));
            return false;
        }

        int data = Util.hexStringToInt(value);

        if(data > 0xff){
            this.changeStatus(new CompilerStatus(
                    this.line,
                    CompErrType.LOGIC,
                    "'" + value + "' too large for one byte.\n"
            ));
            return false;
        }

        return this.addToOpArray(value);
    }

}
