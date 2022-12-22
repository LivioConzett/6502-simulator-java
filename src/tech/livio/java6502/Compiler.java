package tech.livio.java6502;

import java.util.Map;

/**
 * Compiles 6502 assembly code
 */
public class Compiler {

    private CompilerCallBack statusChangeCallback;
    private CompilerStatus status;

    public Compiler(){
        this.statusChangeCallback = c -> {

            if(c.hasError()){
                System.out.println(c.errorType().toString());
                System.out.println("line: "+c.getLine());
            }
            System.out.println(c.details());
        };

        this.status = new CompilerStatus("Initialization of Compiler");
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
}
