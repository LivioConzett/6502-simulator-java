package tech.livio.java6502;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Compiles 6502 assembly code
 */
public class Compiler {

    private CompilerCallBack statusChangeCallback;
    private CompilerStatus status;
    private List<Byte> opList;
    private final String regComment = " *;.*";

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
        String[] noComments = removeComments(codeArray);





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

}
