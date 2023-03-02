/*
Copyright (c) 2023, Livio Conzett
All rights reserved.

This source code is licensed under the BSD-style license found in the
LICENSE file in the root directory of this source tree.
*/

package tech.livio.java6502;

/**
 * The compiler status.<br>
 * Is returned by the compiler.
 */
public class CompilerStatus {

    private final int line;
    private final CompErrType errorType;
    private final String text;

    /**
     * Constructor for the Compiler Status
     * @param line line that error occurred on. If no error just write 0
     * @param errorType type of error that occurred. If no error then just set it to CompErrType.NONE
     * @param text String describing. Status that the Compiler has.
     */
    CompilerStatus(int line, CompErrType errorType, String text){
        this.line = line;
        this.errorType = errorType;
        this.text = text;
    }

    /**
     * Only set the text of the Compiler Status.<br>
     * Used if no error has occurred.
     * @param text Stratus of the compiler.
     */
    CompilerStatus(String text){
        this(0,CompErrType.NONE,text);
    }

    /**
     * Does the Compiler have an error.
     * @return true if error has occurred
     */
    public boolean hasError(){
        return this.errorType != CompErrType.NONE;
    }

    /**
     * What type of error has occurred.
     * @return CompErrTpye.NONE if no error has occurred.
     */
    public CompErrType errorType(){
        return this.errorType;
    }

    /**
     * Gets the details of the status.
     * @return String with more information over the compiler status
     */
    public String details(){
        return this.text;
    }

    /**
     * Gives out the line an error has occurred on.
     * @return 0 if no error has occurred.
     */
    public int getLine(){
        return this.line;
    }

}
