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
    private CompilerStatus status;
    private List<Byte> opList;
    private final String regComment = " *;.*";
    private final String regVariable = "^[\\w\\d]+ *= *.+";
    private final String regHex = "\\$[a-fA-F\\d]+";
    private final String regOct = "@\\d+";
    private final String regBin = "%[01]+";
    private final String regAscii = "(\"((?!\").|\\n)*\")|('((?!').|\\n)*')";


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

            // hex code
            Matcher m = Pattern.compile(regHex).matcher(code[i]);
            if(m.find()){
                code[i] = code[i].replaceAll(regHex,Util.codeNumberToDec(m.group(0),16));
            }

            // Oct Code
            m = Pattern.compile(regOct).matcher(code[i]);
            if(m.find()){
                code[i] = code[i].replaceAll(regOct,Util.codeNumberToDec(m.group(0),8));
            }

            // Oct Code
            m = Pattern.compile(regBin).matcher(code[i]);
            if(m.find()){
                code[i] = code[i].replaceAll(regBin,Util.codeNumberToDec(m.group(0),2));
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

}
