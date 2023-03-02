/*
Copyright (c) 2023, Livio Conzett
All rights reserved.

This source code is licensed under the BSD-style license found in the
LICENSE file in the root directory of this source tree.
*/

package tech.livio.java6502;

/**
 * Enum with the compiler error types
 */
public enum CompErrType {

    NONE("no error"),
    SYNTAX("--- SYNTAX ERROR ---"),
    SIZE("--- SIZE ERROR ---"),
    LOGIC("--- LOGIC ERROR ---"),
    LABEL("--- LABEL ERROR ---"),
    INTERNAL("--- INTERNAL ERROR ---");


    private final String errorString;

    CompErrType(String errorString){
        this.errorString = errorString;
    }

    @Override
    public String toString(){
        return this.errorString;
    }

}
