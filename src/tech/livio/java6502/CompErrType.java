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
