package tech.livio.java6502;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<Byte> opList;
    private Map<String, String> labelToAddress;
    private final String regComment = " *;.*";
    private final String regVariable = "^[\\w\\d]+ *= *.+";
    private final String regHex = "(?<!')\\$[a-fA-F\\d]+";
    private final String regDec = "(?<![@%$'])\\b\\d+\\b";
    private final String regOct = "(?<!')@\\d+";
    private final String regBin = "(?<!')%[01]+";
    private final String regChar = "'.'";
    private final String regAscii = "(\"((?!\").|\\n)*\")|('((?!').|\\n)*')";
    private final String regLabel = "^[\\w\\d]+: *";
    private final String regOpCode = "  [\\w]{3}\\b";
    private final String regAmImmediate = "^#\\$[a-f\\d]{2}$";
    private final String regAmAbsolute = "^\\$[a-f\\d]{4}$";
    private final String regAmZeroPage = "^\\$[a-f\\d]{2}$";
    private final String regAmIndirectAbsolute = "^\\(\\$[a-f\\d]{4}\\)$";
    private final String regAmAbsoluteIndexedX = "^\\$[a-f\\d]{4},? *[Xx]$";
    private final String regAmAbsoluteIndexedY = "^\\$[a-f\\d]{4},? *[Yy]$";
    private final String regAmZeroPageIndexedX = "^\\$[a-f\\d]{2},? *[Xx]$";
    private final String regAmZeroPageIndexedY = "^\\$[a-f\\d]{2},? *[Yy]$";
    private final String regAmIndexedIndirect = "^\\(\\$[a-f\\d]{2},? *[Xx]\\)$";
    private final String regAmIndirectIndexed = "^\\(\\$[a-f\\d]{2}\\),? *[Yy]";
    private final String regAmAccumulator = "^[Aa]$";
    private final String regAmImplied = "^[\\W\\n]*$";


    public Compiler(){
        this.statusChangeCallback = c -> {

            if(c.hasError()){
                System.out.println(c.errorType().toString());
                System.out.println("line: "+c.getLine());
            }
            System.out.print(c.details());
        };

        this.status = new CompilerStatus("Initialization of Compiler\n");
        this.opList = new ArrayList<>();
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
    private void reset(){
        this.opList.clear();
        this.opArrayPointer = 0;
        this.line = 0;
        this.labelToAddress.clear();

    }

    /**
     * Turns the opList into a byteArray
     * @return Array of bytes
     */
    private byte[] listToArray(){
        int length = this.opList.size();
        byte[] opArray = new byte[length];

        for(int i = 0; i < length; i++){
            opArray[i] = this.opList.get(i);
        }
        return opArray;
    }

    /**
     * Takes a string and compiles it.
     * @param code 6502 assembly code to compile
     */
    byte[] compile(String code){
        this.reset();

        changeStatus(new CompilerStatus("-- Compiler starting --"));

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

            // handle the labels
            Matcher m = Pattern.compile(regLabel).matcher(codeArray[this.line]);
            if(m.find()){
                if(!this.saveLabel(m.group(0))) return new byte[0];
                codeArray[this.line] = codeArray[this.line].replaceAll(this.regLabel,"");
            }

            // handle the opcodes
            if(Pattern.matches(this.regOpCode,codeArray[this.line])){
                String newLine = this.handelOpCode(codeArray[this.line]);
                if(newLine.equals("")) return new byte[0];

                codeArray[this.line] = newLine;
            }







        }

        this.changeStatus(new CompilerStatus("done\nCompiler finished with no errors."));

        return this.listToArray();
    }

    /**
     * Remove the comments from the code
     * @param code code to remove the comments from
     * @return code without comments
     */
    String[] removeComments(String[] code){

        changeStatus(new CompilerStatus("Removing comments..."));

        for(int lineNumber = 0; lineNumber < code.length; lineNumber++){
            code[lineNumber] = code[lineNumber].replaceAll(regComment,"");
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
            if(Pattern.matches(regVariable,line)){
                String[] split = line.split("=");
                variables.put(split[0].trim(),split[1].trim());
            }
        }

        // go through the array again to remove the var declarations
        // and put the value of the var into the instances of them
        for(int i = 0; i < code.length; i++) {
            // remove the
            code[i] = code[i].replaceAll(regVariable,"");

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
            Matcher m = Pattern.compile(regHex).matcher(code[i]);
            if(m.find()){
                code[i] = code[i].replace(m.group(0),Util.codeNumberToHex(m.group(0),16));
            }

            // Dec code
            m = Pattern.compile(regDec).matcher(code[i]);
            if(m.find()){
                code[i] = code[i].replace(m.group(0),Util.codeNumberToHex(m.group(0),10));
            }

            // Oct Code
            m = Pattern.compile(regOct).matcher(code[i]);
            if(m.find()){
                code[i] = code[i].replace(m.group(0),Util.codeNumberToHex(m.group(0),8));
            }

            // Bin Code
            m = Pattern.compile(regBin).matcher(code[i]);
            if(m.find()){
                code[i] = code[i].replace(m.group(0),Util.codeNumberToHex(m.group(0),2));
            }

            // char Code
            m = Pattern.compile(regChar).matcher(code[i]);
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

        Pattern p = Pattern.compile(regAscii);


        for(int i = 0; i < code.length; i++){

            Matcher m = p.matcher(code[i]);

            if(m.find()){
                code[i] = code[i].replaceAll(regAscii,Util.asciiToHex(m.group(0)));
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
     * @return String of the hex codes
     */
    String handelOpCode(String line){
        String[] lineArray = line.trim().split(" ");
        String opCode = lineArray[0];
        String values = lineArray[1];

        // check if the op code exists
        if(!OpToHex.opCodeExists(opCode)){
            this.changeStatus(new CompilerStatus(
                    this.line,
                    CompErrType.SYNTAX,
                    "Op Code: '" + opCode + "' doesn't exist."
            ));
            return "";
        }

        AddressingModes mode = getAddressingMode(values);

        if(mode.equals(AddressingModes.NONE)){
            changeStatus(new CompilerStatus(
                    this.line,
                    CompErrType.SYNTAX,
                    values + "not a valid addressing mode."
            ));
            return "";
        }

        Byte byteOpCode = OpToHex.getHex(Util.stringToOpCodes(opCode),mode);

        if(byteOpCode == null){
             changeStatus(new CompilerStatus(
                     this.line,
                     CompErrType.SYNTAX,
                     "Op Code: '" + opCode + "' does not have this addressing mode."
             ));
             return "";
        }

        // remove all the unused stuff from the values
        values = values.replaceAll("[$#(),YyXxAa ]","").trim();

        return "" + Util.hexToString(byteOpCode) + " " + Util.formatFour(values);
    }

    /**
     * Get the addressing mode from a value
     * @param value value to check wha kind of addressing mode it is
     * @return addressing mode for the value
     */
    AddressingModes getAddressingMode(String value){

        value = value.trim().toLowerCase();

        if(Pattern.matches(regAmImmediate,value)) return AddressingModes.IMMEDIATE;
        if(Pattern.matches(regAmAbsolute,value)) return AddressingModes.ABSOLUTE;
        if(Pattern.matches(regAmZeroPage,value)) return AddressingModes.ZERO_PAGE;
        if(Pattern.matches(regAmIndirectAbsolute,value)) return AddressingModes.INDIRECT_ABSOLUTE;
        if(Pattern.matches(regAmAbsoluteIndexedX,value)) return AddressingModes.ABSOLUTE_INDEXED_X;
        if(Pattern.matches(regAmAbsoluteIndexedY,value)) return AddressingModes.ABSOLUTE_INDEXED_Y;
        if(Pattern.matches(regAmZeroPageIndexedX,value)) return AddressingModes.ZERO_PAGE_INDEXED_X;
        if(Pattern.matches(regAmZeroPageIndexedY,value)) return AddressingModes.ZERO_PAGE_INDEXED_Y;
        if(Pattern.matches(regAmIndexedIndirect,value)) return AddressingModes.INDEXED_INDIRECT;
        if(Pattern.matches(regAmIndirectIndexed,value)) return AddressingModes.INDIRECT_INDEXED;
        if(Pattern.matches(regAmAccumulator,value)) return AddressingModes.ACCUMULATOR;
        if(Pattern.matches(regAmImplied,value)) return AddressingModes.IMPLIED;

        return AddressingModes.NONE;
    }

}
